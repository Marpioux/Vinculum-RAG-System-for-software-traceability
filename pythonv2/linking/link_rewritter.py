import json
import os
import redis

from utilities.utilities import log, clean_llm_output, save_response, initialize_system, find_name_by_page_content

from langchain.chat_models import ChatOpenAI
from langchain.embeddings import OpenAIEmbeddings
from langchain.vectorstores.redis import Redis as RedisVectorStore



def linking_with_only_code(code_folder: str, output_file: str, index_name: str):
    """
    Extracts class structure from code JSON files and retrieves the most similar requirements
    using vector search. This can then be used to construct a traceability matrix.
    """
    model, embeddings, redis_client, vector_store = initialize_system(index_name)
    log("linking_with_only_code", level="INFO")

    # Templates for formatting
    prompt_method = "{method_name} {method_code}\n"
    prompt_constructor = "{constructor_name} {constructor_code}\n"
    prompt_variable = "{variable_name}\n"

    for root, dirs, files in os.walk(code_folder):
        for file in files:
            if file.endswith(".json"):
                file_path = os.path.join(root, file)
                try:
                    with open(file_path, 'r', encoding="utf-8") as f:
                        data = json.load(f)

                    if isinstance(data, list):
                        for entry in data:
                            final_prompt_vector = ""

                            # Class name
                            final_prompt_vector += f"Class: {entry.get('signature', 'UnknownClass')}\n"

                            # Methods
                            final_prompt_vector += "Methods:\n"
                            for method in entry.get("methods", []):
                                final_prompt_vector += prompt_method.format(
                                    method_name=method.get("signature", "UnknownMethod"),
                                    method_code=method.get("body", "")
                                )

                            # Constructors
                            final_prompt_vector += "Constructors:\n"
                            for constructor in entry.get("constructors", []):
                                final_prompt_vector += prompt_constructor.format(
                                    constructor_name=constructor.get("signature", "UnknownConstructor"),
                                    constructor_code=constructor.get("body", "")
                                )

                            # Variables
                            final_prompt_vector += "Variables:\n"
                            for variable in entry.get("variables", []):
                                final_prompt_vector += prompt_variable.format(
                                    variable_name=variable
                                )

                            # Vector store search
                            results = vector_store.similarity_search(query=final_prompt_vector, k=4)
                            reqs = []
                            for req in results :
                                document_name = find_name_by_page_content(req.page_content, redis_client, index_name)
                                reqs.append({"name": document_name, "content": req.page_content})

                            response = model.invoke(f"Here is a Java class {fichier}: {contenu}, and a list of requirements from a RAG: {reqs}. A Java class can correspond to zero or one requirement. Can you analyze the code and the requirements, and link each Java class to its corresponding requirement if there's a match? I want the output in the following format: JavaClassName.java -> RequirementName.txt AND only that no other things")
                            response = clean_llm_output(response.content)
                            save_response_as_file(response)

                except Exception as e:
                    log(f"❌ Error processing {file_path}: {str(e)}", level="ERROR")


linking_with_only_code(
    "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/json/eTour",
    "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Python/linking/traceability matrix/iTrust",
    "eTour"
)