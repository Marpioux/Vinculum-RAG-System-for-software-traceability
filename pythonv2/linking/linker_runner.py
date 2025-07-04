from linking.linker2 import linking_with_only_code

index_names = [
#"chroma_eTOUR",
"chroma_Albergate",
"chroma_iTrust"]

json_folders = [
#"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/json/eTour",
"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/json/albergate",
"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/json/iTrust"]


for element in json_folders:
    linking_with_only_code(
        code_folder=element,
        output_file=f"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/pythonv2/results/RQ2/with_code/{index_names[json_folders.index(element)]}.txt", 
        index_name=index_names[json_folders.index(element)])
    
    #with_code_comments(
    #    code_folder= element,
    #    output_file=f"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/pythonv2/results/RQ2/with_code_comment/{index_names[json_folders.index(element)]}.txt",
    #    index_name=index_names[json_folders.index(element)],
    #    file_result=f"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/pythonv2/results/RQ1/with_only_code/{index_names[json_folders.index(element)]}.txt"
    #)

    #with_generated_comments_methods_class(
    #    code_folder=element,
    #    output_file=f"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/pythonv2/results/RQ1/with_all_generated_comment/{index_names[json_folders.index(element)]}.txt", 
    #    index_name=index_names[json_folders.index(element)],
    #    file_result=f"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/pythonv2/results/RQ1/with_only_code/{index_names[json_folders.index(element)]}.txt")
    
    #with_class_comment(
    #    code_folder=element,
    #    output_file=f"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/pythonv2/results/RQ2/with_gen_comment/{index_names[json_folders.index(element)]}.txt", 
    #    index_name=index_names[json_folders.index(element)]
    #)

