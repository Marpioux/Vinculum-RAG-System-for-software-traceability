/*
 * SitoTableModel.java
 *
 * 1.0
 *
 * 21/05/2007
 *
 * © 2007 eTour Project - Copyright by SE @ SA Lab - DMI - University of Salerno
 */

package Handheld;

import Bean.BeanBeneCulturale;
import Bean.BeanPuntoDiRistoro;
import javax.swing.table.AbstractTableModel;
import Util.Punto3D;

/**
 * <b>SitoTableModel</b>
 * Serves as a data container <p> of cultural or refreshment areas that need
 * to be displayed in a JTable. </p>
 * @see javax.swing.table.AbstractTableModel
 * @see javax.swing.JTable
 * @see unisa.gps.etour.bean.BeanBeneCulturale
 * @see unisa.gps.etour.bean.BeanPuntoDiRistoro
 * @version 1.0
 * @author Raphael Landi
 */

public class SitoTableModel extends AbstractTableModel {
    private String[] columnNames = {"Name", "City", "Distance"};
    private Object[][] cells;
    private Punto3D posizioneSito;
    private Punto3D myLocation;

    public SitoTableModel(BeanPuntoDiRistoro[] pr, Punto3D myLocation) {
        super();
        this.myLocation = myLocation;
        cells = new Object[pr.length][3]; 
        for (int i = 0; i < pr.length; i++) {
            cells[i][0] = pr[i].getName();
            cells[i][1] = pr[i].getCitta();
        }
    }

    public SitoTableModel(BeanBeneCulturale[] bc, Punto3D myLocation) {
        super();
        this.myLocation = myLocation;
        cells = new Object[bc.length][3]; 
        for (int i = 0; i < bc.length; i++) {
            cells[i][0] = bc[i].getName();
            cells[i][1] = bc[i].getCitta();
        }
    }

    public int getRowCount() {
        return cells.length;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int r, int c) {
        if (c < columnNames.length - 1) {
            return cells[r][c];
        } else {
            double value = myLocation.distanza(posizioneSito);
            return Double.valueOf(value);
        }
    }

    public String getColumnName(int c) {
        return columnNames[c];
    }
}