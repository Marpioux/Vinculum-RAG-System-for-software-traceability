package unisa.gps.etour.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import unisa.gps.etour.bean.BeanMenu;

/**
 * Class that implements the interface Menu
 *
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public class DBMenu implements IDBMenu {
    // Empty constructor
    public DBMenu() {
    }

    public boolean cancellaMenu(int pIdMenu) throws SQLException {
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
            String query = "DELETE FROM menu WHERE id =" + pIdMenu;
            // You run the query Cancellation
            int i = stat.executeUpdate(query);
            // This returns the backup
            return (i == 1);
        } finally {
            // Is always done and takes care of closing the Statement and the Connection
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    public boolean inserisciMenu(BeanMenu pMenu) throws SQLException {
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
            String query = "INSERT INTO menu (Day, IdPuntoDiRistoro) VALUES ('"
                    + pMenu.getGiorno() + "',"
                    + pMenu.getIdPuntoDiRistoro() + ")";
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

    public boolean modificaMenu(BeanMenu pMenu) throws SQLException {
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
            String query = "UPDATE menu SET Date = '"
                    + pMenu.getGiorno() + "' WHERE Id =" + pMenu.getId();
            // You run the query for Change
            int i = stat.executeUpdate(query);
            // This returns the backup
            return (i == 1);
        } finally {
            // Is always done and takes care of closing the Statement and the Connection
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    public ArrayList<BeanMenu> ottieniMenu(int pIdPuntoDiRistoro) throws SQLException {
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
            // Query to extract the list of Menu
            String query = "SELECT * FROM menu WHERE IdPuntoDiRistoro =" + pIdPuntoDiRistoro;
            // The query is executed
            result = stat.executeQuery(query);
            // List that will contain all BeanMenu obtained
            ArrayList<BeanMenu> list = new ArrayList<BeanMenu>();
            // We extract the results from the result set and moves in List
            // To be returned
            while (result.next()) {
                // Fill the list
                list.add(new BeanMenu(result.getInt("Id"), result.getString("Day"), result.getInt("IdPuntoDiRistoro")));
            }
            // Return the list
            return list;
        } finally {
            // Is always done and takes care to close the Result, the Statement and Connection
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

    public BeanMenu ottieniMenuDelGiorno(int pIdPuntoDiRistoro, String pGiorno) throws SQLException {
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
            // Query for the extraction of Daily Menu
            String query = "SELECT * FROM menu WHERE IdPuntoDiRistoro ="
                    + pIdPuntoDiRistoro + " AND Day = '" + pGiorno + "'";
            // The query is executed
            result = stat.executeQuery(query);
            // Get the bean of the daily menu based on the ID of the point of Dining and a day
            BeanMenu beanTemp = null;
            if (result.next()) {
                // Create the proceeds Bean
                beanTemp = new BeanMenu(result.getInt("Id"), result.getString("Day"), result.getInt("IdPuntoDiRistoro"));
            }
            // Return the Bean obtained
            return beanTemp;
        } finally {
            // Is always done and takes care to close the Result, the Statement and Connection
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