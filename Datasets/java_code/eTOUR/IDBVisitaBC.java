package unisa.gps.etour.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import unisa.gps.etour.bean.BeanVisitaBC;

/**
 * Interface for handling feedback on a given cultural asset
 * 
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public interface IDBVisitaBC {

    /**
     * Inserts a visit
     * 
     * @param PVIS Visit to insert
     * @throws SQLException
     */
    boolean inserisciVisitaBC(BeanVisitaBC PVIS) throws SQLException;

    /**
     * Modify a visit
     * 
     * @param PVIS Visit to edit
     * @throws SQLException
     * @return True if changed, otherwise false
     */
    boolean modificaVisitaBC(BeanVisitaBC PVIS) throws SQLException;

    /**
     * Extract the list of visits to a cultural asset
     * 
     * @param pIdBeneCulturale ID of the cultural asset
     * @throws SQLException
     * @return list of visits of the cultural asset
     */
    ArrayList<BeanVisitaBC> ottieniListaVisitaBC(int pIdBeneCulturale) throws SQLException;

    /**
     * Extract the list of cultural assets visited by a tourist
     * 
     * @param pIdTurista ID of the tourist
     * @throws SQLException
     * @return ArrayList of all feedback issued by a tourist for a specified cultural asset
     */
    ArrayList<BeanVisitaBC> ottieniListaVisitaBCTurista(int pIdTurista) throws SQLException;

    /**
     * Extract a visit by a tourist to a cultural asset
     * 
     * @param pIdBeneCulturale ID of the cultural asset
     * @param pIdTurista ID of the tourist
     * @throws SQLException
     * @return visit
     */
    BeanVisitaBC ottieniVisitaBC(int pIdBeneCulturale, int pIdTurista) throws SQLException;
}