package unisa.gps.etour.repository;

import java.sql.SQLException;
import java.util.ArrayList;

import unisa.gps.etour.bean.BeanPuntoDiRistoro;
import unisa.gps.etour.bean.BeanTag;
import unisa.gps.etour.util.Punto3D;

/**
 * Interface for management of eateries in the database
 * 
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public interface IDBPuntoDiRistoro {

    /**
     * Add a refreshment
     *
     * @param pPuntoDiRistoro Refreshment to add
     * @throws SQLException
     */
    boolean inserisciPuntoDiRistoro(BeanPuntoDiRistoro pPuntoDiRistoro) throws SQLException;

    /**
     * Modify a refreshment
     *
     * @param pPuntoDiRistoro Refreshment to edit
     * @throws SQLException
     * @return True if changed, otherwise false
     */
    boolean modificaPuntoDiRistoro(BeanPuntoDiRistoro pPuntoDiRistoro) throws SQLException;

    /**
     * Delete a refreshment
     *
     * @param pIdPuntoDiRistoro Refreshment to eliminate
     * @throws SQLException
     * @return True if deleted, false otherwise
     */
    boolean cancellaPuntoDiRistoro(int pIdPuntoDiRistoro) throws SQLException;

    /**
     * Returns data from a point of comfort with the ID given as argument
     *
     * @param pid point identification Refreshments
     * @throws SQLException
     * @return Refreshment
     */
    BeanPuntoDiRistoro ottieniPuntoDiRistoro(int pid) throws SQLException;

    /**
     * Advanced Search. Returns the list of eateries that have in
     * Name or description given string as input, sorted according to
     * Preferences of tourists, the tags and filtered according to the distance
     * Max. The list returned contains only the number of catering outlets input data.
     * To scroll the real list, which may contain multiple items, you
     * Use parameter numPagina.
     *
     * @param pIdTurista tourists who carried out the research
     * @param pKeyword string that contains the keyword to search the
     * Name or description of refreshment
     * @param pTags list of tags used to filter the search. The
     * Maximum number of tags to be included should not exceed five
     * Units'. If you exceed this number the other tags
     * Excess will be ignored.
     * @param pNumeroPagina The page number you want to view. 0
     * The 1 page (the first 10 results), 1 for 2 page (results from 11 to 20) etc.
     * @param pPosizione position of the person who carried out the research
     * @param pNumeroElementiPerPagina Number of elements to return
     * @param pDistanzaMassima Maximum distance from the user to refreshment
     * @throws SQLException
     * @return list containing ten points Refreshments
     */
    ArrayList<BeanPuntoDiRistoro> ricercaAvanzata(int pIdTurista, String pKeyword, ArrayList<BeanTag> pTags, int pNumeroPagina, int pNumeroElementiPerPagina, Punto3D pPosizione, double pDistanzaMassima) throws SQLException;

    /**
     * Method to get the number of elements to search.
     *
     * @see ricercaAvanzata()
     * @param pIdTurista tourists who carried out the research
     * @param pKeyword string that contains the keyword to search the
     * Name or description of refreshment
     * @param pTags list of tags used to filter the search. The
     * Maximum number of tags to be included should not exceed five
     * Units'. If you exceed this number the other tags
     * Excess will be ignored.
     * @param pPosizione position of the person who carried out the research
     * @param pDistanzaMassima Maximum distance from the user to refreshment
     * to seek
     * @throws SQLException
     * @return number of pages.
     */
    int ottieniNumeroElementiRicercaAvanzata(int pIdTurista, String pKeyword, ArrayList<BeanTag> pTags, Punto3D pPosizione, double pDistanzaMassima) throws SQLException;

    /**
     * Research. Returns the list of eateries that have the name or
     * Description given string as input, filtered and tags
     * According to the maximum distance. The returned list contains the number of
     * Points Refreshments input data. To scroll the real list, which
     * May contain more items, you use the parameter numPagina.
     *
     * @param pKeyword string that contains the keyword to search the
     * Name or description of refreshment
     * @param pTags list of tags used to filter the search. The
     * Maximum number of tags to be included should not exceed five
     * Units'. If you exceed this number the other tags
     * Excess will be ignored.
     * @param pNumeroPagina The page number you want to view. 0
     * The 1 page (the first 10 results), 1 for 2 page (results from 11 to 20) etc.
     * @param pPosizione position of the person who carried out the research
     * @param pDistanzaMassima Maximum distance from the user to refreshment
     * @param pNumeroElementiPerPagina Number of elements to return
     * @throws SQLException
     * @return list containing ten points Refreshments
     */
    ArrayList<BeanPuntoDiRistoro> ricerca(String pKeyword, ArrayList<BeanTag> pTags, int pNumeroPagina, int pNumeroElementiPerPagina, Punto3D pPosizione, double pDistanzaMassima) throws SQLException;

    /**
     * Method to get you the elements for an advanced search.
     *
     * @see ricerca()
     * @param pKeyword string that contains the keyword to search the
     * Name or description of refreshment
     * @param pTags list of tags used to filter the search. The
     * Maximum number of tags to be included should not exceed five
     * Units'. If you exceed this number the other tags
     * Excess will be ignored.
     * @param pPosizione position of the person who carried out the research
     * @param pDistanzaMassima Maximum distance from the user to refreshment
     * to seek
     * @throws SQLException
     * @return number of pages.
     */
    int ottieniNumeroElementiRicerca(String pKeyword, ArrayList<BeanTag> pTags, Punto3D pPosizione, double pDistanzaMassima) throws SQLException;

    /**
     * Returns a list of all the refreshment
     *
     * @throws SQLException
     * @return list of all the refreshment
     */
    ArrayList<BeanPuntoDiRistoro> ottieniListaPR() throws SQLException;
}