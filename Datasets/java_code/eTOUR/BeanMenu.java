package unisa.gps.etour.bean;

import java.io.Serializable;

/**
 * Bean containing information relating to a Menu
 *
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public class BeanMenu implements Serializable {
    private static final long serialVersionUID = -3112032222839565409L;
    private int id;
    private String day;
    private int idPuntoDiRistoro;

    /**
     * Parameterized constructor
     *
     * @param pId
     * @param pGiorno
     * @param pIdPuntoDiRistoro
     */
    public BeanMenu(int pId, String pGiorno, int pIdPuntoDiRistoro) {
        setId(pId);
        setGiorno(pGiorno);
        setIdPuntoDiRistoro(pIdPuntoDiRistoro);
    }

    /**
     * Empty Constructor
     */
    public BeanMenu() {
    }

    /**
     * Returns the value of days
     *
     * @return Value of the day.
     */
    public String getGiorno() {
        return day;
    }

    /**
     * Sets the new value of days
     *
     * @param pGiorno New pGiorno day.
     */
    public void setGiorno(String pGiorno) {
        day = pGiorno;
    }

    /**
     * Returns the value of id
     *
     * @return value id.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the value of idPuntoDiRistoro
     *
     * @return value idPuntoDiRistoro.
     */
    public int getIdPuntoDiRistoro() {
        return idPuntoDiRistoro;
    }

    /**
     * Sets the new value of id
     *
     * @param pId New value for id.
     */
    public void setId(int pId) {
        id = pId;
    }

    /**
     * Sets the new value of idPuntoDiRistoro
     *
     * @param pIdPuntoDiRistoro New idPuntoDiRistoro.
     */
    public void setIdPuntoDiRistoro(int pIdPuntoDiRistoro) {
        idPuntoDiRistoro = pIdPuntoDiRistoro;
    }
}