from utilities.utilities import lire_lignes_du_fichier

def count_correct_predictions(datasets, results_files, rigth_files):
    """
    Counts the number of correct predictions (exact matches) for each dataset.

    Parameters:
        datasets (list): List of dataset names.
        results_files (list): List of file paths with predicted results.
        rigth_files (list): List of file paths with true labels.
    """
    for dataset, result_file, rigth_file in zip(datasets, results_files, rigth_files):
        list_datasets = lire_lignes_du_fichier(rigth_file)
        list_predicted = lire_lignes_du_fichier(result_file)
        
        count = 0
        for element in list_datasets:
            element_clean = element.strip().lower()
            for element2 in list_predicted:
                element2_clean = element2.strip().lower()
                if element_clean == element2_clean:
                    count += 1

        print(f'Number of correct answers: {count} for {dataset}')
