import os
import requests
import json

API_URL = "http://localhost:8080/api/v1/classJson"
OUTPUT_DIR = "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/json_from_java/iTrust"
os.makedirs(OUTPUT_DIR, exist_ok=True)
not_working = []

def process_java_files(root_dir):
    for dirpath, _, filenames in os.walk(root_dir):
        for file in filenames:
            if file.endswith(".java"):
                full_path = os.path.join(dirpath, file).replace('\\', '/')
                try:
                    response = requests.post(API_URL, json={"aFilePath": full_path})
                    response.raise_for_status()
                    output_filename = os.path.splitext(file)[0] + ".json"
                    output_path = os.path.join(OUTPUT_DIR, output_filename).replace('\\', '/')
                    with open(output_path, "w", encoding="utf-8") as json_file:
                        json.dump(response.json(), json_file, indent=4, ensure_ascii=False)

                    print(f"JSON sauvegardé pour {file} dans {output_path}")
                except requests.RequestException as e:
                    print(f"Erreur lors de l'envoi de {full_path} à l'API: {e}")
                    not_working.append({full_path})
                except Exception as e:
                    print(f"Erreur lors du traitement de {file}: {e}")
                    not_working.append({full_path})


root_directory = "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/iTrust"
process_java_files(root_directory)
print("\n\n\n\n", not_working, "\n\n", len(not_working))
