import getpass
import os
import redis
from langchain_openai import OpenAIEmbeddings
from langchain_redis import RedisVectorStore
from langchain_core.documents import Document


def create_embedding_from_folder(folder_path: str, index_name: str):
    """
    Crée des embeddings OpenAI à partir des fichiers texte dans un dossier donné
    et les stocke dans Redis sous un index donné.
    
    :param folder_path: Chemin du dossier contenant les fichiers.
    :param index_name: Nom de l'index Redis pour stocker les embeddings.
    """

    # Authentification OpenAI
    if not os.environ.get("OPENAI_API_KEY"):
        os.environ["OPENAI_API_KEY"] = getpass.getpass("Enter API key for OpenAI: ")
    os.environ["PYDANTIC_V2"] = "1"

    # Connexion Redis
    redis_client = redis.StrictRedis(host='localhost', port=6379, decode_responses=True)

    # Nettoyage de l'index existant
    keys = redis_client.keys(f"{index_name}:*")
    if keys:
        redis_client.delete(*keys)
        print(f"[INFO] Deleted existing keys: {keys}")
    else:
        print("[INFO] No existing keys to delete.")

    # Chargement du modèle d'embedding
    embeddings = OpenAIEmbeddings(model="text-embedding-3-large")

    # Initialisation du Vector Store
    vector_store = RedisVectorStore(
        index_name=index_name,
        embeddings=embeddings,
        redis_url="redis://localhost:6379",
    )

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
    if documents:
        vector_store.add_documents(documents=documents)
        print(f"[INFO] {len(documents)} documents indexed under '{index_name}'.")
    else:
        print("[WARNING] No documents found or added.")

    return vector_store
