import os
from dotenv import load_dotenv
import redis
import getpass
import json
from langchain_openai import OpenAIEmbeddings, ChatOpenAI
from langchain_community.vectorstores import Redis as RedisVectorStore

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
    redis_client = redis.StrictRedis(
        host='localhost',
        port=6379,
        decode_responses=True
    )

    # Vector store
    vector_store = RedisVectorStore(
        index_name=index_name,
        embedding=embeddings,
        redis_url="redis://localhost:6379",
    )

    return model, embeddings, redis_client, vector_store


def find_name_by_page_content(page_content, redis_client, index_name):
    keys = redis_client.keys(f"{index_name}:*")
    for key in keys:
        stored_content = redis_client.hget(key, "page_content")
        if stored_content == page_content:
            document_name = redis_client.hget(key, "name")
            return document_name
    return None
