import os
import json
import time
import redis
from dotenv import load_dotenv
from linking.chroma_retriever import retrieve_doc_parent

from langchain_community.chat_models import ChatOpenAI
from langchain_community.embeddings import OpenAIEmbeddings
from langchain_community.vectorstores import Redis as RedisVectorStore

from utilities.utilities import (
    log, clean_llm_output, save_response, initialize_system, find_name_by_page_content, retrieve_previous_results, clean_java_comment
)
from linking.prompts.prompt_builder import final_prompt_RQ1_H1, final_prompt_RQ1_H2, final_prompt_RQ1_H3, final_prompt_RQ1_H4

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
    calls_count = 0  # Compteur appels Cohere

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
                        # Gestion rate limit : pause 6s entre appels Cohere après le premier
                        #if calls_count > 0:
                        #    time.sleep(6)

                        results = retrieve_doc_parent(index_name, final_prompt_vector)
                        #calls_count += 1

                        reqs = []
                        for req in results:
                            filename = os.path.basename(req.metadata.get("source", "unknown.txt"))
                            reqs.append({
                                "name": filename,
                                "content": req.page_content
                            })
                        if reqs:
                            prompt = final_prompt_RQ1_H1(
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
        links_text = "\n".join(links)
        output_dir = os.path.dirname(output_file)
        if not os.path.exists(output_dir):
            os.makedirs(output_dir)
        save_response(links_text, output_file)
    except Exception as e:
        log(f"❌ Error saving results: {str(e)}", level="ERROR")

def with_code_comments(code_folder: str, output_file: str, index_name: str, file_result: str):
    """RQ1/Hypothesis 2 : Use the comments found in code,
    if not retrieve the same requirements as the previous Hypothesis (with_only_code)"""

    try:
        model, embeddings, redis_client, vector_store = initialize_system(index_name)
        log("linking_with_code_comments", level="INFO")
    except Exception as e:
        log(f"❌ Error initializing system: {str(e)}", level="ERROR")
        return

    prompt_method = "{method_name}:\n{method_comment}\n"
    prompt_constructor = "{constructor_name}:\n{constructor_comment}\n"
    links = []
    calls_count = 0

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

            if not isinstance(data, list):
                continue

            for entry in data:
                final_prompt_vector = f"Class: {entry.get('signature', 'UnknownClass')}\n"
                methods_with_comments = False
                constructors_with_comments = False

                final_prompt_vector += "Methods:\n"
                for method in entry.get("methods", []):
                    method_comment = ""

                    if method.get("hasInnerComment", False):
                        inner_comments = method.get("innerComments", "")
                        if inner_comments and inner_comments != "Optional[[]]":
                            inner_comments = inner_comments.replace("Optional[[", "").replace("]]", "")
                            method_comment += clean_java_comment("\n".join(inner_comments.split(", ")))
                            methods_with_comments = True

                    if method.get("hasComment", False):
                        comments = method.get("comments", "")
                        if comments:
                            if method_comment:
                                method_comment += "\n"
                            method_comment += clean_java_comment(comments)
                            methods_with_comments = True

                    final_prompt_vector += prompt_method.format(
                        method_name=method.get("signature", "UnknownMethod"),
                        method_comment=method_comment
                    )

                final_prompt_vector += "Constructors:\n"
                for constructor in entry.get("constructors", []):
                    constructor_comment = ""

                    if constructor.get("hasInnerComment", False):
                        inner_comments = constructor.get("innerComments", "")
                        if inner_comments and inner_comments != "Optional[[]]":
                            inner_comments = inner_comments.replace("Optional[[", "").replace("]]", "")
                            constructor_comment += clean_java_comment("\n".join(inner_comments.split(", ")))
                            constructors_with_comments = True

                    if constructor.get("hasComment", False):
                        comments = constructor.get("comments", "")
                        if comments:
                            if constructor_comment:
                                constructor_comment += "\n"
                            constructor_comment += clean_java_comment(comments)
                            constructors_with_comments = True

                    final_prompt_vector += prompt_constructor.format(
                        constructor_name=constructor.get("signature", "UnknownConstructor"),
                        constructor_comment=constructor_comment
                    )

                try:
                    # Gestion rate limit : pause 6s entre appels Cohere après le premier
                        #if calls_count > 0:
                        #    time.sleep(6)

                    results = retrieve_doc_parent(index_name, final_prompt_vector)
                        #calls_count += 1

                    reqs = []
                    for req in results:
                        filename = os.path.basename(req.metadata.get("source", "unknown.txt"))
                        reqs.append({
                            "name": filename,
                            "content": req.page_content
                        })

                    prompt = final_prompt_RQ1_H2(
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
                    log(f"❌ Error retrieving or processing LLM for {file_path}: {str(e)}", level="ERROR")

    try:
        links = "\n".join(links)
        output_dir = os.path.dirname(output_file)
        if not os.path.exists(output_dir):
            os.makedirs(output_dir)
        save_response(links, output_file)
    except Exception as e:
        log(f"❌ Error saving results: {str(e)}", level="ERROR")

def with_class_comment(code_folder: str, output_file: str, index_name: str):
    try:
        model, embeddings, redis_client, vector_store = initialize_system(index_name)
        log("linking_with_only_code", level="INFO")
    except Exception as e:
        log(f"❌ Error initializing system: {str(e)}", level="ERROR")
        return
    
    prompt_class = "Class: {class_name}: {class_comment}\n"

    links = []
    calls_count = 0

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
                    # Get class comment
                    class_comment = entry.get("generated_class_comment", "")
                    final_prompt_vector = prompt_class.format(
                        class_name=entry.get("signature", "UnknownClass"),
                        class_comment=class_comment
                    )
                    try:
                        #if calls_count > 0:
                        #    time.sleep(6)

                        results = retrieve_doc_parent(index_name, final_prompt_vector)
                        #calls_count += 1

                        reqs = []
                        for req in results:
                            filename = os.path.basename(req.metadata.get("source", "unknown.txt"))
                            reqs.append({
                                "name": filename,
                                "content": req.page_content
                            })
                        if reqs : 
                            prompt = final_prompt_RQ1_H4(
                                reqs=reqs,
                                prompt_code=final_prompt_vector,
                                class_name=entry.get("signature", "UnknownClass")
                            )
                            print("\n\n PROMPT :", prompt,"\n\n\n")
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
