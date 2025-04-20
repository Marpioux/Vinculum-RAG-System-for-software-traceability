from ..linking.utilities.utilities import lire_lignes_du_fichier

results_files = [
    "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/pythonv2/results/",
    "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/pythonv2/results/",
    "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/pythonv2/results/"
]

rigth_files = ["C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/pythonv2/results/rigth_matrix/eTOUR.txt",
               "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/pythonv2/results/rigth_matrix/Albergate.txt",
               "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/pythonv2/results/rigth_matrix/iTrust.txt"]

datasets = ["eTOUR", "Albergate", "iTrust"]

for dataset, result_file, rigth_file in zip(datasets, results_files, rigth_files):
    list_datasets = lire_lignes_du_fichier(rigth_file)
    list_predicted = lire_lignes_du_fichier(result_file)
    count = 0
    for element in list_datasets :
        for element2 in list_predicted :
            if element==element2:
                count+=1

    print(f'Number of rigth answers : {count} for {dataset}')