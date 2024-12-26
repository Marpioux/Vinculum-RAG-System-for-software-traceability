package unisa.gps.etour.control.GestioneUtentiRegistrati;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import unisa.gps.etour.bean.BeanBeneCulturale;
import unisa.gps.etour.bean.BeanPreferenzaDiRicerca;
import unisa.gps.etour.bean.BeanPreferenzeGeneriche;
import unisa.gps.etour.bean.BeanPuntoDiRistoro;
import unisa.gps.etour.bean.BeanTurista;
import unisa.gps.etour.bean.BeanVisitaBC;
import unisa.gps.etour.bean.BeanVisitaPR;
import unisa.gps.etour.repository.DBBeneCulturale;
import unisa.gps.etour.repository.DBPreferenzeDiRicerca;
import unisa.gps.etour.repository.DBPreferenzeGeneriche;
import unisa.gps.etour.repository.DBPuntoDiRistoro;
import unisa.gps.etour.repository.DBTurista;
import unisa.gps.etour.repository.DBVisitaBC;
import unisa.gps.etour.repository.DBVisitaPR;
import unisa.gps.etour.repository.IDBBeneCulturale;
import unisa.gps.etour.repository.IDBPreferenzeDiRicerca;
import unisa.gps.etour.repository.IDBPreferenzeGeneriche;
import unisa.gps.etour.repository.IDBPuntoDiRistoro;
import unisa.gps.etour.repository.IDBTurista;
import unisa.gps.etour.repository.IDBVisitaBC;
import unisa.gps.etour.repository.IDBVisitaPR;
import unisa.gps.etour.util.ControlloDati;
import unisa.gps.etour.util.MessaggiErrore;

/**
 * Class on the Management of Tourist Information
 *
 * @author Federico Leon
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab University of DMI Salerno
 */

public class GestioneTuristaClient extends GestioneTuristaComune implements IGestioneTuristaClient {
    private static final long serialVersionUID = -6161592850721537385L;
    private IDBTurista profiloTurista; // Data Management for tourists
    private IDBPreferenzeGeneriche prefGenTurista; // Preferences, general manager of the tourist
    private IDBPreferenzeDiRicerca prefRicTurista; // Handle search preferences of tourists
    private IDBVisitaBC visitatiBC; // Managing cultural visited
    private IDBVisitaPR visitatiPR; // Manager catering outlets visited
    private IDBBeneCulturale beneCulturale; // Managing cultural heritage (we need only obtain a bean BeneCulturale from its ID
    private IDBPuntoDiRistoro puntoDiRistoro; // Management refreshment areas (use the same object "beneCulturale"

    public GestioneTuristaClient() throws RemoteException {
        super();

        profiloTurista = new DBTurista();
        prefGenTurista = new DBPreferenzeGeneriche();
        prefRicTurista = new DBPreferenzeDiRicerca();
        visitatiBC = new DBVisitaBC();
        visitatiPR = new DBVisitaPR();
        beneCulturale = new DBBeneCulturale();
        puntoDiRistoro = new DBPuntoDiRistoro();
    }

    /**
     * (Non-Javadoc)
     *
     * @see unisa.gps.etour.control.GestioneUtentiRegistrati.IGestioneTuristaClient#cancellaPreferenzeDiRicerca(int, int)
     */
    public BeanPreferenzaDiRicerca cancellaPreferenzeDiRicerca(int pIdTurista, int pIdPreferenzaDiRicerca) throws RemoteException {
        try {
            boolean delete = true;
            BeanPreferenzaDiRicerca prefRic = new BeanPreferenzaDiRicerca();
            ArrayList<BeanPreferenzaDiRicerca> elencoPrefRic = new ArrayList<>();

            elencoPrefRic = prefRicTurista.ottieniPreferenzeDiRicercaDelTurista(pIdTurista);
            Iterator<BeanPreferenzaDiRicerca> elenco = elencoPrefRic.iterator();

            while (elenco.hasNext() && delete == true) {
                prefRic = elenco.next();
                if (prefRic.getId() == pIdPreferenzaDiRicerca)
                    delete = false; // Find the anniversary with the id
                // We leave the interested
                // Cycle
            }

            prefRicTurista.cancellaPreferenzaDiRicercaTurista(pIdTurista, pIdPreferenzaDiRicerca);
            return prefRic;
        } catch (SQLException e) {
            // If the data layer is thrown an exception SQLException
            // It throws RemoteException the remote exception
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        }
    }

    /**
     * (Non-Javadoc)
     *
     * @see unisa.gps.etour.control.GestioneUtentiRegistrati.IGestioneTuristaClient#cancellaPreferenzeGeneriche(int)
     */
    public BeanPreferenzeGeneriche cancellaPreferenzeGeneriche(int pIdTurista) throws RemoteException {
        try {
            BeanPreferenzeGeneriche temp = prefGenTurista.ottieniPreferenzaGenerica(pIdTurista);
            boolean canceled = prefGenTurista.cancellaPreferenzaGenerica(temp.getId());

            if (canceled)
                return temp;
            else
                return null; // The cancellation occurred
        } catch (SQLException e) {
            // If the data layer is thrown an exception SQLException
            // It throws RemoteException the remote exception
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        }
    }

    /**
     * (Non-Javadoc)
     *
     * @see unisa.gps.etour.control.GestioneUtentiRegistrati.IGestioneTuristaClient#inserisciPreferenzaDiRicerca(int, unisa.gps.etour.bean.BeanPreferenzaDiRicerca)
     */
    public boolean inserisciPreferenzaDiRicerca(int pIdTurista, BeanPreferenzaDiRicerca pPreferenzaDiRicerca) throws RemoteException {
        try {
            boolean checkdate = ControlloDati.checkBeanPreferenzaDiRicerca(pPreferenzaDiRicerca);

            if (checkdate) {
                // If the data control is positive
                return prefRicTurista.inserisciPreferenzaDiRicercaDelTurista(pIdTurista, pPreferenzaDiRicerca.getId());
            } else {
                return false; // Data error
            }
        } catch (SQLException e) {
            // If the data layer is thrown an exception SQLException
            // It throws RemoteException the remote exception
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        }
    }

    /**
     * (Non-Javadoc)
     *
     * @see unisa.gps.etour.control.GestioneUtentiRegistrati.IGestioneTuristaClient#inserisciPreferenzeGeneriche(unisa.gps.etour.bean.BeanPreferenzeGeneriche)
     */
    public boolean inserisciPreferenzeGeneriche(BeanPreferenzeGeneriche pPreferenzeGeneriche) throws RemoteException {
        try {
            boolean checkdate = ControlloDati.checkBeanPreferenzeGeneriche(pPreferenzeGeneriche);

            if (checkdate) {
                // If the data control is positive
                return prefGenTurista.inserisciPreferenzaGenerica(pPreferenzeGeneriche);
            } else {
                return false; // Data error
            }
        } catch (SQLException e) {
            // If the data layer is thrown an exception SQLException
            // It throws RemoteException the remote exception
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        }
    }

    /**
     * (Non-Javadoc)
     *
     * @see unisa.gps.etour.control.GestioneUtentiRegistrati.IGestioneTuristaClient#inserisciTurista(unisa.gps.etour.bean.BeanTurista)
     */
    public boolean inserisciTurista(BeanTurista pTurista) throws RemoteException {
        try {
            // Check if the username entered is already present in DB
            BeanTurista temp = profiloTurista.ottieniTurista(pTurista.getUsername());

            // If there is no choice all'username Tourist Offices
            if (temp == null) {
                boolean checkdate = ControlloDati.checkBeanTurista(pTurista);

                if (checkdate) {
                    // If the data control is positive
                    return profiloTurista.inserisciTurista(pTurista);
                } else {
                    return false; // Data error
                }
            } else {
                return false; // Username already exists in DB
            }
        } catch (SQLException e) {
            // If the data layer is thrown an exception SQLException
            // It throws RemoteException the remote exception
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        }
    }

    /**
     * (Non-Javadoc)
     *
     * @see unisa.gps.etour.control.GestioneUtentiRegistrati.IGestioneTuristaClient#modificaPreferenzeGeneriche(unisa.gps.etour.bean.BeanPreferenzeGeneriche)
     */
    public boolean modificaPreferenzeGeneriche(BeanPreferenzeGeneriche pPreferenzeGenericheNuove) throws RemoteException {
        try {
            boolean checkdate = ControlloDati.checkBeanPreferenzeGeneriche(pPreferenzeGenericheNuove);

            if (checkdate) {
                // If the data control is positive
                return prefGenTurista.modificaPreferenzaGenerica(pPreferenzeGenericheNuove);
            } else {
                return false; // Data error
            }
        } catch (SQLException e) {
            // If the data layer is thrown an exception SQLException
            // It throws RemoteException the remote exception
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        }
    }

    /**
     * (Non-Javadoc)
     *
     * @see unisa.gps.etour.control.GestioneUtentiRegistrati.IGestioneTuristaClient#ottieniPreferenzeDiRicerca(int)
     */
    public BeanPreferenzaDiRicerca[] ottieniPreferenzeDiRicerca(int pIdTurista) throws RemoteException {
        // Array containing the list of search preferences tourists ID = pIdTurista
        BeanPreferenzaDiRicerca[] preferences = null;

        try {
            // ** Convert ArrayList (return type of method "ottieniPreferenzeDiRicerca") to simple array **
            ArrayList<BeanPreferenzaDiRicerca> listaPreferenze = prefRicTurista.ottieniPreferenzeDiRicercaDelTurista(pIdTurista);
            preferences = new BeanPreferenzaDiRicerca[listaPreferenze.size()];
            preferences = listaPreferenze.toArray(preferences);
            // ** ** End Conversion
        } catch (SQLException e) {
            // If the data layer is thrown an exception SQLException
            // It throws RemoteException the remote exception
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        }

        return preferences;
    }

    /**
     * (Non-Javadoc)
     *
     * @see unisa.gps.etour.control.GestioneUtentiRegistrati.IGestioneTuristaClient#ottieniPreferenzeGeneriche(int)
     */
    public BeanPreferenzeGeneriche ottieniPreferenzeGeneriche(int pIdTurista) throws RemoteException {
        try {
            return prefGenTurista.ottieniPreferenzaGenerica(pIdTurista);
        } catch (SQLException e) {
            // If the data layer is thrown an exception SQLException
            // It throws RemoteException the remote exception
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        }
    }

    /**
     * (Non-Javadoc)
     *
     * @see unisa.gps.etour.control.GestioneUtentiRegistrati.GestioneTuristaComune#modificaTurista(unisa.gps.etour.bean.BeanTurista)
     */
    public boolean modificaTurista(BeanTurista pprofiloTurista) throws RemoteException {
        try {
            // Boolean variable that will hold true if the bean is correct, false otherwise
            boolean checkdate = ControlloDati.checkBeanTurista(pprofiloTurista);

            if (checkdate) {
                // If the data control is positive
                return profiloTurista.modificaTurista(pprofiloTurista);
            } else {
                return false; // Data error
            }
        } catch (SQLException e) {
            // If the data layer is thrown an exception SQLException
            // It throws RemoteException the remote exception
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        }
    }

    /**
     * (Non-Javadoc)
     *
     * @see unisa.gps.etour.control.GestioneUtentiRegistrati.IGestioneTuristaClient#ottieniBeniCulturaliVisitati(int)
     */
    public BeanVisitaBC[] ottieniBeniCulturaliVisitati(int pIdTurista) throws RemoteException {
        BeanVisitaBC[] visited = null;
        if (pIdTurista > 0) {
            try {
                ArrayList<BeanVisitaBC> listavisitati = visitatiBC.ottieniListaVisitaBCTurista(pIdTurista);
                visited = new BeanVisitaBC[listavisitati.size()];
                visited = listavisitati.toArray(visited);
            } catch (SQLException e) {
                throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
            } catch (Exception e) {
                throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
            }
        }
        return visited;
    }

    /**
     * (Non-Javadoc)
     *
     * @see unisa.gps.etour.control.GestioneUtentiRegistrati.IGestioneTuristaClient#ottieniPuntiDiRistoroVisitati(int)
     */
    public BeanVisitaPR[] ottieniPuntiDiRistoroVisitati(int pIdTurista) throws RemoteException {
        // Array containing the list of catering outlets visited by tourists with id "pIdTurista"
        BeanVisitaPR[] visited = null;
        if (pIdTurista > 0) {
            try {
                ArrayList<BeanVisitaPR> listavisitati = visitatiPR.ottieniListaVisitaPRTurista(pIdTurista);
                visited = new BeanVisitaPR[listavisitati.size()];
                visited = listavisitati.toArray(visited);
            } catch (SQLException e) {
                throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
            } catch (Exception e) {
                throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
            }
        }
        return visited;
    }

    /**
     * (Non-Javadoc)
     *
     * @see unisa.gps.etour.control.GestioneUtentiRegistrati.IGestioneTuristaClient#inserisciBeneCulturaleVisitato(unisa.gps.etour.bean.BeanVisitaBC)
     */
    public boolean inserisciBeneCulturaleVisitato(BeanVisitaBC pVisitaBC) throws RemoteException {
        if (ControlloDati.checkBeanVisitaBC(pVisitaBC)) {
            try {
                return visitatiBC.inserisciVisitaBC(pVisitaBC);
            } catch (SQLException e) {
                throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
            } catch (Exception e) {
                throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
            }
        }
        return false;
    }

    /**
     * (Non-Javadoc)
     *
     * @see unisa.gps.etour.control.GestioneUtentiRegistrati.IGestioneTuristaClient#inserisciPuntoDiRistoroVisitato(unisa.gps.etour.bean.BeanVisitaPR)
     */
    public boolean inserisciPuntoDiRistoroVisitato(BeanVisitaPR pVisitaPR) throws RemoteException {
        if (ControlloDati.checkBeanVisitaPR(pVisitaPR)) {
            try {
                return visitatiPR.inserisciVisitaPR(pVisitaPR);
            } catch (SQLException e) {
                throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
            } catch (Exception e) {
                throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
            }
        }
        return false;
    }

    /**
     * (Non-Javadoc)
     *
     * @see unisa.gps.etour.control.GestioneUtentiRegistrati.IGestioneTuristaClient#inserisciBeneCulturalePreferito(int, int)
     */
    public boolean inserisciBeneCulturalePreferito(int pIdTurista, int pIdBeneCulturale) throws RemoteException {
        if (pIdTurista > 0 && pIdBeneCulturale > 0) {
            try {
                return profiloTurista.inserisciBeneCulturalePreferito(pIdTurista, pIdBeneCulturale);
            } catch (SQLException e) {
                throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
            } catch (Exception e) {
                throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
            }
        }
        return false;
    }

    /**
     * (Non-Javadoc)
     *
     * @see unisa.gps.etour.control.GestioneUtentiRegistrati.IGestioneTuristaClient#inserisciPuntoDiRistoroPreferito(int, int)
     */
    public boolean inserisciPuntoDiRistoroPreferito(int pIdTurista, int pIdPuntoDiRistoro) throws RemoteException {
        if (pIdTurista > 0 && pIdPuntoDiRistoro > 0) {
            try {
                return profiloTurista.inserisciPuntoDiRistoroPreferito(pIdTurista, pIdPuntoDiRistoro);
            } catch (SQLException e) {
                throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
            } catch (Exception e) {
                throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
            }
        }
        return false;
    }

    /**
     * (Non-Javadoc)
     *
     * @see unisa.gps.etour.control.GestioneUtentiRegistrati.IGestioneTuristaClient#cancellaBeneCulturalePreferito(int, int)
     */
    public boolean cancellaBeneCulturalePreferito(int pIdTurista, int pIdBeneCulturale) throws RemoteException {
        if (pIdTurista > 0 && pIdBeneCulturale > 0) {
            try {
                return profiloTurista.cancellaBeneCulturalePreferito(pIdTurista, pIdBeneCulturale);
            } catch (SQLException e) {
                throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
            } catch (Exception e) {
                throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
            }
        }
        return false;
    }

    /**
     * (Non-Javadoc)
     *
     * @see unisa.gps.etour.control.GestioneUtentiRegistrati.IGestioneTuristaClient#cancellaPuntoDiRistoroPreferito(int, int)
     */
    public boolean cancellaPuntoDiRistoroPreferito(int pIdTurista, int pIdPuntoDiRistoro) throws RemoteException {
        if (pIdTurista > 0 && pIdPuntoDiRistoro > 0) {
            try {
                return profiloTurista.cancellaPuntoDiRistoroPreferito(pIdTurista, pIdPuntoDiRistoro);
            } catch (SQLException e) {
                throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
            } catch (Exception e) {
                throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
            }
        }
        return false;
    }

    /**
     * (Non-Javadoc)
     * @see unisa.gps.etour.control.GestioneUtentiRegistrati.IGestioneTuristaClient#ottieniBeniCulturaliPreferiti(int)
     */
    public BeanBeneCulturale[] ottieniBeniCulturaliPreferiti(int pIdTurista) throws RemoteException {
        if (pIdTurista > 0) {
            try {
                // List of cultural favorites of tourists with id = "pIdTurista"
                ArrayList<Integer> listaPreferiti = profiloTurista.ottieniBeniCulturaliPreferiti(pIdTurista);
                // Convert ArrayList to simple array
                BeanBeneCulturale[] favorites = new BeanBeneCulturale[listaPreferiti.size()];
                favorites = new BeanBeneCulturale[listaPreferiti.size()];
                listaPreferiti.toArray(favorites);

                int k = 0; // Cycle counter
                for (Integer i : listaPreferiti) {
                    // Fill the array with the favorite bean of all cultural favorites from the tourist
                    favorites[k++] = beneCulturale.ottieniBeneCulturale(i);
                }
                return favorites;
            } catch (SQLException e) {
                throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
            } catch (Exception e) {
                throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
            }
        }
        return null;
    }

    /**
     * (Non-Javadoc)
     * @see unisa.gps.etour.control.GestioneUtentiRegistrati.IGestioneTuristaClient#ottieniPuntiDiRistoroPreferiti(int)
     */
    public BeanPuntoDiRistoro[] ottieniPuntiDiRistoroPreferiti(int pIdTurista) throws RemoteException {
        if (pIdTurista > 0) {
            try {
                // List of places for refreshment favorite tourist with id = "pIdTurista"
                ArrayList<Integer> listaPreferiti = profiloTurista.ottieniPuntoDiRistoroPreferiti(pIdTurista);

                // Convert ArrayList to simple array
                BeanPuntoDiRistoro[] favorites = new BeanPuntoDiRistoro[listaPreferiti.size()];
                listaPreferiti.toArray(favorites);

                int k = 0; // Cycle counter
                for (Integer i : listaPreferiti) {
                    // Fill the array with the favorite bean of all cultural favorites from the tourist
                    favorites[k++] = puntoDiRistoro.ottieniPuntoDiRistoro(i);
                }
                return favorites;
            } catch (SQLException e) {
                throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
            } catch (Exception e) {
                throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
            }
        }
        return null;
    }
}