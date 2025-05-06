import os
import json
import redis
from dotenv import load_dotenv

from langchain_community.chat_models import ChatOpenAI
from langchain_community.embeddings import OpenAIEmbeddings
from langchain_community.vectorstores import Redis as RedisVectorStore

from utilities.utilities import (
    log, clean_llm_output, save_response, initialize_system, find_name_by_page_content
)
from prompts.prompt_builder import prompt_rewriter_with_only_code

def linking_with_only_code(code_folder: str, output_file: str, index_name: str):
    """
    Extracts class structure from code JSON files and retrieves the most similar requirements
    using vector search. This can then be used to construct a traceability matrix.
    """
    try:
        model, embeddings, redis_client, vector_store = initialize_system(index_name)
        log("linking_with_only_code", level="INFO")
    except Exception as e:
        log(f"❌ Error initializing system: {str(e)}", level="ERROR")
        return

    prompt_method = "{method_name} {method_code}\n"
    prompt_constructor = "{constructor_name} {constructor_code}\n"
    prompt_variable = "{variable_name}\n"
    prompt_enum = "{enum_name} {enum_description}\n"

    links = []

    for root, _, files in os.walk(code_folder):
        for file in files:
            if not file.endswith(".json"):
                continue

            file_path = os.path.join(root, file)

            try:
                with open(file_path, 'r', encoding="utf-8") as f:
                    data = json.load(f)
            except Exception as e:
                log(f"❌ Error loading JSON file {file_path}: {str(e)}", level="ERROR")
                continue

            try:
                if not isinstance(data, list):
                    continue

                for entry in data:
                    final_prompt_vector = f"Class: {entry.get('signature', 'UnknownClass')}\n"

                    final_prompt_vector += "Methods:\n"
                    for method in entry.get("methods", []):
                        final_prompt_vector += prompt_method.format(
                            method_name=method.get("signature", "UnknownMethod"),
                            method_code=method.get("body", "")
                        )

                    final_prompt_vector += "Constructors:\n"
                    for constructor in entry.get("constructors", []):
                        final_prompt_vector += prompt_constructor.format(
                            constructor_name=constructor.get("signature", "UnknownConstructor"),
                            constructor_code=constructor.get("body", "")
                        )

                    final_prompt_vector += "Variables:\n"
                    for variable in entry.get("variables", []):
                        final_prompt_vector += prompt_variable.format(
                            variable_name=variable
                        )

                    final_prompt_vector += "Enum Constants:\n"
                    for enum in entry.get("enumConstants", []):
                        try:
                            enum_name, enum_desc = enum.split('(')
                            enum_desc = enum_desc.split(')')[0]
                            final_prompt_vector += prompt_enum.format(
                                enum_name=enum_name.strip(),
                                enum_description=enum_desc.strip()
                            )
                        except Exception as e:
                            log(f"⚠️ Malformed enum in {file_path}: {enum}", level="WARNING")

                    try:
                        results = vector_store.similarity_search(
                            query=final_prompt_vector, k=4, with_distance=False
                        )
                        reqs = []
                        for req in results:
                            document_name = find_name_by_page_content(
                                req.page_content, redis_client, index_name
                            )
                            reqs.append({"name": document_name, "content": req.page_content})

                        prompt = prompt_rewriter_with_only_code(
                            reqs=reqs,
                            prompt_code=final_prompt_vector,
                            class_name=entry.get("signature", "UnknownClass")
                        )
                        response = model.invoke(prompt)
                        cleaned = clean_llm_output(response.content)
                        for line in cleaned.splitlines():
                            if line.strip():
                                links.append(line.strip())

                    except Exception as e:
                        log(f"❌ Vector store error in {file_path}: {str(e)}", level="ERROR")
                        continue

            except Exception as e:
                log(f"❌ Error processing data in {file_path}: {str(e)}", level="ERROR")

    try:
        links = "\n".join(links)
        output_dir = os.path.dirname(output_file)
        if not os.path.exists(output_dir):
            os.makedirs(output_dir)
        save_response(links, output_file)
    except Exception as e:
        log(f"❌ Error saving results: {str(e)}", level="ERROR")
