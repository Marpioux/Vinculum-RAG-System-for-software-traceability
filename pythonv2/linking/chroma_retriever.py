import os
from dotenv import load_dotenv
from langchain.embeddings import OpenAIEmbeddings
from langchain.vectorstores import Chroma


def retrieve_doc(index_name, query):
    load_dotenv()
    openai_key = os.getenv("OPENAI_API_KEY")
    embedding = OpenAIEmbeddings()
    vectorstore = Chroma(persist_directory=index_name, embedding_function=embedding)
    results = vectorstore.similarity_search(query, k=3)
    return results

