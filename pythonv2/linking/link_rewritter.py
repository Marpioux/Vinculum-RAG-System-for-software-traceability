from utilities import log, save_response, clean_llm_output, find_name_by_page_content
from langchain_openai import ChatOpenAI
import os
from langchain_community.vectorstores import Redis as RedisVectorStore
import os
import redis

def run_traceability_pipeline_all(code_folder: str, output_file: str):
    log("🔎 Starting traceability analysis...")
    
    for files in os.walk(code_folder):
        with open(files, 'r', encoding="json") as json:
            data = json.load(json)
            methods = data['methods']
            for method in methods:
                method_name = method['signature']
                method_code = method['body']