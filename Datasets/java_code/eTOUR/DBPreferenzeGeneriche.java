package unisa.gps.etour.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import unisa.gps.etour.bean.BeanPreferenzeGeneriche;

/**
 * Implementing the IDBPreferenzeGeneriche
 *
 * @author Mauro Miranda
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public class DBPreferenzeGeneriche implements IDBPreferenzeGeneriche {

    // Constructor without parameters
    public DBPreferenzeGeneriche() {
    }

    public boolean cancellaPreferenzaGenerica(int pIdPreferenza) throws SQLException {
        // Connect to database
        Connection conn = null;
        // Statement for running queries
        Statement stat = null;
        // Try block which performs the query and the database connection
        try {
            // You get the database connection from the pool
            conn = DBConnessionePool.ottieniConnessione();
            // Create the statement
            stat = conn.createStatement();
            // Query
            String query = "DELETE FROM preferenzegeneriche WHERE Id = " + pIdPreferenza;
            // You run the query
            int i = stat.executeUpdate(query);
            return (i == 1);
        } finally {
            // Finally block that contains the instructions to close the connections
            // Hyenas run in any case
            // This closes the if statement and 'opened
            if (stat != null) {
                stat.close();
            }
            // It returns the connection to the pool if and 'opened
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    public boolean inserisciPreferenzaGenerica(BeanPreferenzeGeneriche pPreferenza) throws SQLException {
        // Connect to database
        Connection conn = null;
        // Statement for running queries
        Statement stat = null;
        // Try block which performs the query and the database connection
        try {
            // You get the database connection from the pool
            conn = DBConnessionePool.ottieniConnessione();
            // Create the statement
            stat = conn.createStatement();
            // Query
            String query = "INSERT INTO preferenzegeneriche (IdTurista, Font, Tema, DimensioneFont) VALUES ("
                    + pPreferenza.getIdTurista() + ", '"
                    + pPreferenza.getFont() + "', '"
                    + pPreferenza.getTema() + "', "
                    + pPreferenza.getDimensioneFont() + ")";
            // You run the query
            int i = stat.executeUpdate(query);
            return (i == 1);
        } finally {
            // Finally block that contains the instructions to close the connections
            // Hyenas run in any case
            // This closes the if statement and 'opened
            if (stat != null) {
                stat.close();
            }
            // It returns the connection to the pool if and 'opened
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    public boolean modificaPreferenzaGenerica(BeanPreferenzeGeneriche pPreferenza) throws SQLException {
        // Connect to database
        Connection conn = null;
        // Statement for running queries
        Statement stat = null;
        // Try block which performs the query and the database connection
        try {
            // You get the database connection from the pool
            conn = DBConnessionePool.ottieniConnessione();
            // Create the statement
            stat = conn.createStatement();
            // Query
            String query = "UPDATE preferenzegeneriche SET IdTurista = " + pPreferenza.getIdTurista() 
                    + ", Font = '" + pPreferenza.getFont() 
                    + "', Tema = '" + pPreferenza.getTema() 
                    + "', DimensioneFont = " + pPreferenza.getDimensioneFont() 
                    + " WHERE Id = " + pPreferenza.getId();
            // You run the query
            int i = stat.executeUpdate(query);
            return (i == 1);
        } finally {
            // Finally block that contains the instructions to close the connections
            // Hyenas run in any case
            // This closes the if statement and 'opened
            if (stat != null) {
                stat.close();
            }
            // It returns the connection to the pool if and 'opened
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    public BeanPreferenzeGeneriche ottieniPreferenzaGenerica(int pIdTurista) throws SQLException {
        // Connect to database
        Connection conn = null;
        // Statement for running queries
        Statement stat = null;
        // Result set where the output of the query is inserted
        ResultSet result = null;
        // Try block which performs the query and the database connection
        try {
            // You get the database connection from the pool
            conn = DBConnessionePool.ottieniConnessione();
            // Create the statement
            stat = conn.createStatement();
            // Query
            String query = "SELECT * FROM preferenzegeneriche WHERE IdTurista = " + pIdTurista;
            // Run the query
            result = stat.executeQuery(query);
            BeanPreferenzeGeneriche pref = null;
            // Check that the query returns at least one result
            if (result.next()) {
                pref = new BeanPreferenzeGeneriche();
                pref.setId(result.getInt("Id"));
                pref.setIdTurista(result.getInt("IdTurista"));
                pref.setDimensioneFont(result.getInt("DimensioneFont"));
                pref.setFont(result.getString("Font"));
                pref.setTema(result.getString("Tema"));
            }
            return pref;
        } finally {
            // Finally block that contains the instructions to close the connections
            // Hyenas run in any case
            // This closes the result set only if and 'the query was made
            if (result != null) {
                result.close();
            }
            // This closes the if statement and 'opened
            if (stat != null) {
                stat.close();
            }
            // It returns the connection to the pool if and 'opened
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }
}