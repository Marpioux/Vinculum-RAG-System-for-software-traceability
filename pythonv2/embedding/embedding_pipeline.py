import os
from summaries.extract_comments import extract_comments

# First step 
root_dir = "<path_to_your_java_files>"
for dirpath, _, filenames in os.walk(root_dir):
    for file in filenames:
        if file.endswith(".java"):
            full_path = os.path.join(dirpath, file)
            with open(full_path, "r", encoding="utf-8") as java_file:
                content = java_file.read()
            # Second step
            # 1) Extract the summaries of the classes and the methods from the java files
            json_file = extract_comments(content)


# 2) Pass in all classes and methods who dont have comment and generate them with the LLM

# Third step
# take all requirements and embed them with the LLM and save them in a redis database

# Fourth step
# go to the next step with the name of the given output_path and project 
