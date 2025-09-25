import matplotlib.pyplot as plt
import numpy as np

# Données pour eTOUR et iTrust (F1-scores)
datasets = ['eTOUR', 'iTrust']
f1_study_1 = [0.334, 0.290]          # Résultats de [1] (Downstream)
f1_study_21 = [0.548, 0.290]         # Résultats de [21] (Upstream)
f1_yours_best = [0.5119, 0.3659]     

# Positionnement des barres
x = np.arange(len(datasets))
width = 0.25  # Largeur des barres

# Création du graphique
fig, ax = plt.subplots(figsize=(10, 6))
fig.patch.set_facecolor('#ffffff')  # Fond blanc
ax.set_facecolor('#ffffff')

# Barres pour chaque étude/méthode (couleurs adaptées)
ax.bar(x - width, f1_study_1, width, color='#d3d3d3', edgecolor='#5a5b48', label='Study [1] (Downstream)')
ax.bar(x, f1_study_21, width, color='#a9a9a9', edgecolor='#5a5b48', label='Study [21] (Upstream)')
ax.bar(x + width, f1_yours_best, width, color='#8a9a8a', edgecolor='#5a5b48', label='Vinculum')

# Configuration du graphique (couleurs et style adaptés)
ax.set_xlabel('Datasets', fontsize=12, color='#5a5b48')
ax.set_ylabel('F1 Score', fontsize=12, color='#5a5b48')
ax.set_title('F1-Score Comparison: Our Method vs. Benchmarks', fontsize=14, color='#5a5b48', pad=20)
ax.set_xticks(x)
ax.set_xticklabels(datasets, color='#5a5b48')
ax.legend(bbox_to_anchor=(1.05, 1), loc='upper left', fontsize=10, facecolor='#ffffff', edgecolor='#5a5b48')
ax.grid(True, linestyle='--', alpha=0.4, color='#5a5b48')
ax.set_ylim(0, 0.6)  # Échelle pour mettre en valeur les différences

# Ajout des valeurs sur les barres (couleur adaptée)
for i, dataset in enumerate(datasets):
    ax.text(x[i] - width, f1_study_1[i] + 0.01, f"{f1_study_1[i]:.3f}", ha='center', fontsize=9, color='#5a5b48')
    ax.text(x[i], f1_study_21[i] + 0.01, f"{f1_study_21[i]:.3f}", ha='center', fontsize=9, color='#5a5b48')
    ax.text(x[i] + width, f1_yours_best[i] + 0.01, f"{f1_yours_best[i]:.3f}", ha='center', fontsize=9, color='white', fontweight='bold')

# Ajout des améliorations en pourcentage (flèches et texte)
ax.annotate('+53.27% (vs. [1])', xy=(x[0] + width, f1_yours_best[0]), xytext=(x[0] + width, f1_yours_best[0] + 0.07),
            arrowprops=dict(arrowstyle='->', color='#d64545'), color='#d64545', fontsize=10, ha='center')
ax.annotate('+23.93%', xy=(x[1] + width, f1_yours_best[1]), xytext=(x[1] + width, f1_yours_best[1] + 0.07),
            arrowprops=dict(arrowstyle='->', color='#d64545'), color='#d64545', fontsize=10, ha='center')

plt.tight_layout()
plt.show()
