package unisa.gps.etour.control.GestioneUtentiRegistrati;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

import unisa.gps.etour.bean.BeanTurista;
import unisa.gps.etour.repository.DBTurista;
import unisa.gps.etour.repository.IDBTurista;
import unisa.gps.etour.util.MessaggiErrore;

/**
 * Class that implements the common tasks for Operators and Tourist Agency
 * Ie modificaTurista and ottieniTurista
 *
 * @author Joseph Morelli
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public class GestioneTuristaComune extends UnicastRemoteObject implements IGestioneTuristaComune {

    protected IDBTurista tourist;

    // Constructor that calls the superclass constructor
    // UnicastRemoteObject to connect via RMI
    // Instantiate and connect to the database
    public GestioneTuristaComune() throws RemoteException {
        super();
        // Connect to the Database
        try {
            tourist = new DBTurista();
        } catch (Exception e) {
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
    }

    // Method that allows you to change the data of a tourist through its data
    public boolean modificaTurista(BeanTurista pProfiloTurista) throws RemoteException {
        // Check the validity of passed data
        if (pProfiloTurista == null || !(pProfiloTurista instanceof BeanTurista)) {
            throw new RemoteException(MessaggiErrore.ERRORE_FORMATO_BEAN);
        }
        // Execution of the amendment
        try {
            // If the changes were made returns true
            if (tourist.modificaTurista(pProfiloTurista)) {
                return true;
            }
        } catch (SQLException e) {
            // If the data layer sends an exception, throws the remote exception
            System.out.println("Error in method modificaTurista: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            // Unexpected exception caused by other factors
            System.out.println("Error in method modificaTurista: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        // If there were no exceptions but the changes are not made, returns false
        return false;
    }

    // Method to obtain the bean with data from the Tourist identified by the parameter passed
    public BeanTurista ottieniTurista(int pIdTurista) throws RemoteException {
        // Check the validity identifier
        if (pIdTurista < 0) {
            throw new RemoteException(MessaggiErrore.ERRORE_DATI);
        }
        BeanTurista toReturn = null; // variable return
        // Retrieve data
        try {
            // Are requested to return the bean layer on the tourist with id equal to pIdTurista
            toReturn = tourist.ottieniTurista(pIdTurista);
            if (toReturn == null) {
                throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
            }
        } catch (SQLException e) {
            // If the data layer sends an exception, throws the remote exception
            System.out.println("Error in method ottieniTurista: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            // Unexpected exceptions caused by other factors
            System.out.println("Error in method ottieniTurista: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        // Return the result
        return toReturn;
    }
}