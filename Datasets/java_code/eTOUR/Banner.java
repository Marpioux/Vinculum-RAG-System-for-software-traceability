package unisa.gps.etour.gui.operatoreagenzia;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.*;
import unisa.gps.etour.bean.BeanBanner;
import unisa.gps.etour.bean.BeanPuntoDiRistoro;
import unisa.gps.etour.bean.BeanTag;
import unisa.gps.etour.control.GestioneAdvertisement.IGestioneAdvertisementAgenzia;
import unisa.gps.etour.control.GestionePuntiDiRistoro.IGestionePuntiDiRistoroAgenzia;
import unisa.gps.etour.control.GestioneTag.IGestioneTagComune;
import unisa.gps.etour.gui.DeskManager;
import unisa.gps.etour.gui.HelpManager;
import unisa.gps.etour.gui.operatoreagenzia.BannerDialog;
import unisa.gps.etour.gui.operatoreagenzia.Home;
import unisa.gps.etour.gui.operatoreagenzia.TagPanel;
import unisa.gps.etour.gui.operatoreagenzia.tables.BannerNode;
import unisa.gps.etour.gui.operatoreagenzia.tables.PRNode;
import unisa.gps.etour.gui.operatoreagenzia.tables.BannerRenderer;

public class JInternalFrame extends Banner {

    private JPanel jContentPane = null;
    private JPanel rightPanel = null;
    private JToolBar bannerToolbar = null;
    private JButton btnInserisci = null;
    private JButton btnSostituisci = null;
    private JButton btnElimina = null;
    private JScrollPane jScrollPane = null;
    private JPanel helpPanel = null;
    private JTextPane textGuida = null;
    private TagPanel panelTag = null;
    private JButton btnRicerca = null;
    private JButton btnAzzera = null;
    private JPanel panelSearch = null;
    private JTextField nomePR = null;
    private JTree treeBanner = null;
    private JDesktopPane jDesktopPane;
    private HelpManager bannerHelp;
    protected DeskManager desktopManager;
    protected IGestionePuntiDiRistoroAgenzia gestionePuntiRistoro;
    protected IGestioneAdvertisementAgenzia gestioneBanner;
    protected IGestioneTagComune tags;

    public JInternalFrame() {
        super("Banner");
        resizable = true;
        closable = true;
        iconable = true;
        maximizable = true;
        setPreferredSize(Home.CHILD_SIZE);
        frameIcon = new ImageIcon(getClass().getResource(Home.URL_IMAGES + "banner2.png"));

        textGuida = new JTextPane();

        try {
            bannerHelp = new HelpManager(Home.URL_HELP + "Banner.txt", textGuida);
        } catch (FileNotFoundException e) {
            textGuida.setText("<html> <b> Help not available </b> </html>");
        }

        setContentPane(getJContentPane());
        addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameOpened(InternalFrameEvent pEvent) {
                JInternalFrame frame = (JInternalFrame) pEvent.getInternalFrame();
                jDesktopPane = frame.getDesktopPane();
                desktopManager = (DeskManager) jDesktopPane.getDesktopManager();

                try {
                    Registry reg = LocateRegistry.getRegistry(Home.HOST);
                    gestioneBanner = (IGestioneAdvertisementAgenzia) reg.lookup("GestioneBeniCulturaliAgenzia");
                    tags = (IGestioneTagComune) reg.lookup("GestioneTagComune");
                    gestionePuntiRistoro = (IGestionePuntiDiRistoroAgenzia) reg.lookup("GestionePuntiDiRistoroAgenzia");
                    createTree();
                    caricaTags();
                } catch (Exception ex) {
                    JLabel error = new JLabel("<html> <h2> Unable to communicate with the server eTour. </h2>"
                            + "<h3> <u> The dialog management request is closed. </u> </h3>"
                            + "<p> <b> Possible Causes: </b>"
                            + "<ul> <li> No connection to the network. </li>"
                            + "<li> Server inactive. </li>"
                            + "<li> Server clogged. </li> </ul>"
                            + "<p> Please try again later. </p>"
                            + "<p> If the error persists, please contact technical support. </p>"
                            + "<p> We apologize for the inconvenience. </html>");
                    ImageIcon err = new ImageIcon(getClass().getResource(Home.URL_IMAGES + "error48.png"));
                    JOptionPane.showMessageDialog(jDesktopPane, error, "Error!", JOptionPane.ERROR_MESSAGE, err);
                    frame.dispose();
                }
            }
        });
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getRightPanel(), BorderLayout.EAST);
            jContentPane.add(getBannerToolbar(), BorderLayout.NORTH);
            jContentPane.add(getTreeBanner(), BorderLayout.CENTER);
        }
        return jContentPane;
    }

    private JToolBar getBannerToolbar() {
        if (bannerToolbar == null) {
            bannerToolbar = new JToolBar();
            bannerToolbar.setLayout(null);
            bannerToolbar.setPreferredSize(new Dimension(1, 50));
            bannerToolbar.setFloatable(false);
            bannerToolbar.add(getBtnInserisci());
            bannerToolbar.add(getBtnSostituisci());
            bannerToolbar.add(getBtnElimina());
        }
        return bannerToolbar;
    }

    private JButton getBtnInserisci() {
        if (btnInserisci == null) {
            btnInserisci = new JButton();
            btnInserisci.setBounds(5, 5, 140, 40);
            btnInserisci.setText("<html> <br> Show Banner </html>");
            btnInserisci.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "NuovoBanner32.png")));
            btnInserisci.setEnabled(false);
            btnInserisci.setName("btnInserisci");
            btnInserisci.addMouseListener(bannerHelp);
            btnInserisci.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeBanner.getSelectionPath().getLastPathComponent();
                    openDialog(selectedNode);
                }
            });
        }
        return btnInserisci;
    }

    private JButton getBtnSostituisci() {
        if (btnSostituisci == null) {
            btnSostituisci = new JButton();
            btnSostituisci.setBounds(155, 5, 140, 40);
            btnSostituisci.setText("<html> Replace <br> Banner </html>");
            btnSostituisci.setEnabled(false);
            btnSostituisci.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "SostituisciBanner32.png")));
            btnSostituisci.setName("btnSostituisci");
            btnSostituisci.addMouseListener(bannerHelp);
            btnSostituisci.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeBanner.getSelectionPath().getLastPathComponent();
                    openDialog(selectedNode);
                }
            });
        }
        return btnSostituisci;
    }

    private JButton getBtnElimina() {
        if (btnElimina == null) {
            btnElimina = new JButton();
            btnElimina.setBounds(305, 5, 140, 40);
            btnElimina.setText("<html> Delete <br> Banner </html>");
            btnElimina.setEnabled(false);
            btnElimina.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "EliminaBanner32.png")));
            btnElimina.setName("btnElimina");
            btnElimina.addMouseListener(bannerHelp);
            btnElimina.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    JPanel root = new JPanel(new BorderLayout());
                    JLabel message = new JLabel("Are you sure you want to delete the banner selected?");
                    message.setFont(new Font("Dialog", Font.BOLD, 14));
                    JLabel alert = new JLabel("The banner cannot be more recovered.", SwingConstants.CENTER);
                    alert.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "warning16.png")));
                    root.add(message, BorderLayout.NORTH);
                    root.add(alert, BorderLayout.CENTER);
                    String[] options = {"Delete", "Cancel"};
                    int choice = JOptionPane.showInternalOptionDialog(jContentPane, root, "Confirm Delete",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource(Home.URL_IMAGES + "EliminaBanner48.png")),
                            options, options[1]);
                    if (choice == JOptionPane.YES_OPTION) {
                        DefaultTreeModel model = (DefaultTreeModel) treeBanner.getModel();
                        model.removeNodeFromParent((DefaultMutableTreeNode) treeBanner.getSelectionPath().getLastPathComponent());
                        JLabel confirm = new JLabel("The banner selected was deleted.");
                        confirm.setFont(new Font("Dialog", Font.BOLD, 14));
                        JOptionPane.showInternalMessageDialog(jContentPane, confirm, "Banner out!",
                                JOptionPane.OK_OPTION, new ImageIcon(getClass().getResource(Home.URL_IMAGES + "ok32.png")));
                    }
                }
            });
        }
        return btnElimina;
    }

    private void createTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("eTour");

        try {
            ArrayList<BeanPuntoDiRistoro> pPR = gestionePuntiRistoro.ottieniPuntiDiRistoro();
            for (int i = 0; i < pPR.size(); i++) {
                BeanPuntoDiRistoro current = pPR.get(i);
                int id = current.getId();
                PRNode puntoDiRistoro = new PRNode(current.getNome(), id);
                HashMap<BeanBanner, ImageIcon> banner = gestioneBanner.ottieniBannersDaID(id);
                Iterator<BeanBanner> iteraBanner = banner.keySet().iterator();
                while (iteraBanner.hasNext()) {
                    BeanBanner currentBanner = iteraBanner.next();
                    BannerNode newBanner = new BannerNode(banner.get(currentBanner), currentBanner.getId());
                    puntoDiRistoro.add(newBanner);
                }
                root.add(puntoDiRistoro);
            }
        } catch (RemoteException ex) {
            JLabel error = new JLabel("<html> <h2> Unable to communicate with the server eTour. </h2>"
                    + "<h3> <u> The list of banners was not loaded. </u> </h3>"
                    + "<p> Please try again later. </p>"
                    + "<p> If the error persists, please contact technical support. </p>"
                    + "<p> We apologize for the inconvenience. </html>");
            ImageIcon err = new ImageIcon(getClass().getResource(Home.URL_IMAGES + "error48.png"));
            JOptionPane.showInternalMessageDialog(this, error, "Error!", JOptionPane.ERROR_MESSAGE, err);
        } finally {
            treeBanner.setModel(new DefaultTreeModel(root));
        }
    }

    private JScrollPane getTreeBanner() {
        if (treeBanner == null) {
            treeBanner = new JTree(new DefaultTreeModel(new DefaultMutableTreeNode("eTour")));
            treeBanner.setScrollsOnExpand(true);
            treeBanner.setAutoscrolls(true);
            treeBanner.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
            treeBanner.setName("treeBanner");
            treeBanner.addMouseListener(bannerHelp);
            treeBanner.setRootVisible(false);
            treeBanner.setCellRenderer(new BannerRenderer());
            treeBanner.addTreeSelectionListener(new TreeSelectionListener() {
                public void valueChanged(TreeSelectionEvent s) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeBanner.getLastSelectedPathComponent();
                    if (node instanceof PRNode) {
                        btnInserisci.setEnabled(true);
                        btnElimina.setEnabled(false);
                        btnSostituisci.setEnabled(false);
                    } else if (node instanceof BannerNode) {
                        btnInserisci.setEnabled(false);
                        btnSostituisci.setEnabled(true);
                        btnElimina.setEnabled(true);
                    } else {
                        btnInserisci.setEnabled(false);
                        btnSostituisci.setEnabled(false);
                        btnElimina.setEnabled(false);
                    }
                }
            });
        }
        jScrollPane = new JScrollPane(treeBanner);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return jScrollPane;
    }

    private JPanel getRightPanel() {
        if (rightPanel == null) {
            rightPanel = new JPanel();
            rightPanel.setLayout(new GridBagLayout());
            GridBagConstraints g = new GridBagConstraints();
            g.gridx = 0;
            g.gridy = 0;
            g.fill = GridBagConstraints.BOTH;
            g.weighty = 0.7;
            rightPanel.add(getSearchPanel(), g);
            g.gridy++;
            g.weighty = 0.3;
            rightPanel.add(getHelpPanel(), g);
        }
        return rightPanel;
    }

    private JPanel getHelpPanel() {
        if (helpPanel == null) {
            helpPanel = new JPanel();
            helpPanel.setLayout(new BorderLayout());
            helpPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(51, 102, 255), 3),
                    "Help", TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(0, 102, 204)));
            helpPanel.setPreferredSize(new Dimension(200, 60));
            textGuida.setPreferredSize(new Dimension(6, 30));
            textGuida.setOpaque(false);
            textGuida.setContentType("text/html");
            textGuida.setText("<html> Move your mouse pointer over a control of interest to display the context-sensitive help. </html>");
            textGuida.setEditable(false);
            textGuida.setName("textGuida");
            textGuida.addMouseListener(bannerHelp);
            helpPanel.add(textGuida, BorderLayout.CENTER);
        }
        return helpPanel;
    }

    private JPanel getSearchPanel() {
        if (panelSearch == null) {
            panelSearch = new JPanel();
            panelSearch.setLayout(new GridBagLayout());
            panelSearch.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(51, 102, 255), 3),
                    "Research Refreshment", TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(0, 102, 204)));
            GridBagConstraints g = new GridBagConstraints();
            g.anchor = GridBagConstraints.CENTER;
            g.gridx = 0;
            g.gridy = 0;
            g.gridwidth = 2;
            g.insets = new Insets(5, 5, 5, 5);
            panelSearch.add(new JLabel("Name Refreshments:"), g);
            nomePR = new JTextField();
            nomePR.setName("nomePR");
            nomePR.addMouseListener(bannerHelp);
            nomePR.setColumns(12);
            g.insets = new Insets(5, 5, 10, 5);
            g.gridy++;
            panelSearch.add(nomePR, g);
            g.insets = new Insets(5, 5, 5, 5);
            g.gridy++;
            panelSearch.add(new JLabel("Select search tags:"), g);
            g.weighty = 1.0;
            g.insets = new Insets(5, 5, 10, 5);
            g.gridy++;
            panelTag = new TagPanel();
            panelTag.setName("panelTag");
            panelTag.addMouseListener(bannerHelp);
            panelTag.setPreferredSize(new Dimension(180, 10));
            g.fill = GridBagConstraints.VERTICAL;
            panelSearch.add(panelTag, g);
            g.fill = GridBagConstraints.NONE;
            g.insets = new Insets(5, 5, 5, 5);
            g.weighty = 0;
            g.gridy++;
            g.gridwidth = 1;
            panelSearch.add(getBtnRicerca(), g);
            g.gridx++;
            panelSearch.add(getBtnAzzera(), g);
        }
        return panelSearch;
    }

    private JButton getBtnRicerca() {
        if (btnRicerca == null) {
            btnRicerca = new JButton();
            btnRicerca.setPreferredSize(new Dimension(98, 26));
            btnRicerca.setText("Search");
            btnRicerca.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "Ricerca16.png")));
            btnRicerca.setName("btnRicerca");
            btnRicerca.addMouseListener(bannerHelp);
        }
        return btnRicerca;
    }

    private JButton getBtnAzzera() {
        if (btnAzzera == null) {
            btnAzzera = new JButton();
            btnAzzera.setPreferredSize(new Dimension(98, 26));
            btnAzzera.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "Azzera16.png")));
            btnAzzera.setText("Clear");
            btnAzzera.setHorizontalTextPosition(SwingConstants.LEADING);
            btnAzzera.setName("btnAzzera");
            btnAzzera.addMouseListener(bannerHelp);
            btnAzzera.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    panelTag.azzera();
                    nomePR.setText("");
                }
            });
        }
        return btnAzzera;
    }

    private void openDialog(DefaultMutableTreeNode pSelectedNode) {
        class ModalAdapter extends InternalFrameAdapter {
            Component glass;

            public ModalAdapter(Component pGlassComponent) {
                this.glass = pGlassComponent;
                MouseInputAdapter adapter = new MouseInputAdapter() {};
                pGlassComponent.addMouseListener(adapter);
                pGlassComponent.addMouseMotionListener(adapter);
            }

            public void internalFrameClosed(InternalFrameEvent s) {
                glass.setVisible(false);
            }
        }

        JOptionPane optionPane = new JOptionPane();
        final JInternalFrame modal = optionPane.createInternalFrame(jDesktopPane, "");
        final JPanel glass = new JPanel();
        BannerDialog dialog = new BannerDialog();
        optionPane.setMessage(dialog);
        optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
        JButton[] options = new JButton[2];
        options[0] = new JButton();
        options[1] = new JButton("Cancel");
        options[0].setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "Salva16.png")));
        options[1].setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "Annulla16.png")));
        optionPane.setOptions(options);
        options[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                modal.setVisible(false);
                glass.setVisible(false);
            }
        });

        glass.setOpaque(false);
        modal.addInternalFrameListener(new ModalAdapter(glass));
        glass.add(modal);
        setGlassPane(glass);
        modal.setLocation(this.getWidth() / 2, this.getHeight() / 2);
        glass.setVisible(true);
        modal.setVisible(true);
        if (pSelectedNode instanceof BannerNode) {
            final BannerNode banner = (BannerNode) pSelectedNode;
            options[0].setText("Replace");
            options[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    DefaultTreeModel model = (DefaultTreeModel) treeBanner.getModel();
                    banner.setBanner(dialog.getSelectedBanner());
                    model.nodeChanged(banner);
                    glass.setVisible(false);
                    modal.setVisible(false);
                }
            });

            optionPane.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "SostituisciBanner48.png")));
            modal.setTitle("Replace the banner to the point of relief " + ((PRNode) pSelectedNode.getParent()).getUserObject().toString());
        } else if (pSelectedNode instanceof PRNode) {
            PRNode final pr = (PRNode) pSelectedNode;
            options[0].setText("Save");
            options[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    DefaultTreeModel model = (DefaultTreeModel) treeBanner.getModel();
                    BannerNode newBanner = new BannerNode(dialog.getSelectedBanner(), pr.getID());
                    model.insertNodeInto(newBanner, pr, 0);
                    glass.setVisible(false);
                    modal.setVisible(false);
                }
            });
            optionPane.setIcon(new ImageIcon(getClass().getResource(Home.URL_IMAGES + "nuovoBanner48.png")));
            modal.setTitle("Enter banner to the point of relief " + pSelectedNode.getUserObject().toString());
        }
    }

    private void caricaTags() {
        ArrayList<BeanTag> beanTags = null;
        try {
            beanTags = tags.ottieniTags();
        } catch (RemoteException e) {
            // Handle exception
        } finally {
            for (BeanTag b : beanTags) {
                panelTag.insertTag(b);
            }
            panelTag.repaint();
        }
    }
}