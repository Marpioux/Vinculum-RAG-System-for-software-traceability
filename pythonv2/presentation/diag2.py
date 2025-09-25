import matplotlib.pyplot as plt
import numpy as np

# Meilleure performance GLOBALE par technique/dataset (F1 Score)
best_f1_scores = {
    'Reranker': [0.4832, 0.3659, 0.5],          # with_code + reranker
    'BM25': [0.4253, 0.3936, 0.5488],          # with_code_comment + bm25 (meilleur Albergate)
    'MMR': [0.4226, 0.3329, 0.4992]           # with_one_comment + MMR
}

datasets = ['eTour', 'iTrust', 'Albergate']
techniques = list(best_f1_scores.keys())

# Palette de couleurs (harmonisée avec #5a5b48)
colors = {
    'Reranker': '#8a9a8a',   # Vert grisâtre
    'BM25': '#a8b8a8',      # Vert pastel
    'MMR': '#d4c9a5'        # Beige doré
}

# Création du graphique
fig, ax = plt.subplots(figsize=(10, 6))
fig.patch.set_facecolor('#ffffff')
ax.set_facecolor('#ffffff')

# Position des barres (centrées)
x = np.arange(len(datasets))
bar_width = 0.25

# Tracer les barres
for i, technique in enumerate(techniques):
    ax.bar(x + i * bar_width - 0.3,  # Recentrage
           best_f1_scores[technique],
           width=bar_width,
           color=colors[technique],
           edgecolor='#5a5b48',
           label=technique)

# Configuration
ax.set_xlabel('Datasets', fontsize=12, color='#5a5b48')
ax.set_ylabel('Best F1 Score', fontsize=12, color='#5a5b48')
ax.set_title('Peak F1 Scores by Technique and Dataset', fontsize=14, color='#5a5b48', pad=20)
ax.set_xticks(x)
ax.set_xticklabels(datasets, color='#5a5b48')
ax.set_ylim(0, 0.6)
ax.legend(bbox_to_anchor=(1.0, 1.0), loc='upper left', fontsize=10, facecolor='#ffffff', edgecolor='#5a5b48')
ax.grid(True, linestyle='--', alpha=0.4, color='#5a5b48')

# Valeurs sur les barres
for i, technique in enumerate(techniques):
    for j, score in enumerate(best_f1_scores[technique]):
        ax.text(x[j] + i * bar_width - 0.3, score + 0.01, f"{score:.3f}",
                ha='center', fontsize=9, color='#5a5b48', fontweight='bold')

plt.tight_layout(rect=[0, 0, 0.9, 1])
plt.show()
