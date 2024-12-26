package unisa.gps.etour.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Bean that contains the data for feedback to a refreshment
 *
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public class BeanVisitaPR implements Serializable {

    private static final long serialVersionUID = -4065240072283418782L;
    private int rating;
    private int idPuntoDiRistoro;
    private String comment;
    private int idTurista;
    private Date dataVisita;

    /**
     * Parameterized constructor
     *
     * @param pVoto
     * @param pIdPuntoDiRistoro
     * @param pCommento
     * @param pIdTurista
     * @param pDataVisita
     */
    public BeanVisitaPR(int pVoto, int pIdPuntoDiRistoro, String pCommento, int pIdTurista, Date pDataVisita) {
        setVoto(pVoto);
        setIdPuntoDiRistoro(pIdPuntoDiRistoro);
        setCommento(pCommento);
        setIdTurista(pIdTurista);
        setDataVisita(pDataVisita);
    }

    /**
     * Empty Constructor
     */
    public BeanVisitaPR() {
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
     * @return value of dataVisita.
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
        idPuntoDiRistoro = pIdPuntoDiRistoro;
    }

    /**
     * Returns the value of idTurista
     *
     * @return value of idTurista.
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
     * @param pVoto New value to vote.
     */
    public void setVoto(int pVoto) {
        rating = pVoto;
    }
}