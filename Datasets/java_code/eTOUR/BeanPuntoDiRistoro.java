package unisa.gps.etour.bean;

import java.io.Serializable;
import java.util.Date;
import unisa.gps.etour.util.Punto3D;

/**
 * Bean for the storage of data refreshment
 *
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public class BeanPuntoDiRistoro implements Serializable {
    private static final long serialVersionUID = 8417686685147484931L;
    private int id;
    private int numeroVoti;
    private double mediaVoti;
    private String name;
    private String description;
    private String phone;
    private String location;
    private String city;
    private String street;
    private String cap;
    private String province;
    private String partitaIva;
    private Punto3D position;
    private Date orarioApertura;
    private Date orarioChiusura;
    private String giornoChiusura;

    /**
     * Parameterized constructor
     *
     * @param pid
     * @param pNumeroVoti
     * @param pMediaVoti
     * @param pNome
     * @param pDescrizione
     * @param pTelefono
     * @param pLocalita
     * @param pCitta
     * @param pVia
     * @param pCap
     * @param pProvincia
     * @param pPartitaIva
     * @param pPosizione
     * @param pOrarioApertura
     * @param pOrarioChiusura
     * @param pGiornoChiusura
     */
    public BeanPuntoDiRistoro(int pid, int pNumeroVoti, double pMediaVoti,
                               String pNome, String pDescrizione, String pTelefono,
                               String pLocalita, String pCitta, String pVia, String pCap,
                               String pProvincia, String pPartitaIva, Punto3D pPosizione,
                               Date pOrarioApertura, Date pOrarioChiusura, String pGiornoChiusura) {
        setId(pid);
        setNumeroVoti(pNumeroVoti);
        setMediaVoti(pMediaVoti);
        setNome(pNome);
        setDescrizione(pDescrizione);
        setTelefono(pTelefono);
        setLocale(pLocalita);
        setCitta(pCitta);
        setVar(pVia);
        setCap(pCap);
        setProvincia(pProvincia);
        setPartitaIva(pPartitaIva);
        setPosizione(pPosizione);
        setOrarioApertura(pOrarioApertura);
        setOrarioChiusura(pOrarioChiusura);
        setGiornoChiusura(pGiornoChiusura);
    }

    /**
     * Empty Constructor
     */
    public BeanPuntoDiRistoro() {
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
     * Returns the value of city
     *
     * @return Value of city.
     */
    public String getCitta() {
        return city;
    }

    /**
     * Sets the new value of city
     *
     * @param pCitta New value for city.
     */
    public void setCitta(String pCitta) {
        this.city = pCitta;
    }

    /**
     * Returns the value of description
     *
     * @return value of description.
     */
    public String getDescrizione() {
        return description;
    }

    /**
     * Sets the new value of description
     *
     * @param pDescrizione New value of description.
     */
    public void setDescrizione(String pDescrizione) {
        this.description = pDescrizione;
    }

    /**
     * Returns the value of giornoChiusura
     *
     * @return value giornoChiusura.
     */
    public String getGiornoChiusura() {
        return giornoChiusura;
    }

    /**
     * Sets the new value of giornoChiusura
     *
     * @param pGiornoChiusura New giornoChiusura.
     */
    public void setGiornoChiusura(String pGiornoChiusura) {
        this.giornoChiusura = pGiornoChiusura;
    }

    /**
     * Returns the value of location
     *
     * @return locale values.
     */
    public String getLocale() {
        return location;
    }

    /**
     * Sets the new value of location
     *
     * @param pLocalita New locale values.
     */
    public void setLocale(String pLocalita) {
        this.location = pLocalita;
    }

    /**
     * Returns the value of mediaVoti
     *
     * @return value mediaVoti.
     */
    public double getMediaVoti() {
        return mediaVoti;
    }

    /**
     * Sets the new value of mediaVoti
     *
     * @param pMediaVoti New mediaVoti.
     */
    public void setMediaVoti(double pMediaVoti) {
        this.mediaVoti = pMediaVoti;
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
     * @param pNome New value for name.
     */
    public void setNome(String pNome) {
        this.name = pNome;
    }

    /**
     * Returns the value of numeroVoti
     *
     * @return value numeroVoti.
     */
    public int getNumeroVoti() {
        return numeroVoti;
    }

    /**
     * Sets the new value of numeroVoti
     *
     * @param pNumeroVoti New numeroVoti.
     */
    public void setNumeroVoti(int pNumeroVoti) {
        this.numeroVoti = pNumeroVoti;
    }

    /**
     * Returns the value of orarioApertura
     *
     * @return value orarioApertura.
     */
    public Date getOrarioApertura() {
        return orarioApertura;
    }

    /**
     * Sets the new value of orarioApertura
     *
     * @param pOrarioApertura New orarioApertura.
     */
    public void setOrarioApertura(Date pOrarioApertura) {
        this.orarioApertura = pOrarioApertura;
    }

    /**
     * Returns the value of orarioChiusura
     *
     * @return value orarioChiusura.
     */
    public Date getOrarioChiusura() {
        return orarioChiusura;
    }

    /**
     * Sets the new value of orarioChiusura
     *
     * @param pOrarioChiusura New orarioChiusura.
     */
    public void setOrarioChiusura(Date pOrarioChiusura) {
        this.orarioChiusura = pOrarioChiusura;
    }

    /**
     * Returns the value of partitaIva
     *
     * @return value of partitaIva.
     */
    public String getPartitaIva() {
        return partitaIva;
    }

    /**
     * Sets the new value of partitaIva
     *
     * @param pPartitaIva New value of partitaIva.
     */
    public void setPartitaIva(String pPartitaIva) {
        this.partitaIva = pPartitaIva;
    }

    /**
     * Returns the value of position
     *
     * @return value of position.
     */
    public Punto3D getPosizione() {
        return position;
    }

    /**
     * Sets the new position value
     *
     * @param pPosizione New position value.
     */
    public void setPosizione(Punto3D pPosizione) {
        this.position = pPosizione;
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
     * @param pProvincia New value for the province.
     */
    public void setProvincia(String pProvincia) {
        this.province = pProvincia;
    }

    /**
     * Returns the value of telephone
     *
     * @return Value of the phone.
     */
    public String getTelefono() {
        return phone;
    }

    /**
     * Sets the new value of telephone
     *
     * @param pTelefono New value for phone.
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
     * Sets the new value for street
     *
     * @param pVia New value for street.
     */
    public void setVar(String pVia) {
        this.street = pVia;
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