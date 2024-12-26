package unisa.gps.etour.control.GestioneBeniCulturali.test.stub;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import unisa.gps.etour.bean.BeanVisitaBC;
import unisa.gps.etour.repository.IDBVisitaBC;

public class DBVisitaBC implements IDBVisitaBC {
    
    public boolean inserisciVisitaBC(BeanVisitaBC PVIS) throws SQLException {
        return false;
    }

    public boolean modificaVisitaBC(BeanVisitaBC PVIS) throws SQLException {
        return false;
    }

    public ArrayList<BeanVisitaBC> ottieniListaVisitaBC(int pIdBeneCulturale) throws SQLException {
        ArrayList<BeanVisitaBC> finteVisite = new ArrayList<BeanVisitaBC>(0);

        finteVisite.add(new BeanVisitaBC(4, 1, "beautiful exhibition", 1, new Date()));
        finteVisite.add(new BeanVisitaBC(3, 1, "show particular but interesting", 1, new Date()));
        finteVisite.add(new BeanVisitaBC(4, 1, "beautiful exhibition", 1, new Date()));
        finteVisite.add(new BeanVisitaBC(3, 1, "show particular but interesting", 1, new Date()));
        finteVisite.add(new BeanVisitaBC(4, 1, "beautiful exhibition", 1, new Date()));
        finteVisite.add(new BeanVisitaBC(3, 1, "show particular but interesting", 1, new Date()));
        finteVisite.add(new BeanVisitaBC(4, 1, "beautiful exhibition", 1, new Date()));
        finteVisite.add(new BeanVisitaBC(3, 1, "show particular but interesting", 1, new Date()));
        finteVisite.add(new BeanVisitaBC(4, 1, "beautiful exhibition", 1, new Date()));
        finteVisite.add(new BeanVisitaBC(3, 1, "show particular but interesting", 1, new Date()));

        finteVisite.add(new BeanVisitaBC(5, 1, "This show is not 'evil'", 1, new Date(new Date().getTime() - 2591000000L)));
        finteVisite.add(new BeanVisitaBC(3, 1, "This show is not 'evil'", 1, new Date(new Date().getTime() - 5182000000L)));

        return finteVisite;
    }

    public ArrayList<BeanVisitaBC> ottieniListaVisitaBCTurista(int pIdTurista) throws SQLException {
        return null;
    }

    public BeanVisitaBC ottieniVisitaBC(int pIdBeneCulturale, int pIdTurista) throws SQLException {
        return null;
    }
}