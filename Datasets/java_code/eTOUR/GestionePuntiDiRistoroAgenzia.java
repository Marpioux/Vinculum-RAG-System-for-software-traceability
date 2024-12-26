package unisa.gps.etour.control.GestionePuntiDiRistoro;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import unisa.gps.etour.bean.BeanConvenzione;
import unisa.gps.etour.bean.BeanPuntoDiRistoro;
import unisa.gps.etour.bean.BeanTag;
import unisa.gps.etour.bean.BeanTurista;
import unisa.gps.etour.bean.BeanVisitaPR;
import unisa.gps.etour.repository.DBConvenzione;
import unisa.gps.etour.repository.DBTurista;
import unisa.gps.etour.repository.IDBConvenzione;
import unisa.gps.etour.util.MessaggiErrore;

/**
 * Class containing methods for managing Refreshments by Operator Agency
 * 
 * author Joseph Morelli
 * version 0.1 © 2007 eTour Project - Copyright by DMI SE @ SA Lab -- University of Salerno
 */
public class GestionePuntiDiRistoroAgenzia extends GestionePuntiDiRistoroComune implements IGestionePuntiDiRistoroAgenzia {

    private static final long serialVersionUID = 1L;
    private DBTurista dbTurista;

    // Constructor
    public GestionePuntiDiRistoroAgenzia() throws RemoteException {
        // Call the constructor of the inherited class to instantiate Database connections
        super();
        dbTurista = new DBTurista();
    }

    // Method that allows the operator to cancel an agency point of Refreshment
    // Passing as parameter the ID of the same Refreshment
    public boolean cancellaPuntoDiRistoro(int pPuntoDiRistoroID) throws RemoteException {
        // Check the validity identifier
        if (pPuntoDiRistoroID < 0) {
            throw new RemoteException(MessaggiErrore.ERRORE_DATI);
        }
        try {
            // Execute the method that clears the Refreshment from the Database
            // And in case of operation successful return true
            if (puntoRistoro.cancellaPuntoDiRistoro(pPuntoDiRistoroID)) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error in method cancellaPuntoDiRistoro: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in method cancellaPuntoDiRistoro: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        // If no operations were successful return false
        return false;
    }

    // Method that allows the operator to include in the Agency database the new Refreshment with the information contained in the bean
    public boolean inserisciPuntoDiRistoro(BeanPuntoDiRistoro pPuntoDiRistoro) throws RemoteException {
        // Check the validity of the bean as a parameter and if triggers except remote
        if ((pPuntoDiRistoro == null) || (!(pPuntoDiRistoro instanceof BeanPuntoDiRistoro))) {
            throw new RemoteException(MessaggiErrore.ERRORE_FORMATO_BEAN);
        }
        try {
            // Calling the method of the class that operates on the database
            // Insert the new Refreshment
            if (puntoRistoro.inserisciPuntoDiRistoro(pPuntoDiRistoro)) {
                // In the case where the operations were successful end
                // Returns true
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error in method inserisciPuntoDiRistoro: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in method inserisciPuntoDiRistoro: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        // If the operation is not successful return false
        return false;
    }

    // Method for obtaining an ArrayList with all the points Bean Refreshments
    public ArrayList<BeanPuntoDiRistoro> ottieniPuntiDiRistoro() throws RemoteException {
        // ArrayList to return to the end of the method
        ArrayList<BeanPuntoDiRistoro> toReturn = null;
        try {
            // Get the list of Refreshments through the class
            // Connect to database
            // And save the list itself nell'ArrayList
            toReturn = puntoRistoro.ottieniListaPR();
        } catch (SQLException e) {
            System.out.println("Error in method ottieniPuntiDiRistoro: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in method ottieniPuntiDiRistoro: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        // Check the ArrayList to return so as not to pass null values to the caller
        if (toReturn == null) {
            throw new RemoteException(MessaggiErrore.ERRORE_FORMATO_BEAN);
        }
        // Return the ArrayList with all the refreshment
        return toReturn;
    }

    // Method that allows you to get all the refreshment that have a Convention on or off depending on the parameter passed
    public ArrayList<BeanPuntoDiRistoro> ottieniPuntiDiRistoro(boolean statoConvenzione) throws RemoteException {
        // Array that allows me to store all the refreshment and which will remove depending on the parameter passed to the refreshment active or not
        ArrayList<BeanPuntoDiRistoro> toReturn = null;
        // Array that allows me to store all the refreshment active using the database connection
        ArrayList<BeanPuntoDiRistoro> active = null;
        // Instance to connect to the database
        IDBConvenzione conv = new DBConvenzione();
        try {
            // Connect all proceeds from the refreshment Assets
            active = conv.ottieniListaConvenzioneAttivaPR();
        } catch (SQLException e) {
            System.out.println("Error in method ottieniPuntiDiRistoro (boolean): " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in method ottieniPuntiDiRistoro (boolean): " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        // If you want to get the refreshment active, then return directly to those passed by the connection to the database
        if (statoConvenzione) {
            // Check the contents dell'ArrayList so as not to return null values to the caller
            if (active == null) {
                throw new RemoteException(MessaggiErrore.ERRORE_FORMATO_BEAN);
            }
            return active;
        } else {
            try {
                // Connect all proceeds from the refreshment then perform comparisons
                toReturn = puntoRistoro.ottieniListaPR();
            } catch (SQLException e) {
                System.out.println("Error in method ottieniPuntiDiRistoro (boolean): " + e.toString());
                throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
            } catch (Exception ee) {
                System.out.println("Error in method ottieniPuntiDiRistoro (boolean): " + ee.toString());
                throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
            }
            // Size dell'ArrayList containing all of gourmet could change size if you remove some element
            int dim = toReturn.size();
            // Variable that allows me to understand whether to remove a Point Refreshments from array that then I must return
            boolean present;
            // First loop to loop through all the ArrayList elements of All Refreshments
            for (int i = 0; i < dim; i++) {
                present = false;
                // Second loop to loop through all the ArrayList elements Cones just Refreshments active
                for (int j = 0; j < active.size(); j++) {
                    // If the catering points in question has the ID equal to one of those assets, then set this to true
                    if (active.get(j).getId() == toReturn.get(i).getId()) {
                        present = true;
                    }
                }
                // If the catering points in question has a Convention active removes it from those to be returned
                if (present) {
                    toReturn.remove(i);
                }
            }
        }
        // Return the ArrayList obtained
        return toReturn;
    }

    // Method that allows you to change the past as a refreshment Parameter
    public boolean modificaPuntoDiRistoro(BeanPuntoDiRistoro pPuntoDiRistoroAggiornato) throws RemoteException {
        // Check the validity of the bean as a parameter and if trigger an exception remote
        if (pPuntoDiRistoroAggiornato == null || (!(pPuntoDiRistoroAggiornato instanceof BeanPuntoDiRistoro))) {
            throw new RemoteException(MessaggiErrore.ERRORE_FORMATO_BEAN);
        }
        try {
            // Call the method to change the database connection The Refreshment
            if (puntoRistoro.modificaPuntoDiRistoro(pPuntoDiRistoroAggiornato)) {
                // Return a positive value if the operation was successful
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error in method modificaPuntoDiRistoro: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in method modificaPuntoDiRistoro: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        // Return false if the operation is successful you should
        return false;
    }

    // Method to obtain the Bean a particular point Refreshment whose Identifier is passed as parameter
    public BeanPuntoDiRistoro ottieniPuntoDiRistoro(int pPuntoDiRistoroID) throws RemoteException {
        // Check the validity identifier
        if (pPuntoDiRistoroID < 0) {
            throw new RemoteException(MessaggiErrore.ERRORE_DATI);
        }
        // Bean to return to the caller
        BeanPuntoDiRistoro toReturn = null;
        try {
            // Revenue catering points in the issue by connecting to Database
            toReturn = puntoRistoro.ottieniPuntoDiRistoro(pPuntoDiRistoroID);
        } catch (SQLException e) {
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in method ottieniPuntoDiRistoro: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        // Check the bean to be returned in order not to return null values to the caller
        if (toReturn == null) {
            throw new RemoteException(MessaggiErrore.ERRORE_FORMATO_BEAN);
        }
        // Return the bean of Refreshment
        return toReturn;
    }

    // Method that allows you to activate a particular convention to a Point Passed as parameter Refreshments
    public boolean attivaConvenzione(int pPuntoDiRistoroID, BeanConvenzione pConv) throws RemoteException {
        // Check the validity of parameters passed
        if ((pPuntoDiRistoroID < 0) || (pConv == null) || (!(pConv instanceof BeanConvenzione))) {
            throw new RemoteException(MessaggiErrore.ERRORE_DATI);
        }
        // Check the data further
        if (pConv.getIdPuntoDiRistoro() != pPuntoDiRistoroID) {
            throw new RemoteException(MessaggiErrore.ERRORE_DATI);
        }
        IDBConvenzione conv = null;
        try {
            // Instantiate the class to connect to the database
            conv = new DBConvenzione();
            // If the Convention is not yet active, previously provided to activate it locally and then pass the bean to the database changed
            if (conv.ottieniConvezioneAttiva(pPuntoDiRistoroID) == null) {
                pConv.setAttiva(true);
                conv.modificaConvenzione(pConv);
                return true;
            }
        } catch (SQLException e) {
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in method attivaConvenzione: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        // If the operation is successful you should return false
        return false;
    }

    // Method that returns a HashMap containing, for Refreshment Passed as a parameter, the feedback associated with it
    public HashMap<BeanVisitaPR, String> ottieniFeedbackPuntoDiRistoro(int pPuntoDiRistoroID) throws RemoteException {
        // Check the ID passed as a parameter
        if (pPuntoDiRistoroID < 0) {
            throw new RemoteException(MessaggiErrore.ERRORE_DATI);
        }
        // Instantiate the map and the performance of ArrayList that I will use Method
        HashMap<BeanVisitaPR, String> mappaRitorno = null;
        ArrayList<BeanVisitaPR> bvisita = null;
        try {
            // Here I take the list of all visits to the PR passed as Parameter
            bvisita = feed.ottieniListaVisitaPR(pPuntoDiRistoroID);
            // Instantiate the map of the same size as the list of BeanVisitaPR
            mappaRitorno = new HashMap<>(bvisita.size());
            // Here we begin to iterate on each visit to add its Username
            for (Iterator<BeanVisitaPR> iteratoreVisitaPR = bvisita.iterator(); iteratoreVisitaPR.hasNext();) {
                // Recuperto the BeanVisitaPR
                BeanVisitaPR bVisitaTemp = iteratoreVisitaPR.next();
                // Retrieve the tourist who left the comment that I am examining
                BeanTurista bTuristaTemp = dbTurista.ottieniTurista(bVisitaTemp.getIdTurista());
                // Get the username of the Tourist
                String usernameTuristaTemp = bTuristaTemp.getUsername();
                // Put the pair in the map
                mappaRitorno.put(bVisitaTemp, usernameTuristaTemp);
            }
        } catch (SQLException e) {
            System.out.println("Error in method ottieniFeedbackPR: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in method ottieniFeedbackPR: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        // Check the return parameter so as not to pass null values to the database
        if (mappaRitorno == null) {
            throw new RemoteException(MessaggiErrore.ERRORE_FORMATO_BEAN);
        }
        return mappaRitorno;
    }

    // Method to insert a tag from those of a refreshment
    public boolean cancellaTagPuntoDiRistoro(int pPuntoDiRistoroId, int pTagId) throws RemoteException {
        // Check the validity of past data
        if ((pPuntoDiRistoroId < 0) || (pTagId < 0)) {
            throw new RemoteException(MessaggiErrore.ERRORE_DATI);
        }
        // ArrayList which stores all the tags
        ArrayList<BeanTag> tags;
        // Boolean variable to check if the Refreshment holds the tag you want to delete
        boolean present = false;
        try {
            // Use the method through the class of database connection
            tags = tag.ottieniTagPuntoDiRistoro(pPuntoDiRistoroId);
        } catch (SQLException e) {
            System.out.println("Error in method cancellaTagPuntoDiRistoro: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in method cancellaTagPuntoDiRistoro: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        // Check if the tag is present cycle currently Between those of Refreshment
        for (BeanTag t : tags) {
            if (t.getId() == pTagId) {
                present = true;
            }
        }
        // If the tag is present among those of eateries, then provides for executing the erase operation
        if (present) {
            try {
                return tag.cancellaTagPuntoDiRistoro(pPuntoDiRistoroId, pTagId);
            } catch (SQLException e) {
                System.out.println("Error in method cancellaTagPuntoDiRistoro: " + e.toString());
                throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
            } catch (Exception ee) {
                System.out.println("Error in method cancellaTagPuntoDiRistoro: " + ee.toString());
                throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
            }
        }
        // In case something did not come to fruition return false
        return false;
    }

    // Method to delete a tag from those of a refreshment
    // The operations are identical to those above, except for Control over the presence of the tag from those of Refreshment
    // Which should give negative results, and the call here is the method of Insert
    public boolean inserisciTagPuntoDiRistoro(int pPuntoDiRistoroId, int pTagId) throws RemoteException {
        if ((pPuntoDiRistoroId < 0) || (pTagId < 0)) {
            throw new RemoteException(MessaggiErrore.ERRORE_DATI);
        }

        ArrayList<BeanTag> tags;
        boolean present = false;
        try {
            tags = tag.ottieniTagPuntoDiRistoro(pPuntoDiRistoroId);
        } catch (SQLException e) {
            System.out.println("Error in method inserisciTagPuntoDiRistoro: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in method inserisciTagPuntoDiRistoro: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        for (BeanTag t : tags) {
            if (t.getId() == pTagId) {
                present = true;
            }
        }
        // Check that the Refreshment has not already specified tag
        if (!present) {
            try {
                // Calling the method of adding the class via Connect to database
                return tag.aggiungeTagPuntoDiRistoro(pPuntoDiRistoroId, pTagId);
            } catch (SQLException e) {
                System.out.println("Error in method inserisciTagPuntoDiRistoro: " + e.toString());
                throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
            } catch (Exception ee) {
                System.out.println("Error in method inserisciTagPuntoDiRistoro: " + ee.toString());
                throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
            }
        }
        // Return false if some operation is not successful you should
        return false;
    }
}