package unisa.gps.etour.gui.operatoreagenzia.tables.test;

import java.util.ArrayList;
import java.util.Date;

import unisa.gps.etour.bean.BeanNews;
import unisa.gps.etour.gui.operatoreagenzia.tables.NewsTableModel;
import junit.framework.TestCase;

public class NewsTableModelTest extends TestCase {

    private NewsTableModel tableModel;
    private BeanNews aNews;
    private BeanNews aNewsModificata;

    public NewsTableModelTest(String pName) {
        super(pName);
        aNews = new BeanNews("An example of news", new Date(), new Date(), 2, 1);
        aNewsModificata = new BeanNews("A news amended sample", new Date(), new Date(), 3, 1);
    }

    protected void setUp() throws Exception {
        super.setUp();
        tableModel = new NewsTableModel();
    }

    public void testCostruttoreConArrayList() {
        ArrayList<BeanNews> test = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            test.add(new BeanNews("text" + i, new Date(), new Date(), 5, i));
        }
        tableModel = new NewsTableModel(test);
        for (int i = 0; i < 10; i++) {
            assertSame(test.get(i).getId(), tableModel.getID(i));
        }
    }

    public void testCostruttoreConArrayListNull() {
        tableModel = new NewsTableModel(null);
    }

    public void testCostruttoreConArrayListVuoto() {
        tableModel = new NewsTableModel(new ArrayList<BeanNews>());
    }

    public void testGetValueAtParametriCorretti() {
        tableModel.insertNews(aNews);
        tableModel.insertNews(aNewsModificata);

        assertSame(aNews.getNews(), tableModel.getValueAt(0, 0));
        assertSame(aNews.getPriorita(), tableModel.getValueAt(0, 1));
        assertSame(aNewsModificata.getNews(), tableModel.getValueAt(1, 0));
        assertSame(aNewsModificata.getPriorita(), tableModel.getValueAt(1, 1));
    }

    public void testGetValueAtRigaSballata() {
        try {
            tableModel.getValueAt(12, 0);
            fail("Should be thrown");
        } catch (IllegalArgumentException success) {
        }
    }

    public void testGetValueAtColonnaSballata() {
        try {
            tableModel.getValueAt(0, -121334);
            fail("Should be thrown");
        } catch (IllegalArgumentException success) {
        }
    }

    public void testInsertNewsParametroCorretto() {
        tableModel.insertNews(aNews);
        assertSame(aNews.getId(), tableModel.getID(0));
    }

    public void testInsertNewsParametroNull() {
        try {
            tableModel.insertNews(null);
            fail("Should be thrown");
        } catch (IllegalArgumentException success) {
        }
    }

    public void testUpdateNewsParametroCorretto() {
        tableModel.insertNews(aNews);
        tableModel.updateNews(aNewsModificata);
        assertSame(aNewsModificata.getNews(), tableModel.getValueAt(0, 0));
        assertSame(aNewsModificata.getPriorita(), tableModel.getValueAt(0, 1));
        assertSame(aNewsModificata.getId(), tableModel.getID(0));
    }

    public void testUpdateNewsParametroNull() {
        try {
            tableModel.updateNews(null);
            fail("Should be thrown");
        } catch (IllegalArgumentException success) {
        }
    }

    public void testRemoveNewsParametroCorretto() {
        tableModel.insertNews(aNews);
        assertSame(aNews.getId(), tableModel.removeNews(0));
    }

    public void testRemoveNewsRigaSballata() {
        try {
            tableModel.removeNews(-1231);
            fail("Should be thrown");
        } catch (IllegalArgumentException success) {
        }
    }
}