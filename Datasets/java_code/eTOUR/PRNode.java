package unisa.gps.etour.gui.operatoreagenzia.tables;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * <b>PRNode</b>
 * <p>This class creates a node in a JTree to store
 * information for a refreshment.</p>
 *
 * @see javax.swing.JTree;
 * @see javax.swing.tree.DefaultMutableTreeNode;
 * @version 1.0
 * @author Mario Gallo
 */
public class PRNode extends DefaultMutableTreeNode {
    private int id;

    public PRNode() {
        super();
    }

    /**
     * Create a node with the name of refreshment and
     * your id supplied as parameters.
     *
     * @param pNome - the name of refreshment.
     * @param pid   - the id of refreshment.
     * @throws IllegalArgumentException - if the name provided as input is invalid.
     */
    public PRNode(String pNome, int pid) throws IllegalArgumentException {
        super();
        if (pNome == null || pNome.equals("")) {
            throw new IllegalArgumentException("Name of refreshment supplied invalid input.");
        }
        setUserObject(pNome);
        id = pid;
    }

    /**
     * Returns the id of the point of comfort for which information
     * are stored in this node.
     *
     * @return int - the id of refreshment.
     */
    public int getID() {
        return id;
    }

    /**
     * Stores the id of the refreshment provided input.
     *
     * @param pid - an ID of an eating place.
     */
    public void setID(int pid) {
        id = pid;
    }

    /**
     * Return the name of refreshment.
     *
     * @return String - the name of refreshment.
     */
    public String getName() {
        return (String) super.getUserObject();
    }

    /**
     * Stores the name of the refreshment provided input.
     *
     * @param pNome - the name of a refreshment.
     * @throws IllegalArgumentException - if the name provided as input is invalid.
     */
    public void setNome(String pNome) throws IllegalArgumentException {
        if (pNome == null || pNome.equals("")) {
            throw new IllegalArgumentException("Name of refreshment supplied invalid input.");
        }
        setUserObject(pNome);
    }
}