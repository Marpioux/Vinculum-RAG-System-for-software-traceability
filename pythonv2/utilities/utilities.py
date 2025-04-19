import os
import redis
import getpass
import json
from langchain_openai import OpenAIEmbeddings, ChatOpenAI
from langchain_community.vectorstores import Redis as RedisVectorStore

def log(msg, level="INFO"):
    print(f"[{level}] {msg}")

def clean_llm_output(text: str) -> str:
    if text.startswith("```"):
        text = text.split("\n", 1)[-1]
    if text.endswith("```"):
        text = text[:-3]
    return text.strip()

def save_response(content: str, output_path: str):
    os.makedirs(os.path.dirname(output_path), exist_ok=True)
    with open(output_path, 'a', encoding='utf-8') as f:
        f.write(content + '\n')

def build_prompt(class_name: str, java_code: str, related_reqs: list) -> str:
    return (
        f"You are a software traceability assistant.\n\n"
        f"Java Class: {class_name}\n\n"
        f"Code:\n{java_code}\n\n"
        f"Related Requirements (from semantic search):\n"
        f"{json.dumps(related_reqs, indent=2)}\n\n"
        f"Instructions:\n"
        f"- Analyze the Java class and the provided requirements.\n"
        f"- Determine if the class corresponds to exactly one of these requirements.\n"
        f"- If so, return the match in this format: {class_name} -> RequirementName.txt\n"
        f"- If no match, return: {class_name} -> None\n"
        f"- Do NOT return anything else."
    )