import matplotlib.pyplot as plt
import numpy as np

# Données des F1-scores
datasets = ['eTour', 'iTrust', 'Albergate']
methods = {
    'with_code': [0.396, 0.305, 0.490],
    'with_code_comment': [0.1628, 0.0107, 0.3654],
    'with_one_comment': [0.5119, 0.3456, 0.4366],
    'with_all_generated_comment': [0.1594, 0.2960, 0.3633]
}

# Palette de couleurs inspirée de #5a5b48 (tons de vert olive, gris-vert, et beiges clairs)
colors = {
    'with_code': '#8a9a8a',          # Vert grisâtre doux (complémentaire à #5a5b48)
    'with_code_comment': '#a8b8a8',  # Vert pastel clair
    'with_one_comment': '#d4c9a5',   # Beige doré (pour mettre en valeur la meilleure méthode)
    'with_all_generated_comment': '#b8a898'  # Taupe clair
}

# Paramètres du graphique
bar_width = 0.2
x = np.arange(len(datasets))

# Création du graphique
fig, ax = plt.subplots(figsize=(10, 6))

# Fond blanc pour correspondre à ton thème
fig.patch.set_facecolor('#ffffff')
ax.set_facecolor('#ffffff')

# Tracer les barres pour chaque méthode
for i, (method, scores) in enumerate(methods.items()):
    bars = ax.bar(x + i * bar_width, scores, width=bar_width, color=colors[method], edgecolor='#5a5b48', label=method.replace('_', ' '))

# Configuration du graphique
ax.set_xlabel('Datasets', fontsize=12, color='#5a5b48')
ax.set_ylabel('F1 Score', fontsize=12, color='#5a5b48')
ax.set_title('F1 Score Comparison by Method and Dataset', fontsize=14, color='#5a5b48', pad=20)
ax.set_xticks(x + 1.5 * bar_width)
ax.set_xticklabels(datasets, color='#5a5b48')
ax.set_ylim(0, 0.6)
ax.legend(bbox_to_anchor=(1.05, 1), loc='upper left', fontsize=10, facecolor='#ffffff', edgecolor='#5a5b48')
ax.grid(True, linestyle='--', alpha=0.4, color='#5a5b48')

# Ajouter les valeurs sur les barres (texte en #5a5b48)
for i, method in enumerate(methods):
    for j, score in enumerate(methods[method]):
        ax.text(x[j] + i * bar_width, score + 0.01, f"{score:.3f}",
                ha='center', fontsize=9, color='#5a5b48', fontweight='bold')

plt.tight_layout()
plt.show()
