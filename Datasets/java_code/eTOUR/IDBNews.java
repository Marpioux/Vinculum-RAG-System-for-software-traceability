package unisa.gps.etour.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import unisa.gps.etour.bean.BeanNews;

/**
 * Interface for the management of news in the Database
 * 
 * @author Mauro Miranda
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public interface IDBNews {
    /**
     * Add a news database
     * 
     * @param news News to add
     * @throws SQLException
     */
    boolean inserisciNews(BeanNews news) throws SQLException;

    /**
     * Modify a news database
     * 
     * @param news News to change with the new data
     * @throws SQLException
     * @return True if there was a modification, false otherwise
     */
    boolean modificaNews(BeanNews news) throws SQLException;

    /**
     * Delete a database from news
     * 
     * @param idNews News to eliminate
     * @throws SQLException
     * @return True if it has been deleted, false otherwise
     */
    boolean cancellaNews(int idNews) throws SQLException;

    /**
     * Returns the active news
     * 
     * @throws SQLException
     * @return list of active news
     */
    ArrayList<BeanNews> ottieniNews() throws SQLException;
}