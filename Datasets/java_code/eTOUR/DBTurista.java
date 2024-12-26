package unisa.gps.etour.control.GestioneBeniCulturali.test.stub;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import unisa.gps.etour.bean.BeanTurista;
import unisa.gps.etour.repository.IDBTurista;

public class DBTurista implements IDBTurista {

    public boolean cancellaBeneCulturalePreferito(int pIdTurista, int pIdBeneCulturale) throws SQLException {
        return false;
    }

    public boolean cancellaPuntoDiRistoroPreferito(int pIdTurista, int pIdPuntoDiRistoro) throws SQLException {
        return false;
    }

    public boolean delete(int pIdTurista) throws SQLException {
        return false;
    }

    public boolean inserisciBeneCulturalePreferito(int pIdTurista, int pIdBeneCulturale) throws SQLException {
        return false;
    }

    public boolean inserisciPuntoDiRistoroPreferito(int pIdTurista, int pIdPuntoDiRistoro) throws SQLException {
        return false;
    }

    public boolean inserisciTurista(BeanTurista pTurista) throws SQLException {
        return false;
    }

    public boolean modificaTurista(BeanTurista pTurista) throws SQLException {
        return false;
    }

    public BeanTurista ottieniTurista(String pUsername) throws SQLException {
        return null;
    }

    public BeanTurista ottieniTurista(int pIdTurista) throws SQLException {
        return new BeanTurista(1, "username", "Astrubale", "Silberschatz", "Naples", "Naples", "0111111", "80100th", "Way of the systems, 1", "NA",
                "trapano@solitario.it", "passwordsolomia", new Date(), new Date(), true);
    }

    public ArrayList<BeanTurista> ottieniTuristi(String pUsernameTurista) throws SQLException {
        return null;
    }

    public ArrayList<BeanTurista> ottieniTuristi(boolean condition) throws SQLException {
        return null;
    }
}