package unisa.gps.etour.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import unisa.gps.etour.bean.BeanPreferenzaDiRicerca;

/**
 * Class that implements the interface PreferenzeDiRicerca
 *
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public class DBPreferenzeDiRicerca implements IDBPreferenzeDiRicerca {

    // Empty constructor
    public DBPreferenzeDiRicerca() {
    }

    public boolean cancellaPreferenzaDiRicerca(int pIdPreferenza) throws SQLException {
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
            String query = "DELETE FROM preferenzediricerca WHERE Id =" + pIdPreferenza;
            // You run the query Cancellation
            int i = stat.executeUpdate(query);
            // This returns the backup
            return (i == 1);
        } finally {
            // Always runs and takes care of closing the Statement and the connection
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    public boolean cancellaPreferenzaDiRicercaBC(int pIdBeneCulturale, int pIdPreferenzaDiRicerca) throws SQLException {
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
            String query = "DELETE FROM associazionebc WHERE IdPreferenzeDiRicerca =" + pIdPreferenzaDiRicerca
                    + " AND IdBeneCulturale =" + pIdBeneCulturale;
            // You run the query Cancellation
            int i = stat.executeUpdate(query);
            // This returns the backup
            return (i == 1);
        } finally {
            // Always runs and takes care of closing the Statement and the connection
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    public boolean cancellaPreferenzaDiRicercaPR(int pIdPuntoDiRistoro, int pIdPreferenza) throws SQLException {
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
            String query = "DELETE FROM associazionepr WHERE IdPreferenzeDiRicerca =" + pIdPreferenza
                    + " AND IdPuntoDiRistoro =" + pIdPuntoDiRistoro;
            // You run the query Cancellation
            int i = stat.executeUpdate(query);
            // This returns the backup
            return (i == 1);
        } finally {
            // Always runs and takes care of closing the Statement and the connection
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    public boolean cancellaPreferenzaDiRicercaTurista(int pIdTurista, int pIdPreferenza) throws SQLException {
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
            String query = "DELETE FROM rating WHERE IdTurista =" + pIdTurista + " AND IdPreferenzeDiRicerca ="
                    + pIdPreferenza;
            // You run the query Cancellation
            int i = stat.executeUpdate(query);
            // This returns the backup
            return (i == 1);
        } finally {
            // Always runs and takes care of closing the Statement and the connection
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    public boolean inserisciPreferenzaDiRicercaDelBC(int pIdBeneCulturale, int pIdPreferenzaDiRicerca) throws SQLException {
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
            String query = "INSERT INTO associazionebc (IdPreferenzeDiRicerca, IdBeneCulturale) VALUES ("
                    + pIdPreferenzaDiRicerca + ", " + pIdBeneCulturale + ")";
            // You run the insert query
            int i = stat.executeUpdate(query);
            // This returns the backup
            return (i == 1);
        } finally {
            // Always runs and takes care of closing the Statement and the connection
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    public boolean inserisciPreferenzaDiRicerca(BeanPreferenzaDiRicerca pPreferenza) throws SQLException {
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
            String query = "INSERT INTO preferenzediricerca (Id, Name) VALUES (" + pPreferenza.getId() + ", '"
                    + pPreferenza.getNome() + "')";
            // You run the insert query
            int i = stat.executeUpdate(query);
            // This returns the backup
            return (i == 1);
        } finally {
            // Always runs and takes care of closing the Statement and the connection
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    public boolean inserisciPreferenzaDiRicercaDelPR(int pIdPuntoDiRistoro, int pIdPreferenzaDiRicerca) throws SQLException {
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
            String query = "INSERT INTO associazionepr (IdPreferenzeDiRicerca, IdPuntoDiRistoro) VALUES ("
                    + pIdPreferenzaDiRicerca + ", " + pIdPuntoDiRistoro + ")";
            // You run the insert query
            int i = stat.executeUpdate(query);
            // This returns the backup
            return (i == 1);
        } finally {
            // Always runs and takes care of closing the Statement and the connection
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    public boolean inserisciPreferenzaDiRicercaDelTurista(int pIdTurista, int pIdPreferenzaDiRicerca) throws SQLException {
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
            String query = "INSERT INTO rating (IdTurista, IdPreferenzeDiRicerca) VALUES (" + pIdTurista + ", "
                    + pIdPreferenzaDiRicerca + ")";
            // You run the insert query
            int i = stat.executeUpdate(query);
            // This returns the backup
            return (i == 1);
        } finally {
            // Always runs and takes care of closing the Statement and the connection
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    public ArrayList<BeanPreferenzaDiRicerca> ottieniPreferenzeDiRicercaDelBC(int pIdBeneCulturale) throws SQLException {
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
            // Query to extract the list of search preferences
            String query = "SELECT * FROM associazionebc, preferenzediricerca WHERE IdBeneCulturale ="
                    + pIdBeneCulturale
                    + " AND IdPreferenzeDiRicerca = preferenzediricerca.Id";
            // The query is executed
            result = stat.executeQuery(query);
            // List that will contain the BeanPreferenzaDiRicerca
            ArrayList<BeanPreferenzaDiRicerca> list = new ArrayList<BeanPreferenzaDiRicerca>();
            // We extract the results from the result set and moves in the list
            while (result.next()) {
                // Add to the list BeanPreferenzaDiRicerca
                list.add(new BeanPreferenzaDiRicerca(result.getInt("Id"), result.getString("Name")));
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

    public ArrayList<BeanPreferenzaDiRicerca> ottieniPreferenzeDiRicercaDelPR(int pIdPuntoDiRistoro) throws SQLException {
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
            // Query to extract the list of search preferences
            String query = "SELECT * FROM associazionepr, preferenzediricerca WHERE IdPuntoDiRistoro ="
                    + pIdPuntoDiRistoro
                    + " AND IdPreferenzeDiRicerca = preferenzediricerca.Id";
            // The query is executed
            result = stat.executeQuery(query);
            // List that will contain the BeanPreferenzaDiRicerca
            ArrayList<BeanPreferenzaDiRicerca> list = new ArrayList<BeanPreferenzaDiRicerca>();
            // We extract the results from the result set and moves in the list
            while (result.next()) {
                // Add to the list BeanPreferenzaDiRicerca
                list.add(new BeanPreferenzaDiRicerca(result.getInt("Id"), result.getString("Name")));
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

    public ArrayList<BeanPreferenzaDiRicerca> ottieniPreferenzeDiRicercaDelTurista(int pIdTurista) throws SQLException {
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
            // Query to extract the list of search preferences
            String query = "SELECT * FROM rating, preferenzediricerca WHERE IdTurista ="
                    + pIdTurista
                    + " AND IdPreferenzeDiRicerca = preferenzediricerca.Id";
            // The query is executed
            result = stat.executeQuery(query);
            // List that will contain the BeanPreferenzaDiRicerca
            ArrayList<BeanPreferenzaDiRicerca> list = new ArrayList<BeanPreferenzaDiRicerca>();
            // We extract the results from the result set and moves in the list
            while (result.next()) {
                // Add to the list BeanPreferenzaDiRicerca
                list.add(new BeanPreferenzaDiRicerca(result.getInt("Id"), result.getString("Name")));
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

    public ArrayList<BeanPreferenzaDiRicerca> ottieniPreferenzeDiRicerca() throws SQLException {
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
            // Query to extract the list of search preferences
            String query = "SELECT * FROM preferenzediricerca";
            // The query is executed
            result = stat.executeQuery(query);
            // List that will contain the BeanPreferenzaDiRicerca
            ArrayList<BeanPreferenzaDiRicerca> list = new ArrayList<BeanPreferenzaDiRicerca>();
            // We extract the results from the result set and moves in the list
            while (result.next()) {
                // Add to the list BeanPreferenzaDiRicerca
                list.add(new BeanPreferenzaDiRicerca(result.getInt("Id"), result.getString("Name")));
            }
            // Return the list of search preferences in the DB
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