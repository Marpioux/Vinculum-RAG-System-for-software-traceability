package unisa.gps.etour.gui.operatoreagenzia;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import javax.swing.ImageIcon;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.JToolBar;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.StringTokenizer;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import unisa.gps.etour.bean.*;
import unisa.gps.etour.bean.util.Punto3D;

/**
 * Class that models the interface for viewing the card,
 * Modify the data and the insertion of a new resting spot.
 *
 * @author Lello
 */
public class SchedaPR extends JInternalFrame {

    private JPanel jContentPane = null;
    private JToolBar toolbarSchedaBC = null;
    private JToggleButton btnModifica = null;
    private JButton btnSalva = null;
    private JButton btnAnnulla = null;
    private JButton btnModificaCommento = null;
    private JTabbedPane jTabbedPane = null;
    private JPanel statistics = null;
    private JPanel feedback = null;
    private JLabel txtNome = null;
    private JLabel txtIndirizzo = null;
    private JLabel txtCAP = null;
    private JLabel txtCitta = null;
    private JLabel txtLocalità = null;
    private JLabel txtProvincia = null;
    private JLabel txtPos = null;
    private JLabel txtTel = null;
    private JLabel txtOraAp = null;
    private JLabel txtOraCh = null;

    private JLabel jLabel = null;
    private JTextField indirizzoPR = null;
    private JComboBox<String> indirizzoPR1 = null;
    private JTextField cittaPR = null;
    private JComboBox<String> localitaPR = null;
    private JTextField capPR = null;
    private JScrollPane jScrollPane = null;
    private JTextArea descrizionePR = null;
    private JTextField telefonoPR = null;
    private JComboBox<String> orarioAPOrePR = null;
    private JLabel jLabel1 = null;
    private JComboBox<String> orarioApMinPR = null;
    private TagPanel pannelloTag;
    private JTextField costoBC = null;

    private JLabel jLabel3 = null;
    private JComboBox<String> orarioCHMinPR = null;
    private JComboBox<String> provPR = null;
    private JPanel datiPR = null;
    private JTextField nomePR = null;
    private JPanel jPanel = null;
    private JScrollPane jScrollPane2 = null;
    private JTable feedbackTable = null;
    private JLabel txtNomeBene = null;
    private JLabel mediaVotoPR = null;
    private JPanel statisticheMeseCorrente = null;
    private JPanel statisticheTotali = null;
    private JLabel jLabel4 = null;
    private JLabel jLabel41 = null;
    private ActionListener campoCompilato;
    private FocusListener validating;
    private JToolBar toolbarSchedaPR = null;
    private JTextField posGeoX = null;
    private JTextField posGeoY = null;
    private JTextField posGeoZ = null;
    private JLabel jLabel2 = null;
    private JComboBox<String> orarioCHOrePR = null;

    /**
     * The default constructor for inclusion of the interface model
     * A new refreshment.
     */
    public SchedaPR() {
        super("New Refreshment");
        campoCompilato = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                ((JComponent) actionEvent.getSource()).transferFocus();
            }
        };
        validating = new FocusListener() {
            private final Color ERROR_BACKGROUND = new Color(255, 215, 215);
            private final Color WARNING_BACKGROUND = new Color(255, 235, 205);
            private String text;

            public void focusGained(FocusEvent fe) {
                if (fe.getSource() instanceof JTextField) {
                    JTextField textbox = (JTextField) fe.getSource();
                    text = textbox.getText();
                }
            }

            public void focusLost(FocusEvent fe) {
                if (fe.getSource() instanceof JTextField) {
                    JTextField textbox = (JTextField) fe.getSource();
                    if (!text.equals(textbox.getText())) {
                        text = textbox.getText();
                        if (text.equals("")) {
                            textbox.setBackground(ERROR_BACKGROUND);
                            Rectangle bounds = textbox.getBounds();
                            JLabel newLabel = new JLabel();
                            newLabel.setIcon(new ImageIcon(getClass().getResource("/unisa/gps/eTour/gui/images/error.png")));
                            newLabel.setBounds(bounds.x - 24, bounds.y, 24, 24);
                            newLabel.setToolTipText("Field " + textbox.getName() + " cannot be empty!");
                            datiPR.add(newLabel, null);
                            datiPR.repaint();
                        }
                    }
                }
            }
        };
        initialize();
    }

    /**
     * This interface models the manufacturer regarding modification of data and
     * Display board a refreshment.
     * 
     * @param pr    the bean contains the data of PuntoDiRistoro selected.
     * @param edit  indicates whether the fields should be editable, so if
     *              You are viewing a card or change the cultural property.
     */
    public SchedaPR(BeanPuntoDiRistoro pr, boolean edit) {
        this();
        nomePR.setText(pr.getNome());
        setTitle(pr.getNome());
        capPR.setText(pr.getCap());
        cittaPR.setText(pr.getCitta());
        descrizionePR.setText(pr.getDescrizione());
        StringTokenizer tokenizer = new StringTokenizer(pr.getVia());
        String[] via = {"Via", "P.zza", "V.le", "V.co", "Largo", "Corso"};
        String string = tokenizer.nextToken();
        int i;
        for (i = 0; i < via.length; i++) {
            if (string.equalsIgnoreCase(via[i])) {
                break;
            }
        }
        this.indirizzoPR1.setSelectedIndex(i);
        while (tokenizer.hasMoreTokens()) {
            this.indirizzoPR.setText(indirizzoPR.getText() + " " + tokenizer.nextToken());
        }
        this.provPR.setSelectedItem(pr.getProvincia());
        Punto3D pos = pr.getPosizione();
        this.posGeoX.setText("" + pos.getX());
        this.posGeoY.setText("" + pos.getY());
        this.posGeoZ.setText("" + pos.getZ());
        this.telefonoPR.setText(pr.getTelefono());
        int minutes = pr.getOrarioApertura().getMinutes();
        if (minutes == 0) {
            this.orarioApMinPR.setSelectedIndex(0);
        } else {
            this.orarioApMinPR.setSelectedItem(minutes);
        }
        int hours = pr.getOrarioApertura().getHours();
        if (hours < 10) {
            this.orarioAPOrePR.setSelectedItem("0" + hours);
        } else {
            this.orarioAPOrePR.setSelectedItem(hours);
        }
        this.orarioCHMinPR.setSelectedItem(pr.getOrarioChiusura().getMinutes());
        this.orarioAPOrePR.setSelectedItem(pr.getOrarioApertura().getHours());
        this.orarioCHOrePR.setSelectedItem(pr.getOrarioChiusura().getHours());
        if (edit) {
            btnModifica.setSelected(true);
        } else {
            makeEditable();
        }
    }

    /**
     * Method called by the constructor
     *
     * @return void
     */
    private void initialize() {
        this.setIconifiable(true);
        this.setBounds(new Rectangle(0, 0, 600, 540));
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setFrameIcon(new ImageIcon(getClass().getResource("/unisa/gps/eTour/gui/operatoreagenzia/images/scheda.png")));
        this.setClosable(true);
        this.setContentPane(getJContentPane());
    }

    private void makeEditable() {
        Component[] components = datiPR.getComponents();
        for (int i = 0; i < components.length; i++) {
            Component current = components[i];
            if (current instanceof JTextField) {
                JTextField textbox = (JTextField) current;
                textbox.setEditable(!textbox.isEditable());
                textbox.setBackground(Color.white);
            } else if (current instanceof JComboBox) {
                JComboBox<?> combo = (JComboBox<?>) current;
                combo.setEnabled(!combo.isEnabled());
            }
        }
        descrizionePR.setEditable(!descrizionePR.isEditable());
        pannelloTag.attivaDisattiva();
    }

    /**
     * Method which initializes a jContentPane
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
            jContentPane.add(getToolbarSchedaPR(), BorderLayout.NORTH);
        }
        return jContentPane;
    }

    /**
     * This method initializes the button (ToggleButton) the alteration
     * Data for puntoDiRistoro
     *
     * @return javax.swing.JToggleButton
     */
    private JToggleButton getBtnModifica() {
        if (btnModifica == null) {
            btnModifica = new JToggleButton();
            btnModifica.setText("Change Data");
            btnModifica.setIcon(new ImageIcon(getClass().getResource("/unisa/gps/eTour/gui/operatoreagenzia/images/modifica.png")));
            btnModifica.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    makeEditable();
                    btnSalva.setVisible(btnModifica.isSelected());
                    btnAnnulla.setVisible(btnModifica.isSelected());
                }
            });
        }
        return btnModifica;
    }

    /**
     * Method to initialize the Save button (btnSalva)
     *
     * @return javax.swing.JButton
     */
    private JButton getBtnSalva() {
        if (btnSalva == null) {
            btnSalva = new JButton();
            btnSalva.setText("Save");
            btnSalva.setIcon(new ImageIcon(getClass().getResource("/unisa/gps/eTour/gui/operatoreagenzia/images/salva.png")));
            btnSalva.setVisible(false);
        }
        return btnSalva;
    }

    /**
     * Method to initialize the Cancel button (btnAnnulla)
     *
     * @return javax.swing.JButton
     */
    private JButton getBtnAnnulla() {
        if (btnAnnulla == null) {
            btnAnnulla = new JButton();
            btnAnnulla.setText("Cancel");
            btnAnnulla.setIcon(new ImageIcon(getClass().getResource("/unisa/gps/eTour/gui/operatoreagenzia/images/annulla.png")));
            btnAnnulla.setVisible(false);
        }
        return btnAnnulla;
    }

    /**
     * Method to initialize the button for
     * Changing a comment (btnModificaCommento)
     *
     * @return javax.swing.JButton
     */
    private JButton getBtnModificaCommento() {
        if (btnModificaCommento == null) {
            btnModificaCommento = new JButton();
            btnModificaCommento.setText("Edit Comment");
            btnModificaCommento.setIcon(new ImageIcon(getClass().getResource("/unisa/gps/eTour/gui/operatoreagenzia/images/modificaCommento.png")));
            btnModificaCommento.setVisible(false);
        }
        return btnModificaCommento;
    }

    /**
     * Create and initialize a JTabbedPane
     *
     * @return javax.swing.JTabbedPane
     */
    private JTabbedPane getJTabbedPane() {
        if (jTabbedPane == null) {
            jTabbedPane = new JTabbedPane();
            jTabbedPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            jTabbedPane.addTab("Data Refreshment", new ImageIcon(getClass().getResource("/unisa/gps/eTour/gui/operatoreagenzia/images/dati.png")), getDatiPR(), null);
            jTabbedPane.addTab("MenuTuristico", new ImageIcon(getClass().getResource("/unisa/gps/etour/gui/operatoreagenzia/images/stat24.png")), null, null);
            jTabbedPane.addTab("Statistics", new ImageIcon(getClass().getResource("/unisa/gps/etour/gui/operatoreagenzia/images/stat24.png")), getStatistiche(), null);
            jTabbedPane.addTab("Feedback received", new ImageIcon(getClass().getResource("/unisa/gps/eTour/gui/operatoreagenzia/images/feedback.png")), getFeedback(), null);
        }
        return jTabbedPane;
    }

    /**
     * Method to initialize a panel (datiPR)
     *
     * @return javax.swing.JPanel
     */
    private JPanel getDatiPR() {
        if (datiPR == null) {
            datiPR = new JPanel();
            datiPR.setLayout(new GridBagLayout());
            datiPR.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.insets = new Insets(5, 5, 5, 5);
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;

            txtNome = new JLabel("Name Refreshment");
            txtIndirizzo = new JLabel("Address");
            txtCitta = new JLabel("City");
            txtLocalità = new JLabel("Location");
            txtCAP = new JLabel("CAP");
            txtProvincia = new JLabel("Province");
            txtPos = new JLabel("Geographic Position");
            txtTel = new JLabel("Phone");
            txtOraAp = new JLabel("Opening Hours");
            txtOraCh = new JLabel("Closing Time");

            datiPR.add(txtNome, gridBagConstraints);
            gridBagConstraints.gridy++;
            datiPR.add(txtIndirizzo, gridBagConstraints);
            gridBagConstraints.gridy++;
            datiPR.add(txtCitta, gridBagConstraints);
            gridBagConstraints.gridy++;
            datiPR.add(txtLocalità, gridBagConstraints);
            gridBagConstraints.gridy++;
            datiPR.add(txtCAP, gridBagConstraints);
            gridBagConstraints.gridy++;
            datiPR.add(txtProvincia, gridBagConstraints);
            gridBagConstraints.gridy++;
            datiPR.add(txtPos, gridBagConstraints);
            gridBagConstraints.gridy++;
            datiPR.add(txtTel, gridBagConstraints);
            gridBagConstraints.gridy++;
            datiPR.add(txtOraAp, gridBagConstraints);
            gridBagConstraints.gridy++;
            datiPR.add(txtOraCh, gridBagConstraints);
        }
        return datiPR;
    }

    /**
     * Method for initializing a panel (statistics)
     *
     * @return javax.swing.JPanel
     */
    private JPanel getStatistiche() {
        if (statistics == null) {
            statistics = new JPanel();
            statistics.setLayout(new GridBagLayout());
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.insets = new Insets(20, 0, 0, 0);
            gridBagConstraints.gridy = 0;

            mediaVotoPR = new JLabel("Average Vote");
            statistics.add(mediaVotoPR, gridBagConstraints);
        }
        return statistics;
    }

    /**
     * Method to initialize a panel (feedback)
     *
     * @return javax.swing.JPanel
     */
    private JPanel getFeedback() {
        if (feedback == null) {
            feedback = new JPanel();
            feedback.setLayout(new GridBagLayout());
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
            gridBagConstraints.gridx = 0;
            feedback.add(getJScrollPane2(), gridBagConstraints);
        }
        return feedback;
    }

    /**
     * Initialize a JTextField (indirizzoPR)
     *
     * @return javax.swing.JTextField
     */
    private JTextField getIndirizzoPR() {
        if (indirizzoPR == null) {
            indirizzoPR = new JTextField();
            indirizzoPR.setColumns(12);
            indirizzoPR.addActionListener(campoCompilato);
        }
        return indirizzoPR;
    }

    /**
     * Method to initialize the type field address (indirizzoPR)
     * Or via, piazza ....
     *
     * @return javax.swing.JComboBox
     */
    private JComboBox<String> getIndirizzoPR1() {
        if (indirizzoPR1 == null) {
            indirizzoPR1 = new JComboBox<>();
            indirizzoPR1.setPreferredSize(new Dimension(60, 20));
            indirizzoPR1.setMinimumSize(new Dimension(60, 25));
            indirizzoPR1.addItem("Via");
            indirizzoPR1.addItem("P.zza");
            indirizzoPR1.addItem("V.le");
            indirizzoPR1.addItem("V.co");
            indirizzoPR1.addItem("Largo");
            indirizzoPR1.addItem("Corso");
        }
        return indirizzoPR1;
    }

    /**
     * Initialize a JTextField for entering a city CittaPR
     *
     * @return javax.swing.JTextField
     */
    private JTextField getCittaPR() {
        if (cittaPR == null) {
            cittaPR = new JTextField();
            cittaPR.setColumns(12);
            cittaPR.addActionListener(campoCompilato);
        }
        return cittaPR;
    }

    /**
     * This method initializes localitaPR
     *
     * @return javax.swing.JComboBox
     */
    private JComboBox<String> getLocalitaPR() {
        if (localitaPR == null) {
            localitaPR = new JComboBox<>();
            localitaPR.setMinimumSize(new Dimension(80, 25));
            localitaPR.setPreferredSize(new Dimension(80, 20));
            localitaPR.addActionListener(campoCompilato);
        }
        return localitaPR;
    }

    /**
     * Code of refreshment. Definition capPR JTextField
     *
     * @return javax.swing.JTextField
     */
    private JTextField getCapPR() {
        if (capPR == null) {
            capPR = new JTextField();
            capPR.setColumns(8);
            capPR.addActionListener(campoCompilato);
        }
        return capPR;
    }

    /**
     * Creation JScrollPane
     *
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            jScrollPane.setViewportView(getDescrizionePR());
        }
        return jScrollPane;
    }

    /**
     * Method to create JTextArea's whole descrizionePR
     *
     * @return javax.swing.JTextArea
     */
    private JTextArea getDescrizionePR() {
        if (descrizionePR == null) {
            descrizionePR = new JTextArea();
            descrizionePR.setColumns(12);
            descrizionePR.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        }
        return descrizionePR;
    }

    /**
     * Method to create the JTextField telefonoPR
     *
     * @return javax.swing.JTextField
     */
    private JTextField getTelefonoPR() {
        if (telefonoPR == null) {
            telefonoPR = new JTextField();
            telefonoPR.setColumns(12);
            telefonoPR.addActionListener(campoCompilato);
        }
        return telefonoPR;
    }

    /**
     * method to initialize a JComboBox with the hours (orarioAPOrePR)
     *
     * @return javax.swing.JComboBox
     */
    private JComboBox<String> getOrarioAPOrePR() {
        if (orarioAPOrePR == null) {
            orarioAPOrePR = new JComboBox<>();
            orarioAPOrePR.setPreferredSize(new Dimension(40, 20));
            for (int i = 0; i < 24; i++) {
                if (i < 10) {
                    orarioAPOrePR.addItem("0" + i);
                } else {
                    orarioAPOrePR.addItem(i);
                }
            }
            orarioAPOrePR.addActionListener(campoCompilato);
        }
        return orarioAPOrePR;
    }

    /**
     * Method to initialize a JComboBox with the minutes (orarioApMinPR)
     *
     * @return javax.swing.JComboBox
     */
    private JComboBox<String> getOrarioApMinPR() {
        if (orarioApMinPR == null) {
            orarioApMinPR = new JComboBox<>();
            orarioApMinPR.setPreferredSize(new Dimension(40, 20));
            orarioApMinPR.addItem("00");
            orarioApMinPR.addItem("15");
            orarioApMinPR.addItem("30");
            orarioApMinPR.addItem("45");
            orarioApMinPR.addActionListener(campoCompilato);
        }
        return orarioApMinPR;
    }

    /**
     * Method to initialize a JComboBox with the minutes (orarioCHMinPR)
     *
     * @return javax.swing.JComboBox
     */
    private JComboBox<String> getOrarioCHMinPR() {
        if (orarioCHMinPR == null) {
            orarioCHMinPR = new JComboBox<>();
            orarioCHMinPR.setPreferredSize(new Dimension(40, 20));
            orarioCHMinPR.addItem("00");
            orarioCHMinPR.addItem("15");
            orarioCHMinPR.addItem("30");
            orarioCHMinPR.addItem("45");
            orarioCHMinPR.addActionListener(campoCompilato);
        }
        return orarioCHMinPR;
    }

    /**
     * Create and initialize a JComboBox with all the provinces (provPR)
     *
     * @return javax.swing.JTextField
     */
    private JComboBox<String> getProvPR() {
        if (provPR == null) {
            final String[] province = {"AG", "AL", "AN", "AO", "AQ", "AR", "AP", "AT", "AV", "BA", "BL", "BN", "BG", "BI", "BO", "BR", "BS", "BZ",
                    "CA", "CB", "CE", "CH", "CI", "CL", "CN", "CO", "CR", "CS", "KR", "EN", "FC", "FE", "FI", "FG", "FR", "GE", "GO", "GR", "IM", "IS", "LC",
                    "LE", "LI", "LO", "LT", "LU", "MC", "ME", "MF", "MN", "MO", "MS", "MT", "NA", "NO", "NU", "OG", "OR", "OT", "PA", "PC", "PD", "PE", "PG", "PO", "PR", "PU", "R", "RA", "RC", "RE", "RG",
                    "RI", "RM", "RN", "RO", "SA", "SI", "SO", "SP", "SS", "SV", "TA", "TE", "TN", "TP", "TR", "TS", "TV", "UD", "VA", "VB", "VC", "VE", "VI", "VR", "VS", "VT", "VV"};
            provPR = new JComboBox<>();
            for (String provincia : province) {
                provPR.addItem(provincia);
            }
            provPR.addActionListener(campoCompilato);
        }
        return provPR;
    }

    class DocumentoNumerico extends PlainDocument {
        private int limit;

        public DocumentoNumerico(int limit) {
            this.limit = limit;
        }

        /**
         * Initialization and management position
         *
         * @param pOffset
         * @param pStr
         * @param attr
         */
        public void insertString(int pOffset, String pStr, AttributeSet attr) throws BadLocationException {
            if (pStr == null) {
                return;
            }

            if ((getLength() + pStr.length()) <= limit) {
                super.insertString(pOffset, pStr, attr);
            }
        }
    }

    /**
     * Initialization of a data point of the snack (nomePR)
     *
     * @return javax.swing.JTextField
     */
    private JTextField getNomePR() {
        if (nomePR == null) {
            nomePR = new JTextField();
            nomePR.setColumns(12);
            nomePR.setPreferredSize(new Dimension(180, 20));
            nomePR.addActionListener(campoCompilato);
            nomePR.addFocusListener(validating);
            nomePR.setDocument(new DocumentoNumerico(20));
        }
        return nomePR;
    }

    /**
     * Initialize and create a panel (JPanel)
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            jPanel = new JPanel();
            jPanel.setLayout(new BorderLayout());
            jPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createEmptyBorder(), "Tag the 'Search",
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12),
                    Color.black));
            BeanTag[] test = new BeanTag[8];
            test[0] = new BeanTag(0, "castle", "really a castle");
            test[1] = new BeanTag(1, "stronghold", "really a hostel");
            test[2] = new BeanTag(2, "statue", "really a basket");
            test[3] = new BeanTag(3, "column", "really a basket");
            test[4] = new BeanTag(4, "internal", "really a basket");
            test[5] = new BeanTag(5, "external", "really a basket");
            test[6] = new BeanTag(6, "eight hundred", "really a basket");
            test[7] = new BeanTag(7, "Novecento", "really a basket");
            pannelloTag = new TagPanel(test);
            jPanel.add(pannelloTag, BorderLayout.CENTER);
        }
        return jPanel;
    }

    /**
     * Creating a JScrollPane (jScrollPane2)
     *
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane2() {
        if (jScrollPane2 == null) {
            jScrollPane2 = new JScrollPane();
            jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            jScrollPane2.setViewportView(getFeedbackTable());
        }
        return jScrollPane2;
    }

    /**
     * Create a JTable (feedbackTable)
     *
     * @return javax.swing.JTable
     */
    private JTable getFeedbackTable() {
        if (feedbackTable == null) {
            feedbackTable = new JTable();
        }
        return feedbackTable;
    }

    /**
     * Creation of a panel (statisticheMeseCorrente)
     *
     * @return javax.swing.JPanel
     */
    private JPanel getStatisticheMeseCorrente() {
        if (statisticheMeseCorrente == null) {
            statisticheMeseCorrente = new JPanel();
            statisticheMeseCorrente.setLayout(new GridBagLayout());
            statisticheMeseCorrente.setPreferredSize(new Dimension(500, 120));
        }
        return statisticheMeseCorrente;
    }

    /**
     * Creation of a panel (statisticheTotali)
     *
     * @return javax.swing.JPanel
     */
    private JPanel getStatisticheTotali() {
        if (statisticheTotali == null) {
            statisticheTotali = new JPanel();
            statisticheTotali.setLayout(new GridBagLayout());
            statisticheTotali.setPreferredSize(new Dimension(500, 120));
        }
        return statisticheTotali;
    }

    /**
     * Method for creating a toolbar (ToolbarSchedaPR)
     *
     * @return javax.swing.JToolBar
     */
    private JToolBar getToolbarSchedaPR() {
        if (toolbarSchedaPR == null) {
            toolbarSchedaPR = new JToolBar();
            toolbarSchedaPR.setFloatable(false);
            toolbarSchedaPR.add(getBtnModifica());
            toolbarSchedaPR.addSeparator();
            toolbarSchedaPR.add(getBtnSalva());
            toolbarSchedaPR.addSeparator();
            toolbarSchedaPR.add(getBtnAnnulla());
            toolbarSchedaPR.addSeparator();
            toolbarSchedaPR.add(getBtnModificaCommento());
            toolbarSchedaPR.addSeparator();
        }
        return toolbarSchedaPR;
    }

    /**
     * Method to initialize posGeoX
     * The X position of the GPS
     *
     * @return javax.swing.JTextField
     */
    private JTextField getPosGeoX() {
        if (posGeoX == null) {
            posGeoX = new JTextField();
        }
        return posGeoX;
    }

    /**
     * Method to initialize posGeoY
     * The Y position of the GPS
     *
     * @return javax.swing.JTextField
     */
    private JTextField getPosGeoY() {
        if (posGeoY == null) {
            posGeoY = new JTextField();
        }
        return posGeoY;
    }

    /**
     * Method to initialize posGeoZ
     * The Z position of the GPS
     *
     * @return javax.swing.JTextField
     */
    private JTextField getPosGeoZ() {
        if (posGeoZ == null) {
            posGeoZ = new JTextField();
        }
        return posGeoZ;
    }

    /**
     * Method to initialize a JComboBox with the hours (orarioCHOrePR)
     *
     * @return javax.swing.JComboBox
     */
    private JComboBox<String> getOrarioCHOrePR() {
        if (orarioCHOrePR == null) {
            orarioCHOrePR = new JComboBox<>();
            orarioCHOrePR.setPreferredSize(new Dimension(40, 20));
        }
        return orarioCHOrePR;
    }
}