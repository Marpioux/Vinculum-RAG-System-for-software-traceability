from linker import linking_with_only_code

index_names = [
#"eTourv1",
"iTrustv1",
"Albergatev1"]

json_folders = [
#"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/json/eTour",
"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/json/iTrust",
"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/Datasets/json/albergate"]


for element in json_folders:
    linking_with_only_code(
        code_folder=element,
        output_file=f"C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/pythonv2/results/RQ1/with_only_code/{index_names[json_folders.index(element)]}.txt", 
        index_name=index_names[json_folders.index(element)])
