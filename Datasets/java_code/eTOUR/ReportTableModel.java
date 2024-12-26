package unisa.gps.etour.gui.operatoreagenzia;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

import unisa.gps.etour.bean.BeanBeneCulturale;
import unisa.gps.etour.bean.BeanPuntoDiRistoro;
import unisa.gps.etour.bean.util.Punto3D;

public class ReportTableModel extends AbstractTableModel {
    
    private static final long serialVersionUID = 1L;
    private static final String[] headers = {"Name", "Description", "Address", "City", "Province"};
    private static final Class[] columnClasses = {String.class, String.class, String.class, String.class, String.class};
    private Vector<Object[]> data;

    public ReportTableModel(BeanBeneCulturale[] bc, BeanPuntoDiRistoro[] pr) {
        data = new Vector<>();
        for (int i = 0; i < pr.length; i++) {
            Object[] newRow = new Object[5];
            newRow[0] = pr[i].getName();
            newRow[1] = pr[i].getDescrizione();
            newRow[2] = pr[i].getVar();
            newRow[3] = pr[i].getCitta();
            newRow[4] = pr[i].getProvincia();
            data.add(newRow);
        }
        for (int i = 0; i < bc.length; i++) {
            Object[] newRow = new Object[5];
            newRow[0] = bc[i].getName();
            newRow[1] = bc[i].getDescrizione();
            newRow[2] = bc[i].getVar();
            newRow[3] = bc[i].getCitta();
            newRow[4] = bc[i].getProvincia();
            data.add(newRow);
        }
    }

    public int getColumnCount() {
        return headers.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return headers[col];
    }

    public Object getValueAt(int row, int col) {
        return data.get(row)[col];
    }

    public Class<?> getColumnClass(int col) {
        return columnClasses[col];
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setValueAt(Object value, int row, int col) {
        if (row >= getRowCount()) {
            Object[] newRow = new Object[headers.length];
            newRow[col] = value;
            data.add(newRow);
        } else {
            data.get(row)[col] = value;
        }
    }

    public void setValueAt(Object[] value, int row) throws IllegalArgumentException {
        if (value.length != headers.length) {
            System.out.println(value.length);
            System.out.println(headers.length);
            throw new IllegalArgumentException();
        }
        if (row >= getRowCount()) {
            data.add(value);
        } else {
            data.remove(row);
            data.add(row, value);
        }
    }
}