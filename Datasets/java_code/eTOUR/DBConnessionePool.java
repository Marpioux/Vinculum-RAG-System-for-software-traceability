package unisa.gps.etour.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that creates the connection to the database using JDBC and
 * allows you to query both read and edit the contents of
 * the database. It is implemented to provide a pool of connections to
 * provide a connection to each thread.
 *
 * @author Mauro Miranda
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * of Salerno
 */
public class DBConnessionePool {
    private final static String driver = "com.mysql.jdbc.Driver";
    private final static String urlConnessione = "jdbc:mysql://localhost/eTour?user=mauro&password=mauro";
    private static List<Connection> connessioniLibere;

    /* Private constructor that initiates the connection to the database */

    /*
     * Static initialization block is used to load the driver
     * into memory
     */
    static {
        connessioniLibere = new ArrayList<>();
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to get the connection to the server.
     *
     * @return Returns the database connection
     * @throws SQLException
     */
    public static synchronized Connection ottieniConnessione() throws SQLException {
        Connection connection;

        if (!connessioniLibere.isEmpty()) {
            // Extract a connection from the free db connection queue
            connection = connessioniLibere.get(0);
            DBConnessionePool.connessioniLibere.remove(0);

            try {
                // If the connection is not valid, a new connection will be
                // created
                if (connection.isClosed()) {
                    connection = DBConnessionePool.ottieniConnessione();
                }
            } catch (SQLException e) {
                connection = DBConnessionePool.ottieniConnessione();
            }
        } else {
            // The free db connection queue is empty, so a new connection will
            // be created
            connection = DBConnessionePool.creaDBConnessione();
        }

        return connection;
    }

    public static void rilasciaConnessione(Connection pReleasedConnection) {
        // Add the connection to the free db connection queue
        DBConnessionePool.connessioniLibere.add(pReleasedConnection);
    }

    private static Connection creaDBConnessione() throws SQLException {
        Connection nuovaConnessione = null;
        // Create a new db connection using the db properties
        nuovaConnessione = DriverManager.getConnection(urlConnessione);
        nuovaConnessione.setAutoCommit(true);
        return nuovaConnessione;
    }
}