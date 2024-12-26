package unisa.gps.etour.control.GestionePuntiDiRistoro;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import unisa.gps.etour.bean.BeanPuntoDiRistoro;
import unisa.gps.etour.bean.BeanTag;
import unisa.gps.etour.bean.BeanVisitaPR;

/**
 * Interface for common operations on the refreshment
 * 
 * Author: Joseph Morelli
 * Version: 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University Of Salerno
 */
public interface IGestionePuntiDiRistoroComune extends Remote {

    /**
     * Method to return a particular Refreshment
     * 
     * @param pPuntoDiRistoroID to identify the Refreshment
     * @return Bean contains the data of Refreshment concerned
     * @throws RemoteException Exception Remote
     */
    BeanPuntoDiRistoro ottieniPuntoDiRistoro(int pPuntoDiRistoroID) throws RemoteException;

    /**
     * Method which returns the tags to some refreshment
     * 
     * @param pPuntoDiRistoroID point identification Refreshment
     * @return structure containing all BeanTag associated with the point Refreshments passed as parameter
     * @throws RemoteException Exception Remote
     */
    ArrayList<BeanTag> ottieniTagPuntoDiRistoro(int pPuntoDiRistoroID) throws RemoteException;

    /**
     * Method which returns the last 10 comments made for a Refreshment
     * 
     * @param pPuntoDiRistoroID ID for the point of rest in Question
     * @return Array of strings containing 10 items
     * @throws RemoteException Exception Remote
     */
    String[] ottieniUltimiCommenti(int pPuntoDiRistoroID) throws RemoteException;

    /**
     * Returns for the Refreshment specified, an array where each Location contains the number of ratings corresponding to the value
     * Index of the array more 'one. The calculation and 'made in the period 30 days and today.
     * 
     * @param pPuntoDiRistoroID unique identifier of Refreshment
     * @return ArrayList containing the counters as explained above
     * @throws RemoteException Exception Remote
     */
    ArrayList<Integer> ottieniStatistichePuntoDiRistoro(int pPuntoDiRistoroID) throws RemoteException;

    /**
     * Method which allows you to change the comment issued for a Refreshment
     * 
     * @param pPuntoDiRistoroId unique identifier of Refreshment
     * @param nuovaVisita Bean containing new comment
     * @return Boolean value-true if the operation went successfully, false otherwise
     * @throws RemoteException Exception Remote
     */
    boolean modificaFeedbackPuntoDiRistoro(int pPuntoDiRistoroId, BeanVisitaPR nuovaVisita) throws RemoteException;
}