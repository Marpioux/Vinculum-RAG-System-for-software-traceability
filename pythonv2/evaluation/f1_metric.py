from sklearn.metrics import f1_score
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
    y_true = lire_lignes_du_fichier(rigth_file)
    y_pred = lire_lignes_du_fichier(result_file)
    
    if(len(y_true) > len(y_pred)):
        y_pred += [""] * (len(y_true) - len(y_pred))
    elif(len(y_true) < len(y_pred)):
        y_true += [""] * (len(y_pred) - len(y_true))

    f1 = f1_score(y_true=y_true, y_pred=y_pred, average='micro')
    print(f"F1 Score: {f1:.2f} for {dataset}")