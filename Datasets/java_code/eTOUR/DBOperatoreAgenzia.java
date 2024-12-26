package unisa.gps.etour.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import unisa.gps.etour.bean.BeanOperatoreAgenzia;

public class DBOperatoreAgenzia implements IDBOperatoreAgenzia {

    @Override
    public BeanOperatoreAgenzia ottieniOperatoreAgenzia(String pUsername) throws SQLException {
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
            String query = "SELECT * FROM operatoreagenzia WHERE Username = '" + pUsername + "'";
            result = stat.executeQuery(query);
            BeanOperatoreAgenzia oa = null;
            if (result.next()) {
                // Build the bean when the query returns a value
                oa = new BeanOperatoreAgenzia();
                oa.setId(result.getInt("Id"));
                oa.setUsername(result.getString("Username"));
                oa.setNome(result.getString("Name"));
                oa.setCognome(result.getString("Surname"));
                oa.setPassword(result.getString("Password"));
            }
            return oa;
        } finally {
            // Finally block that contains the instructions to close the connections
            // Hyenas run in any case
            // This closes the result set only if the query was made
            if (result != null) {
                result.close();
            }
            // This closes the statement if opened
            if (stat != null) {
                stat.close();
            }
            // It returns the connection to the pool if opened
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }

    @Override
    public boolean modificaPassword(BeanOperatoreAgenzia poa) throws SQLException {
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
            String query = "UPDATE operatoreagenzia SET Password = '" + poa.getPassword() + "' WHERE Id = " + poa.getId();
            // You run the query
            int i = stat.executeUpdate(query);
            return (i == 1);
        } finally {
            // Finally block that contains the instructions to close the connections
            // Hyenas run in any case
            // This closes the statement if opened
            if (stat != null) {
                stat.close();
            }
            // It returns the connection to the pool if opened
            if (conn != null) {
                DBConnessionePool.rilasciaConnessione(conn);
            }
        }
    }
}