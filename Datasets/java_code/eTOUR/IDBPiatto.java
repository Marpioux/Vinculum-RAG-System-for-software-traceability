package unisa.gps.etour.repository;

import java.sql.SQLException;
import java.util.ArrayList;

import unisa.gps.etour.bean.BeanPiatto;

/**
 * Interface for the management of food in the database
 * 
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public interface IDBPiatto {

    /**
     * Add a dish
     * 
     * @param pPiatto plate to add
     * @return true if the dish was added, false otherwise
     */
    boolean inserisciPiatto(BeanPiatto pPiatto) throws SQLException;

    /**
     * Modify the data in a dish in the database
     * 
     * @param pPiatto data of the dish to be modified in the database
     * @return true if there was a modification, false otherwise
     */
    boolean modificaPiatto(BeanPiatto pPiatto) throws SQLException;

    /**
     * Delete a dish from the database
     * 
     * @param pIdPiatto ID of the dish to delete
     * @return true if it was deleted, false otherwise
     * @throws SQLException
     */
    boolean cancellaPiatto(int pIdPiatto) throws SQLException;

    /**
     * Returns a list of dishes on a menu
     * 
     * @param pIdMenu ID of the menu
     * @return list of dishes in the menu
     * @throws SQLException
     */
    ArrayList<BeanPiatto> ottieniPiatto(int pIdMenu) throws SQLException;
}