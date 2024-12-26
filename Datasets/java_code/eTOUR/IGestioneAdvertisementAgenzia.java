/**
 * Interface that provides management services for advertisements
 * of the operator agency.
 *
 * @author author
 * @version 0.1
 *
 * © 2007 eTour Project - Copyright by DMI SE @ SA Lab - University of Salerno
 */
package com.trapan.spg.control.GestioneAdvertisement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import com.trapan.spg.bean.BeanNews;

public interface IGestioneAdvertisementAgenzia extends IGestioneAdvertisement {
    /**
     * Method that inserts a new news item into the system.
     *
     * @param news Bean containing news data
     * @throws RemoteException
     */
    boolean inserisciNews(BeanNews news) throws RemoteException;

    /**
     * Method that removes a news item from the system.
     *
     * @param newsID ID of the news item to be erased
     * @throws RemoteException
     */
    boolean cancellaNews(int newsID) throws RemoteException;

    /**
     * Method that amends the text of a news item.
     *
     * @param news Bean containing news data
     * @throws RemoteException
     */
    boolean modificaNews(BeanNews news) throws RemoteException;

    /**
     * Returns the list of active news items.
     *
     * @return ArrayList of BeanNews
     * @throws RemoteException
     */
    ArrayList<BeanNews> ottieniAllNews() throws RemoteException;
}