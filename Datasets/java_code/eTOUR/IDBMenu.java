package unisa.gps.etour.repository;

import java.sql.SQLException;
import java.util.ArrayList;

import unisa.gps.etour.bean.BeanMenu;

/**
 * Interface for managing the menu in the database
 * 
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University Of Salerno
 */
public interface IDBMenu {

    /**
     * Adds a menu in the database
     * 
     * @param pMenu menu to add
     * @throws SQLException
     */
    boolean inserisciMenu(BeanMenu pMenu) throws SQLException;

    /**
     * Modify a menu in the database
     * 
     * @param pMenu Contains the data to change
     * @return True if there was a modification, false otherwise
     * @throws SQLException
     */
    boolean modificaMenu(BeanMenu pMenu) throws SQLException;

    /**
     * Delete a menu from the database
     * 
     * @param pIdMenu menu to delete
     * @return True if it was deleted, false otherwise
     * @throws SQLException
     */
    boolean cancellaMenu(int pIdMenu) throws SQLException;

    /**
     * Returns the menu of the day of a refreshment
     * 
     * @param pIdPuntoDiRistoro point identification of refreshment
     * @param pGiorno day of the week in which the menu is daily
     * @return daily menu of refreshment
     * @throws SQLException
     */
    BeanMenu ottieniMenuDelGiorno(int pIdPuntoDiRistoro, String pGiorno) throws SQLException;

    /**
     * Returns a list of the menu of a refreshment
     * 
     * @param pIdPuntoDiRistoro point identification of refreshment
     * @return list of menus
     * @throws SQLException
     */
    ArrayList<BeanMenu> ottieniMenu(int pIdPuntoDiRistoro) throws SQLException;
}