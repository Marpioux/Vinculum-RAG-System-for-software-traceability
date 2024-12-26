package unisa.gps.etour.bean;

import java.io.Serializable;

/**
 * Bean containing information relating to food
 *
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */

public class BeanPiatto implements Serializable {
    private int id;
    private String name;
    private double price;
    private int idMenu;
    private static final long serialVersionUID = -3775462843748984482L;

    /**
     * Parameterized constructor
     *
     * @param pId
     * @param Pnom
     * @param pPrezzo
     * @param pIdMenu
     */
    public BeanPiatto(int pid, String Pnom, double pPrezzo, int pIdMenu) {
        setId(pid);
        setNome(Pnom);
        setPrezzo(pPrezzo);
        setIdMenu(pIdMenu);
    }

    /**
     * Empty Constructor
     */
    public BeanPiatto() {
    }

    /**
     * Returns the value of name
     *
     * @return value of name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the new name value
     *
     * @param Pnom New value for name.
     */
    public void setNome(String Pnom) {
        name = Pnom;
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
     * @param pPrezzo New value for price.
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
     * Returns the value of idMenu
     *
     * @return value of idMenu.
     */
    public int getIdMenu() {
        return idMenu;
    }

    /**
     * Sets the new value of id
     *
     * @param pid New value for id.
     */
    public void setId(int pid) {
        id = pid;
    }

    /**
     * Sets the new value of idMenu
     *
     * @param pIdMenu New idMenu.
     */
    public void setIdMenu(int pIdMenu) {
        idMenu = pIdMenu;
    }
}