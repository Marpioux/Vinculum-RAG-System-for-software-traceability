package unisa.gps.etour.control.GestioneUtentiRegistrati;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface relating to operations performed by the User login and logout
 * Register
 * 
 * @author Joseph Penna
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab University of DMI
 * Salerno
 */
public interface IAutenticazione extends Remote {

    // Static constants that identify the type of users
    byte VISITORS = 0;
    byte OP_PUNTO_DI_RISTORO = 1;

    /**
     * Method to authenticate a registered user (Turista - Operatore
     * Ristorazione)
     * 
     * @param pUsername Registered User username
     * @param pPassword Registered User password
     * @param pTipologiaUtente User type to be authenticated
     * @return If the data are correct user ID logged in, otherwise -1
     */
    int login(String pUsername, String pPassword, byte pTipologiaUtente) throws RemoteException;
}