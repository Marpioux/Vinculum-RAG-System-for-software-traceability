from langchain.document_loaders import DirectoryLoader, TextLoader
from langchain.schema import Document
import pickle


def load_bm25_corpus_from_folder(folder_path: str) -> list[Document]:
    loader = DirectoryLoader(
        folder_path,
        glob="**/*.txt",
        loader_cls=TextLoader
    )
    documents = loader.load()
    return documents

bm25_corpus = load_bm25_corpus_from_folder("../Datasets/requirements/iTrust/")
with open("bm25_iTrust.pkl", "wb") as f:
    pickle.dump(bm25_corpus, f)