package unisa.gps.etour.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import unisa.gps.etour.bean.BeanPuntoDiRistoro;
import unisa.gps.etour.bean.BeanTag;
import unisa.gps.etour.util.Punto3D;

/**
 * Class that implements the interface of Refreshment
 *
 * @author Joseph Martone
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * Of Salerno
 */
public class DBPuntoDiRistoro implements IDBPuntoDiRistoro {
    // Empty constructor
    public DBPuntoDiRistoro() {
    }

    public boolean cancellaPuntoDiRistoro(int pIdPuntoDiRistoro) throws SQLException {
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
            String query = "DELETE FROM puntodiristoro WHERE Id =" + pIdPuntoDiRistoro;
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

    public boolean inserisciPuntoDiRistoro(BeanPuntoDiRistoro pPuntoDiRistoro) throws SQLException {
        // Create the organization of the Opening and Closing
        Time orarioApertura = new Time(pPuntoDiRistoro.getOrarioApertura().getTime());
        Time orarioChiusura = new Time(pPuntoDiRistoro.getOrarioChiusura().getTime());
        // Variables for database connection
        Connection conn = null;
        // Variable for the query
        Statement stat = null;
        try {
            // Get the connection
            conn = DBConnessionePool.ottieniConnessione();
            // Create the Statement
            stat = conn.createStatement();
            // Query for the insertion of a refreshment
            String query = "INSERT INTO puntodiristoro (Name, Description, Telephone, Latitude, Longitude, Altitude, OrarioApertura, OrarioChiusura, GiornoChiusura, Town, City, Street, Postcode, Province, MediaVoti, NumeroVoti, PartitaIva) VALUES ('" 
                + pPuntoDiRistoro.getNome() + "','" 
                + pPuntoDiRistoro.getDescrizione() + "','" 
                + pPuntoDiRistoro.getTelefono() + "'," 
                + pPuntoDiRistoro.getPosizione().getLatitudine() + "," 
                + pPuntoDiRistoro.getPosizione().getLongitudine() + "," 
                + pPuntoDiRistoro.getPosizione().getAltitudine() + ",'" 
                + orarioApertura.toString() + "','" 
                + orarioChiusura.toString() + "','" 
                + pPuntoDiRistoro.getGiornoChiusura() + "','" 
                + pPuntoDiRistoro.getLocalita() + "','" 
                + pPuntoDiRistoro.getCitta() + "','" 
                + pPuntoDiRistoro.getVia() + "','" 
                + pPuntoDiRistoro.getCap() + "','" 
                + pPuntoDiRistoro.getProvincia() + "'," 
                + pPuntoDiRistoro.getMediaVoti() + "," 
                + pPuntoDiRistoro.getNumeroVoti() + ",'" 
                + pPuntoDiRistoro.getPartitaIva() + "');";
            // You run the insert query
            int i = stat.executeUpdate(query);
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

    public boolean modificaPuntoDiRistoro(BeanPuntoDiRistoro pPuntoDiRistoro) throws SQLException {
        // Create the organization of the Opening and Closing
        Time orarioApertura = new Time(pPuntoDiRistoro.getOrarioApertura().getTime());
        Time orarioChiusura = new Time(pPuntoDiRistoro.getOrarioChiusura().getTime());
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
            String query = "UPDATE puntodiristoro SET Name = '" 
                + pPuntoDiRistoro.getNome() + "', Description ='" 
                + pPuntoDiRistoro.getDescrizione() + "', Phone ='" 
                + pPuntoDiRistoro.getTelefono() + "', Latitude =" 
                + pPuntoDiRistoro.getPosizione().getLatitudine() + ", Longitude =" 
                + pPuntoDiRistoro.getPosizione().getLongitudine() + ", Height =" 
                + pPuntoDiRistoro.getPosizione().getAltitudine() + ", OrarioApertura = '" 
                + orarioApertura.toString() + "', OrarioChiusura ='" 
                + orarioChiusura.toString() + "', GiornoChiusura ='" 
                + pPuntoDiRistoro.getGiornoChiusura() + "', Location ='" 
                + pPuntoDiRistoro.getLocalita() + "', City ='" 
                + pPuntoDiRistoro.getCitta() + "', Via ='" 
                + pPuntoDiRistoro.getVia() + "', postcode ='" 
                + pPuntoDiRistoro.getCap() + "', State ='" 
                + pPuntoDiRistoro.getProvincia() + "', MediaVoti =" 
                + pPuntoDiRistoro.getMediaVoti() + ", NumeroVoti =" 
                + pPuntoDiRistoro.getNumeroVoti() + ", party = '" 
                + pPuntoDiRistoro.getPartitaIva() + "' WHERE Id =" 
                + pPuntoDiRistoro.getId() + ";";
            // You run the query for Change
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

    public int ottieniNumeroElementiRicerca(String pKeyword, ArrayList<BeanTag> pTags, Punto3D pPosizione, double pDistanzaMassima) throws SQLException {
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
            // Query to get the number of pages
            String query = "";
            if (pTags.size() == 0) {
                // Query without the control tag
                query = "SELECT count(number) FROM (SELECT count(puntodiristoro.Id) AS number FROM puntodiristoro WHERE (puntodiristoro.Nome LIKE '%" 
                    + pKeyword + "%' OR puntodiristoro.Descrizione LIKE '%" 
                    + pKeyword + "%') AND distance(puntodiristoro.Latitudine, puntodiristoro.Longitudine, " 
                    + pPosizione.getLatitudine() + ", " + pPosizione.getLongitudine() + ") < " 
                    + pDistanzaMassima + " GROUP BY puntodiristoro.Id ORDER BY count(puntodiristoro.Id) DESC) AS table";
            } else {
                // Query with tags
                query = "SELECT count(number) FROM (SELECT count(puntodiristoro.Id) AS number FROM puntodiristoro JOIN (appartenenzapr JOIN tag ON IdTag = Id) ON puntodiristoro.Id = appartenenzapr.IdPuntoDiRistoro WHERE (puntodiristoro.Nome LIKE '%" 
                    + pKeyword + "%' OR puntodiristoro.Descrizione LIKE '%" 
                    + pKeyword + "%') AND (tag.Nome = '" + pTags.get(0).getName() + "'";
                if (pTags.size() >= 2) {
                    query += " OR tag.Nome ='" + pTags.get(1).getName() + "'";
                }
                if (pTags.size() >= 3) {
                    query += " OR tag.Nome ='" + pTags.get(2).getName() + "'";
                }
                if (pTags.size() >= 4) {
                    query += " OR tag.Nome ='" + pTags.get(3).getName() + "'";
                }
                if (pTags.size() >= 5) {
                    query += " OR tag.Nome ='" + pTags.get(4).getName() + "'";
                }
                query += ") AND distance(puntodiristoro.Latitudine, puntodiristoro.Longitudine, " 
                    + pPosizione.getLatitudine() + ", " + pPosizione.getLongitudine() + ") < " 
                    + pDistanzaMassima + " GROUP BY puntodiristoro.Id ORDER BY count(puntodiristoro.Id) DESC) AS table";
            }
            // You run the query
            result = stat.executeQuery(query);
            // It returns the value of count() that contains the number of tuples
            int i = 0;
            if (result.next()) {
                i = result.getInt(1);
            }
            return i;
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

    public int ottieniNumeroElementiRicercaAvanzata(int pIdTurista, String pKeyword, ArrayList<BeanTag> pTags, Punto3D pPosizione, double pDistanzaMassima) throws SQLException {
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
            // Query to get the number of pages of advanced
            String query = "";
            if (pTags.size() == 0) {
                // Query without the control tag
                query = "SELECT count(number) FROM (SELECT count(puntodiristoro.Id) AS number FROM (puntodiristoro LEFT JOIN (SELECT IdPuntoDiRistoro FROM associazionepr, (SELECT IdPreferenzeDiRicerca FROM rating WHERE IdTurista =" 
                    + pIdTurista + ") AS pref WHERE associazionepr.IdPreferenzeDiRicerca = pref.IdPreferenzeDiRicerca) AS preferenze ON puntodiristoro.Id = preferenze.IdPuntoDiRistoro) WHERE (puntodiristoro.Nome LIKE '%" 
                    + pKeyword + "%' OR puntodiristoro.Descrizione LIKE '%" 
                    + pKeyword + "%') AND distance(puntodiristoro.Latitudine, puntodiristoro.Longitudine, " 
                    + pPosizione.getLatitudine() + ", " + pPosizione.getLongitudine() + ") < " 
                    + pDistanzaMassima + " GROUP BY puntodiristoro.Id ORDER BY count(puntodiristoro.Id) DESC) AS table";
            } else {
                // Query with tags
                query = "SELECT count(number) FROM (SELECT count(puntodiristoro.Id) AS number FROM (puntodiristoro LEFT JOIN (SELECT IdPuntoDiRistoro FROM associazionepr, (SELECT IdPreferenzeDiRicerca FROM rating WHERE IdTurista =" 
                    + pIdTurista + ") AS pref WHERE associazionepr.IdPreferenzeDiRicerca = pref.IdPreferenzeDiRicerca) AS preferenze ON puntodiristoro.Id = preferenze.IdPuntoDiRistoro JOIN (appartenenzapr JOIN tag ON IdTag = Id) ON puntodiristoro.Id = appartenenzapr.IdPuntoDiRistoro WHERE (puntodiristoro.Nome LIKE '%" 
                    + pKeyword + "%' OR puntodiristoro.Descrizione LIKE '%" 
                    + pKeyword + "%') AND (tag.Nome = '" + pTags.get(0).getName() + "'";
                if (pTags.size() >= 2) {
                    query += " OR tag.Nome ='" + pTags.get(1).getName() + "'";
                }
                if (pTags.size() >= 3) {
                    query += " OR tag.Nome ='" + pTags.get(2).getName() + "'";
                }
                if (pTags.size() >= 4) {
                    query += " OR tag.Nome ='" + pTags.get(3).getName() + "'";
                }
                if (pTags.size() >= 5) {
                    query += " OR tag.Nome ='" + pTags.get(4).getName() + "'";
                }
                query += ") AND distance(puntodiristoro.Latitudine, puntodiristoro.Longitudine, " 
                    + pPosizione.getLatitudine() + ", " + pPosizione.getLongitudine() + ") < " 
                    + pDistanzaMassima + " GROUP BY puntodiristoro.Id ORDER BY count(puntodiristoro.Id) DESC) AS table";
            }
            // You run the query
            result = stat.executeQuery(query);
            // It returns the value of count() that contains the number of tuples
            int i = 0;
            if (result.next()) {
                i = result.getInt(1);
            }
            return i;
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

    public BeanPuntoDiRistoro ottieniPuntoDiRistoro(int pid) throws SQLException {
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
            // Query
            String query = "SELECT * FROM puntodiristoro WHERE Id =" + pid;
            // You run the query
            result = stat.executeQuery(query);
            if (result.next()) {
                // We create the objects to be returned
                Punto3D point = new Punto3D(result.getDouble("Latitude"), result.getDouble("Longitude"), result.getDouble("Elevation"));
                Date orarioApertura = new Date(result.getTime("OrarioApertura"));
                Date orarioChiusura = new Date(result.getTime("OrarioChiusura"));
                // This generates the resting spot of bean
                BeanPuntoDiRistoro puntoTemp = new BeanPuntoDiRistoro(result.getInt("Id"), result.getInt("NumeroVoti"), result.getDouble("MediaVoti"), result.getString("Name"), result.getString("Description"), result.getString("Phone"), result.getString("Location"), result.getString("City"), result.getString("Way"), result.getString("Cap"), result.getString("Province"), result.getString("PartitaIva"), point, orarioApertura, orarioChiusura, result.getString("GiornoChiusura"));
                // It returns the refreshment
                return puntoTemp;
            } else {
                return null;
            }
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

    public ArrayList<BeanPuntoDiRistoro> search(String pKeyword, ArrayList<BeanTag> pTags, int pNumeroPagina, int pNumeroElementiPerPagina, Punto3D pPosizione, double pDistanzaMassima) throws SQLException {
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
            // Query for search
            String query = "";
            if (pTags.size() == 0) {
                // Query without the control tag
                query = "SELECT * FROM puntodiristoro WHERE (puntodiristoro.Nome LIKE '%" 
                    + pKeyword + "%' OR puntodiristoro.Descrizione LIKE '%" 
                    + pKeyword + "%') AND distance(puntodiristoro.Latitudine, puntodiristoro.Longitudine, " 
                    + pPosizione.getLatitudine() + ", " + pPosizione.getLongitudine() + ") < " 
                    + pDistanzaMassima + " GROUP BY puntodiristoro.Id ORDER BY count(puntodiristoro.Id) DESC LIMIT " 
                    + (pNumeroPagina * pNumeroElementiPerPagina) + ", " + pNumeroElementiPerPagina;
            } else {
                // Query with tags
                query = "SELECT * FROM puntodiristoro JOIN (appartenenzapr JOIN tag ON IdTag = Id) ON puntodiristoro.Id = appartenenzapr.IdPuntoDiRistoro WHERE (puntodiristoro.Nome LIKE '%" 
                    + pKeyword + "%' OR puntodiristoro.Descrizione LIKE '%" 
                    + pKeyword + "%') AND (tag.Nome = '" + pTags.get(0).getName() + "'";
                if (pTags.size() >= 2) {
                    query += " OR tag.Nome ='" + pTags.get(1).getName() + "'";
                }
                if (pTags.size() >= 3) {
                    query += " OR tag.Nome ='" + pTags.get(2).getName() + "'";
                }
                if (pTags.size() >= 4) {
                    query += " OR tag.Nome ='" + pTags.get(3).getName() + "'";
                }
                if (pTags.size() >= 5) {
                    query += " OR tag.Nome ='" + pTags.get(4).getName() + "'";
                }
                query += ") AND distance(puntodiristoro.Latitudine, puntodiristoro.Longitudine, " 
                    + pPosizione.getLatitudine() + ", " + pPosizione.getLongitudine() + ") < " 
                    + pDistanzaMassima + " GROUP BY puntodiristoro.Id ORDER BY count(puntodiristoro.Id) DESC LIMIT " 
                    + (pNumeroPagina * pNumeroElementiPerPagina) + ", " + pNumeroElementiPerPagina;
            }
            // You run the query
            result = stat.executeQuery(query);
            // List that will contain the BeanPuntoDiRistoro
            ArrayList<BeanPuntoDiRistoro> list = new ArrayList<BeanPuntoDiRistoro>();
            while (result.next()) {
                // We create the objects to be returned
                Punto3D point = new Punto3D(result.getDouble("Latitude"), result.getDouble("Longitude"), result.getDouble("Elevation"));
                Date orarioApertura = new Date(result.getTime("OrarioApertura"));
                Date orarioChiusura = new Date(result.getTime("OrarioChiusura"));
                // Build the refreshment
                BeanPuntoDiRistoro puntoTemp = new BeanPuntoDiRistoro(result.getInt("Id"), result.getInt("NumeroVoti"), result.getDouble("MediaVoti"), result.getString("Name"), result.getString("Description"), result.getString("Phone"), result.getString("Location"), result.getString("City"), result.getString("Way"), result.getString("Cap"), result.getString("Province"), result.getString("PartitaIva"), point, orarioApertura, orarioChiusura, result.getString("GiornoChiusura"));
                // Insert the bean in the list
                list.add(puntoTemp);
            }
            // It returns the list
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

    public ArrayList<BeanPuntoDiRistoro> ricercaAvanzata(int pIdTurista, String pKeyword, ArrayList<BeanTag> pTags, int pNumeroPagina, int pNumeroElementiPerPagina, Punto3D pPosizione, double pDistanzaMassima) throws SQLException {
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
            // Query for advanced search
            String query = "";
            if (pTags.size() == 0) {
                // Query without tags
                query = "SELECT * FROM (puntodiristoro LEFT JOIN (SELECT IdPuntoDiRistoro FROM associazionepr, (SELECT IdPreferenzeDiRicerca FROM rating WHERE IdTurista =" 
                    + pIdTurista + ") AS pref WHERE associazionepr.IdPreferenzeDiRicerca = pref.IdPreferenzeDiRicerca) AS preferenze ON puntodiristoro.Id = preferenze.IdPuntoDiRistoro) WHERE (puntodiristoro.Nome LIKE '%" 
                    + pKeyword + "%' OR puntodiristoro.Descrizione LIKE '%" 
                    + pKeyword + "%') AND distance(puntodiristoro.Latitudine, puntodiristoro.Longitudine, " 
                    + pPosizione.getLatitudine() + ", " + pPosizione.getLongitudine() + ") < " 
                    + pDistanzaMassima + " GROUP BY puntodiristoro.Id ORDER BY count(puntodiristoro.Id) DESC LIMIT " 
                    + (pNumeroPagina * pNumeroElementiPerPagina) + ", " + pNumeroElementiPerPagina;
            } else {
                // Query with tags
                query = "SELECT * FROM (puntodiristoro LEFT JOIN (SELECT IdPuntoDiRistoro FROM associazionepr, (SELECT IdPreferenzeDiRicerca FROM rating WHERE IdTurista =" 
                    + pIdTurista + ") AS pref WHERE associazionepr.IdPreferenzeDiRicerca = pref.IdPreferenzeDiRicerca) AS preferenze ON puntodiristoro.Id = preferenze.IdPuntoDiRistoro JOIN (appartenenzapr JOIN tag ON IdTag = Id) ON puntodiristoro.Id = appartenenzapr.IdPuntoDiRistoro WHERE (puntodiristoro.Nome LIKE '%" 
                    + pKeyword + "%' OR puntodiristoro.Descrizione LIKE '%" 
                    + pKeyword + "%') AND (tag.Nome = '" + pTags.get(0).getName() + "'";
                if (pTags.size() >= 2) {
                    query += " OR tag.Nome ='" + pTags.get(1).getName() + "'";
                }
                if (pTags.size() >= 3) {
                    query += " OR tag.Nome ='" + pTags.get(2).getName() + "'";
                }
                if (pTags.size() >= 4) {
                    query += " OR tag.Nome ='" + pTags.get(3).getName() + "'";
                }
                if (pTags.size() >= 5) {
                    query += " OR tag.Nome ='" + pTags.get(4).getName() + "'";
                }
                query += ") AND distance(puntodiristoro.Latitudine, puntodiristoro.Longitudine, " 
                    + pPosizione.getLatitudine() + ", " + pPosizione.getLongitudine() + ") < " 
                    + pDistanzaMassima + " GROUP BY puntodiristoro.Id ORDER BY count(puntodiristoro.Id) DESC LIMIT " 
                    + (pNumeroPagina * pNumeroElementiPerPagina) + ", " + pNumeroElementiPerPagina;
            }
            // You run the query
            result = stat.executeQuery(query);
            ArrayList<BeanPuntoDiRistoro> list = new ArrayList<BeanPuntoDiRistoro>();
            while (result.next()) {
                // We create the objects to be returned
                Punto3D point = new Punto3D(result.getDouble("Latitude"), result.getDouble("Longitude"), result.getDouble("Elevation"));
                Date orarioApertura = new Date(result.getTime("OrarioApertura"));
                Date orarioChiusura = new Date(result.getTime("OrarioChiusura"));
                // This creates the cultural
                BeanPuntoDiRistoro beneTemp = new BeanPuntoDiRistoro(result.getInt("Id"), result.getInt("NumeroVoti"), result.getDouble("MediaVoti"), result.getString("Name"), result.getString("Description"), result.getString("Phone"), result.getString("Location"), result.getString("City"), result.getString("Way"), result.getString("Cap"), result.getString("Province"), result.getString("PartitaIva"), point, orarioApertura, orarioChiusura, result.getString("GiornoChiusura"));
                // Insert the bean in the list
                list.add(beneTemp);
            }
            // It returns the list
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

    public ArrayList<BeanPuntoDiRistoro> ottieniListaPR() throws SQLException {
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
            // Query for a list of all the refreshment
            String query = "SELECT * FROM puntodiristoro";
            // You run the query
            result = stat.executeQuery(query);
            // List that will contain the BeanPuntoDiRistoro
            ArrayList<BeanPuntoDiRistoro> list = new ArrayList<BeanPuntoDiRistoro>();
            while (result.next()) {
                // We create the objects to be returned
                Punto3D point = new Punto3D(result.getDouble("Latitude"), result.getDouble("Longitude"), result.getDouble("Elevation"));
                Date orarioApertura = new Date(result.getTime("OrarioApertura"));
                Date orarioChiusura = new Date(result.getTime("OrarioChiusura"));
                // Build the refreshment
                BeanPuntoDiRistoro puntoTemp = new BeanPuntoDiRistoro(result.getInt("Id"), result.getInt("NumeroVoti"), result.getDouble("MediaVoti"), result.getString("Name"), result.getString("Description"), result.getString("Phone"), result.getString("Location"), result.getString("City"), result.getString("Way"), result.getString("Cap"), result.getString("Province"), result.getString("PartitaIva"), point, orarioApertura, orarioChiusura, result.getString("GiornoChiusura"));
                // Insert the bean in the list
                list.add(puntoTemp);
            }
            // It returns the list
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