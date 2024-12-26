/**
 * Attribute_Definition
 *
 * @Author Fabio Palladino
 * @Version 0.1
 *
 * © 2007 eTour Project - Copyright by DMI SE @ SA Lab - University of Salerno
 */

package unisa.gps.etour.control.GestioneAdvertisement.test.stubs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import unisa.gps.etour.bean.BeanConvenzione;
import unisa.gps.etour.bean.BeanPuntoDiRistoro;
import unisa.gps.etour.repository.IDBConvenzione;

public class DBConvenzione implements IDBConvenzione {
    private static int numTest = 1;

    /* (Non-Javadoc)
     * @See unisa.gps.etour.repository.IDBConvenzione#cancellaConvenzione(int)
     */
    @Override
    public boolean cancellaConvenzione(int pIdConvenzione) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /* (Non-Javadoc)
     * @See unisa.gps.etour.repository.IDBConvenzione#getStoricoConvenzione(int)
     */
    @Override
    public ArrayList<BeanConvenzione> ottieniStoricoConvenzione(int idPuntoDiRistoro) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (Non-Javadoc)
     * @See unisa.gps.etour.repository.IDBConvenzione#inserisciConvenzione(unisa.gps.etour.bean.BeanConvenzione)
     */
    @Override
    public boolean inserisciConvenzione(BeanConvenzione pConvenzione) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /* (Non-Javadoc)
     * @See unisa.gps.etour.repository.IDBConvenzione#modificaConvenzione(unisa.gps.etour.bean.BeanConvenzione)
     */
    @Override
    public boolean modificaConvenzione(BeanConvenzione pConvenzione) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    /* (Non-Javadoc)
     * @See unisa.gps.etour.repository.IDBConvenzione#ottieniConvezioneAttiva(int)
     */
    @Override
    public BeanConvenzione ottieniConvezioneAttiva(int pIdPuntoDiRistoro) throws SQLException {
        BeanConvenzione convenzione = new BeanConvenzione();
        convenzione.setAttiva(true);
        convenzione.setDataFine(new Date());
        convenzione.setDataInizio(new Date());
        convenzione.setId(12);
        convenzione.setIdPuntoDiRistoro(3);
        convenzione.setPrezzo(100);

        if (numTest == 1) {
            /* Test banners allowed */
            convenzione.setMaxBanner(4);
            return convenzione;
        } else if (numTest == 2) {
            /* Test banners not allowed */
            convenzione.setMaxBanner(3);
            return convenzione;
        } else {
            return null;
        }
    }

    /* (Non-Javadoc)
     * @See unisa.gps.etour.repository.IDBConvenzione#ottieniListaConvenzioneAttivaPR()
     */
    @Override
    public ArrayList<BeanPuntoDiRistoro> ottieniListaConvenzioneAttivaPR() throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    public static void setNumTest(int numTest) {
        DBConvenzione.numTest = numTest;
    }
}