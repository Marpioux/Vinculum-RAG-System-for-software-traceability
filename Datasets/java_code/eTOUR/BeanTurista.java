package unisa.gps.etour.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Bean containing information relating to a tourist
 *
 * @author Mauro Miranda
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public class BeanTurista implements Serializable {
    private static final long serialVersionUID = 4214744963263090577L;
    private int id;
    private String username;
    private String name;
    private String cittaNascita;
    private String cittaResidenza;
    private String phone;
    private String cap;
    private String street;
    private String province;
    private String email;
    private String password;
    private Date dob;
    private Date dataRegistrazione;
    private boolean active;

    /**
     * Parameterized constructor
     *
     * @param pid
     * @param pUsername
     * @param pNome
     * @param pCognome
     * @param pCittaNascita
     * @param pCittaResidenza
     * @param pTelefono
     * @param pCap
     * @param pVia
     * @param pProvincia
     * @param pEmail
     * @param pPassword
     * @param pDataNascita
     * @param pDataRegistrazione
     * @param pAttiva
     */
    public BeanTurista(int pid, String pUsername, String pNome, String pCognome, String pCittaNascita,
                       String pCittaResidenza, String pTelefono, String pCap, String pVia,
                       String pProvincia, String pEmail, String pPassword, Date pDataNascita,
                       Date pDataRegistrazione, boolean pAttiva) {
        setId(pid);
        setUsername(pUsername);
        setNome(pNome);
        setCognome(pCognome);
        setCittaNascita(pCittaNascita);
        setCittaResidenza(pCittaResidenza);
        setTelefono(pTelefono);
        setCap(pCap);
        setVar(pVia);
        setProvincia(pProvincia);
        setEmail(pEmail);
        setPassword(pPassword);
        setDataNascita(pDataNascita);
        setDataRegistrazione(pDataRegistrazione);
        setAttiva(pAttiva);
    }

    /**
     * Empty Constructor
     */
    public BeanTurista() {
    }

    /**
     * Returns the value of cap
     *
     * @return value cap.
     */
    public String getCap() {
        return cap;
    }

    /**
     * Sets the new value of cap
     *
     * @param pCap value cap.
     */
    public void setCap(String pCap) {
        this.cap = pCap;
    }

    /**
     * Returns the value of cittaNascita
     *
     * @return value cittaNascita.
     */
    public String getCittaNascita() {
        return cittaNascita;
    }

    /**
     * Sets the new value of cittaNascita
     *
     * @param pCittaNascita new cittaNascita.
     */
    public void setCittaNascita(String pCittaNascita) {
        this.cittaNascita = pCittaNascita;
    }

    /**
     * Returns the value of cittaResidenza
     *
     * @return value cittaResidenza.
     */
    public String getCittaResidenza() {
        return cittaResidenza;
    }

    /**
     * Sets the new value of cittaResidenza
     *
     * @param pCittaResidenza new cittaResidenza.
     */
    public void setCittaResidenza(String pCittaResidenza) {
        this.cittaResidenza = pCittaResidenza;
    }

    /**
     * Returns the value of name
     *
     * @return value of name.
     */
    public String getCognome() {
        return name;
    }

    /**
     * Sets the new value of name
     *
     * @param pCognome new value of surname.
     */
    public void setCognome(String pCognome) {
        this.name = pCognome;
    }

    /**
     * Returns the value of dob
     *
     * @return value dob.
     */
    public Date getDataNascita() {
        return dob;
    }

    /**
     * Sets the new value of dob
     *
     * @param pDataNascita new value of dob.
     */
    public void setDataNascita(Date pDataNascita) {
        this.dob = pDataNascita;
    }

    /**
     * Returns the value of dataRegistrazione
     *
     * @return value dataRegistrazione.
     */
    public Date getDataRegistrazione() {
        return dataRegistrazione;
    }

    /**
     * Sets the new value of dataRegistrazione
     *
     * @param pDataRegistrazione new dataRegistrazione.
     */
    public void setDataRegistrazione(Date pDataRegistrazione) {
        this.dataRegistrazione = pDataRegistrazione;
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
     * @param pEmail new value of email.
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
     * @param pNome new value.
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
     * Returns the value of the province
     *
     * @return value of the province.
     */
    public String getProvincia() {
        return province;
    }

    /**
     * Sets the new value of the province
     *
     * @param pProvincia new value for the province.
     */
    public void setProvincia(String pProvincia) {
        this.province = pProvincia;
    }

    /**
     * Returns the value of telephone
     *
     * @return value of the phone.
     */
    public String getTelefono() {
        return phone;
    }

    /**
     * Sets the new value of telephone
     *
     * @param pTelefono new value of phone.
     */
    public void setTelefono(String pTelefono) {
        this.phone = pTelefono;
    }

    /**
     * Returns the value of street
     *
     * @return value of street.
     */
    public String getVar() {
        return street;
    }

    /**
     * Sets the new value of street
     *
     * @param pVia new value of street.
     */
    public void setVar(String pVia) {
        this.street = pVia;
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
     * Returns whether the tourist is active or not
     *
     * @return value of activation.
     */
    public boolean isAttiva() {
        return active;
    }

    /**
     * Sets the new value of active
     *
     * @param pAttiva new value for active.
     */
    public void setAttiva(boolean pAttiva) {
        this.active = pAttiva;
    }

    /**
     * Sets the new value of username
     *
     * @param pUsername new value for username.
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
     * @return value of id.
     */
    public int getId() {
        return id;
    }
}