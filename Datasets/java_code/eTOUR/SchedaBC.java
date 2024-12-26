package unisa.gps.etour.gui.operatoreagenzia;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;
import javax.swing.border.*;
import unisa.gps.etour.bean.BeanBeneCulturale;
import unisa.gps.etour.bean.BeanTag;
import unisa.gps.etour.bean.BeanVisitaBC;
import unisa.gps.etour.control.GestioneBeniCulturali.IGestioneBeniCulturaliAgenzia;
import unisa.gps.etour.control.GestioneTag.IGestioneTagComune;
import unisa.gps.etour.gui.operatoreagenzia.document.LimitedDocument;
import unisa.gps.etour.gui.operatoreagenzia.document.NumericDocument;
import unisa.gps.etour.gui.operatoreagenzia.document.OnlyCharactersDocument;
import unisa.gps.etour.gui.operatoreagenzia.tables.FeedBackTableModel;
import unisa.gps.etour.gui.operatoreagenzia.tables.MediaVotiRenderer;
import unisa.gps.etour.util.Punto3D;

/**
 * Class that models the interface for viewing the card,
 * Modify the data and the insertion of a new cultural object.
 *
 * @Version 1.0
 * @Author Mario Gallo
 *
 * © 2007 eTour Project - Copyright by DMI SE @ SA Lab --
 * University of Salerno
 */
public class SchedaBC extends JInternalFrame implements IScheda {

    private static final String[] txts = {
        "Name", "Address", "City", "Location", "CAP", 
        "Province", "Geographical Location", "Phone", 
        "Opening Hours", "Closing Time", 
        "Closing Date", "Ticket price", "Description"
    };
    
    private static final String[] help = {
        "Enter the name of cultural property.",
        "Enter the address where is located the cultural property.",
        "Enter the city where is located the cultural property.",
        "Enter the location of membership of a cultural object.",
        "Enter your zip code, the area where the cultural object is located.",
        "Select the province belonging to the cultural property.",
        "Incorporating three dimensional coordinates for the location of cultural heritage.",
        "Enter the phone for delivery of the cultural management office.",
        "Select the time of public opening of the cultural property.",
        "Select the closing time for the public of cultural property.",
        "Select the weekly closing day.",
        "Give the ticket price of admission to cultural property.",
        "<html> Enter a full and comprehensive description for the cultural property. <br> Please note that this" +
        " description will be used as a source of keywords <br> in research by tourists </ html>",
        "<html> Select the search tags for cultural property. <br> search tags allow tourists to seek" +
        " The sites with the features of interest. </ Html>"
    };
    
    private JPanel jContentPane = null;
    private JToolBar toolbarSchedaBC = null;
    private JToggleButton btnModifica = null;
    private JButton btnSalva = null;
    private JButton btnAnnulla = null;
    private JButton btnModificaCommento = null;
    private JTabbedPane jTabbedPane = null;
    private JPanel statistics = null;
    private JPanel feedback = null;
    private JTextField address2 = null;
    private JComboBox<String> address1 = null;
    private JTextField cittaBC = null;
    private JTextField localitaBC = null;
    private JTextField capBC = null;
    private JTextField posGeoX = null;
    private JScrollPane jScrollPane = null;
    private JTextArea descrizioneBC = null;
    private JTextField telefonoBC = null;
    private JComboBox<String> oreAP = null;
    private JComboBox<String> minAP = null;
    private TagPanel pannelloTag;
    private JTextField costoBC = null;
    private JComboBox<String> oreCH = null;
    private JComboBox<String> minCH = null;
    private JComboBox<String> provBC = null;
    private JPanel datiBC = null;
    private JTextField nomeBC = null;
    private JScrollPane scrollPaneFeedback = null;
    private JTable tableFeedback = null;
    private JLabel txtNomeBene = null;
    private JLabel mediaVotoBC = null;
    private JPanel statMeseCorrente = null;
    private JPanel statTotali = null;
    private JTextField posGeoY = null;
    private JTextField posGeoZ = null;
    private Vector<JLabel> suggestions;
    private BeanBeneCulturale bc;
    private JComboBox<String> giornoChiusura;
    private JLabel[] statMeseC;
    private JLabel[] statt;
    private Beniculturali parent;
    private FeedBackTableModel feedbackModel;
    protected IGestioneTagComune tags;
    protected IGestioneBeniCulturaliAgenzia gestioneBC;
    private ArrayList<Integer> idTag = null;

    /**
     * The default constructor for inclusion of the interface model
     * A new cultural object.
     */
    public SchedaBC(Beniculturali pParent) {
        super("New Cultural Heritage");
        setFrameIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "nuovoBC.png")));
        setClosable(true);
        setResizable(false);
        setIconifiable(true);
        setSize(600, 560);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        suggestions = new Vector<>();
        parent = pParent;
        bc = null;

        // Initialize the content pane
        jContentPane = new JPanel();
        jContentPane.setLayout(new BorderLayout());
        jContentPane.add(getToolbarSchedaBC(), BorderLayout.CENTER);
        jTabbedPane = new JTabbedPane();
        jTabbedPane.addTab("Data Cultural Heritage", new ImageIcon(getClass().getResource(Home.URL_IMAGES + "dati.png")), getDatiBCForm(), null);
        jContentPane.add(jTabbedPane, BorderLayout.CENTER);
        setContentPane(jContentPane);

        // Dialog closure to close the entry window.
        addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameOpened(InternalFrameEvent pEvent) {
                JInternalFrame frame = (JInternalFrame) pEvent.getInternalFrame();
                // Setting up of remote objects for the management of cultural heritage.
                try {
                    Registry reg = LocateRegistry.getRegistry(Home.HOST);
                    tags = (IGestioneTagComune) reg.lookup("GestioneTagComune");
                    gestioneBC = (IGestioneBeniCulturaliAgenzia) reg.lookup("GestioneBeniCulturaliAgenzia");
                    // Load data.
                    caricaTags();
                } catch (Exception ex) {
                    JLabel error = new JLabel("<html> <h2> Unable to communicate with the server eTour. </h2>"
                            + "<h3> <u> Card for entering a new cultural asset will be closed. </u> </h3>"
                            + "<p> <b> Possible Causes: </b>"
                            + "<ul> <li> No connection to the network. </li>"
                            + "<li> Server inactive. </li>"
                            + "<li> Server clogged. </li> </ul>"
                            + "<p> Please try again later. </p>"
                            + "<p> If the error persists, please contact technical support. </p>"
                            + "<p> We apologize for the inconvenience. </html>");
                    ImageIcon err = new ImageIcon(getClass().getResource(Home.URL_IMAGES + "error48.png"));
                    JOptionPane.showMessageDialog(frame, error, "Error!", JOptionPane.ERROR_MESSAGE, err);
                    frame.dispose();
                }
            }

            public void internalFrameClosing(InternalFrameEvent pEvent) {
                JPanel root = new JPanel(new BorderLayout());
                JLabel message = new JLabel("Are you sure you want to cancel the creation of a new cultural asset?");
                message.setFont(new Font("Dialog", Font.BOLD, 14));
                JLabel alert = new JLabel("Warning! Unsaved data will be lost.", SwingConstants.CENTER);
                alert.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "warning16.png")));
                root.add(message, BorderLayout.NORTH);
                root.add(alert, BorderLayout.CENTER);
                String[] options = {"Close", "Cancel"};
                int choice = JOptionPane.showInternalOptionDialog(jContentPane, root, "Confirm closure", 
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, getFrameIcon(), options, options[1]);
                if (choice == JOptionPane.OK_OPTION) {
                    parent.closeScheda((SchedaBC) pEvent.getInternalFrame());
                }
            }
        });

        // Initialize button
        btnModifica.setVisible(false);
        btnSalva.setVisible(true);
        btnAnnulla.setVisible(true);
        btnAnnulla.setText("Clear");
    }

    /**
     * This interface models the manufacturer regarding modification of data and
     * Display of the tab of a cultural object.
     *
     * @param pbc - the bean contains the data of
     * Selected cultural.
     * @param pModifica <ul> <li> true - the fields will be editable, and then you are
     * To amend the data of a cultural object. <li> False - the fields will not be
     * Edit, and then you are viewing the tab of a cultural object. </ul>
     */
    public SchedaBC(Beniculturali pParent, BeanBeneCulturale pbc, boolean pModifica) {
        super();
        setFrameIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "scheda.png")));
        setClosable(true);
        setResizable(false);
        setIconifiable(true);
        setSize(600, 560);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        // Initialize instance variables
        bc = pbc;
        this.parent = pParent;
        suggestions = new Vector<>();
        initializeSchedaBC();

        if (pModifica) { // Are we changing the cultural property.
            btnModifica.setSelected(true);
            btnSalva.setVisible(true);
            btnAnnulla.setVisible(true);
            btnAnnulla.setText("Reset");
        } else { // We're viewing the tab of a cultural object.
            mostraNascondiSuggerimenti();
            attivaDisattivaEdit();
        }
    }

    /**
     * This method returns the id of the cultural property for which you are viewing the
     * Contact or changing data.
     *
     * @return int - the id of the cultural property.
     */
    public int getId() {
        if (bc == null) {
            return -1;
        }
        return bc.getId();
    }

    /**
     *
     * This method initializes the interface for display board
     * A cultural object.
     *
     * @return void
     */
    private void initializeSchedaBC() {
        setTitle(bc.getNome());

        // Dialog closed frame
        addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameOpened(InternalFrameEvent pEvent) {
                JInternalFrame frame = (JInternalFrame) pEvent.getInternalFrame();
                // Setting up of remote objects for the management of cultural heritage.
                try {
                    Registry reg = LocateRegistry.getRegistry(Home.HOST);
                    tags = (IGestioneTagComune) reg.lookup("GestioneTagComune");
                    gestioneBC = (IGestioneBeniCulturaliAgenzia) reg.lookup("GestioneBeniCulturaliAgenzia");
                    // Load data.
                    caricaTags();
                    caricaStatistiche();
                } catch (Exception ex) {
                    JLabel error = new JLabel("<html> <h2> Unable to communicate with the server eTour. </h2>"
                            + "<h3> <u> The board of the cultural inquiry will be closed. </u> </h3>"
                            + "<p> <b> Possible Causes: </b>"
                            + "<ul> <li> No connection to the network. </li>"
                            + "<li> Server inactive. </li>"
                            + "<li> Server clogged. </li> </ul>"
                            + "<p> Please try again later. </p>"
                            + "<p> If the error persists, please contact technical support. </p>"
                            + "<p> We apologize for the inconvenience. </html>");
                    ImageIcon err = new ImageIcon(getClass().getResource(Home.URL_IMAGES + "error48.png"));
                    JOptionPane.showMessageDialog(frame, error, "Error!", JOptionPane.ERROR_MESSAGE, err);
                    frame.dispose();
                }
            }

            public void internalFrameClosing(InternalFrameEvent pEvent) {
                // If you are an amendment asks for confirmation.
                if (btnModifica.isSelected()) {
                    JPanel root = new JPanel(new BorderLayout());
                    JLabel message = new JLabel("Are you sure you want to close the tab of this cultural asset?");
                    message.setFont(new Font("Dialog", Font.BOLD, 14));
                    JLabel alert = new JLabel("Warning! Unsaved data will be lost.", SwingConstants.CENTER);
                    alert.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "warning16.png")));
                    root.add(message, BorderLayout.NORTH);
                    root.add(alert, BorderLayout.CENTER);
                    String[] options = {"Close", "Cancel"};
                    int choice = JOptionPane.showInternalOptionDialog(jContentPane, root,
                            "Confirm closing tab Cultural Heritage: " + bc.getNome(),
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, getFrameIcon(), options, options[1]);
                    if (choice == JOptionPane.OK_OPTION) {
                        parent.closeScheda((SchedaBC) pEvent.getInternalFrame());
                    }
                } else {
                    // Otherwise directly closes the window.
                    parent.closeScheda((SchedaBC) pEvent.getInternalFrame());
                }
            }
        });

        // Initialize the content pane.
        jContentPane = new JPanel();
        jContentPane.setLayout(new BorderLayout());
        jContentPane.add(getToolbarSchedaBC(), BorderLayout.CENTER);
        jTabbedPane = new JTabbedPane();
        jTabbedPane.addTab("Data Cultural Heritage", new ImageIcon(getClass().getResource(Home.URL_IMAGES + "dati.png")), getDatiBCForm(), null);
        JScrollPane newScrollPane = new JScrollPane(getStatistiche());
        newScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jTabbedPane.addTab("Statistics", new ImageIcon(getClass().getResource(Home.URL_IMAGES + "stat24.png")), newScrollPane, null);
        jTabbedPane.addTab("Feedback received", new ImageIcon(getClass().getResource(Home.URL_IMAGES + "feedback.png")), getFeedback(), null);
        jContentPane.add(jTabbedPane, BorderLayout.CENTER);
        setContentPane(jContentPane);
        jTabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent pChange) {
                // Data cultural
                if (jTabbedPane.getSelectedIndex() == 0) {
                    toolbarSchedaBC.setVisible(true);
                    btnModifica.setVisible(true);
                    if (btnModifica.isSelected()) {
                        btnSalva.setVisible(true);
                        btnAnnulla.setVisible(true);
                    }
                    btnModificaCommento.setVisible(false);
                } else if (jTabbedPane.getSelectedIndex() == 1) { // Statistics
                    toolbarSchedaBC.setVisible(false);
                } else { // Feedback received
                    if (btnModifica.isSelected()) {
                        btnSalva.setVisible(false);
                        btnAnnulla.setVisible(false);
                    }
                    toolbarSchedaBC.setVisible(true);
                    btnModificaCommento.setVisible(true);
                    btnModifica.setVisible(false);
                }
            }
        });

        // Load the data of the cultural and statistics.
        caricaDatiForm();
    }

    /**
     *
     * This method loads the data supplied to the constructor of the cultural
     * In the form.
     *
     */
    private void caricaDatiForm() {
        nomeBC.setText(bc.getNome());
        capBC.setText(bc.getCap());
        cittaBC.setText(bc.getCitta());
        costoBC.setText("" + bc.getCostoBiglietto());
        descrizioneBC.setText(bc.getDescrizione());
        StringTokenizer tokenizer = new StringTokenizer(bc.getVia());
        String string = tokenizer.nextToken();
        address1.setSelectedItem(string);
        address2.setText(bc.getVia().substring(string.length()));
        provBC.setSelectedItem(bc.getProvincia());
        Punto3D pos = bc.getPosizione();
        posGeoX.setText("" + pos.getLatitudine());
        posGeoY.setText("" + pos.getLongitudine());
        posGeoZ.setText("" + pos.getAltitudine());
        telefonoBC.setText(bc.getTelefono());
        int minutes = bc.getOrarioApertura().getMinutes();
        minAP.setSelectedIndex(minutes == 0 ? 0 : minutes);
        int hours = bc.getOrarioApertura().getHours();
        oreAP.setSelectedItem(hours < 10 ? "0" + hours : hours);
        minutes = bc.getOrarioChiusura().getMinutes();
        minCH.setSelectedIndex(minutes == 0 ? 0 : minutes);
        hours = bc.getOrarioChiusura().getHours();
        oreCH.setSelectedItem(hours < 10 ? "0" + hours : hours);
    }

    /**
     *
     * This method loads the statistics provided cultural
     * Input to the constructor of the class.
     *
     */
    private void caricaStatistiche() {
        txtNomeBene.setText(bc.getNome());
        double rating = bc.getMediaVoti();
        if (rating >= 4) {
            mediaVotoBC.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "stella5.gif")));
        } else if (rating < 4 && rating >= 3) {
            mediaVotoBC.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "stella4.gif")));
        } else if (rating < 3 && rating >= 2) {
            mediaVotoBC.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "stella3.gif")));
        } else if (rating < 2 && rating >= 1) {
            mediaVotoBC.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "stella2.gif")));
        } else {
            mediaVotoBC.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "stella1.gif")));
        }

        ArrayList<Integer> stats = null;
        try {
            stats = gestioneBC.ottieniStatisticheBeneCulturale(bc.getId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        statMeseC[0].setText("" + stats.get(0));
        statMeseC[1].setText("" + stats.get(1));
        statMeseC[2].setText("" + stats.get(2));
        statMeseC[3].setText("" + stats.get(3));
        statMeseC[4].setText("" + stats.get(4));
        statMeseC[5].setText("" + stats.get(5));
        statt[0].setText("142");
        statt[1].setText("112");
        statt[2].setText("132");
        statt[3].setText("212");
        statt[4].setText("152");
        statt[5].setText("748");
    }

    /**
     *
     * This method shows or hides the label next to the suggestions
     * Of the form.
     *
     */
    private void mostraNascondiSuggerimenti() {
        Iterator<JLabel> s = suggestions.iterator();
        while (s.hasNext()) {
            JLabel current = s.next();
            current.setVisible(!current.isVisible());
        }
    }

    /**
     *
     * This method makes the form editable or not.
     *
     */
    private void attivaDisattivaEdit() {
        Component[] components = datiBC.getComponents();
        for (Component current : components) {
            if (current instanceof JTextField) {
                JTextField textbox = (JTextField) current;
                textbox.setEditable(!textbox.isEditable());
                textbox.setBackground(Color.white);
            } else if (current instanceof JComboBox) {
                JComboBox combo = (JComboBox) current;
                combo.setEnabled(!combo.isEnabled());
            }
        }
        descrizioneBC.setEditable(!descrizioneBC.isEditable());
        pannelloTag.attivaDisattiva();
    }

    /**
     * This method initializes the toolbar tab of a cultural object.
     *
     * @return javax.swing.JToolBar - the toolbar.
     */
    private JToolBar getToolbarSchedaBC() {
        if (toolbarSchedaBC == null) {
            toolbarSchedaBC = new JToolBar();
            toolbarSchedaBC.setFloatable(false);
            toolbarSchedaBC.add(getBtnModifica());
            toolbarSchedaBC.addSeparator();
            toolbarSchedaBC.add(getBtnSalva());
            toolbarSchedaBC.addSeparator();
            toolbarSchedaBC.add(getBtnAnnulla());
            toolbarSchedaBC.addSeparator();
            if (bc != null) {
                toolbarSchedaBC.add(getBtnModificaCommento());
                toolbarSchedaBC.addSeparator();
            }
        }
        return toolbarSchedaBC;
    }

    /**
     * This method initializes the button to modify data of good
     * Cultural.
     *
     * @return javax.swing.JToggleButton - the button for the change.
     */
    private JToggleButton getBtnModifica() {
        if (btnModifica == null) {
            btnModifica = new JToggleButton();
            btnModifica.setText("Change Data");
            btnModifica.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "ModificaBC32.png")));
            btnModifica.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnModifica.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    mostraNascondiSuggerimenti();
                    attivaDisattivaEdit();
                    btnSalva.setVisible(btnModifica.isSelected());
                    btnAnnulla.setVisible(btnModifica.isSelected());
                }
            });
        }
        return btnModifica;
    }

    /**
     * This method initializes btnSalva
     *
     * @return javax.swing.JButton
     */
    private JButton getBtnSalva() {
        if (btnSalva == null) {
            btnSalva = new JButton();
            btnSalva.setText("Save");
            btnSalva.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "salva.png")));
            btnSalva.setVisible(false);
            btnSalva.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnSalva.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent pEvent) {
                    if (bc == null) {
                        bc = riversaDatiNelBean();
                        try {
                            gestioneBC.inserisciBeneCulturale(bc);
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        }
                    } else { // Construction of the dialog for confirmation of the change
                        JPanel root = new JPanel(new BorderLayout());
                        JLabel message = new JLabel("Updating the data of the cultural " + bc.getNome() + " with Data form?");
                        message.setFont(new Font("Dialog", Font.BOLD, 14));
                        JLabel alert = new JLabel("The previous data can not be more recovered.", SwingConstants.CENTER);
                        alert.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "warning16.png")));
                        root.add(message, BorderLayout.NORTH);
                        root.add(alert, BorderLayout.CENTER);
                        String[] options = {"Edit", "Cancel"};
                        // The dialog screen appears
                        int choice = JOptionPane.showInternalOptionDialog(jContentPane, root,
                                "Edit Data Confirm Cultural Heritage", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource(Home.URL_IMAGES + "ModificaBC48.png")), options, options[1]);
                        // If you chose to confirm the change
                        if (choice == JOptionPane.YES_OPTION) {
                            bc = riversaDatiNelBean();
                            caricaDatiForm();
                            attivaDisattivaEdit();
                            btnSalva.setVisible(false);
                            btnAnnulla.setVisible(false);
                            btnModifica.setSelected(false);
                            mostraNascondiSuggerimenti();
                            parent.updateTableModel(bc);
                            JOptionPane.showInternalMessageDialog(jContentPane, "The data of the cultural object has been updated successfully.", "Data cultural change!", JOptionPane.OK_OPTION,
                                    new ImageIcon(getClass().getResource(Home.URL_IMAGES + "ok32.png")));
                        }
                    }
                }
            });
        }
        return btnSalva;
    }

    /**
     * This method initializes the button to clear the form (well again
     * Culture) or reload the data of the cultural (change data).
     *
     * @return javax.swing.JButton - the button above.
     */
    private JButton getBtnAnnulla() {
        if (btnAnnulla == null) {
            btnAnnulla = new JButton();
            btnAnnulla.setText("Cancel");
            btnAnnulla.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "Annulla32.png")));
            btnAnnulla.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnAnnulla.setVisible(false);
            btnAnnulla.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    if (bc == null) {
                        Component[] components = datiBC.getComponents();
                        for (Component current : components) {
                            if (current instanceof JTextComponent) {
                                ((JTextComponent) current).setText("");
                            } else if (current instanceof JComboBox) {
                                JComboBox combo = (JComboBox) current;
                                combo.setSelectedIndex(-1);
                            }
                        }
                        pannelloTag.azzera();
                        descrizioneBC.setText("");
                    } else {
                        caricaDatiForm();
                    }
                }
            });
        }
        return btnAnnulla;
    }

    /**
     * This method initializes the button to edit a comment.
     *
     * @return javax.swing.JButton - the button to edit a comment.
     */
    private JButton getBtnModificaCommento() {
        if (btnModificaCommento == null) {
            btnModificaCommento = new JButton();
            btnModificaCommento.setText("Edit Comment");
            btnModificaCommento.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "modificaCommento.png")));
            btnModificaCommento.setVisible(false);
            btnModificaCommento.setEnabled(false);
            btnModificaCommento.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent pEvent) {
                    int selectedRow = tableFeedback.getSelectedRow();
                    String nuovoCommento = (String) JOptionPane.showInternalInputDialog(jContentPane, "Changing the selected comment:", "Edit Comment", JOptionPane.QUESTION_MESSAGE,
                            new ImageIcon(getClass().getResource(Home.URL_IMAGES + "modificaCommento.png")), null, feedbackModel.getValueAt(selectedRow, 1));
                    if (nuovoCommento != null) {
                        feedbackModel.modificaCommento(nuovoCommento, selectedRow);
                    }
                }
            });
        }
        return btnModificaCommento;
    }

    /**
     * This method initializes the format for the data of a cultural object.
     *
     * @return javax.swing.JPanel - the form for the data.
     */
    private JPanel getDatiBCForm() {
        if (datiBC == null) {
            datiBC = new JPanel(null);
            datiBC.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));

            // Creation Tips
            for (int i = 0; i < help.length; i++) {
                JLabel newLabel = new JLabel();
                newLabel.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "Info16.png")));
                newLabel.setBounds(145, 8 + 30 * i, 24, 24);
                newLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                newLabel.setToolTipText(help[i]);
                suggestions.add(newLabel);
                datiBC.add(newLabel);
                if (i == help.length - 1) {
                    newLabel.setBounds(400, 155, 24, 24);
                }
            }

            for (int i = 0; i < txts.length; i++) {
                JLabel newLabel = new JLabel(txts[i], SwingConstants.RIGHT);
                newLabel.setBounds(25, 10 + 30 * i, 120, 16);
                newLabel.repaint();
                datiBC.add(newLabel, null);
            }
            // Name of Cultural Heritage
            nomeBC = new JTextField();
            nomeBC.setColumns(12);
            nomeBC.setDocument(new LimitedDocument(30));
            nomeBC.setBounds(185, 10, 136, 20);
            nomeBC.setName("Name Cultural Heritage");
            datiBC.add(nomeBC, null);

            // Address
            address2 = new JTextField();
            address2.setBounds(270, 40, 136, 20);
            address2.setDocument(new LimitedDocument(30));
            address1 = new JComboBox<>(address);
            address1.setSelectedIndex(-1);
            address1.setBounds(185, 40, 60, 20);
            address2.setName("Address");
            datiBC.add(address2, null);
            datiBC.add(address1, null);

            // City
            cittaBC = new JTextField();
            cittaBC.setColumns(12);
            cittaBC.setBounds(185, 70, 136, 20);
            cittaBC.setName("City");
            cittaBC.setDocument(new OnlyCharactersDocument(25));
            datiBC.add(cittaBC);

            // Location
            localitaBC = new JTextField();
            localitaBC.setBounds(185, 100, 136, 20);
            localitaBC.setName("Locality");
            localitaBC.setDocument(new OnlyCharactersDocument(25));
            datiBC.add(localitaBC, null);

            // CAP
            capBC = new JTextField();
            capBC.setColumns(8);
            capBC.setBounds(185, 130, 92, 20);
            capBC.setDocument(new NumericDocument(5));
            datiBC.add(capBC, null);

            // Geographical Location
            JLabel txtX = new JLabel("X");
            JLabel txtY = new JLabel("Y");
            JLabel txtZ = new JLabel("Z");
            Font fonts = new Font("Dialog", Font.BOLD, 14);
            txtX.setFont(fonts);
            txtY.setFont(fonts);
            txtZ.setFont(fonts);
            txtZ.setBounds(365, 190, 10, 22);
            txtY.setBounds(295, 190, 10, 22);
            txtX.setBounds(227, 190, 14, 20);
            posGeoX = new JTextField(12);
            posGeoX.setBounds(185, 190, 40, 20);
            posGeoY = new JTextField(12);
            posGeoY.setBounds(255, 190, 40, 20);
            posGeoZ = new JTextField(12);
            posGeoZ.setBounds(325, 190, 40, 20);
            posGeoX.setName("X-coordinate");
            posGeoY.setName("Y coordinate");
            posGeoZ.setName("Z coordinate");
            datiBC.add(txtX, null);
            datiBC.add(txtY, null);
            datiBC.add(txtZ, null);
            datiBC.add(posGeoX, null);
            datiBC.add(posGeoY, null);
            datiBC.add(posGeoZ, null);

            // State
            provBC = new JComboBox<>(province);
            provBC.setSelectedIndex(-1);
            provBC.setBounds(185, 160, 50, 20);
            datiBC.add(provBC, null);

            // Description
            descrizioneBC = new JTextArea();
            descrizioneBC.setCursor(new Cursor(Cursor.TEXT_CURSOR));
            descrizioneBC.setWrapStyleWord(true);
            descrizioneBC.setLineWrap(true);
            jScrollPane = new JScrollPane(descrizioneBC);
            jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            jScrollPane.setBounds(new Rectangle(185, 370, 395, 69));
            descrizioneBC.setName("Description");
            datiBC.add(jScrollPane, null);

            // Phone
            telefonoBC = new JTextField(12);
            telefonoBC.setBounds(185, 220, 136, 20);
            telefonoBC.setDocument(new NumericDocument(10));
            telefonoBC.setName("Phone");
            datiBC.add(telefonoBC, null);

            // Opening
            oreAP = new JComboBox<>();
            oreAP.setBounds(185, 250, 40, 20);
            oreCH = new JComboBox<>();
            oreCH.setBounds(185, 280, 40, 20);
            for (int i = 0; i < 24; i++) {
                if (i < 10) {
                    oreCH.addItem("0" + i);
                    oreAP.addItem("0" + i);
                } else {
                    oreAP.addItem(i);
                    oreCH.addItem(i);
                }
            }
            minAP = new JComboBox<>();
            minAP.setBounds(241, 250, 40, 20);
            minAP.addItem("00");
            minAP.addItem("15");
            minAP.addItem("30");
            minAP.addItem("45");
            minCH = new JComboBox<>();
            minCH.setBounds(241, 280, 40, 20);
            minCH.addItem("00");
            minCH.addItem("15");
            minCH.addItem("30");
            minCH.addItem("45");
            minAP.setSelectedIndex(0);
            oreAP.setSelectedIndex(-1);
            minCH.setSelectedIndex(0);
            oreCH.setSelectedIndex(-1);
            Font newFont = new Font("Dialog", Font.BOLD, 18);
            JLabel punto1 = new JLabel(":");
            punto1.setBounds(230, 245, 10, 24);
            punto1.setFont(newFont);
            JLabel punto2 = new JLabel(":");
            punto2.setBounds(230, 275, 10, 24);
            punto2.setFont(newFont);
            datiBC.add(oreAP, null);
            datiBC.add(minAP, null);
            datiBC.add(minCH, null);
            datiBC.add(oreCH, null);
            datiBC.add(punto1, null);
            datiBC.add(punto2, null);

            // Closed
            String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
            giornoChiusura = new JComboBox<>(days);
            giornoChiusura.setBounds(185, 310, 96, 20);
            giornoChiusura.setSelectedIndex(-1);
            datiBC.add(giornoChiusura, null);

            // Cost
            costoBC = new JTextField();
            costoBC.setColumns(8);
            costoBC.setBounds(185, 340, 40, 20);
            JLabel euro = new JLabel("Euro");
            euro.setBounds(230, 340, 30, 16);
            datiBC.add(costoBC, null);
            datiBC.add(euro, null);

            // PannelloTag
            pannelloTag = new TagPanel();
            pannelloTag.setBounds(405, 180, 180, 170);
            JLabel txtTag = new JLabel("Search Tags");
            txtTag.setBounds(420, 150, 140, 30);
            datiBC.add(txtTag, null);
            datiBC.add(pannelloTag, null);
        }
        return datiBC;
    }

    /**
     * This method initializes the statistics of a container panel
     * Cultural.
     *
     * @return javax.swing.JPanel - the panel statistics.
     *
     */
    private JPanel getStatistiche() {
        if (statistics == null) {
            statistics = new JPanel(new GridBagLayout());
            GridBagConstraints g = new GridBagConstraints();
            g.gridx = 0;
            g.gridy = 0;
            g.insets = new Insets(5, 5, 5, 5);
            g.anchor = GridBagConstraints.WEST;
            JLabel stat = new JLabel("Statistics");
            Font newFonts = new Font("Dialog", Font.BOLD, 18);
            stat.setFont(newFonts);
            statistics.add(stat, g);
            g.gridx = 1;
            txtNomeBene = new JLabel();
            txtNomeBene.setFont(newFonts);
            statistics.add(txtNomeBene, g);
            g.gridx = 2;
            mediaVotoBC = new JLabel();
            statistics.add(mediaVotoBC, g);
            g.gridwidth = 3;
            g.gridx = 0;
            g.anchor = GridBagConstraints.CENTER;
            g.gridy = 1;
            statistics.add(getStatMeseCorrente(), g);
            g.gridy = 2;
            statistics.add(getStatTotali(), g);
        }
        return statistics;
    }

    /**
     * This method initializes the panel to display feedback
     * Received from a cultural object.
     *
     * @return javax.swing.JPanel - the panel of feedback.
     *
     */
    private JPanel getFeedback() {
        if (feedback == null) {
            feedback = new JPanel();
            feedback.setLayout(new BorderLayout());
            feedbackModel = new FeedBackTableModel();
            tableFeedback = new JTable(feedbackModel);
            TableColumn aColumn = tableFeedback.getColumnModel().getColumn(0);
            // Rating
            aColumn.setPreferredWidth(80);
            aColumn.setCellRenderer(new MediaVotiRenderer());
            // Comment
            aColumn = tableFeedback.getColumnModel().getColumn(1);
            aColumn.setPreferredWidth(260);
            // Release Date
            aColumn = tableFeedback.getColumnModel().getColumn(2);
            aColumn.setPreferredWidth(80);
            // Username
            aColumn = tableFeedback.getColumnModel().getColumn(3);
            aColumn.setPreferredWidth(80);
            tableFeedback.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            tableFeedback.setColumnSelectionAllowed(false);
            ListSelectionModel selectionModel = tableFeedback.getSelectionModel();
            selectionModel.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent event) {
                    btnModificaCommento.setEnabled(tableFeedback.getSelectedRow() != -1);
                }
            });
            scrollPaneFeedback = new JScrollPane(tableFeedback);
            scrollPaneFeedback.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            feedback.add(scrollPaneFeedback, BorderLayout.CENTER);
        }
        return feedback;
    }

    /**
     * This method initializes the panel of statistics for the current month.
     *
     * @return javax.swing.JPanel - the panel of statistics for the current month.
     *
     */
    private JPanel getStatMeseCorrente() {
        if (statMeseCorrente == null) {
            statMeseCorrente = new JPanel();
            statMeseCorrente.setLayout(new GridBagLayout());
            statMeseCorrente.setPreferredSize(new Dimension(500, 280));
            statMeseCorrente.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(51, 102, 255), 3),
                "Statistics Current Month", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(0, 102, 204)));
            statMeseCorrente.setBackground(Color.white);
            statMeseC = new JLabel[6];
            GridBagConstraints g = new GridBagConstraints();
            g.gridx = 0;
            g.gridy = 0;
            g.gridwidth = 3;
            g.insets = new Insets(5, 5, 5, 8);
            g.anchor = GridBagConstraints.WEST;
            statMeseCorrente.add(new JLabel("Details votes received this month:"), g);
            g.anchor = GridBagConstraints.CENTER;
            g.gridwidth = 1;
            Font newFonts = new Font("Dialog", Font.BOLD, 16);
            for (int i = 5; i >= 1; i--) {
                int gridX = g.gridx;
                g.gridy++;
                JLabel aLabel = new JLabel(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "star" + i + ".gif")), JLabel.CENTER);
                statMeseCorrente.add(aLabel, g);
                g.gridx++;
                statMeseCorrente.add(new JLabel("=="), g);
                g.gridx++;
                g.anchor = GridBagConstraints.EAST;
                statMeseC[i - 1] = new JLabel();
                statMeseC[i - 1].setFont(newFonts);
                statMeseCorrente.add(statMeseC[i - 1], g);
                g.gridx = gridX;
                g.anchor = GridBagConstraints.CENTER;
            }
            g.gridy = 6;
            g.anchor = GridBagConstraints.WEST;
            g.gridwidth = 2;
            g.gridx = 0;
            statMeseCorrente.add(new JLabel("Number of ratings released this month:"), g);
            statMeseC[5] = new JLabel();
            statMeseC[5].setFont(new Font("Dialog", Font.BOLD, 18));
            g.gridx = 2;
            g.gridwidth = 1;
            statMeseCorrente.add(statMeseC[5], g);
        }
        return statMeseCorrente;
    }

    /**
     * This method initializes the panel on the total statistics
     * The cultural property.
     *
     * @return javax.swing.JPanel - the panel statistics totals.
     *
     */
    private JPanel getStatTotali() {
        if (statTotali == null) {
            statTotali = new JPanel();
            statTotali.setLayout(new GridBagLayout());
            statTotali.setPreferredSize(new Dimension(500, 280));
            statTotali.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(51, 102, 255), 3),
                "Statistics Total", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(0, 102, 204)));
            statTotali.setBackground(Color.white);
            statt = new JLabel[6];
            GridBagConstraints g = new GridBagConstraints();
            g.gridx = 0;
            g.gridy = 0;
            g.gridwidth = 3;
            g.insets = new Insets(5, 5, 5, 8);
            g.anchor = GridBagConstraints.WEST;
            statTotali.add(new JLabel("Details votes received this month:"), g);
            g.anchor = GridBagConstraints.CENTER;
            g.gridwidth = 1;
            Font newFonts = new Font("Dialog", Font.BOLD, 16);
            for (int i = 5; i >= 1; i--) {
                int gridX = g.gridx;
                g.gridy++;
                JLabel aLabel = new JLabel(new ImageIcon(getClass().getResource("/unisa/gps/eTour/gui/images/star" + i + ".gif")), JLabel.CENTER);
                statTotali.add(aLabel, g);
                g.gridx++;
                statTotali.add(new JLabel("=="), g);
                g.gridx++;
                g.anchor = GridBagConstraints.EAST;
                statt[i - 1] = new JLabel();
                statt[i - 1].setFont(newFonts);
                statTotali.add(statt[i - 1], g);
                g.gridx = gridX;
                g.anchor = GridBagConstraints.CENTER;
            }
            g.gridy = 6;
            g.anchor = GridBagConstraints.WEST;
            g.gridwidth = 2;
            g.gridx = 0;
            statTotali.add(new JLabel("Number of ratings released this month:"), g);
            statt[5] = new JLabel();
            statt[5].setFont(new Font("Dialog", Font.BOLD, 18));
            g.gridx = 2;
            g.gridwidth = 1;
            statTotali.add(statt[5], g);
        }
        return statTotali;
    }

    private BeanBeneCulturale riversaDatiNelBean() {
        BeanBeneCulturale newBC = new BeanBeneCulturale();
        newBC.setNome(nomeBC.getText());
        newBC.setDescrizione(descrizioneBC.getText());
        newBC.setCap(capBC.getText());
        newBC.setCostoBiglietto(Double.parseDouble(costoBC.getText()));
        newBC.setGiornoChiusura((String) giornoChiusura.getSelectedItem());
        newBC.setTelefono(telefonoBC.getText());
        newBC.setCitta(cittaBC.getText());
        newBC.setLocalita(localitaBC.getText());
        // Date (int year, int month, int date, int hrs, int min)
        Date orarioAP = new Date(0, 0, 0, oreAP.getSelectedIndex(), minAP.getSelectedIndex());
        Date orarioCH = new Date(0, 0, 0, oreCH.getSelectedIndex(), minCH.getSelectedIndex());
        newBC.setOrarioApertura(orarioAP);
        newBC.setOrarioChiusura(orarioCH);
        newBC.setProvincia((String) provBC.getSelectedItem());
        newBC.setVia(((String) address1.getSelectedItem()) + " " + address2.getText());
        Punto3D position = new Punto3D(
            Double.parseDouble(posGeoX.getText()),
            Double.parseDouble(posGeoY.getText()),
            Double.parseDouble(posGeoZ.getText())
        );
        newBC.setPosizione(position);
        return newBC;
    }

    private void caricaTags() {
        ArrayList<BeanTag> beanTags = null;
        try {
            beanTags = tags.ottieniTags();
            if (bc != null) {
                idTag = new ArrayList<>();
                ArrayList<BeanTag> tagDaSelezionare = gestioneBC.ottieniTagBeneCulturale(bc.getId());
                for (BeanTag b : tagDaSelezionare) {
                    idTag.add(b.getId());
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            for (BeanTag b : beanTags) {
                pannelloTag.insertTag(b);
            }
            if (idTag != null) {
                pannelloTag.setSelectedTags(idTag);
            }
            pannelloTag.repaint();
        }
    }
}