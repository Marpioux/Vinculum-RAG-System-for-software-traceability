import os
import json
from dotenv import load_dotenv
from langchain.chat_models import ChatOpenAI
from langchain.prompts import ChatPromptTemplate

def enrich_json_with_gpt_comments(json_folder_path: str, model_temperature: float = 0.3):
    """
    Parcourt tous les fichiers JSON dans un dossier, détecte les méthodes ou constructeurs sans commentaires
    et utilise GPT via LangChain pour en générer, puis ajoute le résultat au JSON sous 'commentedComment'.

    :param json_folder_path: Dossier contenant les fichiers JSON à enrichir
    :param model_temperature: Température du modèle GPT (0.0 = très factuel, 1.0 = créatif)
    """

    load_dotenv()
    openai_key = os.getenv("OPENAI_API_KEY")
    llm = ChatOpenAI(temperature=model_temperature)
    prompt_template = ChatPromptTemplate.from_template(
        "Here is the signature of a Java method: {signature} And its body: {body} Can you generate a clear and relevant JavaDoc comment that accurately describes this method, its parameters, its behavior, and its return value if applicable?"
    )
    for filename in os.listdir(json_folder_path):
        if filename.endswith(".json"):
            file_path = os.path.join(json_folder_path, filename)
            with open(file_path, "r", encoding="utf-8") as f:
                data = json.load(f)
            modified = False
            for category in ["methods", "constructors"]:
                for element in data[0].get(category, []):
                    if not element.get("hasComment", False) or not element.get("hasInnerComment", False):
                        body = element.get("body", "").replace("Optional[", "").replace("]", "").strip()
                        try:
                            prompt = prompt_template.format_messages(
                                signature=element["signature"],
                                body=body
                            )
                            response = llm(prompt)
                            generated_comment = response.content.strip()
                            element["generated_comment"] = generated_comment
                            modified = True
                        except Exception as e:
                            print(f"Erreur pour {filename} → {element['signature']} : {e}")
            if modified:
                with open(file_path, "w", encoding="utf-8") as f:
                    json.dump(data, f, indent=4, ensure_ascii=False)
                print(f"📝 {filename} mis à jour avec des commentaires générés.")

