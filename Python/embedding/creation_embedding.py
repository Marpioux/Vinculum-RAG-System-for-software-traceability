import json
import os
import getpass

from langchain.text_splitter import CharacterTextSplitter
from langchain_community.embeddings.openai import OpenAIEmbeddings
from langchain.vectorstores.redis import Redis
from dotenv import load_dotenv

load_dotenv()
if not os.environ.get("OPENAI_API_KEY"):
  os.environ["OPENAI_API_KEY"] = getpass.getpass("Enter API key for OpenAI: ")

def ensure_folder_exists(folder_path):
    if not os.path.exists(folder_path):
        os.makedirs(folder_path)
    return folder_path


#ensure the ouput folders exist
ensure_folder_exists("./parse")
ensure_folder_exists("./repositories")
ensure_folder_exists("./embeddings")




def extract_from_json(fichier_json):
    results = []
    with open(fichier_json, "r") as file:
        json_data = json.load(file)

    texts = []
    metadatas = []
    vectors = []
    texts.append(json_data["content"])
    vectors.append(json_data["vector"])
    json_data2 = {
        'software': json_data["software"]
        }
    metadatas.append(json_data2)

    results.append(texts)
    results.append(metadatas)
    results.append(vectors)
    return results


def process_files(index_name):
    redis_url="redis://127.0.0.1:6379"
    total_embeddings = 0
    dossier= ensure_folder_exists("./embeddings")

    for root, dirs, files in os.walk(dossier):
        print(f"Number of files: {len(files)}")
        counter = 0
        start_index = 0

        for i in range(start_index, len(files)):
            file_path = os.path.join(root, files[i])
            print(f"Processing file: {file_path}")
            segments = extract_from_json(file_path)
            total_embeddings = len(segments[0]) + total_embeddings

            texts = segments[0]
            metadatas = segments[1]
            embeddings = segments[2]

            #with open("./redis/schema.yml", "r") as file:
            #    index_schema = yaml.safe_load(file)

            embedder = OpenAIEmbeddings()
            redis = Redis(redis_url=redis_url, index_name=index_name, embedding=embedder)#, index_schema=index_schema)

            redis.add_texts(texts, metadatas, embeddings)

            counter += 1
            print(f" {counter} / {len(files)} : {file_path}")

    print(f"Total embeddings processed: {total_embeddings}")

    embedder = OpenAIEmbeddings()
    redis = Redis(redis_url=redis_url, index_name=index_name, embedding=embedder)
    redis.write_schema("redis_schema.yaml")

def parse_folder_code(dossier):
    index_name="eTOUR"
    dossier = dossier
    text_splitter = CharacterTextSplitter.from_tiktoken_encoder(chunk_size=1600, chunk_overlap=0)

    for racine, repertoires, fichiers in os.walk(dossier):
        for fichier in fichiers:
            chemin_fichier = os.path.join(racine, fichier)
            if (fichier.endswith(".java")):
                with open(chemin_fichier, 'r', encoding='iso-8859-1') as f:
                    contenu = f.read()
                    texts = text_splitter.split_text(contenu)
                    i=0
                    for text in texts:
                        json_data = {
                            'titre': fichier,
                            'contenu': text
                        }
                        nom_fichier_json = os.path.splitext(fichier)[0] + str(i) +'.json'
                        i=i+1
                        parseFolder = ensure_folder_exists("./parse")
                        chemin_fichier_json = os.path.join(parseFolder, nom_fichier_json)
                        with open(chemin_fichier_json, 'w') as json_file:
                            json.dump(json_data, json_file, indent=4)
    embedding_folder_code(index_name)

def embedding_folder_code(index_name):
    
    dossier = ensure_folder_exists("./parse")

    embeddings = OpenAIEmbeddings(model="text-embedding-ada-002")
    
    for fichier in os.listdir(dossier):
        
        chemin_fichier = os.path.join(dossier, fichier)
        if os.path.isfile(chemin_fichier) and fichier.endswith('.json'):
            with open(chemin_fichier, 'r', encoding='iso-8859-1') as f:
                data = json.load(f)
                if 'titre' in data and 'contenu' in data:
                    txt_embedding = embeddings.embed_documents([data['contenu']])
                    json_data = {
                        'titre': data['titre'],
                        'content': data['contenu'],
                        'vector': txt_embedding[0],
                        'software': 'eTOUR'
                    }
                    chemin_fichier_json = os.path.join("./embeddings", fichier)
                    with open(chemin_fichier_json, 'w') as json_file:
                        json.dump(json_data, json_file, indent=4)
    process_files(index_name)


parse_folder_code("C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR")