package unisa.gps.etour.control.GestioneUtentiRegistrati;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import unisa.gps.etour.bean.BeanTurista;
import unisa.gps.etour.bean.BeanVisitaBC;
import unisa.gps.etour.bean.BeanVisitaPR;
import unisa.gps.etour.repository.DBVisitaBC;
import unisa.gps.etour.repository.DBVisitaPR;
import unisa.gps.etour.repository.IDBVisitaBC;
import unisa.gps.etour.repository.IDBVisitaPR;
import unisa.gps.etour.util.MessaggiErrore;

public class GestioneTuristiAgenzia extends GestioneTuristaComune implements IGestioneTuristiAgenzia {

    private IDBVisitaBC feedbackBC;
    private IDBVisitaPR feedbackPR;

    public GestioneTuristiAgenzia() throws RemoteException {
        super();
        try {
            feedbackBC = new DBVisitaBC();
            feedbackPR = new DBVisitaPR();
        } catch (Exception e) {
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
    }

    public ArrayList<BeanTurista> ottieniTuristi() throws RemoteException {
        ArrayList<BeanTurista> toReturn;
        try {
            toReturn = turista.ottieniTuristi("");
            if (null == toReturn) {
                throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
            }
        } catch (SQLException e) {
            System.out.println("Error in method ottieniTuristi: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in method ottieniTuristi: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        if (null == toReturn) {
            throw new RemoteException(MessaggiErrore.ERRORE_FORMATO_BEAN);
        }
        return toReturn;
    }

    public ArrayList<BeanTurista> ottieniTuristi(boolean statoAccount) throws RemoteException {
        ArrayList<BeanTurista> toReturn;
        try {
            toReturn = turista.ottieniTuristi(statoAccount);
        } catch (SQLException e) {
            System.out.println("Error in method ottieniTuristi (boolean): " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in method ottieniTuristi (boolean): " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        if (null == toReturn) {
            throw new RemoteException(MessaggiErrore.ERRORE_FORMATO_BEAN);
        }
        return toReturn;
    }

    public boolean attivaTurista(int pIdTurista) throws RemoteException {
        if (pIdTurista < 0) {
            throw new RemoteException(MessaggiErrore.ERRORE_DATI);
        }
        BeanTurista toChange;
        try {
            toChange = turista.ottieniTurista(pIdTurista);
            if (toChange.isAttiva()) {
                throw new RemoteException(MessaggiErrore.ERRORE_DATI);
            }
            toChange.setAttiva(true);
            if (turista.modificaTurista(toChange)) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error in method attivaTurista: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in method attivaTurista: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        return false;
    }

    public boolean disattivaTurista(int pIdTurista) throws RemoteException {
        if (pIdTurista < 0) {
            throw new RemoteException(MessaggiErrore.ERRORE_DATI);
        }
        BeanTurista toChange;
        try {
            toChange = turista.ottieniTurista(pIdTurista);
            if (!toChange.isAttiva()) {
                throw new RemoteException(MessaggiErrore.ERRORE_DATI);
            }
            toChange.setAttiva(false);
            if (turista.modificaTurista(toChange)) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error in method disattivaTurista: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in method disattivaTurista: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        return false;
    }

    public boolean delete(int pIdTurista) throws RemoteException {
        if (pIdTurista < 0) {
            throw new RemoteException(MessaggiErrore.ERRORE_DATI);
        }
        try {
            if (turista.cancellaTurista(pIdTurista)) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error in delete method: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in delete method: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        return false;
    }

    public ArrayList<BeanVisitaBC> ottieniFeedbackBC(int pIdTurista) throws RemoteException {
        if (pIdTurista < 0) {
            throw new RemoteException(MessaggiErrore.ERRORE_DATI);
        }
        ArrayList<BeanVisitaBC> toReturn;
        try {
            toReturn = feedbackBC.ottieniListaVisitaBCTurista(pIdTurista);
        } catch (SQLException e) {
            System.out.println("Error in method ottieniFeedbackBC: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in method ottieniFeedbackBC: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        if (null == toReturn) {
            throw new RemoteException(MessaggiErrore.ERRORE_FORMATO_BEAN);
        }
        return toReturn;
    }

    public ArrayList<BeanVisitaPR> ottieniFeedbackPR(int pIdTurista) throws RemoteException {
        if (pIdTurista < 0) {
            throw new RemoteException(MessaggiErrore.ERRORE_DATI);
        }
        ArrayList<BeanVisitaPR> toReturn;
        try {
            toReturn = feedbackPR.ottieniListaVisitaPRTurista(pIdTurista);
        } catch (SQLException e) {
            System.out.println("Error in method ottieniFeedbackPR: " + e.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_DBMS);
        } catch (Exception ee) {
            System.out.println("Error in method ottieniFeedbackPR: " + ee.toString());
            throw new RemoteException(MessaggiErrore.ERRORE_SCONOSCIUTO);
        }
        if (null == toReturn) {
            throw new RemoteException(MessaggiErrore.ERRORE_FORMATO_BEAN);
        }
        return toReturn;
    }
}