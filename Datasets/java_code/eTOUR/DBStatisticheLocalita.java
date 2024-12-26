package unisa.gps.etour.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Class that implements the local statistical
 * 
 * @author Joseph Martone
 * @version 0.1 © 2007 eTour Project - Copyright by DMI SE @ SA Lab --
 * University of Salerno
 */
public class DBStatisticheLocalita implements IDBStatisticheLocalita {

    // Empty constructor
    public DBStatisticheLocalita() {
    }

    public ArrayList<String> ottieniListaLocalita() throws SQLException {
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
            // Query for the extraction of location between the PR and BC
            String query = "(SELECT DISTINCT Location FROM beneculturale) UNION (SELECT DISTINCT Location FROM puntodiristoro)";
            // The query is executed
            result = stat.executeQuery(query);
            // List that includes the results obtained
            ArrayList<String> list = new ArrayList<>();
            while (result.next()) {
                // Add to the list the locations obtained
                list.add(result.getString("Location"));
            }
            return list;
        } finally {
            // Always runs and takes care to close the Result, the Statement, and Connection
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

    public double ottieniMedieVotiLocalita(String plocalita) throws SQLException {
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
            // Query to extract the average of the votes of catering outlets according to a Passo
            String query = "SELECT avg(MediaVoti) AS MediaVoti FROM puntodiristoro WHERE Location = '" + plocalita + "'";
            // The query is executed
            result = stat.executeQuery(query);
            // We extract the results from the result set
            double point = 0.0;
            if (result.next()) {
                point = result.getDouble("MediaVoti");
            }
            // Query to extract the average of the votes of cultural according to a Passo
            query = "SELECT avg(MediaVoti) AS MediaVoti FROM beneculturale WHERE Location = '" + plocalita + "'";
            // The query is executed
            result = stat.executeQuery(query);
            // We extract the results from the result set
            double good = 0.0;
            if (result.next()) {
                good = result.getDouble("MediaVoti");
            }
            // It returns the average of the refreshment and heritage
            return (good + point) / 2;
        } finally {
            // Always runs and takes care to close the Result, the Statement, and Connection
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