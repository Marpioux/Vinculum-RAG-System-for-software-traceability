# Script pour mettre les REQ.TXT à gauche et supprimer les lignes avec "None"
# Modifie le fichier original

def process_file_in_place(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        lines = f.readlines()

    processed_lines = []

    for line in lines:
        line = line.strip()
        if not line or '-> None' in line:
            continue  # Ignore les lignes vides et celles contenant None

        parts = line.split('->')
        if len(parts) != 2:
            continue  # Ignore les lignes mal formées

        left = parts[0].strip()
        right = parts[1].strip()

        # Mettre le REQ.TXT (UCxx.TXT) à gauche
        if right.startswith('U') and right.endswith('.TXT'):
            processed_lines.append(f'{right} -> {left}')
        elif left.startswith('U') and left.endswith('.TXT'):
            processed_lines.append(f'{left} -> {right}')
        # Sinon, ligne ignorée

    # Réécriture du fichier avec les lignes nettoyées et réordonnées
    with open(file_path, 'w', encoding='utf-8') as f:
        for line in processed_lines:
            f.write(line + '\n')

# Utilisation : remplace "chemin/vers/fichier.txt" par ton chemin
process_file_in_place('./RQ2/with_gen_comment/chroma_eTOUR.txt')
