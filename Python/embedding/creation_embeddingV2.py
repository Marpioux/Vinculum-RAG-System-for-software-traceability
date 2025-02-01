import getpass
import os
import redis
from langchain_openai import OpenAIEmbeddings
from langchain_redis import RedisVectorStore
from langchain_core.documents import Document


if not os.environ.get("OPENAI_API_KEY"):
    os.environ["OPENAI_API_KEY"] = getpass.getpass("Enter API key for OpenAI: ")
os.environ["PYDANTIC_V2"] = "1"

index_name = "iTrustv1"
redis_client = redis.StrictRedis(host='localhost', port=6379, decode_responses=True)
keys = redis_client.keys(f"{index_name}:*")
if keys:
    redis_client.delete(*keys)
    print(f"Deleted keys: {keys}")
else:
    print("No keys to delete.")

embeddings = OpenAIEmbeddings(model="text-embedding-3-large")

vector_store = RedisVectorStore(
    index_name=index_name,
    embeddings=embeddings,
    redis_url="redis://localhost:6379",
)

def find_name_by_page_content(page_content):
    keys = redis_client.keys(f"{index_name}:*")
    for key in keys:
        stored_content = redis_client.hget(key, "page_content")
        if stored_content == page_content:
            document_name = redis_client.hget(key, "name")
            return document_name
    return None

documents = []
#data_directory = "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/requirements/eTOUR"
#data_directory = "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/requirements/Albergate"
data_directory = "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/requirements/iTrust"

# Ajout des documents dans Redis
for racine, repertoires, fichiers in os.walk(data_directory):
    for fichier in fichiers:
        chemin_fichier = os.path.join(racine, fichier)
        with open(chemin_fichier, "r", encoding="iso-8859-1") as f:
            contenu = f.read()
            document = Document(
                page_content=contenu,
                metadata={"name": fichier}
            )
            documents.append(document)

            doc_key = f"{index_name}:{fichier}"
            redis_client.hset(doc_key, "page_content", document.page_content)
            redis_client.hset(doc_key, "name", document.metadata["name"])



vector_store.add_documents(documents=documents)

"""results = vector_store.similarity_search(query="avoir", k=2)
for result in results:
    print(f"Page Content: {result.page_content[:100]}...")

    # Trouver le nom du document dans Redis à partir du page_content
    document_name = find_name_by_page_content(result.page_content)
    if document_name:
        print(f"Document Name from Redis: {document_name}")
    else:
        print("No matching document found for this page content.")"""
