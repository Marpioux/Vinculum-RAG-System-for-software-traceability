package unisa.gps.etour.repository;

import java.sql.SQLException;
import java.util.ArrayList;

import unisa.gps.etour.bean.BeanConvenzione;
import unisa.gps.etour.bean.BeanPuntoDiRistoro;

/**
 * Interface for managing the database Business
 * 
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public interface IDBConvenzione {

    /**
     * Add a convention in the database
     * 
     * @param pConvenzione Convention to be added
     * @throws SQLException
     */
    boolean inserisciConvenzione(BeanConvenzione pConvenzione) throws SQLException;

    /**
     * Modify a convention in the database
     * 
     * @param pConvenzione Convention to be updated
     * @return True if there was a modification, false otherwise
     * @throws SQLException
     */
    boolean modificaConvenzione(BeanConvenzione pConvenzione) throws SQLException;

    /**
     * Delete a convention from the database
     * 
     * @param pIdConvenzione ID of the convention to be removed
     * @return True if deleted, false otherwise
     * @throws SQLException
     */
    boolean cancellaConvenzione(int pIdConvenzione) throws SQLException;

    /**
     * Returns the historical conventions of a refreshment
     * 
     * @param idPuntoDiRistoro Point identification of the refreshment
     * @return List of conventions of the refreshment given as argument
     * @throws SQLException
     */
    ArrayList<BeanConvenzione> ottieniStoricoConvenzione(int idPuntoDiRistoro) throws SQLException;

    /**
     * Returns the active convention of a refreshment
     * 
     * @param pIdPuntoDiRistoro Point identification of the refreshment
     * @return Active convention
     * @throws SQLException
     */
    BeanConvenzione ottieniConvenzioneAttiva(int pIdPuntoDiRistoro) throws SQLException;

    /**
     * Returns a list of all the refreshment points that have an active convention
     * 
     * @return List of all the refreshment points with the active convention
     * @throws SQLException
     */
    ArrayList<BeanPuntoDiRistoro> ottieniListaConvenzioneAttivaPR() throws SQLException;
}