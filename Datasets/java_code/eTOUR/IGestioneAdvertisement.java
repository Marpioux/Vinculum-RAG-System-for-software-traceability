package unisa.gps.etour.control.GestioneAdvertisement;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.ImageIcon;
import unisa.gps.etour.bean.BeanBanner;

/**
 * Interface General Manager of Banner and news.
 * 
 * @author Fabio Palladino
 * @version 0.1
 * 
 * 2007 eTour Project - Copyright by SE @ SA Lab DMI University of Salerno
 */
public interface IGestioneAdvertisement extends Remote {
    /**
     * Inserts a new banner.
     * 
     * @param pIdPuntoDiRistoro Bean contains the data of the banner
     * @param pImmagineBanner    Image of the banner
     * @return true if the operation is successful, false otherwise.
     * @throws RemoteException
     */
    boolean inserisciBanner(int pIdPuntoDiRistoro, ImageIcon pImmagineBanner) throws RemoteException;

    /**
     * Delete a banner from the system.
     * 
     * @param pBannerID ID banner to be deleted.
     * @return true if the operation is successful, false otherwise.
     * @throws RemoteException
     */
    boolean cancellaBanner(int pBannerID) throws RemoteException;

    /**
     * Modify the data of the banner or the image associated.
     * 
     * @param pBannerID ID of the banner.
     * @param pImmagine Image associated with the banner.
     * @return true if the operation is successful, false otherwise.
     * @throws RemoteException
     */
    boolean modificaBanner(int pBannerID, ImageIcon pImmagine) throws RemoteException;

    /**
     * Returns a list of Banner of a particular point of comfort.
     * 
     * @param pIdPuntoDiRistoro ID of the refreshment owner of the banner
     * @return HashMap containing the list of banners for the refreshment
     * @throws RemoteException
     */
    HashMap<BeanBanner, ImageIcon> ottieniBannersDaID(int pIdPuntoDiRistoro) throws RemoteException;
}