package unisa.gps.etour.control.GestioneBeniCulturali;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import unisa.gps.etour.bean.BeanBeneCulturale;
import unisa.gps.etour.bean.BeanTag;
import unisa.gps.etour.bean.BeanVisitaBC;

/**
 * Interface for operations common to users and operators on Agency
 * Beniculturali
 * 
 * Author: Michelangelo De Simone
 * Version: 0.1
 * 
 * © 2007 eTour Project - Copyright by DMI SE @ SA Lab - University of Salerno
 */
public interface IGestioneBeniCulturaliComune extends Remote {

    /**
     * Method to return a particular Cultural Heritage
     * 
     * @param pBeneCulturaleID The identifier of the cultural property to be returned
     * @return BeanBeneCulturale Contains data required of Cultural Heritage
     * @throws RemoteException Exception flow
     */
    BeanBeneCulturale ottieniBeneCulturale(int pBeneCulturaleID) throws RemoteException;

    /**
     * Returns the list of tags of a cultural
     * 
     * @param pBeneCulturaleID ID of Cultural Heritage
     * @return ArrayList<BeanTag> of the cultural tags specified
     * @throws RemoteException Exception flow
     */
    ArrayList<BeanTag> ottieniTagBeneCulturale(int pBeneCulturaleID) throws RemoteException;

    /**
     * Returns a list of feedback to the cultural specified
     * 
     * @param pBeneCulturaleID ID of Cultural Heritage
     * @return HashMap<BeanVisitaBC, String> The feedback of Cultural Heritage
     * @throws RemoteException Exception flow
     */
    HashMap<BeanVisitaBC, String> ottieniFeedbackBeneCulturale(int pBeneCulturaleID) throws RemoteException;

    /**
     * Returns for the cultural property specified, an array where each position contains the number of
     * feedback corresponding to the value of the array more than 'one.
     * The calculation is made in the period between 30 days and today.
     * 
     * @param pBeneCulturaleID ID of Cultural Heritage
     * @return ArrayList<Integer> The statistics of last thirty days
     * @throws RemoteException Exception flow
     */
    ArrayList<Integer> ottieniStatisticheBeneCulturale(int pBeneCulturaleID) throws RemoteException;

    /**
     * Method for updating (or modification) of a feedback for a certain good
     * Cultural. The method has the burden of
     * 
     * @param pBeneCulturaleID The identifier of the cultural change which the feedback
     * @param pBeanVisitaBC The new feedback to the cultural indicated
     * @return boolean The result of the operation; true if was successful, false otherwise
     * @throws RemoteException Exception flow
     */
    boolean modificaFeedbackBeneCulturale(int pBeneCulturaleID, BeanVisitaBC pBeanVisitaBC) throws RemoteException;
}