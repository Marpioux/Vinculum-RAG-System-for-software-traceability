package unisa.gps.etour.gui.operatoreagenzia;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.*;
import unisa.gps.etour.bean.BeanTurista;
import unisa.gps.etour.util.Data;

/**
 * Class that models the interface for displaying the card and
 * modifying the data of an account tourist.
 *
 * @version 1.0
 * @author Mario Gallo
 *
 * © 2007 eTour Project - Copyright by DMI SE @ SA Lab --
 * University of Salerno
 */
public class SchedaTurista extends JInternalFrame implements ISchedaTurista {

    private static final String[] help = {"", "", "", "", "", "", "", "", "", "", "", "", ""};
    private JPanel jContentPane = null;
    private JToolBar toolbarscheda = null;
    private JToggleButton btnModifica = null;
    private JButton btnSalva = null;
    private JButton btnReimposta = null;
    private JTabbedPane jTabbedPane = null;
    private JTextField address2 = null;
    private JComboBox<String> address1 = null;
    private JTextField city = null;
    private JTextField ch = null;
    private JTextField phone = null;
    private JComboBox<String> province = null;
    private JPanel datiTurista = null;
    private JTextField name = null;
    private Vector<JLabel> suggestions;
    private BeanTurista tourist;
    private JComboBox<String> day;
    private JComboBox<String> month;
    private JComboBox<String> year;
    private JTextField luogoNascita;
    private JTextField email;
    private JTextField username;
    private JPasswordField password;
    private JLabel dataRegistrazione;
    private Tourists parent;

    /**
     * The only card manufacturer model of a tourist or modification of data
     * from the bean.
     *
     * @param pParent unisa.gps.etour.gui.operatoreagenzia.Turisti - the window "father."
     * @param pTurista unisa.gps.etour.bean.BeanTurista - the bean containing data
     *                 of the tourist.
     * @param pModifica <ul> <li>true - if amendments are made to the
     *                  data. <li>false - if you are viewing the card.
     */
    public SchedaTurista(Turisti pParent, BeanTurista pTurista, boolean pModifica) {
        super();
        this.parent = pParent;
        setIconifiable(true);
        setSize(560, 520);
        suggestions = new Vector<>();
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setClosable(true);
        tourist = pTurista;
        if (tourist.isAttiva()) {
            setFrameIcon(new ImageIcon(
                    getClass().getResource("/unisa/gps/eTour/gui/operatoreagenzia/images/tab_turista.png")));
        } else {
            setFrameIcon(new ImageIcon(
                    getClass().getResource("/unisa/gps/etour/gui/operatoreagenzia/images/DisattivaTurista32.png")));
        }
        initialize();
        if (pModifica) {
            btnModifica.setSelected(true);
            btnSalva.setVisible(true);
            btnReimposta.setVisible(true);
        } else {
            mostraNascondiSuggerimenti();
            attivaDisattivaEdit();
        }
        addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent pEvent) {
                if (btnModifica.isSelected()) {
                    JPanel root = new JPanel(new BorderLayout());
                    JLabel message = new JLabel("Are you sure you want to close the tab of this tourist?");
                    message.setFont(new Font("Dialog", Font.BOLD, 14));
                    JLabel alert = new JLabel("Warning! Unsaved data will be lost.", SwingConstants.CENTER);
                    alert.setIcon(new ImageIcon(getClass().getResource("/unisa/gps/etour/gui/operatoreagenzia/images/warning16.png")));
                    root.add(message, BorderLayout.NORTH);
                    root.add(alert, BorderLayout.CENTER);
                    String[] options = {"Close", "Cancel"};
                    int choice = JOptionPane.showInternalOptionDialog(jContentPane, root,
                            "Confirm closing Tourist Card " + tourist.getNome() + " " + tourist.getCognome(),
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, getFrameIcon(), options, options[1]);
                    if (choice == JOptionPane.OK_OPTION) {
                        parent.closeScheda((SchedaTurista) pEvent.getInternalFrame());
                    }
                } else {
                    parent.closeScheda((SchedaTurista) pEvent.getInternalFrame());
                }
            }
        });
    }

    /**
     * This method initializes the interface card for tourists.
     *
     * @return void
     */
    private void initialize() {
        jContentPane = new JPanel();
        jContentPane.setLayout(new BorderLayout());
        jContentPane.add(getToolbarscheda(), BorderLayout.CENTER);
        jTabbedPane = new JTabbedPane();
        jTabbedPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        jTabbedPane.addTab("Tourist Information", getFrameIcon(), getDatiTuristaForm(), null);
        jContentPane.add(jTabbedPane, BorderLayout.CENTER);
        setContentPane(jContentPane);
        caricaDatiForm();
    }

    /**
     * This method loads the bean data provided tourist camps
     * of the form.
     *
     * @return void
     */
    private void caricaDatiForm() {
        setTitle("Profile Tourist - " + tourist.getNome() + " " + tourist.getCognome());
        name.setText(tourist.getNome());
        ch.setText(tourist.getCognome());
        Date dob = tourist.getDataNascita();
        day.setSelectedIndex(dob.getDate() - 1);
        month.setSelectedIndex(dob.getMonth());
        year.setSelectedIndex(dob.getYear() + 1900);
        StringTokenizer tokenizer = new StringTokenizer(tourist.getVia());
        String string = tokenizer.nextToken();
        address1.setSelectedItem(string);
        address2.setText(tourist.getVia().substring(string.length()));
        luogoNascita.setText(tourist.getCittaNascita());
        phone.setText(tourist.getTelefono());
        city.setText(tourist.getCittaResidenza());
        password.setText(tourist.getPassword());
        province.setSelectedItem(tourist.getProvincia());
        username.setText(tourist.getUsername());
        ch.setText(tourist.getCap());
        dataRegistrazione.setText(Data.toEstesa(tourist.getDataRegistrazione()));
    }

    /**
     * This method shows or hides the suggestions relating to the form fields.
     *
     * @return void
     */
    private void mostraNascondiSuggerimenti() {
        Iterator<JLabel> s = suggestions.iterator();
        while (s.hasNext()) {
            JLabel current = s.next();
            current.setVisible(!current.isVisible());
        }
    }

    /**
     * This method makes it or not editable form fields.
     *
     * @return void
     */
    private void attivaDisattivaEdit() {
        Component[] components = datiTurista.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField) {
                JTextField textbox = (JTextField) component;
                textbox.setEditable(!textbox.isEditable());
                textbox.setBackground(Color.white);
            } else if (component instanceof JComboBox) {
                JComboBox<?> combo = (JComboBox<?>) component;
                combo.setEnabled(!combo.isEnabled());
            }
        }
    }

    /**
     * This method initializes the toolbar for the functionality of the card
     * Tourist.
     *
     * @return javax.swing.JToolBar
     */
    private JToolBar getToolbarscheda() {
        if (toolbarscheda == null) {
            toolbarscheda = new JToolBar();
            toolbarscheda.setFloatable(false);
            toolbarscheda.add(getBtnModifica());
            toolbarscheda.addSeparator();
            toolbarscheda.add(getBtnSalva());
            toolbarscheda.addSeparator();
            toolbarscheda.add(getBtnReimposta());
            toolbarscheda.addSeparator();
        }
        return toolbarscheda;
    }

    /**
     * This method initializes the button for editing data.
     *
     * @return javax.swing.JToggleButton
     */
    private JToggleButton getBtnModifica() {
        if (btnModifica == null) {
            btnModifica = new JToggleButton();
            btnModifica.setText("Change Data");
            btnModifica.setIcon(new ImageIcon(
                    getClass().getResource("/unisa/gps/etour/gui/operatoreagenzia/images/ModificaTurista32.png")));
            btnModifica.setToolTipText("Enable or disable data modification tourists selected.");
            btnModifica.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    mostraNascondiSuggerimenti();
                    attivaDisattivaEdit();
                    btnSalva.setVisible(btnModifica.isSelected());
                    btnReimposta.setVisible(btnModifica.isSelected());
                }
            });
        }
        return btnModifica;
    }

    /**
     * This method initializes the button to save the changes
     * made to the data of the tourist.
     *
     * @return javax.swing.JButton
     */
    private JButton getBtnSalva() {
        if (btnSalva == null) {
            btnSalva = new JButton();
            btnSalva.setText("Save");
            btnSalva.setIcon(new ImageIcon(getClass().getResource(
                    "/unisa/gps/eTour/gui/operatoreagenzia/images/salva.png")));
            btnSalva.setToolTipText("Save changes to the tourist profile selected.");
            btnSalva.setVisible(false);
            btnSalva.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent pEvent) {
                    JPanel root = new JPanel(new BorderLayout());
                    JLabel message = new JLabel("Updating the tourist profile of "
                            + tourist.getNome() + " " + tourist.getCognome() + " with data form?");
                    message.setFont(new Font("Dialog", Font.BOLD, 14));
                    JLabel alert = new JLabel("The previous data can not be more recovered.", SwingConstants.CENTER);
                    alert.setIcon(new ImageIcon(getClass().getResource("/unisa/gps/etour/gui/operatoreagenzia/images/warning16.png")));
                    root.add(message, BorderLayout.NORTH);
                    root.add(alert, BorderLayout.CENTER);
                    String[] options = {"Edit", "Cancel"};
                    int choice = JOptionPane.showInternalOptionDialog(jContentPane, root,
                            "Commit Changes tourist figures",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            new ImageIcon(getClass().getResource("/unisa/gps/etour/gui/operatoreagenzia/images/ModificaTurista48.png")),
                            options, options[1]);
                    if (choice == JOptionPane.YES_OPTION) {
                        tourist.setNome(name.getText());
                        tourist.setCognome(ch.getText());
                        tourist.setCap(ch.getText());
                        tourist.setCittaNascita(luogoNascita.getText());
                        tourist.setDataNascita(new Date(year.getSelectedIndex() + 1900, month.getSelectedIndex(), day.getSelectedIndex() + 1));
                        tourist.setCittaResidenza(city.getText());
                        tourist.setUsername(username.getText());
                        tourist.setEmail(email.getText());
                        tourist.setTelefono(phone.getText());
                        tourist.setVia(address1.getSelectedItem().toString() + " " + address2.getText());
                        tourist.setProvincia(province.getSelectedItem().toString());
                        String pass = "";
                        char[] passwordChars = password.getPassword();
                        for (char passwordChar : passwordChars) {
                            pass += passwordChar;
                        }
                        tourist.setPassword(pass);
                        caricaDatiForm();
                        attivaDisattivaEdit();
                        btnSalva.setVisible(false);
                        btnReimposta.setVisible(false);
                        btnModifica.setSelected(false);
                        mostraNascondiSuggerimenti();
                        parent.updateTableModel(tourist);
                        JOptionPane.showInternalMessageDialog(jContentPane,
                                "The data of tourists have been updated successfully.", "Modified Profile Tourist!",
                                JOptionPane.OK_OPTION,
                                new ImageIcon(getClass().getResource("/unisa/gps/etour/gui/operatoreagenzia/images/ok48.png")));
                    }
                }
            });
        }
        return btnSalva;
    }

    /**
     * This method initializes the button to reset the data of the tourist
     * in the form.
     *
     * @return javax.swing.JButton
     */
    private JButton getBtnReimposta() {
        if (btnReimposta == null) {
            btnReimposta = new JButton();
            btnReimposta.setText("Reset");
            btnReimposta.setIcon(new ImageIcon(getClass().getResource(
                    "/unisa/gps/etour/gui/operatoreagenzia/images/Annulla32.png")));
            btnReimposta.setToolTipText("Reload the selected tourist information.");
            btnReimposta.setVisible(false);
            btnReimposta.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    caricaDatiForm();
                }
            });
        }
        return btnReimposta;
    }

    /**
     * This method initializes the form containing data of the tourist.
     *
     * @return javax.swing.JPanel
     */
    private JPanel getDatiTuristaForm() {
        if (datiTurista == null) {
            datiTurista = new JPanel(null);
            datiTurista.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
            // Creation Tips
            String[] txts = {"Name", "Surname", "Date of Birth", "Place of Birth", "Phone", "Address", "City", "CAP", "Province", "E-Mail", "Username", "Password", "Save"};

            for (int i = 0; i < help.length; i++) {
                JLabel newLabel = new JLabel();
                newLabel.setIcon(new ImageIcon(getClass().getResource("/unisa/gps/etour/gui/images/Info16.png")));
                newLabel.setBounds(145, 8 + 30 * i, 24, 24);
                newLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                newLabel.setToolTipText(help[i]);
                suggestions.add(newLabel);
                datiTurista.add(newLabel);
            }

            for (int i = 0; i < txts.length; i++) {
                JLabel newLabel = new JLabel(txts[i], SwingConstants.RIGHT);
                newLabel.setBounds(25, 10 + 30 * i, 120, 20);
                datiTurista.add(newLabel, null);
            }
            // Name
            name = new JTextField(12);
            name.setBounds(185, 10, 136, 20);
            name.setName("Name");
            datiTurista.add(name, null);

            // Surname
            ch = new JTextField(12);
            ch.setBounds(185, 40, 136, 20);
            ch.setName("Surname");
            datiTurista.add(ch, null);

            // Date of Birth
            day = new JComboBox<>();
            day.setBounds(185, 70, 40, 20);
            for (int i = 1; i <= 31; i++) {
                day.addItem(i);
            }
            month = new JComboBox<>();
            month.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent pEvent) {
                    int number = day.getItemCount();
                    switch (month.getSelectedIndex()) {
                        case 0:
                        case 2:
                        case 4:
                        case 6:
                        case 7:
                        case 9:
                        case 11:
                            for (int i = number + 1; i <= 31; i++) {
                                day.addItem(i);
                            }
                            break;
                        case 1:
                            int yearIndex = (Integer) year.getSelectedItem();
                            boolean leap = ((yearIndex % 4 == 0 && yearIndex % 100 != 0) || (yearIndex % 400 == 0));
                            if (number != 28) {
                                for (int i = number - 1; i > 27; i--) {
                                    day.removeItemAt(i);
                                }
                            }
                            if (leap && number != 29) {
                                day.addItem("29");
                            }
                            break;
                        case 3:
                        case 5:
                        case 8:
                        case 10:
                            if (number == 31) {
                                day.removeItemAt(30);
                            } else {
                                for (int i = number + 1; i <= 30; i++) {
                                    day.addItem(i);
                                }
                            }
                            break;
                    }
                }
            });
            month.setBounds(245, 70, 40, 20);
            for (int i = 1; i <= 12; i++) {
                month.addItem(i);
            }
            year = new JComboBox<>();
            year.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    if (month.getSelectedIndex() == 1) {
                        int yearIndex = (Integer) year.getSelectedItem();
                        boolean leap = ((yearIndex % 4 == 0 && yearIndex % 100 != 0) || (yearIndex % 400 == 0));
                        int number = day.getItemCount();
                        if (leap && number != 29) {
                            day.addItem("29");
                        } else if (leap && number == 29) {
                            day.removeItemAt(28);
                        }
                    }
                }
            });
            year.setBounds(305, 70, 80, 20);
            Date today = new Date();
            for (int i = 0; i <= today.getYear() - 14; i++) {
                year.addItem(1900 + i);
            }
            datiTurista.add(day, null);
            datiTurista.add(month, null);
            datiTurista.add(year, null);

            // Place of Birth
            luogoNascita = new JTextField(12);
            luogoNascita.setBounds(185, 100, 136, 20);
            luogoNascita.setName("Birth Place");
            datiTurista.add(luogoNascita, null);

            // Phone
            phone = new JTextField(12);
            phone.setBounds(185, 130, 136, 20);
            phone.setName("Phone");
            datiTurista.add(phone, null);

            // Address
            address2 = new JTextField(12);
            address2.setBounds(270, 160, 136, 20);
            address1 = new JComboBox<>();
            address1.setSelectedIndex(-1);
            address1.setBounds(185, 160, 60, 20);
            datiTurista.add(address2, null);
            datiTurista.add(address1, null);

            // City
            city = new JTextField(12);
            city.setBounds(185, 190, 136, 20);
            city.setName("City");
            datiTurista.add(city, null);

            // CAP
            ch = new JTextField(8);
            ch.setBounds(185, 220, 92, 20);
            datiTurista.add(ch, null);

            // State
            province = new JComboBox<>();
            province.setSelectedIndex(-1);
            province.setBounds(185, 250, 50, 20);
            datiTurista.add(province, null);

            // E-Mail
            email = new JTextField();
            email.setBounds(185, 280, 200, 20);
            email.setName("E-Mail");
            datiTurista.add(email, null);

            // Username
            username = new JTextField();
            username.setBounds(185, 310, 136, 20);
            username.setName("Username");
            datiTurista.add(username, null);

            // Password
            password = new JPasswordField(12);
            password.setBounds(185, 340, 136, 20);
            password.setName("Password");
            datiTurista.add(password, null);

            // Data entry
            dataRegistrazione = new JLabel();
            dataRegistrazione.setBounds(185, 370, 140, 20);
            datiTurista.add(dataRegistrazione, null);
        }
        return datiTurista;
    }

    /**
     * This method returns the id of the tourist who is viewing /
     * edit.
     *
     * @return int - the id of the tourist.
     */
    public int getId() {
        return tourist.getId();
    }
}