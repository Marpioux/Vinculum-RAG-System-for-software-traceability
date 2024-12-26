package unisa.gps.etour.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import unisa.gps.etour.bean.BeanPreferenzaDiRicerca;

/**
 * Interface for managing search preferences in database
 * 
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University of Salerno
 */
public interface IDBPreferenzeDiRicerca {

    /**
     * Add a preference of Search
     * 
     * @param pPreferenza Search Preferences
     * @throws SQLException
     */
    boolean inserisciPreferenzaDiRicerca(BeanPreferenzaDiRicerca pPreferenza) throws SQLException;

    /**
     * Delete a preference for research
     * 
     * @param pIdPreferenza preference to eliminate
     * @throws SQLException
     * @return True if deleted, false otherwise
     */
    boolean cancellaPreferenzaDiRicerca(int pIdPreferenza) throws SQLException;

    /**
     * Returns the list of preferences to find a tourist
     * 
     * @param pIdTurista tourists
     * @throws SQLException
     * @return List of Search Preferences
     */
    ArrayList<BeanPreferenzaDiRicerca> ottieniPreferenzeDiRicercaDelTurista(int pIdTurista) throws SQLException;

    /**
     * Returns the list of preferences for research of a cultural
     * 
     * @param pIdBeneCulturale ID of the cultural
     * @throws SQLException
     * @return list of search preferences.
     */
    ArrayList<BeanPreferenzaDiRicerca> ottieniPreferenzeDiRicercaDelBC(int pIdBeneCulturale) throws SQLException;

    /**
     * Returns the list of preferences to find a resting spot
     * 
     * @param pIdPuntoDiRistoro a refreshment
     * @throws SQLException
     * @return list of search preferences.
     */
    ArrayList<BeanPreferenzaDiRicerca> ottieniPreferenzeDiRicercaDelPR(int pIdPuntoDiRistoro) throws SQLException;

    /**
     * Add a preference for a cultural
     * 
     * @param pIdBeneCulturale ID of the cultural
     * @param pIdPreferenzaDiRicerca ID PreferenzaDiRicerca
     * @throws SQLException
     */
    boolean inserisciPreferenzaDiRicercaDelBC(int pIdBeneCulturale, int pIdPreferenzaDiRicerca) throws SQLException;

    /**
     * Add a search preference to a tourist
     * 
     * @param pIdTurista tourists
     * @param pIdPreferenzaDiRicerca ID PreferenzeDiRicerca
     * @throws SQLException
     */
    boolean inserisciPreferenzaDiRicercaDelTurista(int pIdTurista, int pIdPreferenzaDiRicerca) throws SQLException;

    /**
     * Add a preference research to a refreshment
     * 
     * @param pIdPuntoDiRistoro point identification Refreshments
     * @param pIdPreferenzaDiRicerca ID PreferenzaDiRicerca
     * @throws SQLException
     */
    boolean inserisciPreferenzaDiRicercaDelPR(int pIdPuntoDiRistoro, int pIdPreferenzaDiRicerca) throws SQLException;

    /**
     * Deletes a preference to find a Tourist
     * 
     * @param pIdTurista tourists
     * @param pIdPreferenza Search Preferences
     * @throws SQLException
     * @return True if deleted, false otherwise
     */
    boolean cancellaPreferenzaDiRicercaTurista(int pIdTurista, int pIdPreferenza) throws SQLException;

    /**
     * Deletes a preference for research of a cultural
     * 
     * @param pIdBeneCulturale ID of the cultural
     * @param pIdPreferenzaDiRicerca Search Preferences
     * @throws SQLException
     * @return True if deleted, false otherwise
     */
    boolean cancellaPreferenzaDiRicercaBC(int pIdBeneCulturale, int pIdPreferenzaDiRicerca) throws SQLException;

    /**
     * Deletes a preference to find a resting spot
     * 
     * @param pIdPuntoDiRistoro point identification Refreshments
     * @param pIdPreferenza Search Preferences
     * @throws SQLException
     * @return True if deleted, false otherwise
     */
    boolean cancellaPreferenzaDiRicercaPR(int pIdPuntoDiRistoro, int pIdPreferenza) throws SQLException;

    /**
     * Returns a list of all search preferences in the DB
     * 
     * @throws SQLException
     * @return List of search preferences in the DB
     */
    ArrayList<BeanPreferenzaDiRicerca> ottieniPreferenzeDiRicerca() throws SQLException;
}