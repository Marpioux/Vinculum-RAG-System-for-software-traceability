import getpass
import os
import redis
from langchain_openai import OpenAIEmbeddings
from langchain_redis import RedisConfig, RedisVectorStore
from langchain_core.documents import Document

def create_embedding_from_folder(folder_path: str, index_name: str):
    """
    Crée des embeddings OpenAI à partir des fichiers texte dans un dossier donné
    et les stocke dans Redis sous un index donné.

    :param folder_path: Chemin du dossier contenant les fichiers.
    :param index_name: Nom de l'index Redis pour stocker les embeddings.
    """
    # Validation des paramètres
    if not os.path.isdir(folder_path):
        print(f"[ERROR] The folder path '{folder_path}' is not a valid directory.")
        return

    # Authentification OpenAI
    if not os.environ.get("OPENAI_API_KEY"):
        os.environ["OPENAI_API_KEY"] = getpass.getpass("Enter API key for OpenAI: ")
    os.environ["PYDANTIC_V2"] = "1"

    # Connexion Redis
    try:
        redis_client = redis.StrictRedis(host='localhost', port=6379, decode_responses=True)
        redis_client.ping()  # Vérifie la connexion à Redis
    except redis.ConnectionError as e:
        print(f"[ERROR] Failed to connect to Redis: {e}")
        return

    # Nettoyage de l'index existant
    try:
        keys = redis_client.keys(f"{index_name}:*")
        if keys:
            redis_client.delete(*keys)
            print(f"[INFO] Deleted existing keys: {keys}")
        else:
            print("[INFO] No existing keys to delete.")
    except Exception as e:
        print(f"[ERROR] Failed to clean existing keys: {e}")
        return

    # Chargement du modèle d'embedding
    try:
        embeddings = OpenAIEmbeddings(model="text-embedding-3-large")
    except Exception as e:
        print(f"[ERROR] Failed to load OpenAI embeddings: {e}")
        return

    # Initialisation du Vector Store
    try:
        vector_store = RedisVectorStore(
            index_name=index_name,
            embeddings=embeddings,
            redis_url="redis://localhost:6379",
        )
    except Exception as e:
        print(f"[ERROR] Failed to initialize RedisVectorStore: {e}")
        return

    # Lecture des documents
    documents = []
    for root, _, files in os.walk(folder_path):
        for filename in files:
            file_path = os.path.join(root, filename)
            try:
                with open(file_path, "r", encoding="iso-8859-1") as f:
                    content = f.read()
                    document = Document(
                        page_content=content,
                        metadata={"name": filename}
                    )
                    documents.append(document)

                    doc_key = f"{index_name}:{filename}"
                    redis_client.hset(doc_key, "page_content", content)
                    redis_client.hset(doc_key, "name", filename)
            except Exception as e:
                print(f"[ERROR] Failed to process file {file_path}: {e}")

    # Ajout dans le vector store
    try:
        if documents:
            vector_store.add_documents(documents=documents)
            print(f"[INFO] {len(documents)} documents indexed under '{index_name}'.")
        else:
            print("[WARNING] No documents found or added.")
    except Exception as e:
        print(f"[ERROR] Failed to add documents to vector store: {e}")

    return vector_store

# Example usage
# create_embedding_from_folder(
#     folder_path="C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/requirements/eTOUR",
#     index_name="eTour"
# )
