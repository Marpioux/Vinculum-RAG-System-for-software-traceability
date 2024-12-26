package unisa.gps.etour.control.fuzzy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class ElencoCategorie implements Serializable {

    private static final long serialVersionUID = 1L;
    private Hashtable<String, Categoria> categories; // hash table that keeps categories
    private Hashtable<String, float[]> totTermini; // hash table that keeps terms of all categories
    private float maxDist; // contains the maximum distances

    /**
     * The constructor initializes the two hash tables that contain
     * Categories and terms of all categories
     */
    public ElencoCategorie() {
        categories = new Hashtable<>();
        totTermini = new Hashtable<>();
    }

    /**
     * Access method attribute maxDist
     *
     * @return the maximum distance of all the terms in all categories
     */
    public float getMaxDist() {
        return maxDist;
    }

    /**
     * Access method to the table of categories
     *
     * @return categories
     */
    public Hashtable<String, Categoria> getAllCategorie() {
        return categories;
    }

    /**
     * Method of accessing the table of total time
     *
     * @return totTermini
     */
    public Hashtable<String, float[]> getTotTermini() {
        return totTermini;
    }

    /**
     * Method to access a category in the table of categories
     *
     * @param pNomeCategoria
     * @return object categories representing the category name PNomeCategoria
     */
    public Categoria getCategoria(String pNomeCategoria) {
        if (esisteCategoria(pNomeCategoria)) { // if there is the appropriate category
            return categories.get(pNomeCategoria); // returns the category associated with pNomeCategoria
        }
        return null; // otherwise null
    }

    /**
     * Method of accessing the values of a particular term in this
     * Category table
     *
     * @param pTermine
     * @return Returns the values associated with the term pTermine
     */
    public float[] getTermine(String pTermine) {
        if (esisteTermine(pTermine)) { // if the term is present in table
            return totTermini.get(pTermine); // return the value associated
        }
        return null; // null otherwise
    }

    /**
     * Method which allows you to add a category to the table of categories
     *
     * @param pNomeCategoria category name to add
     * @param pCategoria associated object category
     * @return true if the operation was successfully carried out, false otherwise
     */
    public boolean addCategoria(String pNomeCategoria, Categoria pCategoria) {
        if (!esisteCategoria(pNomeCategoria)) { // if the category exists
            return false; // returns false
        }
        categories.put(pNomeCategoria, pCategoria); // otherwise load the category in the table
        return true; // returns true
    }

    /**
     * Edit a category of the category table
     *
     * @param pNomeCategoria category name to edit
     * @param pCategoria associated object to this category
     * @return true if the operation was successfully carried out, false otherwise
     */
    public boolean setCategoria(String pNomeCategoria, Categoria pCategoria) {
        if (!esisteCategoria(pNomeCategoria)) { // if the category does not exist
            return false; // returns false
        }
        categories.put(pNomeCategoria, pCategoria); // update the table of categories
        return true; // returns true
    }

    /**
     * Method which allows you to set the value of a term in the tables of total time
     *
     * @param pTermine term name
     * @param pVal value to associate with the term
     */
    public void setTermine(String pTermine, float[] pVal) {
        totTermini.put(pTermine, pVal);
    }

    /**
     * Method which allows to set the value of the maximum distance of terms from one category
     *
     * @param pMaxDist
     */
    public void setMaxDist(float pMaxDist) {
        maxDist = pMaxDist;
    }

    /**
     * Method which allows to derive a collection of names of all categories in the categories table
     *
     * @return string iterable Collection
     */
    public Iterable<String> getCategorie() {
        List<String> toReturn = new ArrayList<>(); // create a new list
        for (Enumeration<String> val = categories.keys(); val.hasMoreElements();) { // iterates through categories
            toReturn.add(val.nextElement()); // adds to the list the name of a category
        }
        return toReturn;
    }

    /**
     * Method aids to verify the existence of a category in the table of categories
     *
     * @param PKEY name of the category
     * @return true if the category exists, false otherwise
     */
    public boolean esisteCategoria(String PKEY) {
        try {
            categories.get(PKEY); // try to extract the category name PKEY from the table of categories
            return true; // if the transaction does not raise exceptions, category exists and returns true
        } catch (NullPointerException e) {
            return false; // false otherwise
        }
    }

    /**
     * Method aids to verify the existence of a term in the table of total time
     *
     * @param PKEY term
     * @return true if the term exists, false otherwise
     */
    public boolean esisteTermine(String PKEY) {
        try {
            if (totTermini.get(PKEY) != null) {
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }
}