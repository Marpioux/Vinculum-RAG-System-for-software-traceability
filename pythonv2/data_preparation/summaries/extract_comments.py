import os
import json
import requests

# First step
def exctract_comments(root_directory: str, output_directory: str):
    """
    Parcourt récursivement le dossier donné pour trouver tous les fichiers .java,
    envoie chaque chemin complet à l'API Java, puis enregistre la réponse dans un fichier .json
    dans le dossier de sortie. Les fichiers en erreur sont listés dans errors.txt.
    """
    os.makedirs(output_directory, exist_ok=True)
    error_log_path = os.path.join(output_directory, "errors.txt")
    with open(error_log_path, 'w', encoding='utf-8') as error_file:
        for dirpath, _, filenames in os.walk(root_directory):
            for filename in filenames:
                if filename.endswith(".java"):
                    java_file_path = os.path.join(dirpath, filename)
                    payload = {
                        "filePath": java_file_path
                    }

                    try:
                        response = requests.post("http://localhost:8080/api/retrieveJson", json=payload)
                        response.raise_for_status() 
                        json_data = response.json()
                        json_filename = os.path.splitext(filename)[0] + ".json"
                        output_path = os.path.join(output_directory, json_filename)
                        with open(output_path, 'w', encoding='utf-8') as f:
                            json.dump(json_data, f, indent=4, ensure_ascii=False)
                        print(f"✅ Fichier traité : {filename} → {json_filename}")

                    except (requests.exceptions.RequestException, json.JSONDecodeError) as e:
                        error_message = f"{filename} : {str(e)}\n"
                        error_file.write(error_message)
                        print(f"❌ Erreur pour {filename} : {e}")


exctract_comments(
    root_directory="C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/iTrust",
    output_directory="C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/json/iTrust"
)
