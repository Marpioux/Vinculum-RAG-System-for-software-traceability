package unisa.gps.etour.control;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import unisa.gps.etour.control.GestioneBeniCulturali.GestioneBeniCulturaliComune;
import unisa.gps.etour.control.GestioneBeniCulturali.IGestioneBeniCulturaliComune;

/**
 * This is the entry point of the control server. This class is responsible for
 * making the deployment of services on the RMI registry, thus rendering the
 * some services available and usable.
 * 
 * @author Michelangelo De Simone
 * @version 0.1 © 2007 eTour Project - Copyright by DMI SE @ SA Lab - University of Salerno
 */
public class ControlServerLauncher {
    /**
     * Entry point of ControlServer
     * 
     * @param args The command line parameters
     */
    public static void main(String[] args) {
        try {
            // GestioneBeniCulturaliComune is the class that implements the interface IGestioneBeniCulturaliComune,
            // this interface on both the client and the server
            GestioneBeniCulturaliComune gBCC = new GestioneBeniCulturaliComune();

            // Here you create the stub for the registry, making it clear to the RMI system you are exporting the object on a gBCC
            // Anonymous port
            IGestioneBeniCulturaliComune stubGBCC = (IGestioneBeniCulturaliComune) UnicastRemoteObject.exportObject(gBCC, 0);

            // There shall call the register (default is on localhost) and "bind" (alloy)
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("stubGBCC", stubGBCC);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getLocalizedMessage());
        }
    }
}