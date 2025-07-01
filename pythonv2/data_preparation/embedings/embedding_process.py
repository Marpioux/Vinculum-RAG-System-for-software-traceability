import os
import redis
import getpass
from langchain_redis import RedisVectorStore
from langchain_openai import OpenAIEmbeddings
from langchain_core.documents import Document

def embed_and_store(folder_path: str, index_name: str):
    """
    Vectorise tous les fichiers texte dans le dossier donné
    et stocke dans Redis avec le nom d'index fourni.
    """

    # --- Vérifications ---
    if not os.path.isdir(folder_path):
        print(f"[❌] Le dossier '{folder_path}' n'existe pas.")
        return

    if not os.environ.get("OPENAI_API_KEY"):
        os.environ["OPENAI_API_KEY"] = getpass.getpass("🔐 Entrez votre clé API OpenAI: ")
    os.environ["PYDANTIC_V2"] = "1"

    # --- Connexion Redis ---
    try:
        redis_client = redis.StrictRedis(host="localhost", port=6379, decode_responses=True)
        redis_client.ping()
    except Exception as e:
        print(f"[❌] Erreur de connexion à Redis : {e}")
        return

    # --- Chargement du modèle ---
    try:
        embeddings = OpenAIEmbeddings(
            model="text-embedding-3-large",
        )
    except Exception as e:
        print(f"[❌] Erreur de chargement des embeddings : {e}")
        return

    # --- Initialisation du vector store ---
    try:
        vector_store = RedisVectorStore(
            index_name=index_name,
            redis_url="redis://localhost:6379",
            embeddings=embeddings
        )
    except Exception as e:
        print(f"[❌] Erreur d'initialisation du RedisVectorStore : {e}")
        return

    # --- Parcours des fichiers ---
    documents = []
    for root, _, files in os.walk(folder_path):
        for file in files:
            if not file.endswith((".txt", ".md", ".java", ".py")):
                continue
            path = os.path.join(root, file)
            try:
                with open(path, "r", encoding="utf-8") as f:
                    content = f.read().strip()
                    if not content:
                        continue
                    doc = Document(
                        page_content=content,
                        metadata={"name": file}
                    )
                    documents.append(doc)
                    print(f"[📄] Préparé : {file}")
            except Exception as e:
                print(f"[⚠️] Erreur lecture fichier {file} : {e}")

    # --- Embedding & Stockage ---
    try:
        if documents:
            vector_store.add_documents(documents)
            print(f"[✅] {len(documents)} documents indexés sous '{index_name}'")
        else:
            print("[⚠️] Aucun document trouvé à indexer.")
    except Exception as e:
        print(f"[❌] Erreur d'indexation : {e}")