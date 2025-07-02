import os
import chromadb
from dotenv import load_dotenv
from langchain_openai import OpenAIEmbeddings
from langchain_community.vectorstores import Chroma
from langchain_community.document_loaders import TextLoader
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain_community.document_loaders import TextLoader
import chardet

chromadb.config.Settings.enable_telemetry = False

def safe_load_text(path):
    # Détection automatique d'encodage
    with open(path, 'rb') as f:
        raw_data = f.read()
        result = chardet.detect(raw_data)
        detected_encoding = result['encoding'] or 'utf-8'
    
    # Lecture avec l'encodage détecté
    try:
        loader = TextLoader(path, encoding=detected_encoding)
        return loader.load()
    except Exception as e:
        print(f"⚠️ Échec du chargement de {path} ({detected_encoding}): {e}")
        return []
    
load_dotenv()
openai_key = os.getenv("OPENAI_API_KEY")

# ✅ Dossiers contenant les .txt
folders = {
    "eTOUR": r"../Datasets/requirements/eTOUR",
    "Albergate": r"../Datasets/requirements/Albergate",
    "iTrust": r"../Datasets/requirements/iTrust",
}

# ✅ Initialisation des embeddings
embedding = OpenAIEmbeddings()

# ✅ Chargement + découpage de documents
def load_documents_from_folder(folder_path):
    docs = []
    for file in os.listdir(folder_path):
        if file.lower().endswith(".txt"):
            path = os.path.join(folder_path, file)
            print(f"📄 Chargement : {file}")
            docs.extend(safe_load_text(path))
    splitter = RecursiveCharacterTextSplitter(chunk_size=500, chunk_overlap=100)
    return splitter.split_documents(docs)

# ✅ Création des vectorstores
for index_name, folder_path in folders.items():
    print(f"\n📁 Traitement de {index_name} ({folder_path})...")

    documents = load_documents_from_folder(folder_path)
    print(f"Fichiers trouvés dans {folder_path} :")
    print(os.listdir(folder_path))
    if not documents:
        raise ValueError(f"Aucun document trouvé dans le dossier : {folder_path}")

    print(f"📄 {len(documents)} documents chargés.")
    print("🔎 Test de l'embedding...")
    test_vec = embedding.embed_query("test")
    if not test_vec or not isinstance(test_vec, list):
        raise ValueError("Échec de génération des embeddings")

    # Création du vectorstore et persistance
    vs = Chroma.from_documents(
        documents,
        embedding,
        persist_directory=f"./chroma_{index_name}"
    )
    vs.persist()
    print(f"✅ Index '{index_name}' enregistré dans ./chroma_{index_name}")
