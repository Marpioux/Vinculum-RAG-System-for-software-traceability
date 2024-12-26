package unisa.gps.etour.repository;

import java.sql.SQLException;
import java.util.ArrayList;

import unisa.gps.etour.bean.BeanBanner;

/**
 * Interface for managing the banner in the database
 * 
 * @author Mauro Miranda
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public interface IDBBanner {
    /**
     * Add a banner to the database
     * 
     * @param pBanner bean containing the information of the banner
     * @throws SQLException
     */
    boolean inserisciBanner(BeanBanner pBanner) throws SQLException;

    /**
     * Modify the contents of the advertisement, and returns the contents before
     * edit
     * 
     * @param pBanner Bean that contains the new information of the banner
     * @return True if there was a modification, false otherwise
     * @throws SQLException
     */
    boolean modificaBanner(BeanBanner pBanner) throws SQLException;

    /**
     * Delete a banner from the database and returns
     * 
     * @param pIdBanner ID of BeanBanner
     * @return True if it was deleted, false otherwise
     * @throws SQLException
     */
    boolean cancellaBanner(int pIdBanner) throws SQLException;

    /**
     * Returns a list of banners for a refreshment point; if the id of
     * refreshment is equal to -1, it will return the complete list
     * of banners
     * 
     * @param pIdPuntoDiRistoro of refreshment point from which to obtain the list
     * @return list of banners linked to refreshment
     * @throws SQLException
     */
    ArrayList<BeanBanner> ottieniBanner(int pIdPuntoDiRistoro) throws SQLException;

    /**
     * Method which returns a banner given its id
     * 
     * @param pIdBanner the banner to return
     * @return Banner found in the database, null if there is no match
     * @throws SQLException
     */
    BeanBanner ottieniBannerDaID(int pIdBanner) throws SQLException;
}