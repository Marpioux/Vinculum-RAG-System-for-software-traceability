import os
"""from summaries.extract_comments import exctract_comments
from summaries.summaries_creation import enrich_json_with_gpt_comments"""
from data_preparation.embedings.embedding_process import embed_and_store

#paths for folders
code_folders = [
"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR",
"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/albergate",
"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/iTrust"]

json_folders = [
"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/json/eTour",
"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/json/albergate",
"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/json/iTrust"]

requirements_folders = [
"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/requirements/eTOUR",
"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/requirements/Albergate",
"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/requirements/iTrust"]

index_names = [
"eTourv1",
"Albergatev1",
"iTrustv1"]

for element in code_folders:
    # First step 
    #exctract_comments(root_directory=element,
    #                  output_directory=json_folders[code_folders.index(element)])
    
    # 2) Pass in all classes and methods who dont have comment and generate them with the LLM
    #enrich_json_with_gpt_comments(json_folder_path=json_folders[code_folders.index(element)], model_temperature=0.3)

    # Third step
    # take all requirements and embed them with the LLM and save them in a redis database
    embed_and_store(folder_path=requirements_folders[code_folders.index(element)],
                                index_name=index_names[code_folders.index(element)])