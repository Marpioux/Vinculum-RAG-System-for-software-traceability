package unisa.gps.etour.bean;

/**
 * Bean containing information relating to the News
 *
 * @author Mauro Miranda
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */

import java.io.Serializable;
import java.util.Date;

public class BeanNews implements Serializable {
    private String news;
    private Date dataPubblicazione;
    private Date dataScadenza;
    private int priority;
    private int id;
    private static final long serialVersionUID = -6249491056436689386L;

    /**
     * Parameterized constructor
     *
     * @param Pnews
     * @param pDataPubblicazione
     * @param pDataScadenza
     * @param pPriorita
     * @param pId
     */
    public BeanNews(String Pnews, Date pDataPubblicazione, Date pDataScadenza, int pPriorita, int pid) {
        setNews(Pnews);
        setDataPubblicazione(pDataPubblicazione);
        setDataScadenza(pDataScadenza);
        setPriorita(pPriorita);
        setId(pid);
    }

    /**
     * Empty Constructor
     */
    public BeanNews() {
    }

    /**
     * Returns the value of dataPubblicazione
     *
     * @return value dataPubblicazione.
     */
    public Date getDataPubblicazione() {
        return dataPubblicazione;
    }

    /**
     * Sets the new value of dataPubblicazione
     *
     * @param pDataPubblicazione New dataPubblicazione.
     */
    public void setDataPubblicazione(Date pDataPubblicazione) {
        dataPubblicazione = pDataPubblicazione;
    }

    /**
     * Returns the value of dataScadenza
     *
     * @return value dataScadenza.
     */
    public Date getDataScadenza() {
        return dataScadenza;
    }

    /**
     * Sets the new value of dataScadenza
     *
     * @param pDataScadenza New dataScadenza.
     */
    public void setDataScadenza(Date pDataScadenza) {
        dataScadenza = pDataScadenza;
    }

    /**
     * Returns the value of news
     *
     * @return value of news.
     */
    public String getNews() {
        return news;
    }

    /**
     * Sets the new value of news
     *
     * @param Pnews New Pnews news.
     */
    public void setNews(String Pnews) {
        news = Pnews;
    }

    /**
     * Returns the priority value
     *
     * @return the priority value.
     */
    public int getPriorita() {
        return priority;
    }

    /**
     * Set the new priority value
     *
     * @param pPriorita New priority value.
     */
    public void setPriorita(int pPriorita) {
        priority = pPriorita;
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
     * @param pid New value for id.
     */
    public void setId(int pid) {
        id = pid;
    }
}