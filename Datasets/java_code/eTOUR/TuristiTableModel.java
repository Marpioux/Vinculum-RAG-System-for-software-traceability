package unisa.gps.etour.gui.operatoreagenzia.tables;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import unisa.gps.etour.bean.BeanTurista;

public class TuristiTableModel extends AbstractTableModel {
    private static final String[] headers = {"Status", "Name", "Surname", "E-Mail", "Phone", 
        "Date of Birth", "City of Birth", "Address", "City", "CPC", "Test", "Save"};
    private static final Class[] columnClasses = {Boolean.class, String.class, String.class, 
        String.class, String.class, Date.class, String.class, String.class, String.class, 
        String.class, String.class, Date.class};
    private Vector<Object[]> data;

    public TuristiTableModel() {
        data = new Vector<>();
    }

    public TuristiTableModel(ArrayList<BeanTurista> pTuristi) {
        this();
        if (pTuristi == null) {
            return;
        }
        for (int i = 0; i < pTuristi.size(); i++) {
            insertTurista(pTuristi.get(i));
        }
    }

    public int getColumnCount() {
        return headers.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int pColumn) throws IllegalArgumentException {
        if (pColumn >= getColumnCount() || pColumn < 0) {
            throw new IllegalArgumentException("The column index is not provided in the model.");
        }
        return headers[pColumn];
    }

    public Object getValueAt(int pRow, int pColumn) throws IllegalArgumentException {
        if (pRow >= getRowCount() || pRow < 0) {
            throw new IllegalArgumentException("The row index is not provided in the model.");
        }
        if (pColumn >= getColumnCount() || pColumn < 0) {
            throw new IllegalArgumentException("The column index is not provided in the model.");
        }
        return data.get(pRow)[pColumn];
    }

    public Class<?> getColumnClass(int pColumn) throws IllegalArgumentException {
        if (pColumn >= getColumnCount() || pColumn < 0) {
            throw new IllegalArgumentException("The column index is not provided in the model.");
        }
        return columnClasses[pColumn];
    }

    public boolean isCellEditable(int pRow, int pColumn) throws IllegalArgumentException {
        return false;
    }

    public void setValueAt(Object value, int row, int col) {
        // Method intentionally left blank
    }

    public int attivaTurista(int pRow) throws IllegalArgumentException {
        data.get(pRow)[0] = isAttivato(pRow) ? false : true;
        fireTableDataChanged();
        return getID(pRow);
    }

    public boolean isAttivato(int pRow) throws IllegalArgumentException {
        return (Boolean) getValueAt(pRow, 0);
    }

    public void insertTurista(BeanTurista pTurista) throws IllegalArgumentException {
        if (pTurista == null) {
            throw new IllegalArgumentException("The bean provided cannot be null.");
        }
        Object[] aRow = new Object[13];
        aRow[0] = pTurista.isAttiva();
        aRow[1] = pTurista.getNome();
        aRow[2] = pTurista.getCognome();
        aRow[3] = pTurista.getEmail();
        aRow[4] = pTurista.getTelefono();
        aRow[5] = pTurista.getDataNascita();
        aRow[6] = pTurista.getCittaNascita();
        aRow[7] = pTurista.getVia();
        aRow[8] = pTurista.getCittaResidenza();
        aRow[9] = pTurista.getCap();
        aRow[10] = pTurista.getProvincia();
        aRow[11] = pTurista.getDataRegistrazione();
        aRow[12] = pTurista.getId();
        data.add(aRow);
    }

    public void updateTurista(BeanTurista pTurista) throws IllegalArgumentException {
        if (pTurista == null) {
            throw new IllegalArgumentException("The bean provided cannot be null.");
        }
        int i;
        for (i = 0; i < data.size(); i++) {
            int id = (Integer) data.get(i)[12];
            if (id == pTurista.getId()) {
                break;
            }
        }
        if (i < data.size()) {
            Object[] aRow = new Object[13];
            aRow[0] = pTurista.isAttiva();
            aRow[1] = pTurista.getNome();
            aRow[2] = pTurista.getCognome();
            aRow[3] = pTurista.getEmail();
            aRow[4] = pTurista.getTelefono();
            aRow[5] = pTurista.getDataNascita();
            aRow[6] = pTurista.getCittaNascita();
            aRow[7] = pTurista.getVia();
            aRow[8] = pTurista.getCittaResidenza();
            aRow[9] = pTurista.getCap();
            aRow[10] = pTurista.getProvincia();
            aRow[11] = pTurista.getDataRegistrazione();
            aRow[12] = pTurista.getId();
            data.set(i, aRow);
            fireTableDataChanged();
        }
    }

    public int getID(int pRow) throws IllegalArgumentException {
        if (pRow >= getRowCount() || pRow < 0) {
            throw new IllegalArgumentException("The row index is not provided in the model.");
        }
        return (Integer) data.get(pRow)[12];
    }

    public int removeTurista(int pRow) throws IllegalArgumentException {
        int id = getID(pRow);
        data.remove(pRow);
        return id;
    }
}