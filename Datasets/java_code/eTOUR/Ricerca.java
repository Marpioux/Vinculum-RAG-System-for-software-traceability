package unisa.gps.etour.control.GestioneRicerche;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import unisa.gps.etour.bean.BeanBeneCulturale;
import unisa.gps.etour.bean.BeanPuntoDiRistoro;
import unisa.gps.etour.bean.BeanTag;
import unisa.gps.etour.repository.DBBeneCulturale;
import unisa.gps.etour.repository.DBPuntoDiRistoro;
import unisa.gps.etour.repository.DBTag;
import unisa.gps.etour.repository.IDBBeneCulturale;
import unisa.gps.etour.repository.IDBPuntoDiRistoro;
import unisa.gps.etour.repository.IDBTag;
import unisa.gps.etour.util.ControlloDati;
import unisa.gps.etour.util.MessaggiErrore;
import unisa.gps.etour.util.Punto3D;

public class Search extends UnicastRemoteObject implements IRicerca {

    private static final long serialVersionUID = -6009809097302884655L;

    public Search() throws RemoteException {
        super();
    }

    // Search Parameters
    private int pIdTurista = -1;
    private String pParoleChiave;
    private double pRaggioMax;
    private Punto3D pPosizioneUtente;
    private int pElementiPerPagina = -1;
    private byte pTipologiaSito = -1;

    // Objects for the database connection
    private IDBBeneCulturale BeneCulturale = new DBBeneCulturale();
    private IDBPuntoDiRistoro PuntoDiRistoro = new DBPuntoDiRistoro();
    private IDBTag Tag = new DBTag();

    // List of tags obtained
    private ArrayList<BeanTag> pTags;

    // Search Results pages for partitioned
    private Hashtable<Integer, ArrayList<BeanBeneCulturale>> risultatiRicercaBeniCulturali = new Hashtable<>();
    private Hashtable<Integer, ArrayList<BeanPuntoDiRistoro>> risultatiRicercaPuntiDiRistoro = new Hashtable<>();

    // Quantity of items and pages results in the research phase
    private int numeroPagineRisultati = -1;
    private int numeroElementiRicerca = -1;

    /** 
     * (Non-Javadoc)
     * @see unisa.gps.etour.control.GestioneRicerche.IRicerca#search(int, java.lang.String, int[], double, int, unisa.gps.etour.util.Punto3D, byte)
     */
    public int search(int pIdTurista, String pParoleChiave, int[] pTagsId,
                      double pRaggioMax, int pElementiPerPagina,
                      Punto3D pPosizioneUtente, byte pTipologiaSito)
            throws RemoteException {
        // Reset the parameters of the previous search
        azzeraParametri();

        // Start checking the correctness of the search parameters
        try {
            this.pIdTurista = pIdTurista;
            this.pTags = arrayToArrayListTag(pTagsId);
            this.pParoleChiave = ControlloDati.correggiStringa(pParoleChiave,
                    true, true, "", ControlloDati.LUNGHEZZA_MASSIMA);
            this.pRaggioMax = pRaggioMax;
            this.pPosizioneUtente = pPosizioneUtente;
            this.pTipologiaSito = pTipologiaSito;

            // If the parameters are valid, I get the number of results
            if (pElementiPerPagina > 0 &&
                ((pTipologiaSito == 0) || (pTipologiaSito == 1)) &&
                pRaggioMax > 0 &&
                pPosizioneUtente != null &&
                (this.numeroElementiRicerca = ottieniNumeroElementiRicercaSpecializzato()) >= 0) {
                
                this.pElementiPerPagina = pElementiPerPagina;
                this.numeroPagineRisultati = calcolaNumeroPagineRicerca();
                return this.numeroElementiRicerca;
            }

            // Otherwise returns -1
            else
                return -1;
        } catch (SQLException e) {
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception e) {
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
    }

    /** 
     * (Non-Javadoc)
     * @see unisa.gps.etour.control.GestioneRicerche.IRicerca#ottieniPaginaRisultatiBeneCulturale(int)
     */
    public BeanBeneCulturale[] ottieniPaginaRisultatiBeneCulturale(int pPagina)
            throws RemoteException {
        // Check that the type of site is valid and that the page is in the range
        if ((this.pTipologiaSito == BENE_CULTURALE) && controllaPagina(pPagina)) {
            // Transform the list obtained in array
            BeanBeneCulturale[] results = new BeanBeneCulturale[calcolaNumeroElementiPagina(pPagina)];
            results = ricercaPerPagina(pPagina).toArray(results);
            return results;
        } else
            return null;
    }

    /** 
     * (Non-Javadoc)
     * @see unisa.gps.etour.control.GestioneRicerche.IRicerca#ottieniPaginaRisultatiPuntoDiRistoro(int)
     */
    public BeanPuntoDiRistoro[] ottieniPaginaRisultatiPuntoDiRistoro(int pPagina)
            throws RemoteException {
        // Check that the type of site is valid and that the page is in the range
        if ((this.pTipologiaSito == PUNTO_DI_RISTORO) && controllaPagina(pPagina)) {
            // Transform the list obtained in array
            BeanPuntoDiRistoro[] results = new BeanPuntoDiRistoro[calcolaNumeroElementiPagina(pPagina)];
            results = ricercaPerPagina(pPagina).toArray(results);
            return results;
        } else
            return null;
    }

    /** 
     * (Non-Javadoc)
     * @see unisa.gps.etour.control.GestioneRicerche.IRicerca#ottieniNumeroElementiRicerca()
     */
    public int ottieniNumeroElementiRicerca() throws RemoteException {
        return numeroElementiRicerca;
    }

    /** 
     * (Non-Javadoc)
     * @see unisa.gps.etour.control.GestioneRicerche.IRicerca#ottieniNumeroPagineRicerca()
     */
    public int ottieniNumeroPagineRicerca() throws RemoteException {
        return numeroPagineRisultati;
    }

    /**
     * Method for returning the number of findings from research
     *
     * @return number of findings from research. On error Returns -1
     * @throws SQLException Exception Connecting to Database
     */
    private int ottieniNumeroElementiRicercaSpecializzato() throws SQLException {
        // Check the type of site and gets the number of results
        if (controllaIdTurista()) {
            switch (pTipologiaSito) {
                case BENE_CULTURALE:
                    return pIdTurista > 0 ? BeneCulturale.ottieniNumeroElementiRicercaAvanzata(
                            this.pIdTurista, this.pParoleChiave,
                            this.pTags, this.pPosizioneUtente,
                            this.pRaggioMax) : BeneCulturale.ottieniNumeroElementiRicerca(
                            this.pParoleChiave,
                            this.pTags, this.pPosizioneUtente,
                            this.pRaggioMax);
                case PUNTO_DI_RISTORO:
                    return pIdTurista > 0 ? PuntoDiRistoro.ottieniNumeroElementiRicercaAvanzata(
                            this.pIdTurista, this.pParoleChiave,
                            this.pTags, this.pPosizioneUtente,
                            this.pRaggioMax) : PuntoDiRistoro.ottieniNumeroElementiRicerca(
                            this.pParoleChiave,
                            this.pTags, this.pPosizioneUtente,
                            this.pRaggioMax);
            }
        }
        return -1;
    }

    /**
     * Method for returning the list of results
     *
     * @param pNumeroPagina range of results to return
     * @return Container sites emerged in the research phase as the interval
     * @throws RemoteException Exception Remote
     */
    private ArrayList<?> ricercaPerPagina(int pNumeroPagina)
            throws RemoteException {

        // Declare the list of sites related to the page input
        ArrayList<?> risultatiPaginaCorrente = null;
        try {
            if (controllaIdTurista()) {
                // Check the type of site
                switch (pTipologiaSito) {
                    case BENE_CULTURALE:
                        // If the list has not already been obtained previously
                        // Search
                        if ((risultatiPaginaCorrente = risultatiRicercaBeniCulturali.get(pNumeroPagina)) == null) {
                            risultatiPaginaCorrente = (ArrayList<?>) (pIdTurista > 0 ? BeneCulturale
                                    .ricercaAvanzata(pIdTurista, pParoleChiave,
                                            pTags, pNumeroPagina,
                                            pElementiPerPagina,
                                            pPosizioneUtente, pRaggioMax)
                                    : BeneCulturale.ricerca(pParoleChiave,
                                            pTags, pNumeroPagina,
                                            pElementiPerPagina,
                                            pPosizioneUtente, pRaggioMax));
                            risultatiRicercaBeniCulturali.put(
                                    pNumeroPagina,
                                    (ArrayList<BeanBeneCulturale>) risultatiPaginaCorrente);
                        }
                        break;

                    case PUNTO_DI_RISTORO:
                        // If the list has not already been obtained previously
                        // Search
                        if ((risultatiPaginaCorrente = risultatiRicercaPuntiDiRistoro.get(pNumeroPagina)) == null) {
                            risultatiPaginaCorrente = (ArrayList<?>) (pIdTurista > 0 ? PuntoDiRistoro
                                    .ricercaAvanzata(pIdTurista, pParoleChiave,
                                            pTags, pNumeroPagina,
                                            pElementiPerPagina,
                                            pPosizioneUtente, pRaggioMax)
                                    : PuntoDiRistoro.ricerca(pParoleChiave,
                                            pTags, pNumeroPagina,
                                            pElementiPerPagina,
                                            pPosizioneUtente, pRaggioMax));
                            risultatiRicercaPuntiDiRistoro.put(
                                    pNumeroPagina,
                                    (ArrayList<BeanPuntoDiRistoro>) risultatiPaginaCorrente);
                        }
                        break;
                }
            }
            // Return the results
            return risultatiPaginaCorrente;
        } catch (SQLException e) {
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception e) {
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
    }

    /**
     * Method for calculating the number of pages found at Search
     *
     * @return many pages in the research phase
     */
    private int calcolaNumeroPagineRicerca() {
        // If the number of pages is greater than or equal to 0 returns the number of Pages
        if (numeroPagineRisultati >= 0) return numeroPagineRisultati;
        // If the number of elements is a multiple of the number of items per page
        // Return their relationship
        if ((numeroElementiRicerca % pElementiPerPagina == 0))
            return (numeroElementiRicerca / pElementiPerPagina);
        // Otherwise returns their relationship + 1
        return (numeroElementiRicerca / pElementiPerPagina) + 1;
    }

    /**
     * Method for calculating the number of elements of nell'intevallo A given page
     *
     * @param nPagina results
     * @return number of elements in a page
     */
    private int calcolaNumeroElementiPagina(int nPagina) {
        // If page number is the last return their form
        if (nPagina == (numeroPagineRisultati - 1))
            return (numeroElementiRicerca % pElementiPerPagina);
        // Otherwise returns the number of items per page
        else
            return (pElementiPerPagina);
    }

    /**
     * Method for checking the validity of a page
     *
     * @param pPagina page to check
     * @return true if the page is valid
     */
    private boolean controllaPagina(int pPagina) {
        // If page number is in the range returns true
        return (pPagina >= 0 && pPagina < numeroPagineRisultati);
    }

    /**
     * Method for checking the correctness of the identifier Tourist
     *
     * @return true if the identifier is valid, false otherwise
     */
    private boolean controllaIdTurista() {
        return ((pIdTurista > 0) || (pIdTurista == -1));
    }

    /**
     * Method to reset all the variables related to a search
     *
     * @return A constant that indicates the correct zero
     */
    private int azzeraParametri() {
        // Clears all the search parameters
        pIdTurista = -1;
        pTipologiaSito = -1;
        pElementiPerPagina = -1;
        numeroElementiRicerca = -1;
        numeroPagineRisultati = -1;

        risultatiRicercaBeniCulturali.clear();
        risultatiRicercaPuntiDiRistoro.clear();

        return -1;
    }

    /**
     * Method for the detection and conversion of data tags in struttara List
     *
     * @param pTagsId Search Tags
     * @return List Search Tags
     * @throws SQLException
     */
    private ArrayList<BeanTag> arrayToArrayListTag(int[] pTagsId) throws SQLException {
        // Initialize the list of Tags
        ArrayList<BeanTag> pTags = new ArrayList<>();
        // If the list of identifiers of the tag is empty I get the tags
        if (pTagsId == null) {
            BeanTag currentTag = null;
            try {
                // Loop for the insertion of tags found in the list
                for (int i = 0; i < pTagsId.length; i++) {
                    // If the identifier is greater than 0
                    if (pTagsId[i] > 0) {
                        currentTag = Tag.ottieniTag(pTagsId[i]);
                        // Check correctness on tags
                        if (ControlloDati.checkBeanTag(currentTag))
                            pTags.add(currentTag);
                    }
                }
            } catch (SQLException e) {
                throw new SQLException(MessaggiErrore.ERRORE_DBMS);
            }
        }
        // Return the list of tags
        return pTags;
    }
}