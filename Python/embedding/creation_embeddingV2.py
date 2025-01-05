import getpass
import os

if not os.environ.get("OPENAI_API_KEY"):
  os.environ["OPENAI_API_KEY"] = getpass.getpass("Enter API key for OpenAI: ")

from langchain_openai import OpenAIEmbeddings

embeddings = OpenAIEmbeddings(model="text-embedding-3-large")

from langchain_redis import RedisVectorStore
from langchain_core.documents import Document

vector_store = RedisVectorStore(
    index_name="marius_info",
    embeddings=OpenAIEmbeddings(),
    redis_url="redis://localhost:6379",
)

"""documents = []
ids = []
for racine, repertoires, fichiers in os.walk("C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Python/test"):
        for fichier in fichiers:
            chemin_fichier = os.path.join(racine, fichier)
            with open(chemin_fichier, "r", encoding="iso-8859-1") as f:
                contenu = f.read()
                document = Document(page_content=contenu, metadata={"titre": fichier})
                ids.append(fichier)
                documents.append(document)

vector_store.add_documents(documents=documents, ids=ids)"""

results = vector_store.similarity_search(query="avoir", k=2)
print(results)

