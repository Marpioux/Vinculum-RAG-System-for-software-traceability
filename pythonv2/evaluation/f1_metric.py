from sklearn.metrics import f1_score
from utilities.utilities import lire_lignes_du_fichier
from sklearn.metrics import f1_score

def evaluate_datasets_f1(datasets, results_files, rigth_files):
    """
    Evaluates F1 scores for multiple datasets.

    Parameters:
        datasets (list): List of dataset names.
        results_files (list): List of file paths with predicted results.
        rigth_files (list): List of file paths with true labels.
    """
    for dataset, result_file, rigth_file in zip(datasets, results_files, rigth_files):
        y_true = lire_lignes_du_fichier(rigth_file)
        y_pred = lire_lignes_du_fichier(result_file)
        
        # Padding to make lengths equal
        if len(y_true) > len(y_pred):
            y_pred += [""] * (len(y_true) - len(y_pred))
        elif len(y_true) < len(y_pred):
            y_true += [""] * (len(y_pred) - len(y_true))

        # Compute F1 score
        f1 = f1_score(y_true=y_true, y_pred=y_pred, average='micro')
        print(f"F1 Score: {f1:.2f} for {dataset}")