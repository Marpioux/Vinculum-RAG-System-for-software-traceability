package unisa.gps.etour.control.GestioneTag;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import unisa.gps.etour.bean.BeanTag;

/**
 * Common interface for managing Tag
 * 
 * Author: Joseph Morelli
 * Version: 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public interface IGestioneTagComune extends Remote {

    /**
     * Method which returns all the tags stored in the database
     * 
     * @return an ArrayList of BeanTag
     * @throws RemoteException Exception Remote
     */
    ArrayList<BeanTag> ottieniTags() throws RemoteException;
}