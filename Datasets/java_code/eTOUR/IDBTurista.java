package unisa.gps.etour.repository;

import java.sql.SQLException;
import java.util.ArrayList;

import unisa.gps.etour.bean.BeanBeneCulturale;
import unisa.gps.etour.bean.BeanTurista;

/**
 * Interface for the management of tourists in the database
 * 
 * @author Mauro Miranda
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public interface IDBTurista {

    /**
     * Add a tourist
     * 
     * @param pTurista Tourist to add
     * @throws SQLException
     */
    boolean inserisciTurista(BeanTurista pTurista) throws SQLException;

    /**
     * Modify a tourist
     * 
     * @param pTurista Tourist to change
     * @throws SQLException
     * @return True if changed, otherwise false
     */
    boolean modificaTurista(BeanTurista pTurista) throws SQLException;

    /**
     * Delete a tourist from the database
     * 
     * @param pIdTurista Identifier of the tourist to delete
     * @throws SQLException
     * @return True if deleted, otherwise false
     */
    boolean delete(int pIdTurista) throws SQLException;

    /**
     * Returns the data of the Tourist
     * 
     * @param pUsername Username of the tourist
     * @throws SQLException
     * @return Information about the tourist
     */
    BeanTurista ottieniTurista(String pUsername) throws SQLException;

    /**
     * Attach a cultural heritage to the preferred tourist
     * 
     * @param pIdTurista ID of the tourist
     * @param pIdBeneCulturale ID of the cultural heritage
     * @throws SQLException
     */
    boolean inserisciBeneCulturalePreferito(int pIdTurista, int pIdBeneCulturale) throws SQLException;

    /**
     * Attach a dining point to the tourist's favorites
     * 
     * @param pIdTurista ID of the tourist
     * @param pIdPuntoDiRistoro ID of the dining point
     * @throws SQLException
     */
    boolean inserisciPuntoDiRistoroPreferito(int pIdTurista, int pIdPuntoDiRistoro) throws SQLException;

    /**
     * Delete a cultural favorite
     * 
     * @param pIdTurista ID of the tourist
     * @param pIdBeneCulturale ID of the cultural heritage
     * @throws SQLException
     * @return True if deleted, otherwise false
     */
    boolean cancellaBeneCulturalePreferito(int pIdTurista, int pIdBeneCulturale) throws SQLException;

    /**
     * Delete a favorite resting spot
     * 
     * @param pIdTurista ID of the tourist
     * @param pIdPuntoDiRistoro ID of the dining point
     * @throws SQLException
     * @return True if deleted, otherwise false
     */
    boolean cancellaPuntoDiRistoroPreferito(int pIdTurista, int pIdPuntoDiRistoro) throws SQLException;

    /**
     * Returns an ArrayList of tourists who have a username like the one given as argument
     * 
     * @param pUsernameTurista Username of the tourists to search
     * @throws SQLException
     * @return Data for tourists
     */
    ArrayList<BeanTurista> ottieniTuristi(String pUsernameTurista) throws SQLException;

    /**
     * Returns the list of tourists turned on or off
     * 
     * @param condition True for those tourists turned off
     * @return Data for tourists
     * @throws SQLException
     */
    ArrayList<BeanTurista> ottieniTuristi(boolean condition) throws SQLException;

    /**
     * Returns the data of the tourist with ID equal to that given in input
     * 
     * @param pIdTurista ID of the tourist to find
     * @return Tourist with ID equal to the input, null if not found
     * @throws SQLException
     */
    BeanTurista ottieniTurista(int pIdTurista) throws SQLException;

    /**
     * Returns the list of cultural favorites from a particular tourist
     * 
     * @param pIdTurista ID of the tourist to find
     * @return List of Cultural Heritage Favorites
     * @throws SQLException
     */
    ArrayList<BeanBeneCulturale> ottieniBeniCulturaliPreferiti(int pIdTurista) throws SQLException;

    /**
     * Returns a list of favorite resting spots by a particular tourist
     * 
     * @param pIdTurista ID of the tourist to find
     * @return List of Refreshment Favorites
     * @throws SQLException
     */
    ArrayList<BeanBeneCulturale> ottieniPuntoDiRistoroPreferiti(int pIdTurista) throws SQLException;
}