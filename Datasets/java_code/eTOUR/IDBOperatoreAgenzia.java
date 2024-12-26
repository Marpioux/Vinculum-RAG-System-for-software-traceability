package unisa.gps.etour.repository;

import java.sql.SQLException;
import unisa.gps.etour.bean.BeanOperatoreAgenzia;

/**
 * Interface for managing the database OperatoreAgenzia
 * 
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public interface IDBOperatoreAgenzia {

    /**
     * Returns the data Operator Agency with ID equal to that given in
     * input
     *
     * @param pUsername Username dell'OperatoreAgenzia to find
     * @return OperatoreAgenzia with id equal to the input, null if there is
     * @throws SQLException
     */
    BeanOperatoreAgenzia ottieniOperatoreAgenzia(String pUsername) throws SQLException;

    /**
     * Returns the data Operator Agency with ID equal to that given in
     * input
     *
     * @param poa OperatoreAgenzia to modify
     * @return true if the password was modified, false otherwise
     * @throws SQLException
     */
    boolean modificaPassword(BeanOperatoreAgenzia poa) throws SQLException;
}