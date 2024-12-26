package unisa.gps.etour.control.GestioneTag;

import java.rmi.RemoteException;
import unisa.gps.etour.bean.BeanTag;

/**
 * Interface for the tag handler by the Operator Agency
 * 
 * Author: Joseph Morelli
 * Version: 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University Of Salerno
 */
public interface IGestioneTagOperatoreAgenzia extends IGestioneTagComune {

    /**
     * Method to insert a new tag
     * 
     * @param pTagNuovo containing all the data of the new Tag
     * @return true if the operation is successful, false otherwise
     * @throws RemoteException Exception Remote
     */
    boolean inserisciTag(BeanTag pTagNuovo) throws RemoteException;

    /**
     * Method for the cancellation of an existing tag
     * 
     * @param pTagID to identify the tags in question
     * @return true if the operation is successful, false otherwise
     * @throws RemoteException Exception Remote
     */
    boolean cancellaTag(int pTagID) throws RemoteException;

    /**
     * Method for editing a Tag
     * 
     * @param pTagModificato containing the details of the new Tag
     * @return true if the operation is successful, false otherwise
     * @throws RemoteException Exception Remote
     */
    boolean modificaTag(BeanTag pTagModificato) throws RemoteException;

    /**
     * Method which returns a tag identified by its ID
     * 
     * @param pTagID to identify a particular tag
     * @return a BeanTag containing data Tag concerned
     * @throws RemoteException Exception Remote
     */
    BeanTag ottieniTag(int pTagID) throws RemoteException;
}