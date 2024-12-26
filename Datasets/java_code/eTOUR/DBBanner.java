/*
 * Stubs for dynamic class DBBanner. Is used for testing
 * Class package GestioneAdvertisement.
 *
 * @ Author Fabio Palladino
 * @ Version 0.1
 *
 * © 2007 eTour Project - Copyright by DMI SE @ SA Lab - University of Salerno
 */

package unisa.gps.etour.control.GestioneAdvertisement.test.stubs;

import java.sql.SQLException;
import java.util.ArrayList;

import unisa.gps.etour.bean.BeanBanner;
import unisa.gps.etour.repository.IDBBanner;

public class DBBanner implements IDBBanner {

    private static int numTest = 0;

    public boolean cancellaBanner(int pIdBanner) throws SQLException {
        if (numTest == 5) {
            throw new SQLException();
        } else {
            return true;
        }
    }

    /* (Non-Javadoc)
     * @ See unisa.gps.etour.repository.IDBBanner # inserisciBanner (unisa.gps.etour.bean.BeanBanner)
     */
    public boolean inserisciBanner(BeanBanner pBanner) throws SQLException {
        return numTest == 1 || numTest == 2;
    }

    /* (Non-Javadoc)
     * @ See unisa.gps.etour.repository.IDBBanner # modificaBanner (unisa.gps.etour.bean.BeanBanner)
     */
    public boolean modificaBanner(BeanBanner pBanner) throws SQLException {
        return true;
    }

    /* (Non-Javadoc)
     * @ See unisa.gps.etour.repository.IDBBanner # ottieniBanner (int)
     */
    public ArrayList<BeanBanner> ottieniBanner(int pIdPuntoDiRistoro) throws SQLException {
        ArrayList<BeanBanner> toReturn = new ArrayList<>();

        if (numTest == 1 || numTest == 2) {
            /* Must return an ArrayList with 3 elements */
            toReturn.add(new BeanBanner());
            toReturn.add(new BeanBanner());
            toReturn.add(new BeanBanner());
            return toReturn;
        } else if (numTest == 4) {
            toReturn.add(new BeanBanner(3, "c:\\ProvaBannerInserimento.jpg", 55));
            toReturn.add(new BeanBanner(4, "c:\\ProvaBannerInserimento.jpg", 55));
            toReturn.add(new BeanBanner(5, "c:\\ProvaBannerInserimento.jpg", 55));
            toReturn.add(new BeanBanner(5, "c:\\ProvaBannerInserimento.jpg", 55));
            return toReturn;
        } else {
            return null;
        }
    }

    /* (Non-Javadoc)
     * @ See unisa.gps.etour.repository.IDBBanner # ottieniBannerDaID (int)
     */
    public BeanBanner ottieniBannerDaID(int pIdBanner) throws SQLException {
        if (numTest == 7) {
            return null;
        } else {
            return new BeanBanner(55, "c:\\ProvaBanner.jpg", 3);
        }
    }

    public static void setNumTest(int numTest) {
        DBBanner.numTest = numTest;
    }
}