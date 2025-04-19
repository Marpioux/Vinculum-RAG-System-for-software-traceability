import os
import json
import requests

# First step
def exctract_comments(root_directory: str, output_directory: str):
        """
        Parcourt récursivement le dossier donné pour trouver tous les fichiers .java,
        envoie chaque chemin complet à l'API Java, puis enregistre la réponse dans un fichier .json
        dans le dossier de sortie.

        :param root_directory: Le dossier à parcourir récursivement pour chercher les fichiers .java
        :param output_directory: Le dossier où sauvegarder les fichiers .json obtenus de l'API
        """
        # S'assurer que le dossier de sortie existe
        os.makedirs(output_directory, exist_ok=True)

        # Parcours récursif des fichiers
        for dirpath, _, filenames in os.walk(root_directory):
            for filename in filenames:
                if filename.endswith(".java"):
                    # Construction du chemin complet vers le fichier Java
                    java_file_path = os.path.join(dirpath, filename)

                    # Préparation du body pour l'API
                    payload = {
                        "filePath": java_file_path
                    }

                    try:
                        # Envoi de la requête POST à l'API
                        response = requests.post("http://localhost:8080/api/retrieveJson", json=payload)

                        # Vérification de la réponse
                        response.raise_for_status()  # Lève une exception en cas d'erreur HTTP

                        # Récupération du contenu JSON retourné
                        json_data = response.json()

                        # Création du chemin du fichier de sortie (.json)
                        json_filename = os.path.splitext(filename)[0] + ".json"
                        output_path = os.path.join(output_directory, json_filename)

                        # Écriture du JSON dans le fichier de sortie
                        with open(output_path, 'w', encoding='utf-8') as f:
                            json.dump(json_data, f, indent=4, ensure_ascii=False)

                        print(f"✅ Fichier traité : {filename} → {json_filename}")

                    except requests.exceptions.RequestException as e:
                        print(f"❌ Erreur lors de l'appel API pour {filename} : {e}")
                    except json.JSONDecodeError:
                        print(f"❌ Réponse non JSON pour {filename}")

# Return the json folder
exctract_comments(root_directory="C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR",
                  output_directory="C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/json/eTour")