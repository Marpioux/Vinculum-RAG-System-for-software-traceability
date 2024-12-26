package unisa.gps.etour.repository;

import java.sql.SQLException;
import unisa.gps.etour.bean.BeanPreferenzeGeneriche;

/**
 * Interface for handling general preferences in the database
 *
 * @author Mauro Miranda
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public interface IDBPreferenzeGeneriche {
    /**
     * Add a general preference
     *
     * @param pPreferenza preference to be added
     * @throws SQLException
     */
    boolean inserisciPreferenzaGenerica(BeanPreferenzeGeneriche pPreferenza) throws SQLException;

    /**
     * Edit a general preference
     *
     * @param pPreferenza preference to change
     * @throws SQLException
     * @return true if it has been changed, otherwise false
     */
    boolean modificaPreferenzaGenerica(BeanPreferenzeGeneriche pPreferenza) throws SQLException;

    /**
     * Delete a general preference
     *
     * @param pIdPreferenza preference to delete
     * @throws SQLException
     * @return true if it has been deleted, false otherwise
     */
    boolean cancellaPreferenzaGenerica(int pIdPreferenza) throws SQLException;

    /**
     * Returns the generic preference for tourists
     *
     * @param pIdTurista tourist's ID
     * @throws SQLException
     * @return generic preference
     */
    BeanPreferenzeGeneriche ottieniPreferenzaGenerica(int pIdTurista) throws SQLException;
}