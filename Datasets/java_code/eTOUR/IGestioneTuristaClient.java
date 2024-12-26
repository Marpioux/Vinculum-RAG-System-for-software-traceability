package unisa.gps.etour.control.GestioneUtentiRegistrati;

import java.rmi.RemoteException;
import unisa.gps.etour.bean.BeanBeneCulturale;
import unisa.gps.etour.bean.BeanPreferenzaDiRicerca;
import unisa.gps.etour.bean.BeanPreferenzeGeneriche;
import unisa.gps.etour.bean.BeanPuntoDiRistoro;
import unisa.gps.etour.bean.BeanTurista;
import unisa.gps.etour.bean.BeanVisitaBC;
import unisa.gps.etour.bean.BeanVisitaPR;

/**
 * Interface on the Management of Tourist Information
 * 
 * @author Joseph Penna, Federico Leon
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab University of DMI Salerno
 */
public interface IGestioneTuristaClient extends IGestioneTuristaComune {

    /**
     * Method for the insertion of a Tourist
     * 
     * @param pTurista container for all data relating to tourism by Insert
     * @return Boolean: True if the insertion is successful, False otherwise
     * @throws RemoteException Exception Remote
     */
    boolean inserisciTurista(BeanTurista pTurista) throws RemoteException;

    /**
     * Method for including the General Preferences Tourist
     * 
     * @param pPreferenzeGeneriche General Preferences for inclusion
     * @return Boolean: True if the insertion is successful, False otherwise
     * @throws RemoteException Exception Remote
     */
    boolean inserisciPreferenzeGeneriche(BeanPreferenzeGeneriche pPreferenzeGeneriche) throws RemoteException;

    /**
     * Method for the extraction Preferences generously given Tourists
     * 
     * @param pIdTurista Identifier Turista which you want to Get the General Preferences
     * @return Preferences General information relating to tourism
     * @throws RemoteException Exception Remote
     */
    BeanPreferenzeGeneriche ottieniPreferenzeGeneriche(int pIdTurista) throws RemoteException;

    /**
     * Method for changing the Preferences generously given Tourists
     * 
     * @param pPreferenzeGenericheNuove The Preferenze General for inclusion
     * @return Boolean: True if the modification is successful, False otherwise
     * @throws RemoteException
     */
    boolean modificaPreferenzeGeneriche(BeanPreferenzeGeneriche pPreferenzeGenericheNuove) throws RemoteException;

    /**
     * Method for the removal of preferences associated with the General Tourist
     * 
     * @param pIdTurista of tourists for whom you want to Delete the General Preferences
     * @return Preferences General erased
     * @throws RemoteException Exception Remote
     */
    BeanPreferenzeGeneriche cancellaPreferenzeGeneriche(int pIdTurista) throws RemoteException;

    /**
     * Method to insert a Search Preferences
     * 
     * @param pIdTurista of tourists for which you intend to Insert a Search Preferences
     * @param pPreferenzaDiRicerca Search Preferences to be included
     * @return Boolean: True if the insertion is successful, False otherwise
     * @throws RemoteException
     */
    boolean inserisciPreferenzaDiRicerca(int pIdTurista, BeanPreferenzaDiRicerca pPreferenzaDiRicerca) throws RemoteException;

    /**
     * Method for extracting the set of Search Preferences given Tourist
     * 
     * @param pIdTurista of tourists for whom you want to Extract search preferences
     * @return the search preferences associated with the Tourist information
     * @throws RemoteException Exception Remote
     */
    BeanPreferenzaDiRicerca[] ottieniPreferenzeDiRicerca(int pIdTurista) throws RemoteException;

    /**
     * Method for deleting a Search Preference given its ID and Tourists
     * 
     * @param pIdTurista of tourists for whom you want to Delete a Search Preferences
     * @param pIdPreferenzaDiRicerca Search Preferences to cancel
     * @return The preference of search Deleted
     * @throws RemoteException Exception Remote
     */
    BeanPreferenzaDiRicerca cancellaPreferenzeDiRicerca(int pIdTurista, int pIdPreferenzaDiRicerca) throws RemoteException;

    /**
     * Method to extract the list references to the Cultural Heritage Visited by a tourist
     * 
     * @param pIdTurista Identifier Turista
     * @return list of references to the Cultural Heritage Visited
     * @throws RemoteException Exception Remote
     */
    BeanVisitaBC[] ottieniBeniCulturaliVisitati(int pIdTurista) throws RemoteException;

    /**
     * Method for the extraction of the list when making reference to Refreshments Visited by a tourist
     * 
     * @param pIdTurista Identifier Turista
     * @return list of references to Refreshments Visited
     * @throws RemoteException Exception Remote
     */
    BeanVisitaPR[] ottieniPuntiDiRistoroVisitati(int pIdTurista) throws RemoteException;

    /**
     * Method for the insertion of a cultural Visited
     * 
     * @param pVisitaBC package containing all information relating to Visit
     * @return true if the item is added successfully, false otherwise
     * @throws RemoteException Exception Remote
     */
    boolean inserisciBeneCulturaleVisitato(BeanVisitaBC pVisitaBC) throws RemoteException;

    /**
     * Method for inserting a refreshment Visited
     * 
     * @param pVisitaPR package containing all information relating to Visit
     * @return true if the item is added successfully, false otherwise
     * @throws RemoteException Exception Remote
     */
    boolean inserisciPuntoDiRistoroVisitato(BeanVisitaPR pVisitaPR) throws RemoteException;

    /**
     * Method for the insertion of a cultural object in the list of Favorites
     * 
     * @param pIdTurista Identifier Turista
     * @param pIdBeneCulturale of Cultural Heritage
     * @return true if the insertion is successful, false otherwise
     * @throws RemoteException Exception Remote
     */
    boolean inserisciBeneCulturalePreferito(int pIdTurista, int pIdBeneCulturale) throws RemoteException;

    /**
     * Method for inserting a refreshment to my Favorites
     * 
     * @param pIdTurista Identifier Turista
     * @param pIdPuntoDiRistoro point identification Refreshment
     * @return true if the insertion is successful, false otherwise
     * @throws RemoteException Exception Remote
     */
    boolean inserisciPuntoDiRistoroPreferito(int pIdTurista, int pIdPuntoDiRistoro) throws RemoteException;

    /**
     * Method for the cancellation of a cultural object from the list of Favorites
     * 
     * @param pIdTurista Identifier Turista
     * @param pIdBeneCulturale of Cultural Heritage
     * @return true if the cancellation is successful, false otherwise
     * @throws RemoteException Exception Remote
     */
    boolean cancellaBeneCulturalePreferito(int pIdTurista, int pIdBeneCulturale) throws RemoteException;

    /**
     * Method for deleting a refreshment from the list of Favorites
     * 
     * @param pIdTurista Identifier Turista
     * @param pIdPuntoDiRistoro point identification Refreshment
     * @return true if the cancellation is successful, false otherwise
     * @throws RemoteException Exception Remote
     */
    boolean cancellaPuntoDiRistoroPreferito(int pIdTurista, int pIdPuntoDiRistoro) throws RemoteException;

    /**
     * Method to extract the list of Cultural Heritage Favorites
     * 
     * @param pIdTurista Identifier Turista
     * @return List of Cultural Heritage Favorites
     * @throws RemoteException Exception Remote
     */
    BeanBeneCulturale[] ottieniBeniCulturaliPreferiti(int pIdTurista) throws RemoteException;

    /**
     * Method to extract the list of Refreshments
     * 
     * @param pIdTurista Identifier Turista
     * @return list of eateries Favorites
     * @throws RemoteException Exception Remote
     */
    BeanPuntoDiRistoro[] ottieniPuntiDiRistoroPreferiti(int pIdTurista) throws RemoteException;
}