import os
from summaries.extract_comments import exctract_comments
from summaries.summaries_creation import enrich_json_with_gpt_comments

# First step 
exctract_comments(root_directory="C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/java_code/eTOUR",
                  output_directory="C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/json/eTour")


# 2) Pass in all classes and methods who dont have comment and generate them with the LLM
enrich_json_with_gpt_comments(json_folder_path="C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/json/eTour",
                            model_temperature=0.3)

# Third step
# take all requirements and embed them with the LLM and save them in a redis database

# Fourth step
# go to the next step with the name of the given output_path and project 
