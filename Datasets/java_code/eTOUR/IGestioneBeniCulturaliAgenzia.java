package unisa.gps.etour.control.GestioneBeniCulturali;

import java.rmi.RemoteException;
import java.util.ArrayList;
import unisa.gps.etour.bean.BeanBeneCulturale;

public interface IGestioneBeniCulturali extends IGestioneBeniCulturaliAgenzia, IGestioneBeniCulturaliComune {

    /**
     * Method for the insertion of a new cultural
     *
     * @param pBeneCulturale The raw bean to be included in the database
     * @return boolean The result of the operation; true if successful, false otherwise
     * @throws RemoteException Exception flow
     */
    boolean inserisciBeneCulturale(BeanBeneCulturale pBeneCulturale) throws RemoteException;

    /**
     * Method for the cancellation of a cultural object by id
     *
     * @param pBeneCulturaleID the bean to be deleted
     * @return boolean The result of the operation; true if successful, false otherwise
     * @throws RemoteException Exception flow
     */
    boolean cancellaBeneCulturale(int pBeneCulturaleID) throws RemoteException;

    /**
     * Method for the return of all cultural property in the
     * Database
     *
     * @return ArrayList all the beans in the database
     * @throws RemoteException Exception flow
     */
    ArrayList<BeanBeneCulturale> ottieniBeniCulturali() throws RemoteException;

    /**
     * Method for updating (or changing) the data of a cultural
     *
     * @param pBeneCulturale The bean with the new information of the cultural
     * @return boolean The result of the operation; true if successful, false otherwise
     * @throws RemoteException Exception flow
     */
    boolean modificaBeneCulturale(BeanBeneCulturale pBeneCulturale) throws RemoteException;

    /**
     * Method for setting a tag to a certain cultural
     *
     * @param pBeneCulturaleID The identifier of the cultural object to which to add a tag
     * @param pTagID The ID tag to add to the cultural indicated
     * @return boolean The result of the operation; true if successful, false otherwise
     * @throws RemoteException Exception flow
     */
    boolean aggiungiTagBeneCulturale(int pBeneCulturaleID, int pTagID) throws RemoteException;

    /**
     * Method for removing a tag from a certain cultural
     * To ensure that the operation is successful it is necessary that the cultural property has
     * actually set the specified tag
     *
     * @param pBeneCulturaleID The identifier of the cultural object from which to remove the tag
     * @param pTagID The ID tag to be removed from the cultural indicated
     * @return boolean The result of the operation; true if successful, false otherwise
     * @throws RemoteException Exception flow
     */
    boolean rimuoviTagBeneCulturale(int pBeneCulturaleID, int pTagID) throws RemoteException;
}