import os
from dotenv import load_dotenv
import cohere
from langchain_community.embeddings import OpenAIEmbeddings
from langchain_chroma import Chroma
from langchain.retrievers import ParentDocumentRetriever, BM25Retriever, EnsembleRetriever
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain.storage import InMemoryStore
import pickle

def retrieve_doc(index_name, query):
    load_dotenv()
    embedding = OpenAIEmbeddings()
    vectorstore = Chroma(persist_directory=index_name, embedding_function=embedding)
    results = vectorstore.similarity_search(query, k=3)
    return results

def retrieve_doc_rerank(index_name, query):
    load_dotenv()
    cohere_api_key = os.getenv("COHERE_API_KEY")
    co = cohere.ClientV2(api_key=cohere_api_key)

    embedding = OpenAIEmbeddings()
    vectorstore = Chroma(persist_directory=index_name, embedding_function=embedding)
    
    # Obtenir les documents candidats depuis le vector store (top 10 par similarité dense)
    docs = vectorstore.similarity_search(query, k=10)
    
    # Extraire les contenus textuels (la partie `page_content`)
    documents = [doc.page_content for doc in docs]

    # Appliquer rerank Cohere
    response = co.rerank(
        model="rerank-v3.5",
        query=query,
        documents=documents,
        top_n=3
    )

    # Récupérer les documents rerankés à partir de leurs indices
    reranked_docs = [docs[result.index] for result in response.results]
    return reranked_docs

def retrieve_doc_parent(index_name, query):
    load_dotenv()
    embedding = OpenAIEmbeddings()
    vectorstore = Chroma(persist_directory=index_name, embedding_function=embedding)
    splitter = RecursiveCharacterTextSplitter(chunk_size=500, chunk_overlap=50)
    store = InMemoryStore()
    retriever = ParentDocumentRetriever(
        vectorstore=vectorstore,
        docstore=store,
        child_splitter=splitter,
    )
    return retriever.invoke(query)

def retrieve_doc_hybrid(index_name, query, bm25_corpus_path):
    load_dotenv()
    embedding = OpenAIEmbeddings()
    vectorstore = Chroma(persist_directory=index_name, embedding_function=embedding)
    with open(bm25_corpus_path, "rb") as f:
        bm25_corpus = pickle.load(f)


    bm25 = BM25Retriever.from_documents(bm25_corpus)
    bm25.k = 3

    dense = vectorstore.as_retriever(search_kwargs={"k": 3})
    hybrid = EnsembleRetriever(retrievers=[bm25, dense], weights=[0.2, 0.8])
    return hybrid.invoke(query)

def retrieve_doc_mmr(index_name, query):
    load_dotenv()
    embedding = OpenAIEmbeddings()
    vectorstore = Chroma(persist_directory=index_name, embedding_function=embedding)
    results = vectorstore.max_marginal_relevance_search(query, k=10, fetch_k=20)
    return results
