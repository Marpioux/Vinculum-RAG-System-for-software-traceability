package unisa.gps.etour.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import unisa.gps.etour.bean.BeanPiatto;

/**
 * Class that implements the interface plate
 *
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public class DBPiatto implements IDBPiatto {
    // Empty constructor
    public DBPiatto() {
    }

    public boolean cancellaPiatto(int pIdPiatto) throws SQLException {
        // Variables for database connection
        Connection conn = null;
        // Variable for the query
        Statement stat = null;
        try {
            // Get the connection
            conn = DBConnessionePool.ottieniConnessione();
            // Create the Statement
            stat = conn.createStatement();
            // Query cancellation
            String query = "DELETE FROM courses WHERE ID =" + pIdPiatto;
            // You run the query Cancellation
            int i = stat.executeUpdate(query);
            // This returns the backup
            return (i == 1);
        } finally {
            // Always runs and takes care of closing the Statement and the Connection
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    public boolean inserisciPiatto(BeanPiatto pPiatto) throws SQLException {
        // Variables for database connection
        Connection conn = null;
        // Variable for the query
        Statement stat = null;
        try {
            // Get the connection
            conn = DBConnessionePool.ottieniConnessione();
            // Create the Statement
            stat = conn.createStatement();
            // Query for the insertion
            String query = "INSERT INTO courses (Name, Price, IdMenu) VALUES ('"
                    + pPiatto.getNome() + "', " + pPiatto.getPrezzo() + ", "
                    + pPiatto.getIdMenu() + ")";
            // You run the insert query
            int i = stat.executeUpdate(query);
            // This returns the backup
            return (i == 1);
        } finally {
            // Always runs and takes care of closing the Statement and the Connection
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    public boolean modificaPiatto(BeanPiatto pPiatto) throws SQLException {
        // Variables for database connection
        Connection conn = null;
        // Variable for the query
        Statement stat = null;
        try {
            // Get the connection
            conn = DBConnessionePool.ottieniConnessione();
            // Create the Statement
            stat = conn.createStatement();
            // Query for amendment
            String query = "UPDATE courses SET Name = '" + pPiatto.getNome() + "', Price = "
                    + pPiatto.getPrezzo() + ", IdMenu = " + pPiatto.getIdMenu() 
                    + " WHERE Id =" + pPiatto.getId();
            // You run the query for Change
            int i = stat.executeUpdate(query);
            // This returns the backup
            return (i == 1);
        } finally {
            // Always runs and takes care of closing the Statement and the Connection
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    public ArrayList<BeanPiatto> ottieniPiatto(int pIdMenu) throws SQLException {
        // Variables for database connection
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
            // Query to extract the list of dishes given the id of the Menu
            String query = "SELECT * FROM courses WHERE IdMenu =" + pIdMenu;
            // The query is executed
            result = stat.executeQuery(query);
            // List that contains all the plates obtained
            ArrayList<BeanPiatto> list = new ArrayList<BeanPiatto>();
            // We extract the results from the result set and moves in List
            // To be returned
            while (result.next()) {
                // Add the list BeanPiatto
                list.add(new BeanPiatto(result.getInt("Price"), result.getString("Name"), result.getInt("IdMenu"), result.getInt("Id")));
            }
            return list;
        } finally {
            // Always runs and takes care to close the Result, the Statement and Connection
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