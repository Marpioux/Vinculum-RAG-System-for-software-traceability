package unisa.gps.etour.repository;

import java.sql.SQLException;
import java.util.ArrayList;

import unisa.gps.etour.bean.BeanBeneCulturale;
import unisa.gps.etour.bean.BeanTag;
import unisa.gps.etour.util.Punto3D;

/**
 * Interface for the management of cultural heritage database
 * 
 * @author Mauro Miranda
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University Of Salerno
 */
public interface IDBBeneCulturale {

    /**
     * Add a cultural heritage, given input
     * 
     * @param pBene Cultural Heritage for inclusion in database
     * @throws SQLException
     */
    boolean inserisciBeneCulturale(BeanBeneCulturale pBene) throws SQLException;

    /**
     * Modify the information in the cultural
     * 
     * @param pBene contains the information to modify the database
     * @throws SQLException
     * @return True if there was a modified false otherwise
     */
    boolean modificaBeneCulturale(BeanBeneCulturale pBene) throws SQLException;

    /**
     * Delete a cultural object from the database
     * 
     * @param pIdBene cultural property to delete
     * @throws SQLException
     * @return True if it was deleted false otherwise
     */
    boolean cancellaBeneCulturale(int pIdBene) throws SQLException;

    /**
     * Returns the cultural object with id as input
     * 
     * @param pid cultural property to be extracted from the database
     * @throws SQLException
     * @return cultural property obtained from the database
     */
    BeanBeneCulturale ottieniBeneCulturale(int pid) throws SQLException;

    /**
     * Research. Returns the list of cultural property in their name or
     * Description given string as input, filtered according to tags and
     * Maximum distance. The returned list contains the number of goods given as input.
     * To browse the real list, which may contain more of
     * Ten elements, you use the parameter numPagina.
     * 
     * @param pKeyword string that contains the keyword to search the
     * Name or description of the cultural
     * @param pTags list of tags used to filter the search. the
     * Maximum number of tags to be included should not exceed five
     * Units. If you exceed this number the other tags
     * Excess will be ignored.
     * @param pNumPagina The page number you want to view. 0 for
     * 1 page (the first 10 results), 1 for 2 page (results
     * from 11 to 20) etc ...
     * @param pPosizione position of the person who carried out the research
     * @param pDistanzaMassima Maximum distance from the user to search for good
     * @param pNumeroElementiPerPagina number of items to return per page
     * @throws SQLException
     * @return list contained ten cultural
     */
    ArrayList<BeanBeneCulturale> search(String pKeyword,
            ArrayList<BeanTag> pTags, int pNumPagina, int pNumeroElementiPerPagina, 
            Punto3D pPosizione, double pDistanzaMassima) throws SQLException;

    /**
     * Advanced Search. Returns the list of cultural goods which have in
     * Name or description given string as input, sorted according to
     * Preferences of tourists and filtered according to the tag and the maximum distance. The
     * Returned list contains the number of goods given as input. To scroll
     * The actual list, which may contain multiple items, you
     * Use parameter numPagina.
     * 
     * @param pIdTurista tourists who carried out the research
     * @param pKeyword string that contains the keyword to search the
     * Name or description of the cultural
     * @param pTags list of tags used to filter the search. the
     * Maximum number of tags to be included should not exceed five
     * Units. If you exceed this number the other tags
     * Excess will be ignored.
     * @param pNumPagina The page number you want to view. 0 for
     * 1 page (the first 10 results), 1 for 2 page (results
     * from 11 to 20) etc ...
     * @param pPosizione position of the person who carried out the research
     * @param pDistanzaMassima Maximum distance from the user to search for good
     * @param pNumeroElementiPerPagina number of items to return per page
     * @throws SQLException
     * @return list contained ten cultural
     */
    ArrayList<BeanBeneCulturale> ricercaAvanzata(int pIdTurista,
            String pKeyword, ArrayList<BeanTag> pTags, int pNumPagina, 
            int pNumeroElementiPerPagina, Punto3D pPosizione, double pDistanzaMassima) throws SQLException;

    /**
     * Method to get the number of elements to search.
     * 
     * @param pKeyword string that contains the keyword to search the
     * Name or description of the cultural
     * @param pTags list of tags used to filter the search. the
     * Maximum number of tags to be included should not exceed five
     * Units. If you exceed this number the other tags
     * Excess will be ignored.
     * @param pPosizione position of the person who carried out the research
     * @param pDistanzaMassima Maximum distance from the user to search for good
     * @throws SQLException
     * @return number of pages.
     */
    int ottieniNumeroElementiRicerca(String pKeyword,
            ArrayList<BeanTag> pTags, Punto3D pPosizione, double pDistanzaMassima) throws SQLException;

    /**
     * Method to get the number of elements to search.
     * 
     * @param pIdTurista tourists who carried out the research
     * @param pKeyword string that contains the keyword to search the
     * Name or description of the cultural
     * @param pTags list of tags used to filter the search. the
     * Maximum number of tags to be included should not exceed five
     * Units. If you exceed this number the other tags
     * Excess will be ignored.
     * @param pPosizione position of the person who carried out the research
     * @param pDistanzaMassima Maximum distance from the user to search for good
     * @throws SQLException
     * @return number of pages.
     */
    int ottieniNumeroElementiRicercaAvanzata(int pIdTurista,
            String pKeyword, ArrayList<BeanTag> pTags, Punto3D pPosizione,
            double pDistanzaMassima) throws SQLException;

    /**
     * Returns a list of all cultural
     * 
     * @throws SQLException
     * @return List of all cultural
     */
    ArrayList<BeanBeneCulturale> ottieniListaBC() throws SQLException;
}