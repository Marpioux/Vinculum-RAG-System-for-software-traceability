import json
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
    if code.startswith("```java") or code.startswith("```json"):
        code = code[7:]
    elif code.startswith("```"):
        code = code[3:]
    if code.endswith("```"):
        code = code[:-3]
    return code.strip()

def update_files(file_list):
    for file_set in file_list:
        for file_path in file_set:
            try:
                with open(file_path, 'r', encoding='iso-8859-1') as file:
                    content = file.read()
                    new_content = model.invoke("Fix the syntax of this Java code while adhering to standard coding conventions. Return only the corrected Java code, without any explanations or additional comments and in utf8 encoding:" + content)
                with open(file_path, 'w', encoding='utf-8') as file:
                    file.write(cleanLlmOutput(new_content.content))
                print(f"Fichier traité et mis à jour : {file_path}")
            except Exception as e:
                print(f"Erreur lors du traitement du fichier {file_path} : {e}")

def manual_treatment(file_list, output_path): 
    for file_set in file_list:
        for file_path in file_set:
            try:
                with open(file_path, 'r', encoding='iso-8859-1') as file:
                    content = file.read()
                    new_content = model.invoke(
                        "I want to construct some JSON files with the following structure {\"nameClass\" : [{\"parentClass\": \"Authentication\",\"name\": \"include method name\",\"sourceCode\": \"include ALL java method code\"} and this for each method of the class] }. Construct the same json for the following java class, return ONLY the json :" + content
                    )
                    output_filename = os.path.splitext(os.path.basename(file_path))[0] + ".txt"
                    output_path_file = os.path.join(output_path, output_filename).replace('\\', '/')
                    with open(output_path_file, "w", encoding="utf-8") as json_file:
                        json_file.write(cleanLlmOutput(new_content.content))
            except Exception as e:
                print("Erreur lors de l'ouverture de", file_path, ":", e)


#dossier = "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/Datasets/eTOUR/eTOUR/CC"
#contenu_fichiers = lire_contenu_txt(dossier)

file_list = [{"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/Banner.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/BeniCulturali.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IAutenticazione.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IDBBanner.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IDBBeneCulturale.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IDBConvenzione.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IDBMenu.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IDBNews.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IDBOperatoreAgenzia.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IDBOperatorePuntoDiRistoro.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IDBPiatto.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IDBPreferenzeDiRicerca.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IDBPreferenzeGeneriche.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IDBPuntoDiRistoro.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IDBTag.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IDBTurista.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IDBVisitaBC.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IDBVisitaPR.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IGestioneAdvertisement.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IGestioneAdvertisementAgenzia.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IGestioneAdvertisementPuntoDiRistoro.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IGestioneBeniCulturaliAgenzia.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IGestioneBeniCulturaliComune.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IGestionePuntiDiRistoroAgenzia.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IGestionePuntiDiRistoroComune.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IGestioneTagComune.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IGestioneTagOperatoreAgenzia.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IGestioneTuristaClient.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IGestioneTuristiAgenzia.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/IRicerca.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/SchedaBC.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/SchedaPR.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/SchedaTurista.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/SitoTableModel.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/TAGTableModel.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/TestoNewsRendererTest.java"},
 {"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/Turisti.java"}]

#with open("C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR/Turisti.java", 'r', encoding='iso-8859-1') as file:
#    output = model.invoke("I want to construct some JSON files with the following structure {\"nameClass\" : [{\"parentClass\": \"Authentication\",\"name\": \"include method name\",\"sourceCode\": \"include ALL THE java method code\"} and this for each method of the class] }. Construct the same json for the following java class, return ONLY the json :" + file.read())
#print(output.content)
#update_files(file_list)

output_path = "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/testLLM"
manual_treatment(file_list, output_path)