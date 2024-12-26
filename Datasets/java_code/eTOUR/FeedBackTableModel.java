package unisa.gps.etour.gui.operatoreagenzia.tables;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.table.AbstractTableModel;

import unisa.gps.etour.bean.BeanVisitaBC;
import unisa.gps.etour.bean.BeanVisitaPR;

/**
 * <b>FeedbackTableModel</b>
 * <p>'Container model of data for feedback received
 * To be a cultural or a refreshment.</p>
 *
 * @see javax.swing.table.AbstractTableModel
 * @see unisa.gps.etour.bean.BeanVisitaBC
 * @see unisa.gps.etour.bean.BeanVisitaPR
 * @version 1.0
 * @author Mario Gallo
 */
public class FeedbackTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private static final String[] headers = {"Rating", "Comment", "Release Date", "Issued by"};
    private static final Class[] columnClasses = {Integer.class, String.class, Date.class, String.class};
    private ArrayList<Object[]> data;

    /**
     * Default Constructor. We only provide the model but not
     * Loads no data in it.
     */
    public FeedbackTableModel() {
        data = new ArrayList<>();
    }

    /**
     * Constructor that takes as input an ArrayList of BeanVisitaBC or
     * BeanVisitaPR and copies of the data within the model
     * Preparing for display.
     *
     * @param pDatiFeedback java.util.HashMap - the HashMap of bean.
     */
    public FeedbackTableModel(HashMap<?, String> pDatiFeedback) {
        this();
        if (pDatiFeedback == null || pDatiFeedback.size() == 0) {
            return;
        }
        Iterator<?> iter = pDatiFeedback.keySet().iterator();
        while (iter.hasNext()) {
            Object current = iter.next();
            if (current instanceof BeanVisitaBC) {
                insertVisitaBC((BeanVisitaBC) current, pDatiFeedback.get(current));
            } else if (current instanceof BeanVisitaPR) {
                insertVisitaPR((BeanVisitaPR) current, pDatiFeedback.get(current));
            }
        }
    }

    /**
     * Returns the number of columns provided by the model.
     *
     * @return int - the number of columns.
     */
    public int getColumnCount() {
        return headers.length;
    }

    /**
     * Returns the number of rows currently in the model.
     *
     * @return int - the number of rows.
     */
    public int getRowCount() {
        return data.size();
    }

    /**
     * Returns the column name from the index provided.
     *
     * @return String - the name of the column.
     * @throws IllegalArgumentException - if the column index is not provided
     *                                   in the model.
     */
    public String getColumnName(int pColumn) throws IllegalArgumentException {
        if (pColumn >= getColumnCount() || pColumn < 0) {
            throw new IllegalArgumentException("The column index is not provided in the model.");
        }
        return headers[pColumn];
    }

    /**
     * Returns the object in the model to the row and column provided.
     *
     * @param pRow    - the line number.
     * @param pColumn - the column number.
     * @return Object - the object contained in the selected cell.
     * @throws IllegalArgumentException - if the index or the row or column not provided
     *                                   are present in the model.
     */
    public Object getValueAt(int pRow, int pColumn) throws IllegalArgumentException {
        if (pRow >= getRowCount() || pRow < 0) {
            throw new IllegalArgumentException("The row index is not provided in the model.");
        }
        if (pColumn >= getColumnCount() || pColumn < 0) {
            throw new IllegalArgumentException("The column index is not provided in the model.");
        }
        return data.get(pRow)[pColumn];
    }

    /**
     * Returns the class of objects in the column of which is provided in the index.
     *
     * @param pColumn - the column number.
     * @return Class - the class of objects of the selected column.
     * @throws IllegalArgumentException - if the index column does not pro
     *                                   in the model.
     */
    public Class getColumnClass(int pColumn) throws IllegalArgumentException {
        if (pColumn >= getColumnCount() || pColumn < 0) {
            throw new IllegalArgumentException("The column index is not provided in the model.");
        }
        return columnClasses[pColumn];
    }

    /**
     * Returns whether the selected cell editable.
     *
     * @return boolean - true if the cell editable
     * false otherwise
     * @param pRow    - the line number.
     * @param pColumn - the column number.
     * @throws IllegalArgumentException - if the index or the row or column are not provided
     *                                   in the model.
     */
    public boolean isCellEditable(int pRow, int pColumn) throws IllegalArgumentException {
        return false;
    }

    /**
     * The method is inherited by setValueAt TableModel does not need to FeedbackTableModel
     * Because it provided the possibility to change a single cell.
     * @deprecated
     */
    public void setValueAt(Object value, int row, int col) {
    }

    /**
     * Enter data on the feedback received from a cultural
     * In the model from Bean.
     *
     * @param pVisitaBC - the bean that contains the feedback from
     *                   Cultural.
     * @throws IllegalArgumentException - if the parameters supplied input not
     *                                   are valid.
     */
    public void insertVisitaBC(BeanVisitaBC pVisitaBC, String pUsername) throws IllegalArgumentException {
        if (pVisitaBC == null || pUsername == null || pUsername.equals("")) {
            throw new IllegalArgumentException("Invalid parameters supplied input.");
        }
        Object[] aRow = new Object[6];
        aRow[0] = pVisitaBC.getVoto();
        aRow[1] = pVisitaBC.getCommento();
        aRow[2] = pVisitaBC.getDataVisita();
        aRow[3] = pUsername;
        aRow[4] = pVisitaBC.getIdBeneCulturale();
        aRow[5] = pVisitaBC.getIdTurista();
        data.add(aRow);
    }

    /**
     * Enter data on the feedback received from a refreshment
     * In the model from Bean.
     *
     * @param pVisitaPR - the bean that contains the feedback from
     *                   Refreshment.
     * @throws IllegalArgumentException - if the parameters supplied input not
     *                                   are valid.
     */
    public void insertVisitaPR(BeanVisitaPR pVisitaPR, String pUsername) throws IllegalArgumentException {
        if (pVisitaPR == null || pUsername == null || pUsername.equals("")) {
            throw new IllegalArgumentException("Invalid parameters supplied input.");
        }
        Object[] aRow = new Object[6];
        aRow[0] = pVisitaPR.getVoto();
        aRow[1] = pVisitaPR.getCommento();
        aRow[2] = pVisitaPR.getDataVisita();
        aRow[3] = pUsername;
        aRow[4] = pVisitaPR.getIdPuntoDiRistoro();
        aRow[5] = pVisitaPR.getIdTurista();
        data.add(aRow);
    }

    /**
     * Updates the comment feedback contained in the table row selected.
     *
     * @param pNuovoCommento - the new comment.
     * @param pRow           - the row to update.
     * @throws IllegalArgumentException - if <ul> <li> the row index is not present in the model.
     *                                    <li> New comment supplied input is zero. </ul>
     */
    public void modificaCommento(String pNuovoCommento, int pRow) throws IllegalArgumentException {
        if (pRow >= getRowCount() || pRow < 0) {
            throw new IllegalArgumentException("The row index is not provided in the model.");
        }
        if (pNuovoCommento == null) {
            throw new IllegalArgumentException("The new comment can not be null.");
        }
        data.get(pRow)[1] = pNuovoCommento;
        fireTableDataChanged();
    }

    /**
     * Returns the id of the row receive feedback provided on input.
     *
     * @param pRow int - the row number.
     * @return int[] - the id of the feedback.
     * @throws IllegalArgumentException - if the row index does not pro
     *                                   in the model.
     */
    public int[] getIDFeedback(int pRow) throws IllegalArgumentException {
        if (pRow >= getRowCount() || pRow < 0) {
            throw new IllegalArgumentException("The row index is not provided in the model.");
        }
        int[] ids = new int[2];
        ids[0] = (Integer) data.get(pRow)[4];
        ids[1] = (Integer) data.get(pRow)[5];
        return ids;
    }

    /**
     * Returns the id of feedback to line number provided as input and removes it from the model.
     *
     * @param pRow int - the row number.
     * @return int[] - the id of the feedback.
     * @throws IllegalArgumentException - if the row index does not pro
     *                                   in the model.
     */
    public int[] removeFeedback(int pRow) throws IllegalArgumentException {
        int[] ids = getIDFeedback(pRow);
        data.remove(pRow);
        return ids;
    }
}