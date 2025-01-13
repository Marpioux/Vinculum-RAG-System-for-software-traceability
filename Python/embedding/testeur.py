import redis
from langchain_core.documents import Document

# Connexion à Redis
redis_client = redis.StrictRedis(host='localhost', port=6379, decode_responses=True)

# Fonction pour rechercher un document par son contenu
def find_name_by_page_content(page_content):
    # Recherche le contenu dans Redis (par exemple avec un hash ou une clé préfixée)
    search_key = f"document:{page_content[:50]}"  # Utilisez une partie du contenu pour former une clé unique

    # Récupère le nom du fichier associé
    document_name = redis_client.hget(search_key, "text")  # Recherche du 'name' dans les métadonnées
    if document_name:
        return document_name
    else:
        return "Document not found"

# Exemple de récupération du name pour un page_content donné
page_content_example = "Use case name VISUALIZZASTORICOCONVENZIONI"
document_name = find_name_by_page_content(page_content_example)
print(f"Document name: {document_name}")