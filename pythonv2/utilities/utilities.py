import os
from dotenv import load_dotenv
import redis
import getpass
import json
import re
from langchain_openai import OpenAIEmbeddings, ChatOpenAI
from langchain_redis import RedisVectorStore

def log(msg, level="INFO"):
    print(f"[{level}] {msg}")

def clean_llm_output(text: str) -> str:
    if text.startswith("```"):
        text = text.split("\n", 1)[-1]
    if text.endswith("```"):
        text = text[:-3]
    return text.strip()

def save_response(content: str, output_path: str):
    os.makedirs(os.path.dirname(output_path), exist_ok=True)
    with open(output_path, 'a', encoding='utf-8') as f:
        f.write(content + '\n')

def initialize_system(index_name: str):
    """
    Initialise le modèle LLM, le modèle d'embedding, le client Redis,
    et le vector store Redis.

    Args:
        index_name (str): Le nom de l'index Redis pour le vector store.

    Returns:
        model: Le modèle ChatOpenAI (gpt-4o-mini).
        embeddings: Le modèle d'embedding OpenAI (text-embedding-3-large).
        redis_client: Le client Redis standard.
        vector_store: Le vector store RedisVectorStore pour la similarité.
    """

    # Charge les variables du fichier .env
    load_dotenv()

    # Récupère la clé d'API
    openai_key = os.getenv("OPENAI_API_KEY")

    # LLM
    model = ChatOpenAI(model="gpt-4o-mini")

    # Embeddings
    embeddings = OpenAIEmbeddings(model="text-embedding-3-large")

    # Redis client
    redis_client = True,
    vector_store = True,

    return model, embeddings, redis_client, vector_store


def find_name_by_page_content(page_content, redis_client, index_name):
    keys = redis_client.keys(f"{index_name}:*")
    for key in keys:
        stored_content = redis_client.hget(key, "page_content")
        if stored_content == page_content:
            document_name = redis_client.hget(key, "name")
            return document_name
    return None

def lire_lignes_du_fichier(chemin_fichier):
    with open(chemin_fichier, 'r', encoding='utf-8') as fichier:
        lignes = [ligne.strip() for ligne in fichier]
    return lignes

def clean_links(input_file):
    # Read and filter the lines
    with open(input_file, "r") as file:
        lines = file.readlines()
    filtered_lines = [line for line in lines if not line.strip().endswith("-> None")]
    with open(input_file, "w") as file:
        file.writelines(filtered_lines)

    print("Filtered file saved.")

def retrieve_previous_results(class_name: str, file_result: str):
    with open(file_result, 'r', encoding="utf-8") as f:
        lines = f.readlines()
    filtered_lines = [line for line in lines if class_name.lower() in line.lower()]
    return filtered_lines

def clean_java_comment(comment: str) -> str:
    comment = comment.replace("Optional[[", "").replace("]]", "")
    comment_lines = comment.splitlines()
    cleaned_lines = []
    for line in comment_lines:
        line = re.sub(r"^\s*/?\**\s?", "", line)  # Supprime /**, *, //, etc.
        cleaned_lines.append(line.strip())
    return "\n".join(cleaned_lines).strip()
