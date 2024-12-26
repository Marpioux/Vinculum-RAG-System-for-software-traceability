package unisa.gps.etour.bean;

import java.io.Serializable;

/**
 * Bean containing information relating to food
 *
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public class BeanOperatorePuntoDiRistoro implements Serializable {
    private int id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private int idPuntoDiRistoro;
    private static final long serialVersionUID = -6485826396352557404L;

    /**
     * Parameterized constructor
     *
     * @param pId
     * @param pName
     * @param pSurname
     * @param pUsername
     * @param pPassword
     * @param pEmail
     * @param pIdPuntoDiRistoro
     */
    public BeanOperatorePuntoDiRistoro(int pId, String pName, String pSurname,
                                        String pUsername, String pPassword, String pEmail,
                                        int pIdPuntoDiRistoro) {
        setId(pId);
        setName(pName);
        setSurname(pSurname);
        setUsername(pUsername);
        setPassword(pPassword);
        setEmail(pEmail);
        setIdPuntoDiRistoro(pIdPuntoDiRistoro);
    }

    /**
     * Empty Constructor
     */
    public BeanOperatorePuntoDiRistoro() {
    }

    /**
     * Returns the value of surname
     *
     * @return value of surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the new value of surname
     *
     * @param pSurname New surname.
     */
    public void setSurname(String pSurname) {
        this.surname = pSurname;
    }

    /**
     * Returns the value of email
     *
     * @return value of email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the new value of email
     *
     * @param pEmail New value of email.
     */
    public void setEmail(String pEmail) {
        this.email = pEmail;
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
     * @param pName New value name.
     */
    public void setName(String pName) {
        this.name = pName;
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
    public String getUsername() {
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
     * Returns the value of idPuntoDiRistoro
     *
     * @return value of idPuntoDiRistoro.
     */
    public int getIdPuntoDiRistoro() {
        return idPuntoDiRistoro;
    }

    /**
     * Sets the new value of idPuntoDiRistoro
     *
     * @param pIdPuntoDiRistoro New idPuntoDiRistoro.
     */
    public void setIdPuntoDiRistoro(int pIdPuntoDiRistoro) {
        this.idPuntoDiRistoro = pIdPuntoDiRistoro;
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
        this.id = pId;
    }
}