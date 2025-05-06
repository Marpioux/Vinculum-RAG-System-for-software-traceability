from evaluation.f1_metric import evaluate_datasets_f1
from evaluation.correct_matches import count_correct_predictions
from utilities.utilities import clean_links

results_files = [
    "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/pythonv2/results/RQ1/with_only_code/eTourv1.txt",
    "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/pythonv2/results/RQ1/with_only_code/iTrustv1.txt",
    "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/pythonv2/results/RQ1/with_only_code/Albergatev1.txt",
]

rigth_files = ["C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/pythonv2/results/rigth_matrix/eTOUR.txt",
               "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/pythonv2/results/rigth_matrix/iTrust.txt",
               "C:/Users/marius.pingaud/OneDrive - BERGER-LEVRAULT/Bureau/Sorbonne/M2/Master thesis/Requirement Engineering/master_thesis_xp/pythonv2/results/rigth_matrix/Albergate.txt"]

datasets = ["eTOUR", "iTrust", "Albergate"]

for element in results_files:
    clean_links(element)

evaluate_datasets_f1(datasets, results_files, rigth_files)
count_correct_predictions(datasets, results_files, rigth_files)
