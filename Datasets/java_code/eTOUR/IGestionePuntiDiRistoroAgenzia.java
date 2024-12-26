package unisa.gps.etour.control.GestionePuntiDiRistoro;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import unisa.gps.etour.bean.BeanConvenzione;
import unisa.gps.etour.bean.BeanPuntoDiRistoro;
import unisa.gps.etour.bean.BeanVisitaPR;

/**
 * Interface for refreshments on the side of the agency
 * 
 * @author Joseph Morelli
 * @version 0.1 © 2007 eTour Project - Copyright by DMI SE @ SA Lab - University
 * Of Salerno
 */
public interface IGestionePuntiDiRistoro extends IGestionePuntiDiRistoroAgenzia, IGestionePuntiDiRistoroComune {

    /**
     * Method for inserting a new Refreshment
     * 
     * @param pPuntoDiRistoro containing all the data from the Refreshment
     * @throws RemoteException Exception Remote
     */
    boolean inserisciPuntoDiRistoro(BeanPuntoDiRistoro pPuntoDiRistoro) throws RemoteException;

    /**
     * Method for deleting a refreshment bar with ID
     * 
     * @param pPuntoDiRistoroID for the unique identification of point
     * @throws RemoteException Exception Remote
     */
    boolean cancellaPuntoDiRistoro(int pPuntoDiRistoroID) throws RemoteException;

    /**
     * Method to return all the refreshment of the DataBase
     * 
     * @return ArrayList containing all the beans of the present Refreshments
     * @throws RemoteException Exception Remote
     */
    ArrayList<BeanPuntoDiRistoro> ottieniPuntiDiRistoro() throws RemoteException;

    /**
     * Method to return all the refreshment with convention Active or not
     * 
     * @param statoConvenzione for the type of eateries by
     * @return ArrayList containing all the beans of the present Refreshments
     * @throws RemoteException Exception Remote
     */
    ArrayList<BeanPuntoDiRistoro> ottieniPuntiDiRistoro(boolean statoConvenzione) throws RemoteException;

    /**
     * Method for inserting a new convention for a certain point Refreshments
     * 
     * @param pPuntoDiRistoroID integer that uniquely identifies the point
     * @param pConv Convention that will enable
     * @return boolean for confirmation of operation
     * @throws RemoteException Exception Remote
     */
    boolean attivaConvenzione(int pPuntoDiRistoroID, BeanConvenzione pConv) throws RemoteException;

    /**
     * Method to get all the feedback associated to a certain point Refreshments
     * 
     * @param pPuntoDiRistoroID unique identifier of the Refreshment
     * @return HashMap containing the bean as the key value of feedback and how
     * @throws RemoteException Exception Remote
     */
    HashMap<BeanVisitaPR, String> ottieniFeedbackPuntoDiRistoro(int pPuntoDiRistoroID) throws RemoteException;

    /**
     * Method for updating (or changing) the data of a Refreshment
     * 
     * @param pPuntoDiRistoroAggiornato containing the new data to be saved
     * @return boolean value - true if the operation went successfully,
     * @throws RemoteException Exception Remote
     */
    boolean modificaPuntoDiRistoro(BeanPuntoDiRistoro pPuntoDiRistoroAggiornato) throws RemoteException;

    /**
     * Method which allows you to insert a tag to search for a useful point Refreshments
     * 
     * @param pPuntoDiRistoroId unique identifier of Refreshment
     * @param pTagId unique ID tags to be inserted
     * @return boolean value - true if the operation went successfully,
     * @throws RemoteException Exception Remote
     */
    boolean inserisciTagPuntoDiRistoro(int pPuntoDiRistoroId, int pTagId) throws RemoteException;

    /**
     * Method which allows you to delete a tag to search for a useful point Refreshments
     * 
     * @param pPuntoDiRistoroId unique identifier of Refreshment
     * @param pTagId unique ID tags to be inserted
     * @return boolean value - true if the operation went successfully,
     * @throws RemoteException Exception Remote
     */
    boolean cancellaTagPuntoDiRistoro(int pPuntoDiRistoroId, int pTagId) throws RemoteException;
}