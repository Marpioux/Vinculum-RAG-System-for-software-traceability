from evaluation.f1_metric import evaluate_datasets_f1
from evaluation.correct_matches import count_correct_predictions
from utilities.utilities import clean_links

# Définition des chemins vers les fichiers de résultats
results_with_only_code = [
    "./results/RQ1/with_only_code/eTourv1.txt",
    "./results/RQ1/with_only_code/iTrustv1.txt",
    "./results/RQ1/with_only_code/Albergatev1.txt",
]

results_with_code_comment = [
    "./results/RQ1/with_code_comment/eTourv1.txt",
    "./results/RQ1/with_code_comment/iTrustv1.txt",
    "./results/RQ1/with_code_comment/Albergatev1.txt",
]

results_with_all_generated_comments = [
    "./results/RQ1/with_all_generated_comment/eTourv1.txt",
]

results_with_one_comment = [
    "./results/RQ1/with_all_generated_comment/chroma_eTOUR.txt",
    "./results/RQ1/with_all_generated_comment/chroma_iTrust.txt",
    "./results/RQ1/with_all_generated_comment/chroma_Albergate.txt",
]

# Chemins vers les fichiers de vérité terrain
ground_truth_files = [
    "./results/rigth_matrix/eTOUR.txt",
    "./results/rigth_matrix/iTrust.txt",
    "./results/rigth_matrix/Albergate.txt",
]

results_RQ2_code = [
    "./results/RQ2/with_code/chroma_eTOUR.txt",
    "./results/RQ2/with_code/chroma_iTrust.txt",
    "./results/RQ2/with_code/chroma_Albergate.txt",
]

results_RQ2_code_comment = [
    "./results/RQ2/with_code_comment/chroma_eTOUR.txt",
    "./results/RQ2/with_code_comment/chroma_iTrust.txt",
    "./results/RQ2/with_code_comment/chroma_Albergate.txt",
]

results_RQ2_gen_comment = [
    "./results/RQ2/with_gen_comment/chroma_eTOUR.txt",
    "./results/RQ2/with_gen_comment/chroma_iTrust.txt",
    "./results/RQ2/with_gen_comment/chroma_Albergate.txt",
]

# Noms des jeux de données
datasets = ["eTOUR", "iTrust", "Albergate"]

# Exécution des évaluations
evaluate_datasets_f1(datasets, results_RQ2_gen_comment, ground_truth_files)
count_correct_predictions(datasets, results_RQ2_gen_comment, ground_truth_files)
