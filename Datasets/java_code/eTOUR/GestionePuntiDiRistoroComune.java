package unisa.gps.etour.control.GestionePuntiDiRistoro;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import unisa.gps.etour.bean.BeanPuntoDiRistoro;
import unisa.gps.etour.bean.BeanTag;
import unisa.gps.etour.bean.BeanVisitaPR;
import unisa.gps.etour.repository.DBPuntoDiRistoro;
import unisa.gps.etour.repository.DBVisitaPR;
import unisa.gps.etour.repository.DBTag;
import unisa.gps.etour.repository.IDBPuntoDiRistoro;
import unisa.gps.etour.repository.IDBTag;
import unisa.gps.etour.repository.IDBTurista;
import unisa.gps.etour.repository.IDBVisitaPR;
import unisa.gps.etour.util.CostantiGlobali;
import unisa.gps.etour.util.MessaggiErrore;

/**
 * Class that implements the common tasks for the operator of dining and
 * For the Operator Agency
 * 
 * @author Joseph Morelli
 * @version 0.1 © 2007 eTour Project - Copyright by DMI SE @ SA Lab --
 * University of Salerno
 */
public class GestionePuntiDiRistoroComune extends UnicastRemoteObject implements IGestionePuntiDiRistoroComune {

    // Instance for database connections
    private static final long serialVersionUID = 1L;
    protected IDBPuntoDiRistoro puntoRistoro;
    protected IDBTag tags;
    protected IDBVisitaPR feeds;
    protected IDBTurista dbTurista;

    public GestionePuntiDiRistoroComune() throws RemoteException {
        super();
        // Connect to the Database
        try {
            puntoRistoro = new DBPuntoDiRistoro();
            tags = new DBTag();
            feeds = new DBVisitaPR();
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
    }

    /**
     * Method which allows to obtain a BeanPuntoDiRistoro through
     * Connect to database
     */
    public BeanPuntoDiRistoro ottieniPuntoDiRistoro(int pPuntoDiRistoroID) throws RemoteException {
        // Check identifier passed
        if (pPuntoDiRistoroID < 0) {
            throw new RemoteException(MessaggiErrore.ERRORE_DATI);
        }
        // Return Instance
        BeanPuntoDiRistoro toReturn = null;
        try {
            // Revenue data through the instance of the database connection
            toReturn = puntoRistoro.ottieniPuntoDiRistoro(pPuntoDiRistoroID);
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        // Check the variable return, so they do not pass bad data
        // To the caller
        // And triggers an exception if the format of the bean
        if (toReturn == null) {
            throw new RemoteException(MessaggiErrore.ERRORE_FORMATO_BEAN);
        }
        // Return the bean that contains information about Refreshment
        // Required
        return toReturn;
    }

    // Method that returns an ArrayList containing the tag identification of a
    // Some Refreshment
    public ArrayList<BeanTag> ottieniTagPuntoDiRistoro(int pPuntoDiRistoroID) throws RemoteException {
        // Check identifier passed
        if (pPuntoDiRistoroID < 0) {
            throw new RemoteException(MessaggiErrore.ERRORE_DATI);
        }
        // Return Instance
        ArrayList<BeanTag> toReturn = null;
        try {
            // Revenue data through the instance of the database connection
            toReturn = tags.ottieniTagPuntoDiRistoro(pPuntoDiRistoroID);
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        // Check the variable return, so they do not pass bad data
        // To the caller
        // And triggers an exception if the format of the bean
        if (toReturn == null) {
            throw new RemoteException(MessaggiErrore.ERRORE_FORMATO_BEAN);
        }
        // Return the ArrayList containing beans tag of refreshment
        // Passed as parameter
        return toReturn;
    }

    // Returns an array of 10 strings containing the last 10 comments
    // Issued by tourists for Refreshment passed as parameter
    public String[] ottieniUltimiCommenti(int pPuntoDiRistoroID) throws RemoteException {
        // Check the validity identifier passed
        if (pPuntoDiRistoroID < 0) {
            throw new RemoteException(MessaggiErrore.ERRORE_DATI);
        }
        // String that contains only the last 10 comments made
        String[] toReturn = new String[10];
        // ArrayList temporary then save the comments contained in the beans
        // Inside
        ArrayList<BeanVisitaPR> temp = null;
        try {
            // Attempt to retrieve information from the database via
            // The connection instance
            temp = feeds.ottieniListaVisitaPR(pPuntoDiRistoroID);
        } catch (SQLException e) {
            System.out.println("Error in method ottieniUltimiCommenti: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in method ottieniUltimiCommenti: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        // The bean that I receive from the database are already sorted by date
        // Implicitly, so there is no need for sorting algorithms
        for (int i = 0; i < 10; i++) {
            // Insert the text of the comments in cyclically
            // Array
            toReturn[i] = (temp.get(temp.size() - (i + 1))).getCommento();
        }
        // E 'unnecessary control the format of the array
        // Then return the array directly with comments
        return toReturn;
    }

    // Method that returns an ArrayList containing the number of votes
    // Release from 1 to 5 for
    // The Refreshment passed as parameter. The method inserts in order
    // Counters in positions
    // From 0 to 1 vote, 1 vote for 2, etc. ..
    public ArrayList<Integer> ottieniStatistichePuntoDiRistoro(int pPuntoDiRistoroID) throws RemoteException {
        // Check the validity identifier passed
        if (pPuntoDiRistoroID < 0) {
            throw new RemoteException(MessaggiErrore.ERRORE_DATI);
        }
        // ArrayList that allows me to store the bean containing the feedback
        ArrayList<BeanVisitaPR> bVisita = null;
        // ArrayList that allows me to store the counters
        // And then return to the calling method
        ArrayList<Integer> listaRisultati = new ArrayList<>(5);
        // Initialize the array of counters
        for (int i = 0; i < 5; i++) {
            listaRisultati.add(0);
        }
        // Data useful to verify that the issued date back thirty Feedback
        // Days from current date
        Date ultimiTrentaGiorni = new Date(new Date().getTime() - CostantiGlobali.TRENTA_GIORNI);

        System.out.println("The date of 30 days ago: " + ultimiTrentaGiorni);

        try {
            // All feedback Revenue issued for Refreshment
            // Passed as parameter
            bVisita = feeds.ottieniListaVisitaPR(pPuntoDiRistoroID);
            // Iterate the collection of elements to control the bean
            // Visits
            for (Iterator<BeanVisitaPR> iteratoreVisitaPR = bVisita.iterator(); iteratoreVisitaPR.hasNext();) {
                // Recuperto the BeanVisitaPR
                BeanVisitaPR bVisitaTemp = iteratoreVisitaPR.next();
                // ... Do not know what does ...
                System.out.println("The date of this visit is: " + bVisitaTemp.getDataVisita());
                if (bVisitaTemp.getDataVisita().after(ultimiTrentaGiorni)) {
                    listaRisultati.set(bVisitaTemp.getVoto() - 1, listaRisultati.get(bVisitaTemp.getVoto() - 1) + 1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in method ottieniStatistichePuntoDiRistoro: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in method ottieniStatistichePuntoDiRistoro: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        if (listaRisultati == null) {
            throw new RemoteException(MessaggiErrore.ERRORE_FORMATO_BEAN);
        }
        // Return the list of results that should contain counters
        // Comments
        // Issued in the last 30 days
        return listaRisultati;
    }

    // Method that allows you to change the comment issued for a
    // Refreshment
    public boolean modificaFeedbackPuntoDiRistoro(int pPuntoDiRistoroId, BeanVisitaPR nuovaVisita) throws RemoteException {
        // Check the validity of past data
        if ((pPuntoDiRistoroId < 0) || (!(nuovaVisita instanceof BeanVisitaPR))) {
            throw new RemoteException(MessaggiErrore.ERRORE_DATI);
        }
        // Revenue from the database the bean and stores saved
        // In order to verify that the vote has not changed
        // Which had been previously released
        BeanVisitaPR temp = null;
        try {
            temp = feeds.ottieniVisitaPR(pPuntoDiRistoroId, nuovaVisita.getIdTurista());
        } catch (SQLException e) {
            System.out.println("Error in method modificaFeedbackPuntoDiRistoro: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in method modificaFeedbackPuntoDiRistoro: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        // If the vote was amended returns an exception
        if (temp.getVoto() != nuovaVisita.getVoto()) {
            throw new RemoteException(MessaggiErrore.ERRORE_FORMATO_BEAN);
        } else {
            // If the vote has not been amended shall save the new
            // Comment in the database using the specific method of the bean
            try {
                feeds.modificaVisitaPR(nuovaVisita);
                return true;
            } catch (SQLException e) {
                System.out.println("Error in method modificaFeedbackPuntoDiRistoro: " + e.toString());
                throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
            } catch (Exception ee) {
                System.out.println("Error in method modificaFeedbackPuntoDiRistoro: " + ee.toString());
                throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
            }
        }
    }
}