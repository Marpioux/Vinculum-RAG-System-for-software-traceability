import getpass
import os
import redis

if not os.environ.get("OPENAI_API_KEY"):
  os.environ["OPENAI_API_KEY"] = getpass.getpass("Enter API key for OpenAI: ")

from langchain_openai import OpenAIEmbeddings
from langchain_openai import ChatOpenAI

model = ChatOpenAI(model="gpt-4o-mini")

embeddings = OpenAIEmbeddings(model="text-embedding-3-large")

from langchain_redis import RedisVectorStore

index_name="eTOURv1.1"
redis_client = redis.StrictRedis(host='localhost', port=6379, decode_responses=True)
vector_store = RedisVectorStore(
    index_name=index_name,
    embeddings=embeddings,
    redis_url="redis://localhost:6379",
)

def save_response_as_file(chemin_fichier, response_content):
    dossier_sortie = "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Python/linking/traceability matrix/eTourv1"
    nom_fichier = os.path.splitext(os.path.basename(chemin_fichier))[0]
    os.makedirs(dossier_sortie, exist_ok=True)
    chemin_fichier_java = os.path.join(dossier_sortie, f"{nom_fichier}.txt")
    with open(chemin_fichier_java, 'w', encoding='iso-8859-1') as fichier_sortie:
        fichier_sortie.write(response_content)

def cleanLlmOutput(code):
    if code.startswith("```java") or code.startswith("```json"):
        code = code[7:]
    elif code.startswith("```"):
        code = code[3:]
    if code.endswith("```"):
        code = code[:-3]
    return code.strip()

def find_name_by_page_content(page_content):
    keys = redis_client.keys(f"{index_name}:*")
    for key in keys:
        stored_content = redis_client.hget(key, "page_content")
        if stored_content == page_content:
            document_name = redis_client.hget(key, "name")
            return document_name
    return None

        

for racine, repertoires, fichiers in os.walk("C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR"):
        for fichier in fichiers:
            chemin_fichier = os.path.join(racine, fichier)
            with open(chemin_fichier, "r", encoding="iso-8859-1") as f:
                contenu = f.read()
                results = vector_store.similarity_search(query=contenu, k=2)
                reqs = []
                for req in results :
                    document_name = find_name_by_page_content(req.page_content)
                    reqs.append({"name": document_name, "content": req.page_content})
                response = model.invoke(f"Here is a Java class: {contenu}, and a list of requirements from a RAG: {reqs}. A Java class can correspond to zero or one requirement. Can you analyze the code and the requirements, and link each Java class to its corresponding requirement if there's a match? I want the output in the following format: JavaClassName -> RequirementName.txt AND only that no other things")
                response = cleanLlmOutput(response.content)
                save_response_as_file(chemin_fichier, response)

                