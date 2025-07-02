import os
import getpass
from utilities.utilities import initialize_system
from langchain_redis import RedisVectorStore
from langchain_openai import OpenAIEmbeddings
from langchain_core.documents import Document
os.environ["PYDANTIC_V2"] = "1"

index_name = "Albergatev1"
model, embeddings, redis_client, vector_store = initialize_system(index_name)

retriever = vector_store.as_retriever()
results = retriever.invoke(""" Requisito: Gestione delle stanze commissionate ad agenzia.Descrizione:
Alcune delle stanze dell�albergo per determinati periodi potrebbero essere date 
in gestione ad una agenzia viaggi, in questo caso tali stanze risulteranno per 
quei periodi commissionate, quindi non  disponibili. Quando l�agenzia invia 
delle prenotazioni deve essere possibile passare le stanze da commissionate ad 
assegnate, ovviamente solo per il periodo della prenotazione. Le stanze 
commissionate diventeranno disponibili, nel caso in cui nessuna prenotazione 
giunga dall'agenzia entro la data concordata al momento della stipulazione del 
contratto di commissionamento. 
Input richiesto:
Informazioni sugli accordi presi con le agenzie ovvero numero di stanza 
commissionata, periodo di commissionamento (data di inizio e di fine), data 
dopo la quale la stanza, se non occupata, pu� essere considerata disponibile.
Output desiderato:
Situazione aggiornata delle stanze.
Criterio di """)

print(results)
for doc in results:
    print(doc.metadata["name"])
    print(doc.page_content[:300])
