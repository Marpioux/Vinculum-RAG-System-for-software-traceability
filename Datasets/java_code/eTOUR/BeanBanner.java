package unisa.gps.etour.bean;

/**
 * Bean which contains data on the Banner
 *
 * @Author Mauro Miranda
 * @Version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */

import java.io.Serializable;

public class BeanBanner implements Serializable {
    private static final long serialVersionUID = -872783211721655763L;
    private int id;
    private int idPuntoDiRistoro;
    private String filepath;

    /**
     * Parameterized constructor
     *
     * @param pid
     * @param pPercorsoFile
     * @param pidPuntoDiRistoro
     */
    public BeanBanner(int pid, String pPercorsoFile, int pidPuntoDiRistoro) {
        setId(pid);
        setPercorsoFile(pPercorsoFile);
        setIdPuntoDiRistoro(pidPuntoDiRistoro);
    }

    /**
     * Empty Constructor
     */
    public BeanBanner() {
    }

    /**
     * Returns the value of FilePath
     *
     * @return value of FilePath.
     */
    public String getPercorsoFile() {
        return filepath;
    }

    /**
     * Sets the new value of filepath
     *
     * @param pPercorsoFile New value filepath.
     */
    public void setPercorsoFile(String pPercorsoFile) {
        filepath = pPercorsoFile;
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
     * Returns the value of idPuntoDiRistoro
     *
     * @return value idPuntoDiRistoro.
     */
    public int getIdPuntoDiRistoro() {
        return idPuntoDiRistoro;
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
     * Sets the new value of idPuntoDiRistoro
     *
     * @param pIdPuntoDiRistoro New idPuntoDiRistoro.
     */
    public void setIdPuntoDiRistoro(int pIdPuntoDiRistoro) {
        idPuntoDiRistoro = pIdPuntoDiRistoro;
    }
}