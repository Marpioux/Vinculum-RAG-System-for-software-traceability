package unisa.gps.etour.control.GestioneUtentiRegistrati;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

import unisa.gps.etour.bean.BeanTurista;
import unisa.gps.etour.repository.DBTurista;
import unisa.gps.etour.util.ControlloDati;
import unisa.gps.etour.util.MessaggiErrore;

public class Authentication extends UnicastRemoteObject implements IAutenticazione {

    private static final long serialVersionUID = 0L;

    public Authentication() throws RemoteException {
        super();
    }

    // Objects to manipulate data Turista
    private DBTurista tourist = new DBTurista();
    private BeanTurista bTurista;

    public int login(String pUsername, String pPassword, byte pTipologiaUtente)
            throws RemoteException {
        // Check if the string username and password
        if (ControlloDati.controllaStringa(pUsername, true, true, "_-", null, 6, 12)
                && ControlloDati.controllaStringa(pPassword, true, true, "_-", null, 5, 12)) {
            try {
                switch (pTipologiaUtente) {
                    // If the type is Turista
                    case 0: // Assuming VISITORS is replaced with a constant value
                        // Invoke the method to obtain the Bean del Turista
                        // Given the username
                        bTurista = tourist.ottieniTurista(pUsername);
                        // Check that the Bean is not null and
                        // Passwords match
                        if (bTurista != null && bTurista.getPassword().equals(pPassword)) {
                            return bTurista.getId();
                        }
                        // If the type is OP_PUNTO_DI_RISTORO
                    case 1: // Assuming OP_PUNTO_DI_RISTORO is replaced with a constant value
                        // Not implemented was the operational point of
                        // Refreshment
                        return -1;
                    // If not match any known type
                    default:
                        return -1;
                }
            } catch (SQLException e) {
                throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
            } catch (Exception e) {
                throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
            }
        }
        // If the data are incorrect returns -1
        return -1;
    }
}