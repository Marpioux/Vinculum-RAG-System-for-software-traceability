package unisa.gps.etour.gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.DefaultDesktopManager;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import unisa.gps.etour.gui.operatoreagenzia.IScheda;

/**
 * Class for handling custom internal frame inserted in a
 * JDesktopPane.
 *
 * @Version 0.1
 * @Author Mario Gallo
 *
 * © 2007 eTour Project - Copyright by DMI SE @ SA Lab --
 * University of Salerno
 */
public class DeskManager extends DefaultDesktopManager {
    private static final String URL_IMAGES = "/unisa/gps/eTour/gui/images/";
    private JPopupMenu deskMenu;
    private JMenuItem riduciTutti;
    private JMenuItem ripristinaTutti;
    private JMenuItem closeAll;
    private Vector<JInternalFrame> iconifiedFrames;
    private int locationX;
    private int locationY;

    /**
     * Default Constructor.
     */
    public DeskManager() {
        super();
        iconifiedFrames = new Vector<>();
        initializeDeskMenu();
        locationX = 0;
        locationY = -1;
    }

    /**
     * Manages the movement of JInternalFrame inside the area of
     * JDesktopPane, preventing the frames from being moved out of the visible area.
     *
     * @param component JComponent - the component of which
     *                  to manage the move.
     * @param x - x coordinate of the point where the component was moved
     * @param y - y coordinate of the point where the component was moved
     */
    public void dragFrame(JComponent component, int x, int y) {
        if (component instanceof JInternalFrame) {
            JInternalFrame frame = (JInternalFrame) component;
            if (frame.isIcon()) {
                x = frame.getLocation().x;
                y = frame.getLocation().y;
            } else {
                JDesktopPane desk = frame.getDesktopPane();
                Dimension d = desk.getSize();
                if (x < 0) {
                    x = 0;
                } else if (x + frame.getWidth() > d.width) {
                    x = d.width - frame.getWidth();
                }
                if (y < 0) {
                    y = 0;
                } else if (y + frame.getHeight() > d.height) {
                    y = d.height - frame.getHeight();
                }
            }
        }
        super.dragFrame(component, x, y);
    }

    /**
     * Customize the action of reducing the JInternalFrame to an icon.
     *
     * @param frame JInternalFrame - a frame inside a JDesktopPane.
     */
    public void iconifyFrame(JInternalFrame frame) {
        try {
            JDesktopPane desk = frame.getDesktopPane();
            Dimension d = desk.getSize();
            frame.setClosable(false);
            frame.setMaximizable(true);
            frame.setIconifiable(false);
            Rectangle features;
            if (frame.isMaximum()) {
                features = frame.getNormalBounds();
            } else {
                features = frame.getBounds();
            }
            frame.setSize(200, 30);
            setPreviousBounds(frame, features);
            if (iconifiedFrames.isEmpty()) {
                locationX = 0;
            } else {
                locationX += 200;
            }
            if (locationY == -1) {
                locationY = d.height - 30;
            }
            if (locationX + 200 > d.width) {
                locationX = 0;
                locationY -= 30;
            }
            frame.setLocation(locationX, locationY);
            frame.setResizable(false);
            iconifiedFrames.add(frame);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Restore the frame from being minimized, resetting the
     * position and size it had before.
     *
     * @param frame JInternalFrame - a frame inside a JDesktopPane.
     */
    public void deiconifyFrame(JInternalFrame frame) {
        try {
            JDesktopPane desk = frame.getDesktopPane();
            Dimension deskSize = desk.getSize();
            iconifiedFrames.remove(frame);
            Rectangle features = getPreviousBounds(frame);
            if (features.width > deskSize.width) {
                features.width = deskSize.width;
                features.x = 0;
            }
            if (features.width + features.x > deskSize.width) {
                features.x = (deskSize.width - features.width) / 2;
            }
            if (features.height > deskSize.height) {
                features.height = deskSize.height;
                features.y = 0;
            }
            if (features.height + features.y > deskSize.height) {
                features.y = (deskSize.height - features.height) / 2;
            }
            frame.setSize(features.width, features.height);
            frame.setLocation(features.x, features.y);
            frame.setIconifiable(true);
            frame.setClosable(true);
            if (frame instanceof IScheda) {
                frame.setMaximizable(false);
                frame.setResizable(false);
            } else {
                frame.setMaximizable(true);
                frame.setResizable(true);
            }
            locationX -= 200;
            if (locationX < 0) {
                locationX = deskSize.width / 200 - 200;
                if (locationY != deskSize.height - 30) {
                    locationY -= 30;
                }
            }
            repaintIconifiedFrames(desk);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Return the focus to a selected frame, and, if the frame
     * is iconified, it deiconifies it.
     *
     * @param frame JInternalFrame - a frame within a JDesktopPane
     */
    public void activateFrame(JInternalFrame frame) {
        try {
            if (frame.isIcon()) {
                frame.setIcon(false);
            }
            frame.setSelected(true);
            super.activateFrame(frame);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    /**
     * Center the frame supplied as a parameter in JDesktopPane.
     *
     * @param frame JInternalFrame - a frame inside a JDesktopPane.
     */
    public void centerFrame(JInternalFrame frame) {
        JDesktopPane desk = frame.getDesktopPane();
        Dimension d = desk.getSize();
        Dimension f = frame.getSize();
        frame.setLocation(d.width / 2 - f.width / 2, d.height / 2 - f.height / 2);
    }

    /**
     * Redraw the frames in the desktop that are iconified.
     *
     * @param desk JDesktopPane - a desktop pane associated with a
     *              DeskManager.
     * @throws IllegalArgumentException - if the supplied parameter is not
     *                                    associated with a DeskManager.
     */
    public void repaintIconifiedFrames(JDesktopPane desk) throws IllegalArgumentException {
        if (desk.getDesktopManager() != this) {
            throw new IllegalArgumentException("No DeskManager associated with the provided JDesktopPane.");
        }
        Iterator<JInternalFrame> iconificati = iconifiedFrames.iterator();
        int i = 0;
        int xLocation;
        int yLocation = desk.getHeight() - 30;
        while (iconificati.hasNext()) {
            JInternalFrame current = iconificati.next();
            xLocation = 200 * i;
            if (xLocation + 200 >= desk.getWidth()) {
                xLocation = 0;
                yLocation -= 30;
                i = 0;
            }
            current.setLocation(xLocation, yLocation);
            i++;
        }
    }

    /**
     * Redraw (and resize if necessary) all the frames contained in a
     * given JDesktopPane.
     *
     * @param desk JDesktopPane - a desktop pane.
     * @throws IllegalArgumentException - if the supplied desktop pane is not
     *                                    associated with a DeskManager.
     */
    public void repaintAllFrames(JDesktopPane desk) throws IllegalArgumentException {
        if (desk.getDesktopManager() != this) {
            throw new IllegalArgumentException("No DeskManager associated with the provided JDesktopPane.");
        }
        JInternalFrame[] frames = desk.getAllFrames();
        Dimension deskSize = desk.getSize();
        for (int i = 0; i < frames.length; i++) {
            JInternalFrame current = frames[i];
            if (!current.isIcon()) {
                Rectangle frameBounds = current.getBounds();
                if (frameBounds.width > deskSize.width) {
                    frameBounds.width = deskSize.width;
                }
                if (frameBounds.height > deskSize.height) {
                    frameBounds.height = deskSize.height;
                }
                if (frameBounds.x + frameBounds.width > deskSize.width) {
                    frameBounds.x = deskSize.width - frameBounds.width;
                }
                if (frameBounds.y + frameBounds.height > deskSize.height) {
                    frameBounds.y = deskSize.height - frameBounds.height;
                }
                current.setBounds(frameBounds);
            }
        }
        repaintIconifiedFrames(desk);
    }

    /**
     * Open a frame of the specified class using the display.
     * If a frame of the given class already exists, it activates that frame.
     *
     * @param clazz Class - a class type that extends JInternalFrame.
     * @param desk JDesktopPane - a desktop pane.
     * @throws IllegalArgumentException - if the provided class does not extend JInternalFrame.
     */
    public void openFrame(Class<?> clazz, JDesktopPane desk) throws IllegalArgumentException {
        if (!JInternalFrame.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("The provided class does not extend javax.swing.JInternalFrame.");
        }
        try {
            JInternalFrame[] frames = desk.getAllFrames();
            int i;
            for (i = 0; i < frames.length; i++) {
                if (frames[i].getClass().equals(clazz)) {
                    break;
                }
            }
            if (i == frames.length) {
                JInternalFrame newFrame = (JInternalFrame) clazz.getDeclaredConstructor().newInstance();
                desk.add(newFrame, Integer.MAX_VALUE);
                Dimension frameSize = newFrame.getPreferredSize();
                newFrame.setSize(frameSize);
                Dimension deskSize = desk.getSize();
                Point posNuovo = new Point(10, 10);
                for (i = frames.length - 1; i >= 0; i--) {
                    if (frames[i].getLocation().equals(posNuovo)) {
                        posNuovo.x = frames[i].getLocation().x + 30;
                        posNuovo.y = frames[i].getLocation().y + 30;
                    }
                }
                if ((posNuovo.x + frameSize.width > deskSize.width) || (posNuovo.y + frameSize.height > deskSize.height)) {
                    centerFrame(newFrame);
                } else {
                    newFrame.setLocation(posNuovo);
                }
                newFrame.setVisible(true);
            } else {
                activateFrame(frames[i]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Displays a popup menu with options for frames of a desktop pane
     * at the selected location.
     *
     * @param point Point - the point where to place the menu.
     * @param desk JDesktopPane - a JDesktopPane that is associated with a DeskManager.
     * @throws IllegalArgumentException - if the supplied parameter is not associated with a DeskManager.
     */
    public void showPopupMenu(Point point, JDesktopPane desk) throws IllegalArgumentException {
        if (desk.getDesktopManager() != this) {
            throw new IllegalArgumentException("No DeskManager associated with the provided JDesktopPane.");
        }
        ripristinaTutti.setEnabled(true);
        closeAll.setEnabled(true);
        riduciTutti.setEnabled(true);
        JInternalFrame[] frames = desk.getAllFrames();
        if (frames.length == 0) {
            ripristinaTutti.setEnabled(false);
            closeAll.setEnabled(false);
            riduciTutti.setEnabled(false);
        }
        if (iconifiedFrames.size() == 0) {
            ripristinaTutti.setEnabled(false);
        }
        if (iconifiedFrames.size() == frames.length) {
            riduciTutti.setEnabled(false);
        }
        deskMenu.show(desk, point.x, point.y);
    }

    /**
     * Deiconifies all frames previously iconified.
     */
    public void deiconifyAll() {
        if (iconifiedFrames.size() != 0) {
            Vector<JInternalFrame> copy = (Vector<JInternalFrame>) iconifiedFrames.clone();
            Iterator<JInternalFrame> frames = copy.iterator();
            while (frames.hasNext()) {
                try {
                    frames.next().setIcon(false);
                } catch (PropertyVetoException e) {
                    e.printStackTrace();
                }
            }
            copy = null;
            iconifiedFrames.removeAllElements();
        }
    }

    /**
     * Minimize all frames of a provided JDesktopPane that is associated with a DeskManager.
     *
     * @param desk JDesktopPane - a desktop pane.
     * @throws IllegalArgumentException - if the supplied parameter is not associated with a DeskManager.
     */
    public void iconifyAll(JDesktopPane desk) throws IllegalArgumentException {
        if (desk.getDesktopManager() != this) {
            throw new IllegalArgumentException("No DeskManager associated with the provided JDesktopPane.");
        }
        JInternalFrame[] frames = desk.getAllFrames();
        for (int i = 0; i < frames.length; i++) {
            try {
                frames[i].setIcon(true);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Close all frames in a given JDesktopPane.
     *
     * @param desk JDesktopPane - a desktop pane associated with a DeskManager.
     * @throws IllegalArgumentException - if the supplied parameter is not associated with a DeskManager.
     */
    public void closeAll(JDesktopPane desk) throws IllegalArgumentException {
        if (desk.getDesktopManager() != this) {
            throw new IllegalArgumentException("No DeskManager associated with the provided JDesktopPane.");
        }
        JInternalFrame[] frames = desk.getAllFrames();
        if (frames.length != 0) {
            for (int i = 0; i < frames.length; i++) {
                frames[i].dispose();
            }
            iconifiedFrames.removeAllElements();
        }
    }

    /**
     * Initialize the DeskMenu.
     */
    public void initializeDeskMenu() {
        deskMenu = new JPopupMenu();
        riduciTutti = new JMenuItem("Collapse All");
        riduciTutti.setIcon(new ImageIcon(getClass().getResource(URL_IMAGES + "reduceAll.png")));
        ripristinaTutti = new JMenuItem("Reset All");
        ripristinaTutti.setIcon(new ImageIcon(getClass().getResource(URL_IMAGES + "activateall.png")));
        closeAll = new JMenuItem("Close All");
        closeAll.setIcon(new ImageIcon(getClass().getResource(URL_IMAGES + "closeall.png")));
        deskMenu.add(riduciTutti);
        deskMenu.addSeparator();
        deskMenu.add(ripristinaTutti);
        deskMenu.addSeparator();
        deskMenu.add(closeAll);
        ActionListener menuListener = new ActionListener() {
            public void actionPerformed(ActionEvent aEvent) {
                if (aEvent.getSource() == ripristinaTutti) {
                    deiconifyAll();
                }
                if (aEvent.getSource() == closeAll) {
                    closeAll((JDesktopPane) deskMenu.getInvoker());
                }
                if (aEvent.getSource() == riduciTutti) {
                    iconifyAll((JDesktopPane) deskMenu.getInvoker());
                }
            }
        };
        riduciTutti.addActionListener(menuListener);
        ripristinaTutti.addActionListener(menuListener);
        closeAll.addActionListener(menuListener);
    }
}