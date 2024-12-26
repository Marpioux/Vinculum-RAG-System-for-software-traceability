package unisa.gps.etour.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Bean containing information relating to the feedback of a cultural
 *
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public class BeanVisitaBC implements Serializable {
    private static final long serialVersionUID = 3331567128449243852L;
    private int rating;
    private int idBeneCulturale;
    private String comment;
    private int idTurista;
    private Date dataVisita;

    /**
     * Parameterized constructor
     *
     * @param pVoto
     * @param pIdBeneCulturale
     * @param pCommento
     * @param pIdTurista
     * @param pDataVisita
     */
    public BeanVisitaBC(int pVoto, int pIdBeneCulturale, String pCommento, int pIdTurista, Date pDataVisita) {
        setVoto(pVoto);
        setIdBeneCulturale(pIdBeneCulturale);
        setCommento(pCommento);
        setIdTurista(pIdTurista);
        setDataVisita(pDataVisita);
    }

    /**
     * Empty Constructor
     */
    public BeanVisitaBC() {
    }

    /**
     * Returns the value of comment
     *
     * @return value of comment.
     */
    public String getCommento() {
        return comment;
    }

    /**
     * Sets the new value of comment
     *
     * @param pCommento New comment.
     */
    public void setCommento(String pCommento) {
        comment = pCommento;
    }

    /**
     * Returns the value of dataVisita
     *
     * @return value dataVisita.
     */
    public Date getDataVisita() {
        return dataVisita;
    }

    /**
     * Sets the new value of dataVisita
     *
     * @param pDataVisita New dataVisita.
     */
    public void setDataVisita(Date pDataVisita) {
        dataVisita = pDataVisita;
    }

    /**
     * Returns the value of idBeneCulturale
     *
     * @return value idBeneCulturale.
     */
    public int getIdBeneCulturale() {
        return idBeneCulturale;
    }

    /**
     * Sets the new value of idBeneCulturale
     *
     * @param pIdBeneCulturale New idBeneCulturale.
     */
    public void setIdBeneCulturale(int pIdBeneCulturale) {
        idBeneCulturale = pIdBeneCulturale;
    }

    /**
     * Returns the value of idTurista
     *
     * @return value idTurista.
     */
    public int getIdTurista() {
        return idTurista;
    }

    /**
     * Sets the new value of idTurista
     *
     * @param pIdTurista New idTurista.
     */
    public void setIdTurista(int pIdTurista) {
        idTurista = pIdTurista;
    }

    /**
     * Returns the value of rating
     *
     * @return value of rating.
     */
    public int getVoto() {
        return rating;
    }

    /**
     * Sets the new value of rating
     *
     * @param pVoto New value for rating.
     */
    public void setVoto(int pVoto) {
        rating = pVoto;
    }
}