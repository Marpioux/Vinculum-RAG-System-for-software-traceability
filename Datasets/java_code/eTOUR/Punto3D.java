package unisa.gps.etour.util;

/**
 * Bean that contains the coordinates of a point on the surface of the earth and
 * that it realizes the calculation of the distance from the system. The values of
 * coordinates must be represented in radians and must fall in
 * target range: 0 to Greek-Pi / 4 for the latitude south of the equator
 * 0 to + Pi Greek / 4 for the latitude north of the equator from 0 to Pi-Greek /
 * 2 for the meridian of longitude west of Greenwich Greek from 0 to + Pi / 2
 * for the meridian of longitude east of Greenwich
 *
 * @author Mauro Miranda
 * @version 0.1 2007 eTour Project - Copyright by SE @ SA Lab DMI University
 * of Salerno
 */

public class Punto3D {
    // Radius of the earth
    final double EARTH_RADIUS = 6371.0;
    private double latitude, longitude, altitude;

    public Punto3D() {
        latitude = longitude = altitude = 0;
    }

    public Punto3D(double pLatitudine, double pLongitudine, double pAltitudine) {
        latitude = pLatitudine;
        longitude = pLongitudine;
        altitude = pAltitudine;
    }

    /**
     * Returns the latitude
     *
     * @return
     */
    public double getLatitudine() {
        return latitude;
    }

    /**
     * Sets the latitude
     *
     * @param pLatitudine
     */
    public void setLatitudine(double pLatitudine) {
        this.latitude = pLatitudine;
    }

    /**
     * Returns the longitude
     *
     * @return
     */
    public double getLongitudine() {
        return longitude;
    }

    /**
     * Sets the longitude
     *
     * @param pLongitudine
     */
    public void setLongitudine(double pLongitudine) {
        this.longitude = pLongitudine;
    }

    /**
     * Returns the altitude
     *
     * @return
     */
    public double getAltitudine() {
        return altitude;
    }

    /**
     * Sets the altitude
     *
     * @param pAltitudine
     */
    public void setAltitudine(double pAltitudine) {
        this.altitude = pAltitudine;
    }

    /**
     * Calculate the distance between the point and another point given as argument
     *
     * @param p
     * @return
     */
    public double distance(Punto3D p) {
        double differenzaLongitudine = this.longitude - p.longitude;
        double p1 = Math.pow(Math.cos(p.latitude) * Math.sin(differenzaLongitudine), 2);
        double p2 = Math.pow(Math.cos(latitude) * Math.sin(p.latitude) - Math.sin(latitude) * Math.cos(p.latitude) * Math.cos(differenzaLongitudine), 2);
        double p3 = Math.sin(latitude) * Math.sin(p.latitude) + Math.cos(latitude) * Math.cos(p.latitude) * Math.cos(differenzaLongitudine);
        return (Math.atan(Math.sqrt(p1 + p2) / p3) * EARTH_RADIUS);
    }

    /**
     * Method which creates a 3D point from coordinates measured in degrees. The
     * 3D point instead represents the coordinates in radians
     *
     * @param pLatitudine latitude in degrees
     * @param pLongitudine Longitude in degrees
     * @param pAltitudine
     * @return Punto3D with the coordinates in radians
     */
    public static Punto3D gradiRadianti(double pLatitudine, double pLongitudine, double pAltitudine) {
        Punto3D point = new Punto3D();
        point.setLatitudine(pLatitudine * Math.PI / 180);
        point.setLongitudine(pLongitudine * Math.PI / 180);
        point.setAltitudine(pAltitudine);
        return point;
    }
}