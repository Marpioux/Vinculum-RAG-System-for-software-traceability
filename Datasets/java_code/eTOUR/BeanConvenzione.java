package unisa.gps.etour.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Bean containing information relating to a Convention
 *
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public class BeanConvenzione implements Serializable {

    private static final long serialVersionUID = -3255500940680220001L;
    private int id;
    private int maxBanner;
    private Date startDate;
    private Date endDate;
    private double price;
    private boolean active;
    private int idPuntoDiRistoro;

    /**
     * Parameterized constructor
     *
     * @param pId
     * @param pMaxBanner
     * @param pDataInizio
     * @param pDataFine
     * @param pPrezzo
     * @param pActs
     * @param pIdPuntoDiRistoro
     */
    public BeanConvenzione(int pId, int pMaxBanner, Date pDataInizio,
                           Date pDataFine, double pPrezzo, boolean pActs,
                           int pIdPuntoDiRistoro) {
        setId(pId);
        setMaxBanner(pMaxBanner);
        setDataInizio(pDataInizio);
        setDataFine(pDataFine);
        setPrezzo(pPrezzo);
        setAttiva(pActs);
        setIdPuntoDiRistoro(pIdPuntoDiRistoro);
    }

    /**
     * Empty Constructor
     */
    public BeanConvenzione() {
    }

    /**
     * Returns the value of active
     *
     * @return value of active.
     */
    public boolean isAttiva() {
        return active;
    }

    /**
     * Sets the new value of active
     *
     * @param pActs new value of active.
     */
    public void setAttiva(boolean pActs) {
        active = pActs;
    }

    /**
     * Returns the value of endDate
     *
     * @return value of endDate.
     */
    public Date getDataFine() {
        return endDate;
    }

    /**
     * Sets the new value for endDate
     *
     * @param pDataFine new value for endDate.
     */
    public void setDataFine(Date pDataFine) {
        endDate = pDataFine;
    }

    /**
     * Returns the value of startDate
     *
     * @return value of startDate.
     */
    public Date getDataInizio() {
        return startDate;
    }

    /**
     * Sets the new value of startDate
     *
     * @param pDataInizio new value for startDate.
     */
    public void setDataInizio(Date pDataInizio) {
        startDate = pDataInizio;
    }

    /**
     * Returns the value of maxBanner
     *
     * @return value of maxBanner.
     */
    public int getMaxBanner() {
        return maxBanner;
    }

    /**
     * Sets the new value of maxBanner
     *
     * @param pMaxBanner new maxBanner.
     */
    public void setMaxBanner(int pMaxBanner) {
        maxBanner = pMaxBanner;
    }

    /**
     * Returns the value of price
     *
     * @return value of price.
     */
    public double getPrezzo() {
        return price;
    }

    /**
     * Sets the new value of price
     *
     * @param pPrezzo new value for price.
     */
    public void setPrezzo(double pPrezzo) {
        price = pPrezzo;
    }

    /**
     * Returns the value of id
     *
     * @return value of id.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the value of idPuntoDiRistoro
     *
     * @return value of idPuntoDiRistoro.
     */
    public int getIdPuntoDiRistoro() {
        return idPuntoDiRistoro;
    }

    /**
     * Sets the new value of id
     *
     * @param pId new value for id.
     */
    public void setId(int pId) {
        id = pId;
    }

    /**
     * Sets the new value of idPuntoDiRistoro
     *
     * @param pIdPuntoDiRistoro new idPuntoDiRistoro.
     */
    public void setIdPuntoDiRistoro(int pIdPuntoDiRistoro) {
        idPuntoDiRistoro = pIdPuntoDiRistoro;
    }
}