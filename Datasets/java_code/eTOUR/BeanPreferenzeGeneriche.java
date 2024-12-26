package unisa.gps.etour.bean;

/**
 * Bean containing information relating to the General Preferences
 *
 * @author Mauro Miranda
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University of Salerno
 */

import java.io.Serializable;

public class BeanPreferenzeGeneriche implements Serializable {
    private static final long serialVersionUID = 6805656922951334071L;
    private int id;
    private int dimensioneFont;
    private String font;
    private String tema;
    private int idTurista;

    /**
     * Parameterized constructor
     *
     * @param pId
     * @param pDimensioneFont
     * @param pFont
     * @param pTema
     * @param pIdTurista
     */
    public BeanPreferenzeGeneriche(int pId, int pDimensioneFont, String pFont, String pTema, int pIdTurista) {
        setId(pId);
        setDimensioneFont(pDimensioneFont);
        setFont(pFont);
        setTema(pTema);
        setIdTurista(pIdTurista);
    }

    /**
     * Empty Constructor
     */
    public BeanPreferenzeGeneriche() {
    }

    /**
     * Returns the value of dimensioneFont
     *
     * @return value dimensioneFont.
     */
    public int getDimensioneFont() {
        return dimensioneFont;
    }

    /**
     * Sets the new value of dimensioneFont
     *
     * @param pDimensioneFont New dimensioneFont.
     */
    public void setDimensioneFont(int pDimensioneFont) {
        dimensioneFont = pDimensioneFont;
    }

    /**
     * Returns the value of font
     *
     * @return Value of font.
     */
    public String getFont() {
        return font;
    }

    /**
     * Sets the new value of font
     *
     * @param pFont New value of font.
     */
    public void setFont(String pFont) {
        font = pFont;
    }

    /**
     * Returns the value of the tema
     *
     * @return value tema.
     */
    public String getTema() {
        return tema;
    }

    /**
     * Sets the new value of the tema
     *
     * @param pTema New value for tema.
     */
    public void setTema(String pTema) {
        tema = pTema;
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
     * @param pIdTurista New value for idTurista.
     */
    public void setIdTurista(int pIdTurista) {
        idTurista = pIdTurista;
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