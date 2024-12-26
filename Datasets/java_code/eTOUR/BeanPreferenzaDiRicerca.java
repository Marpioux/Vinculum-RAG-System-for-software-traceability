package unisa.gps.etour.bean;

import java.io.Serializable;

/**
 * Bean which contains data search preferences
 *
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public class BeanPreferenzaDiRicerca implements Serializable {
    private static final long serialVersionUID = 7576354037868937929L;
    private int id;
    private String name;

    /**
     * Parameterized constructor
     *
     * @param pId
     * @param pNom
     */
    public BeanPreferenzaDiRicerca(int pId, String pNom) {
        setId(pId);
        setNome(pNom);
    }

    /**
     * Empty Constructor
     */
    public BeanPreferenzaDiRicerca() {
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
     * @param pNom New value.
     */
    public void setNome(String pNom) {
        name = pNom;
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
     * Sets the new value of id
     *
     * @param pId New value for id.
     */
    public void setId(int pId) {
        id = pId;
    }
}