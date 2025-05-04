import os
import json
from dotenv import load_dotenv
from langchain.chat_models import ChatOpenAI
from langchain.prompts import ChatPromptTemplate

def enrich_json_with_gpt_comments(json_folder_path: str, model_temperature: float = 0.3):
    """
    Parcourt tous les fichiers JSON dans un dossier, génère des commentaires pour les méthodes,
    les constructeurs et un commentaire global pour la classe en utilisant GPT via LangChain,
    puis ajoute le résultat au JSON sous 'generated_comment'.

    :param json_folder_path: Dossier contenant les fichiers JSON à enrichir
    :param model_temperature: Température du modèle GPT (0.0 = très factuel, 1.0 = créatif)
    """

    load_dotenv()
    openai_key = os.getenv("OPENAI_API_KEY")
    llm = ChatOpenAI(temperature=model_temperature)

    method_prompt_template = ChatPromptTemplate.from_template(
        "Here is the signature of a Java method: {signature} And its body: {body} Can you generate a clear and relevant JavaDoc comment that accurately describes this method, its parameters, its behavior, and its return value if applicable?"
    )

    constructor_prompt_template = ChatPromptTemplate.from_template(
        "Here is the signature of a Java constructor: {signature} And its body: {body} Can you generate a clear and relevant JavaDoc comment that accurately describes this constructor, its parameters, and its behavior?"
    )

    class_prompt_template = ChatPromptTemplate.from_template(
        "Here is the entire Java class: {class_content} Can you generate a comprehensive JavaDoc comment that describes the purpose of the class, its main functionalities, and its overall behavior?"
    )

    def split_class_content(class_content, max_tokens=2000):
        """
        Split the class content into smaller chunks.

        :param class_content: The content of the class as a string.
        :param max_tokens: The maximum number of tokens per chunk.
        :return: A list of smaller chunks of the class content.
        """
        words = class_content.split()
        chunks = []
        current_chunk = []
        current_length = 0

        for word in words:
            word_length = len(word)
            if current_length + word_length > max_tokens:
                chunks.append(" ".join(current_chunk))
                current_chunk = []
                current_length = 0
            current_chunk.append(word)
            current_length += word_length + 1  # Add 1 for the space

        if current_chunk:
            chunks.append(" ".join(current_chunk))

        return chunks

    for filename in os.listdir(json_folder_path):
        if filename.endswith(".json"):
            file_path = os.path.join(json_folder_path, filename)
            with open(file_path, "r", encoding="utf-8") as f:
                data = json.load(f)

            # Vérifiez si la structure du JSON est correcte
            if not isinstance(data, list) or len(data) == 0:
                print(f"Skipping {filename} due to invalid structure.")
                continue

            data_entry = data[0]
            modified = False

            # Generate comments for methods
            if "methods" in data_entry:
                for method in data_entry["methods"]:
                    if "generated_comment" not in method:
                        body = method.get("body", "").replace("Optional[", "").replace("]", "").strip()
                        try:
                            prompt = method_prompt_template.format_messages(
                                signature=method["signature"],
                                body=body
                            )
                            response = llm(prompt)
                            generated_comment = response.content.strip()
                            method["generated_comment"] = generated_comment
                            modified = True
                        except Exception as e:
                            print(f"Error for {filename} → {method['signature']} : {e}")

            # Generate comments for constructors
            if "constructors" in data_entry:
                for constructor in data_entry["constructors"]:
                    if "generated_comment" not in constructor:
                        body = constructor.get("body", "").replace("Optional[", "").replace("]", "").strip()
                        try:
                            prompt = constructor_prompt_template.format_messages(
                                signature=constructor["signature"],
                                body=body
                            )
                            response = llm(prompt)
                            generated_comment = response.content.strip()
                            constructor["generated_comment"] = generated_comment
                            modified = True
                        except Exception as e:
                            print(f"Error for {filename} → {constructor['signature']} : {e}")

            # Generate a global comment for the class
            if "generated_class_comment" not in data_entry:
                class_content = json.dumps(data, indent=4)
                class_chunks = split_class_content(class_content)
                generated_class_comment = ""

                for chunk in class_chunks:
                    try:
                        prompt = class_prompt_template.format_messages(
                            class_content=chunk
                        )
                        response = llm(prompt)
                        generated_class_comment += response.content.strip() + "\n"
                        modified = True
                    except Exception as e:
                        print(f"Error generating class comment for {filename} : {e}")

                data_entry["generated_class_comment"] = generated_class_comment.strip()

            if modified:
                with open(file_path, "w", encoding="utf-8") as f:
                    json.dump(data, f, indent=4, ensure_ascii=False)
                print(f"📝 {filename} mis à jour avec des commentaires générés.")

# Example usage
# enrich_json_with_gpt_comments("path/to/json/folder")
