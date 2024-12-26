/*
 * Test case for class TestoNewsRenderer
 *
 * @ Author Mario Gallo
 * @ Version 0.1 © 2007 eTour Project - Copyright by DMI SE @ SA Lab --
 * University of Salerno
 */
package unisa.gps.etour.gui.operatoreagenzia.tables.test;

import java.util.Date;
import javax.swing.JTable;
import javax.swing.JTextArea;
import unisa.gps.etour.bean.BeanNews;
import unisa.gps.etour.gui.operatoreagenzia.tables.NewsTableModel;
import unisa.gps.etour.gui.operatoreagenzia.tables.TestoNewsRenderer;
import junit.framework.TestCase;

public class TestoNewsRendererTest extends TestCase {

    private TestoNewsRenderer renderer;
    private BeanNews aNewsAttiva;
    private BeanNews aNewsScaduta;
    private JTable aTable;

    public TestoNewsRendererTest() {
        super();
        renderer = new TestoNewsRenderer();
        aNewsAttiva = new BeanNews("Here's a news active", new Date(),
                new Date(120, 1, 1), 5, 0);
        aNewsScaduta = new BeanNews("Here's a news expired", new Date(),
                new Date(), 5, 0);
        aTable = new JTable(new NewsTableModel());
    }

    /*
     * Verify the behavior of the method with the correct parameters.
     */
    public void testGetTableCellRendererParametriCorretti() {
        NewsTableModel aModel = (NewsTableModel) aTable.getModel();
        aModel.insertNews(aNewsAttiva);
        aModel.insertNews(aNewsScaduta);

        // Test the renderer with a news active.
        JTextArea aArea = (JTextArea) renderer.getTableCellRendererComponent(
                aTable, "Here's a news active", true, true, 0, 0);
        assertEquals(aNewsAttiva.getNews(), aArea.getText());

        // Test the renderer with a news expired.
        aArea = (JTextArea) renderer.getTableCellRendererComponent(aTable,
                "Here's a news expired", true, true, 0, 0);
        assertEquals(aNewsScaduta.getNews(), aArea.getText());
    }

    /*
     * Verification comparing the table with a table without NewsTableModel
     * associated.
     */
    public void testGetTableCellRendererNoNewsModel() {
        JTable anotherTable = new JTable();
        try {
            renderer.getTableCellRendererComponent(anotherTable,
                    "Here's a news", true, true, 0, 0);
            fail("Should be thrown.");
        } catch (IllegalArgumentException success) {
        }
    }

    /*
     * Verify the behavior of the method with a parameter as null.
     */
    public void testGetTableCellRendererParametroNull() {
        try {
            renderer.getTableCellRendererComponent(aTable, null, true, true, 0,
                    0);
            fail("Should be thrown.");
        } catch (IllegalArgumentException success) {
        }
    }

    /*
     * Verify the behavior of the method with an unexpected data type.
     */
    public void testGetTableCellRendererTipoInatteso() {
        try {
            renderer.getTableCellRendererComponent(aTable, 12, true, true, 0, 0);
            fail("Should be thrown.");
        } catch (IllegalArgumentException success) {
        }
    }
}