package unisa.gps.etour.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import unisa.gps.etour.bean.BeanVisitaPR;

/**
 * Class that implements the interface IDBVisitaPR
 *
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public class DBVisitaPR implements IDBVisitaPR {

    // Empty constructor
    public DBVisitaPR() {
    }

    public boolean inserisciVisitaPR(BeanVisitaPR pVisita) throws SQLException {
        // Variable for the connection
        Connection conn = null;
        // Variable for the query
        Statement stat = null;
        // Variable for the query results
        ResultSet result = null;
        try {
            // Create the date of visit
            java.sql.Date dataVisita = new Date(pVisita.getDataVisita().getTime());
            // Get the connection
            conn = DBConnessionePool.ottieniConnessione();
            // Create the Statement
            stat = conn.createStatement();
            // Query to get the average rating of a property
            String queryMediaVoti = "SELECT MediaVoti, NumeroVoti FROM puntodiristoro WHERE Id = "
                    + pVisita.getIdPuntoDiRistoro();
            result = stat.executeQuery(queryMediaVoti);
            // Variable for the average rating
            double average = 0;
            // Variable for the number of votes
            int numeroVoti = 0;
            if (result.next()) {
                average = result.getDouble("MediaVoti");
                numeroVoti = result.getInt("NumeroVoti");
                average = ((average * numeroVoti) + pVisita.getVoto()) / (++numeroVoti);
            }
            // Query for the insertion
            String query = "INSERT INTO visitapr (IdTurista, IdPuntoDiRistoro, DataVisita, Vote, Comment) VALUES ("
                    + pVisita.getIdTurista() + ", "
                    + pVisita.getIdPuntoDiRistoro() + ", '"
                    + dataVisita + "', "
                    + pVisita.getVoto() + ", '" + pVisita.getCommento() + "')";
            String query2 = "UPDATE puntodiristoro SET MediaVoti = " + average
                    + ", NumeroVoti = " + numeroVoti + " WHERE Id = "
                    + pVisita.getIdPuntoDiRistoro();
            // Execute the insert query
            stat.executeQuery("BEGIN");
            int i = stat.executeUpdate(query);
            i = i * stat.executeUpdate(query2);
            stat.executeQuery("COMMIT");
            // This returns the backup
            return (i == 1);
        } finally {
            // Always runs and takes care to close the Result, the Statement
            // And Connection
            if (result != null) {
                result.close();
            }
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    public boolean modificaVisitaPR(BeanVisitaPR pVisita) throws SQLException {
        // Variable for the connection
        Connection conn = null;
        // Variable for the query
        Statement stat = null;
        try {
            // Create the date of visit
            java.sql.Date dataVisita = new Date(pVisita.getDataVisita().getTime());
            // Get the connection
            conn = DBConnessionePool.ottieniConnessione();
            // Create the Statement
            stat = conn.createStatement();
            // Query for amendment
            String query = "UPDATE visitapr SET DataVisita = '"
                    + dataVisita + "', Comment = '" + pVisita.getCommento()
                    + "' WHERE IdPuntoDiRistoro = "
                    + pVisita.getIdPuntoDiRistoro() + " AND IdTurista = "
                    + pVisita.getIdTurista();
            // Execute the query for Change
            int i = stat.executeUpdate(query);
            // This returns the backup
            return (i == 1);
        } finally {
            // Always runs and takes care to close the Result, the Statement
            // And Connection
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    public ArrayList<BeanVisitaPR> ottieniListaVisitaPR(int pIdPuntoDiRistoro) throws SQLException {
        // Variable for the connection
        Connection conn = null;
        // Variable for the query
        Statement stat = null;
        // Variable for the query results
        ResultSet result = null;
        try {
            // Get the connection
            conn = DBConnessionePool.ottieniConnessione();
            // Create the Statement
            stat = conn.createStatement();
            // Query to extract the list of requests for a Refreshment
            String query = "SELECT * FROM visitapr WHERE IdPuntoDiRistoro = " + pIdPuntoDiRistoro;
            // The query is executed
            result = stat.executeQuery(query);
            ArrayList<BeanVisitaPR> list = new ArrayList<BeanVisitaPR>();
            // Extract the results from the result set and move to List
            while (result.next()) {
                java.util.Date dataVisita = new java.util.Date(result.getDate("DataVisita").getTime());
                list.add(new BeanVisitaPR(result.getInt("IdTurista"), result.getInt("IdPuntoDiRistoro"),
                        result.getString("Comment"), result.getInt("IdTurista"), dataVisita));
            }
            return list;
        } finally {
            // Always runs and takes care to close the Result, the Statement
            // And Connection
            if (result != null) {
                result.close();
            }
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    public ArrayList<BeanVisitaPR> ottieniListaVisitaPRTurista(int pIdTurista) throws SQLException {
        // Variable for the connection
        Connection conn = null;
        // Variable for the query
        Statement stat = null;
        // Variable for the query results
        ResultSet result = null;
        try {
            // Get the connection
            conn = DBConnessionePool.ottieniConnessione();
            // Create the Statement
            stat = conn.createStatement();
            // Query to extract the list of requests for a Eating place for tourists
            String query = "SELECT * FROM visitapr WHERE IdTurista = " + pIdTurista;
            // The query is executed
            result = stat.executeQuery(query);
            // List that will contain the BeanVisitaPR
            ArrayList<BeanVisitaPR> list = new ArrayList<BeanVisitaPR>();
            // Extract the results from the result set and move to List
            while (result.next()) {
                // Add to the list BeanVisitaPR
                java.util.Date dataVisita = new java.util.Date(result.getDate("DataVisita").getTime());
                list.add(new BeanVisitaPR(result.getInt("IdTurista"), result.getInt("IdPuntoDiRistoro"),
                        result.getString("Comment"), result.getInt("IdTurista"), dataVisita));
            }
            // Return the list
            return list;
        } finally {
            // Always runs and takes care to close the Result, the Statement
            // And Connection
            if (result != null) {
                result.close();
            }
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    public BeanVisitaPR ottieniVisitaPR(int pIdPuntoDiRistoro, int pIdTurista) throws SQLException {
        // Variable for the connection
        Connection conn = null;
        // Variable for the query
        Statement stat = null;
        // Variable for the query results
        ResultSet result = null;
        try {
            // Get the connection
            conn = DBConnessionePool.ottieniConnessione();
            // Create the Statement
            stat = conn.createStatement();
            // Query for the extraction of the visit made by a tourist to A given point of comfort
            String query = "SELECT * FROM visitapr WHERE IdPuntoDiRistoro = " + pIdPuntoDiRistoro + " AND IdTurista = " + pIdTurista;
            // The query is executed
            result = stat.executeQuery(query);
            // Get the bean's visit sought based on the ID of the tourist and Of refreshment
            BeanVisitaPR beanTemp = null;
            if (result.next()) {
                // Create the BeanVisitaPR
                java.util.Date dataVisita = new java.util.Date(result.getDate("DataVisita").getTime());
                beanTemp = new BeanVisitaPR(result.getInt("IdTurista"), result.getInt("IdPuntoDiRistoro"),
                        result.getString("Comment"), result.getInt("IdTurista"), dataVisita);
            }
            // Return the BeanTemp
            return beanTemp;
        } finally {
            // Always runs and takes care to close the Result, the Statement
            // And Connection
            if (result != null) {
                result.close();
            }
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }
}