package unisa.gps.etour.repository;

import java.sql.SQLException;
import unisa.gps.etour.bean.BeanOperatorePuntoDiRistoro;

/**
 * Interface for the operator to the point of comfort in the database
 * 
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public interface IDBOperatorePuntoDiRistoro {

    /**
     * Adds an operator refreshment
     * 
     * @param popera Additional operating
     * @throws SQLException
     */
    boolean inserisciOperatorePuntoDiRistoro(BeanOperatorePuntoDiRistoro popera) throws SQLException;

    /**
     * Modify an operator in the database
     * 
     * @param popera New data Operator
     * @throws SQLException
     * @return True if there was a modification, false otherwise
     */
    boolean modificaOperatorePuntoDiRistoro(BeanOperatorePuntoDiRistoro popera) throws SQLException;

    /**
     * Delete an operator
     * 
     * @param pIdOperatore Operator ID to delete
     * @throws SQLException
     * @return True if it was deleted, false otherwise
     */
    boolean cancellaOperatorePuntoDiRistoro(int pIdOperatore) throws SQLException;

    /**
     * Returns data operator
     * 
     * @param pIdOperatore Operator ID
     * @throws SQLException
     * @return Operator refreshment
     */
    BeanOperatorePuntoDiRistoro ottieniOperatorePuntoDiRistoro(int pIdOperatore) throws SQLException;
}