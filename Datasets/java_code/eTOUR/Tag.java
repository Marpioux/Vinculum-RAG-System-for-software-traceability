package unisa.gps.etour.gui.operatoreagenzia;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import unisa.gps.etour.bean.BeanTag;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Insets;

public class Tag extends JInternalFrame {

    private JPanel jContentPane = null;
    private JPanel CenterPanel = null;
    private JPanel EastPanel = null;
    private JScrollPane jScrollPane = null;
    private JTable jTable = null;
    private JToolBar jJToolBarBar = null;
    private JButton btnModifica = null;
    private JButton btnElimina = null;
    private JButton btnExit = null;
    private JPanel jPanelUp = null;
    private JPanel jPanelHelp = null;
    private JTextPane jTextPane = null;
    private JLabel tagname = null;
    private JTextField jTextField = null;
    private JLabel description = null;
    private JTextArea jTextArea = null;
    private JButton btnOK = null;
    private JButton btnResetta = null;

    /**
     * This is the default constructor
     */
    public Tag() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        this.setSize(508, 398);
        this.setFrameIcon(new ImageIcon(getClass().getResource("/interfacceAgenzia/images/Properties.png")));
        this.setTitle("Manage Tag");
        this.setContentPane(getJContentPane());
    }

    /**
     * This method initializes jContentPane
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (null == jContentPane) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getCenterPanel(), BorderLayout.CENTER);
            jContentPane.add(getEastPanel(), BorderLayout.EAST);
            jContentPane.add(getJJToolBarBar(), BorderLayout.NORTH);
        }
        return jContentPane;
    }

    /**
     * This method initializes CenterPanel
     *
     * @return javax.swing.JPanel
     */
    private JPanel getCenterPanel() {
        if (null == CenterPanel) {
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.weightx = 1.0;
            CenterPanel = new JPanel();
            CenterPanel.setLayout(new GridBagLayout());
            CenterPanel.add(getJScrollPane(), gridBagConstraints);
        }
        return CenterPanel;
    }

    /**
     * This method initializes EastPanel
     *
     * @return javax.swing.JPanel
     */
    private JPanel getEastPanel() {
        if (null == EastPanel) {
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.gridy = 1;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.gridy = 0;
            EastPanel = new JPanel();
            EastPanel.setLayout(new GridBagLayout());
            EastPanel.add(getJPanelUp(), gridBagConstraints2);
            EastPanel.add(getJPanelHelp(), gridBagConstraints3);
        }
        return EastPanel;
    }

    /**
     * This method initializes JScrollPane
     *
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getJTable());
        }
        return jScrollPane;
    }

    /**
     * This method initializes JTable
     *
     * @return javax.swing.JTable
     */
    private JTable getJTable() {
        if (jTable == null) {
            jTable = new JTable();
            BeanTag[] listaTag = new BeanTag[11];
            listaTag[0] = new BeanTag(1, "romantic", "place for couples and unforgettable moments");
            listaTag[1] = new BeanTag(2, "esoteric", "places of magic");
            listaTag[2] = new BeanTag(3, "pizza", "The best pizza");
            listaTag[3] = new BeanTag(6, "music", "live music venues, concerts ...");
            listaTag[4] = new BeanTag(76, "trattoria", "typical");
            listaTag[5] = new BeanTag(7, "fairs", "for important purchases or souvenirs bellissimi");
            listaTag[6] = new BeanTag(9, "Market", "typical");
            listaTag[7] = new BeanTag(8, "History", "typical");
            listaTag[8] = new BeanTag(5, "nineteenth century", "typical");
            listaTag[9] = new BeanTag(4, "range", "typical");
            listaTag[10] = new BeanTag(56, "Cinema", "typical");
        }
        return jTable;
    }

    /**
     * This method initializes jJToolBarBar
     *
     * @return javax.swing.JToolBar
     */
    private JToolBar getJJToolBarBar() {
        if (null == jJToolBarBar) {
            jJToolBarBar = new JToolBar();
            jJToolBarBar.add(getBtnModifica());
            jJToolBarBar.add(getBtnElimina());
            jJToolBarBar.addSeparator();
            jJToolBarBar.add(getBtnExit());
        }
        return jJToolBarBar;
    }

    /**
     * This method initializes btnModifica
     *
     * @return javax.swing.JButton
     */
    private JButton getBtnModifica() {
        if (null == btnModifica) {
            btnModifica = new JButton();
            btnModifica.setText("Edit Tag");
            btnModifica.setIcon(new ImageIcon(getClass().getResource("/interfacceAgenzia/immagini/edit-32x32.png")));
        }
        return btnModifica;
    }

    /**
     * This method initializes btnElimina
     *
     * @return javax.swing.JButton
     */
    private JButton getBtnElimina() {
        if (null == btnElimina) {
            btnElimina = new JButton();
            btnElimina.setText("Remove Tag");
            btnElimina.setIcon(new ImageIcon(getClass().getResource("/interfacceAgenzia/immagini/button-cancel-32x32.png")));
        }
        return btnElimina;
    }

    /**
     * This method initializes btnExit
     *
     * @return javax.swing.JButton
     */
    private JButton getBtnExit() {
        if (null == btnExit) {
            btnExit = new JButton();
            btnExit.setText("Exit");
            btnExit.setIcon(new ImageIcon(getClass().getResource("/interfacceAgenzia/images/Forward 2.png")));
        }
        return btnExit;
    }

    /**
     * This method initializes jPanelUp
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanelUp() {
        if (null == jPanelUp) {
            GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
            gridBagConstraints9.gridx = 1;
            gridBagConstraints9.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints9.gridy = 4;
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.gridx = 0;
            gridBagConstraints8.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints8.gridy = 4;
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.fill = GridBagConstraints.BOTH;
            gridBagConstraints7.gridy = 3;
            gridBagConstraints7.weightx = 1.0;
            gridBagConstraints7.weighty = 1.0;
            gridBagConstraints7.gridwidth = 2;
            gridBagConstraints7.insets = new Insets(5, 0, 5, 0);
            gridBagConstraints7.gridx = 0;
            GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
            gridBagConstraints6.gridx = 0;
            gridBagConstraints6.gridwidth = 2;
            gridBagConstraints6.insets = new Insets(5, 0, 5, 0);
            gridBagConstraints6.gridy = 2;
            description = new JLabel();
            description.setText("Description:");
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            gridBagConstraints5.fill = GridBagConstraints.VERTICAL;
            gridBagConstraints5.gridy = 1;
            gridBagConstraints5.weightx = 1.0;
            gridBagConstraints5.gridwidth = 2;
            gridBagConstraints5.insets = new Insets(5, 0, 5, 0);
            gridBagConstraints5.gridx = 0;
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.gridx = 0;
            gridBagConstraints4.gridwidth = 2;
            gridBagConstraints4.insets = new Insets(5, 0, 5, 0);
            gridBagConstraints4.gridy = 0;
            tagname = new JLabel();
            tagname.setText("Tag Name:");
            jPanelUp = new JPanel();
            jPanelUp.setLayout(new GridBagLayout());
            jPanelUp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 102, 255), 3), "Insert New", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(0, 102, 255)));
            jPanelUp.add(tagname, gridBagConstraints4);
            jPanelUp.add(getJTextField(), gridBagConstraints5);
            jPanelUp.add(description, gridBagConstraints6);
            jPanelUp.add(getJTextArea(), gridBagConstraints7);
            jPanelUp.add(getBtnOk(), gridBagConstraints8);
            jPanelUp.add(getBtnResetta(), gridBagConstraints9);
        }
        return jPanelUp;
    }

    /**
     * This method initializes jPanelHelp
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanelHelp() {
        if (null == jPanelHelp) {
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.fill = GridBagConstraints.BOTH;
            gridBagConstraints1.weighty = 1.0;
            gridBagConstraints1.weightx = 1.0;
            jPanelHelp = new JPanel();
            jPanelHelp.setLayout(new GridBagLayout());
            jPanelHelp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 102, 255), 3), "Help", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 153, 255)));
            jPanelHelp.add(getJTextPane(), gridBagConstraints1);
        }
        return jPanelHelp;
    }

    /**
     * This method initializes jTextPane
     *
     * @return javax.swing.JTextPane
     */
    private JTextPane getJTextPane() {
        if (null == jTextPane) {
            jTextPane = new JTextPane();
            jTextPane.setPreferredSize(new Dimension(190, 80));
        }
        return jTextPane;
    }

    /**
     * This method initializes JTextField
     *
     * @return javax.swing.JTextField
     */
    private JTextField getJTextField() {
        if (jTextField == null) {
            jTextField = new JTextField();
            jTextField.setColumns(10);
        }
        return jTextField;
    }

    /**
     * This method initializes JTextArea
     *
     * @return javax.swing.JTextArea
     */
    private JTextArea getJTextArea() {
        if (jTextArea == null) {
            jTextArea = new JTextArea();
            jTextArea.setRows(3);
        }
        return jTextArea;
    }

    /**
     * This method initializes btnOK
     *
     * @return javax.swing.JButton
     */
    private JButton getBtnOk() {
        if (btnOK == null) {
            btnOK = new JButton();
            btnOK.setText("Ok");
            btnOK.setIcon(new ImageIcon(getClass().getResource("/interfacceAgenzia/immagini/Button_ok16.png")));
        }
        return btnOK;
    }

    /**
     * This method initializes btnResetta
     *
     * @return javax.swing.JButton
     */
    private JButton getBtnResetta() {
        if (null == btnResetta) {
            btnResetta = new JButton();
            btnResetta.setText("Reset");
            btnResetta.setIcon(new ImageIcon(getClass().getResource("/interfacceAgenzia/images/Trash.png")));
        }
        return btnResetta;
    }
}