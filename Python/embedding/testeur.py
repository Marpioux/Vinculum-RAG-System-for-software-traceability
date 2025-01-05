from langchain_community.vectorstores.redis import Redis
from langchain_openai import OpenAIEmbeddings
import os
import getpass

if not os.environ.get("OPENAI_API_KEY"):
  os.environ["OPENAI_API_KEY"] = getpass.getpass("Enter API key for OpenAI: ")

vector_store = Redis(
    redis_url="redis://localhost:8001",
    embedding=OpenAIEmbeddings(),
    index_name="doc",
)

results = vector_store.similarity_search(query="MediaVotiRenderer",k=1)
for doc in results:
    print(f"* {doc.page_content} [{doc.metadata}]")