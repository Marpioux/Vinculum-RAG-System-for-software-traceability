package unisa.gps.etour.repository;

import java.sql.SQLException;
import java.util.ArrayList;

import unisa.gps.etour.bean.BeanTag;

/**
 * Interface for managing the database Tag
 * 
 * @author Mauro Miranda
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University of Salerno
 */
public interface IDBTag {
    /**
     * Add a tag
     * 
     * @param ptagi Tag to add
     * @throws SQLException
     */
    boolean inserisciTag(BeanTag ptagi) throws SQLException;

    /**
     * Modify the data in a tag
     * 
     * @param ptagi Tag to modify
     * @throws SQLException
     * @return True if changed, otherwise false
     */
    boolean modificaTag(BeanTag ptagi) throws SQLException;

    /**
     * Delete a tag from the database
     * 
     * @param pIdTag ID Tag to be deleted
     * @throws SQLException
     * @return True if deleted, false otherwise
     */
    boolean cancellaTag(int pIdTag) throws SQLException;

    /**
     * Returns the list of tags in the database
     * 
     * @throws SQLException
     * @return List containing the tags
     */
    ArrayList<BeanTag> ottieniListaTag() throws SQLException;

    /**
     * Returns a single tag
     * 
     * @param pid ID tag
     * @throws SQLException
     * @return Tag
     */
    BeanTag ottieniTag(int pid) throws SQLException;

    /**
     * Tag with immovable cultural
     * 
     * @param pIdBeneCulturale ID of Cultural Heritage
     * @param pIdTag ID tag
     * @throws SQLException
     */
    boolean aggiungeTagBeneCulturale(int pIdBeneCulturale, int pIdTag) throws SQLException;

    /**
     * Tag to a refreshment
     * 
     * @param pIdPuntoDiRistoro Point identification Refreshments
     * @param pIdTag ID tag
     * @throws SQLException
     */
    boolean aggiungeTagPuntoDiRistoro(int pIdPuntoDiRistoro, int pIdTag) throws SQLException;

    /**
     * Returns the list of tags of a cultural
     * 
     * @param pIdBeneCulturale ID of Cultural Heritage
     * @throws SQLException
     * @return List of tags
     */
    ArrayList<BeanTag> ottieniTagBeneCulturale(int pIdBeneCulturale) throws SQLException;

    /**
     * Returns a list of tags of a refreshment
     * 
     * @param pIdPuntoDiRistoro Point identification Refreshments
     * @throws SQLException
     * @return List of tags
     */
    ArrayList<BeanTag> ottieniTagPuntoDiRistoro(int pIdPuntoDiRistoro) throws SQLException;

    /**
     * Delete a tag from a cultural
     * 
     * @param pIdBeneCulturale ID of Cultural Heritage
     * @param pIdTag ID tag
     * @throws SQLException
     * @return True if deleted, false otherwise
     */
    boolean cancellaTagBeneCulturale(int pIdBeneCulturale, int pIdTag) throws SQLException;

    /**
     * Delete a tag from a refreshment
     * 
     * @param pIdPuntoDiRistoro ID
     * @param pIdTag ID tag
     * @throws SQLException
     * @return True if deleted, false otherwise
     */
    boolean cancellaTagPuntoDiRistoro(int pIdPuntoDiRistoro, int pIdTag) throws SQLException;
}