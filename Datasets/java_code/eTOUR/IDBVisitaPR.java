package unisa.gps.etour.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import unisa.gps.etour.bean.BeanVisitaPR;

/**
 * Interface for managing feedback related to a specific point
 * Refreshments
 * 
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * of Salerno
 */
public interface IDBVisitaPR {

    /**
     * Add a visit to a refreshment
     *
     * @param pVis visit to add
     * @throws SQLException
     */
    boolean inserisciVisitaPR(BeanVisitaPR pVis) throws SQLException;

    /**
     * Modify a visit
     *
     * @param pVis Visit to edit
     * @return True if changed, otherwise false
     * @throws SQLException
     */
    boolean modificaVisitaPR(BeanVisitaPR pVis) throws SQLException;

    /**
     * Extract the list of visits to a refreshment
     *
     * @param pIdPuntoDiRistoro point identification Refreshments
     * @return List of visits
     * @throws SQLException
     */
    ArrayList<BeanVisitaPR> ottieniListaVisitaPR(int pIdPuntoDiRistoro) throws SQLException;

    /**
     * Extract a visit by a tourist at a refreshment
     *
     * @param pIdPuntoDiRistoro point identification Refreshments
     * @param pIdTurista tourists
     * @return visit
     * @throws SQLException
     */
    BeanVisitaPR ottieniVisitaPR(int pIdPuntoDiRistoro, int pIdTurista) throws SQLException;

    /**
     * Extract the list of visits of a tourist
     *
     * @param pIdTurista tourists
     * @return List of visits
     * @throws SQLException
     */
    ArrayList<BeanVisitaPR> ottieniListaVisitaPRTurista(int pIdTurista) throws SQLException;
}