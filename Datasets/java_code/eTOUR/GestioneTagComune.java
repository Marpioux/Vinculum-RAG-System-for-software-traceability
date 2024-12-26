package unisa.gps.etour.control.GestioneTag;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import unisa.gps.etour.bean.BeanTag;
import unisa.gps.etour.repository.DBTag;
import unisa.gps.etour.repository.IDBTag;
import unisa.gps.etour.util.MessaggiErrore;

/**
 * Class that implements the common tasks for the use of tags
 *
 * @author Joseph Morelli
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University of Salerno
 */
public class GestioneTagComune extends UnicastRemoteObject implements IGestioneTagComune {

    private static final long serialVersionUID = 1L;
    // Object for the database connection
    protected IDBTag tag;

    public GestioneTagComune() throws RemoteException {
        super();
        // Connect to the Database
        try {
            tag = new DBTag();
        } catch (Exception e) {
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
    }

    // Method that returns all tags
    public ArrayList<BeanTag> ottieniTags() throws RemoteException {
        // ArrayList to fill with the tags to return
        ArrayList<BeanTag> toReturn;
        // Retrieve data from Database
        try {
            // Get the information from the Database
            toReturn = tag.ottieniListaTag();
        } catch (SQLException e) {
            System.out.println("Error in method ottieniTags: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in method ottieniTags: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        // Check the data back in order not to return null values
        if (toReturn == null) {
            throw new RemoteException(MessaggiErrore.ERRORE_FORMATO_BEAN);
        }
        return toReturn;
    }
}