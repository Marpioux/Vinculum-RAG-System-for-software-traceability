import os
import getpass

#if not os.environ.get("MISTRAL_API_KEY"):
#  os.environ["MISTRAL_API_KEY"] = getpass.getpass("Enter API key for Mistral AI: ")
#from langchain_mistralai import ChatMistralAI
#model = ChatMistralAI(model="mistral-large-latest")

if not os.environ.get("OPENAI_API_KEY"):
  os.environ["OPENAI_API_KEY"] = getpass.getpass("Enter API key for OpenAI: ")
from langchain_openai import ChatOpenAI
model = ChatOpenAI(model="gpt-4o-mini")

def save_response_as_java(chemin_fichier, response_content):
    dossier_sortie = "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/Datasets/eTOUR/eTOUR/CodeJava"
    nom_fichier = os.path.splitext(os.path.basename(chemin_fichier))[0]
    os.makedirs(dossier_sortie, exist_ok=True)
    chemin_fichier_java = os.path.join(dossier_sortie, f"{nom_fichier}.java")
    with open(chemin_fichier_java, 'w', encoding='iso-8859-1') as fichier_sortie:
        fichier_sortie.write(response_content)

def lire_contenu_txt(dossier):
    for fichier in os.listdir(dossier):
        if fichier.endswith(".txt"):
            chemin_fichier = os.path.join(dossier, fichier)
            with open(chemin_fichier, 'r', encoding='iso-8859-1') as f:
                code = f.read()
                response = model.invoke("Fix the syntax of this Java code while adhering to standard coding conventions. Return only the corrected Java code, without any explanations or additional comments :" + code)
                output = cleanLlmOutput(response.content)
                save_response_as_java(chemin_fichier, output)
    return True

def cleanLlmOutput(code):
    if code.startswith("```java"):
        code = code[7:]
    elif code.startswith("```"):
        code = code[3:]
    if code.endswith("```"):
        code = code[:-3]
    return code.strip()

dossier = "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/Datasets/eTOUR/eTOUR/CC"
contenu_fichiers = lire_contenu_txt(dossier)
