package unisa.gps.etour.bean;

import java.io.Serializable;

/**
 * Bean containing information relating to an Agency Operator
 *
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public class BeanOperatoreAgenzia implements Serializable {

    private static final long serialVersionUID = -3489147679484477440L;
    private int id;
    private String username;
    private String name;
    private String cognome;
    private String password;

    /**
     * Parameterized constructor
     *
     * @param pid
     * @param pUsername
     * @param pNome
     * @param pCognome
     * @param pPassword
     */
    public BeanOperatoreAgenzia(int pid, String pUsername, String pNome, String pCognome, String pPassword) {
        setId(pid);
        setUsername(pUsername);
        setNome(pNome);
        setCognome(pCognome);
        setPassword(pPassword);
    }

    /**
     * Empty Constructor
     */
    public BeanOperatoreAgenzia() {
    }

    /**
     * Returns the value of cognome
     *
     * @return value of cognome.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Sets the new value of cognome
     *
     * @param pCognome New surname.
     */
    public void setCognome(String pCognome) {
        this.cognome = pCognome;
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
     * @param pNome New value.
     */
    public void setNome(String pNome) {
        this.name = pNome;
    }

    /**
     * Returns the value of password
     *
     * @return value of password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the new password value
     *
     * @param pPassword new password value.
     */
    public void setPassword(String pPassword) {
        this.password = pPassword;
    }

    /**
     * Returns the value of username
     *
     * @return value of username.
     */
    public String getUserName() {
        return username;
    }

    /**
     * Sets the new value of username
     *
     * @param pUsername New value for username.
     */
    public void setUsername(String pUsername) {
        this.username = pUsername;
    }

    /**
     * Sets the new value of id
     *
     * @param pid new value of id.
     */
    public void setId(int pid) {
        this.id = pid;
    }

    /**
     * Returns the value of id
     *
     * @return value id.
     */
    public int getId() {
        return id;
    }
}