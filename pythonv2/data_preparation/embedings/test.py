import os
import getpass
from utilities.utilities import initialize_system
from langchain_redis import RedisVectorStore
from langchain_openai import OpenAIEmbeddings
from langchain_core.documents import Document

if not os.environ.get("OPENAI_API_KEY"):
        os.environ["OPENAI_API_KEY"] = getpass.getpass("🔐 Entrez votre clé API OpenAI: ")
os.environ["PYDANTIC_V2"] = "1"

embeddings = OpenAIEmbeddings(
            model="text-embedding-3-large",
        )
vector_store = RedisVectorStore(
            index_name="test",
            redis_url="redis://localhost:6379",
            embeddings=embeddings
        )

doc = Document(page_content="Test document qui parle de mot de passe", metadata={"name": "test.txt"})
vector_store.add_documents([doc])

index_name = "test"
model, embeddings, redis_client, vector_store = initialize_system(index_name)

retriever = vector_store.as_retriever()
results = retriever.invoke("mot de passe utilisateur")

print(results)
for doc in results:
    print(doc.metadata["name"])
    print(doc.page_content[:300])
