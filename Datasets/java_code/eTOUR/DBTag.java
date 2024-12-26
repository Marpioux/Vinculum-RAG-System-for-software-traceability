package unisa.gps.etour.control.GestioneBeniCulturali.test.stub;

import java.sql.SQLException;
import java.util.ArrayList;

import unisa.gps.etour.bean.BeanTag;
import unisa.gps.etour.repository.IDBTag;

public class DBTag implements IDBTag {
    private ArrayList<BeanTag> b;

    public DBTag() {
        b = new ArrayList<BeanTag>(0);
    }

    public boolean aggiungeTagBeneCulturale(int pIdBeneCulturale, int pIdTag) throws SQLException {
        return true;
    }

    public boolean aggiungeTagPuntoDiRistoro(int pIdPuntoDiRistoro, int pIdTag) throws SQLException {
        return true;
    }

    public boolean cancellaTag(int pIdTag) throws SQLException {
        return true;
    }

    public boolean cancellaTagBeneCulturale(int pIdBeneCulturale, int pIdTag) throws SQLException {
        return true;
    }

    public boolean cancellaTagPuntoDiRistoro(int pIdPuntoDiRistoro, int pIdTag) throws SQLException {
        return true;
    }

    public boolean inserisciTag(BeanTag ptagi) throws SQLException {
        return true;
    }

    public boolean modificaTag(BeanTag ptagi) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    public ArrayList<BeanTag> ottieniListaTag() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    public BeanTag ottieniTag(int pid) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    public ArrayList<BeanTag> ottieniTagBeneCulturale(int pIdBeneCulturale) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    public ArrayList<BeanTag> ottieniTagPuntoDiRistoro(int pIdPuntoDiRistoro) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }
}