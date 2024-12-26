package unisa.gps.etour.control.fuzzy;

import java.io.Serializable;
import java.util.Hashtable;

public class Category implements Serializable {

    private static final long serialVersionUID = -8652232946927756089L;
    private String name; // name of the category
    private Hashtable<String, float[]> terms; // list of terms and their frequencies and distances

    /**
     * Constructor: Get the category name as a parameter to create
     */
    public Category(String pNom) {
        name = pNom;
        terms = new Hashtable<>();
    }

    /**
     * Returns the output Hashtable containing the terms
     * with the respective values of frequency, relevance and distance
     */
    public Hashtable<String, float[]> getTermini() {
        return terms;
    }

    /**
     * Returns the name of the output category
     */
    public String getName() {
        return name;
    }

    /**
     * Get the string as a parameter representing the term
     * of which you want to pick the values of frequency, range and bearing
     */
    public float[] getVal(String pTermine) throws NullPointerException {
        if (esisteTermine(pTermine)) {
            return terms.get(pTermine);
        }
        return null;
    }

    /**
     * Adds a term to the category dictionary
     */
    public void addTermine(String pTermine) {
        terms.put(pTermine, new float[3]);
    }

    /**
     * Adds a term to the category dictionary
     * with the values of frequency, distance and relevance
     */
    public boolean addTermine(String pTermine, float[] pVal) {
        if ((pVal == null) || (pTermine.equals(""))) {
            return false;
        }
        terms.put(pTermine, pVal);
        return true;
    }

    /**
     * Sets the values for the term pTermine
     */
    public boolean setValTermine(String pTermine, float[] pVal) throws NullPointerException {
        if (esisteTermine(pTermine)) {
            terms.put(pTermine, pVal);
            return true;
        }
        return false;
    }

    public void setTermini(Hashtable<String, float[]> pTermini) {
        terms = pTermini;
    }

    /**
     * Returns true if the term is present in
     * the dictionary of Category, false otherwise
     */
    public boolean esisteTermine(String pTermine) {
        try {
            float[] ret = terms.get(pTermine);
            if (ret != null) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }
}