package ui;
import controllers.AuthController;
import controllers.MainController;
import controllers.SettingsController;
import modeles.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
public class MainWindow extends JFrame implements ActionListener
{
    private final MainController mainController;
    private final AuthController authController;
    private final SettingsController settingsController;
    private JMenu menuAffichage;
    private JMenu menuAjouter;
    private JMenuItem menuQuitter;
    private JMenuItem menuType;
    private JCheckBoxMenuItem menuModeSombre;
    private JMenuItem menuConnexion;
    private JMenuItem menuInscription;
    private JMenuItem menuDeconnexion;
    private JMenuItem menuSauvegarde;
    private JMenuItem menuDate;
    private JButton btnParametres;
    private JButton btnAjouterJeu;
    private JButton btnAjouterTravail;
    private JButton btnAjouterMm;
    private JTextField searchField;
    private JTable dataGrid;
    private DefaultTableModel tableModel;
    private JList<Item> listJeu;
    private JList<Item> listTravail;
    private JList<Item> listMm;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public MainWindow(MainController mainController,
                      AuthController authController,
                      SettingsController settingsController)
    {
        this.mainController = mainController;
        this.authController = authController;
        this.settingsController = settingsController;
        setTitle("StartApp");
        setSize(950, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buildMenuBar();
        buildUI();
        refreshMenuState();
    }
    private void buildMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFichier = new JMenu("Fichier");
        menuSauvegarde = new JMenuItem("Sauvegarder");
        menuSauvegarde.addActionListener(this);
        menuFichier.add(menuSauvegarde);
        menuFichier.addSeparator();
        menuQuitter = new JMenuItem("Quitter");
        menuQuitter.addActionListener(this);
        menuFichier.add(menuQuitter);
        menuAffichage = new JMenu("Affichage");
        menuDate = new JMenuItem("Format de la date");
        menuDate.addActionListener(this);
        menuAffichage.add(menuDate);
        menuAffichage.addSeparator();
        menuModeSombre = new JCheckBoxMenuItem("Mode sombre");
        menuModeSombre.addActionListener(this);
        menuAffichage.add(menuModeSombre);
        menuAjouter = new JMenu("Ajouter");
        menuType = new JMenuItem("Type");
        menuType.addActionListener(this);
        menuAjouter.add(menuType);
        JMenu menuCompte = new JMenu("Compte");
        menuConnexion = new JMenuItem("Connexion");
        menuConnexion.addActionListener(this);
        menuInscription = new JMenuItem("Inscription");
        menuInscription.addActionListener(this);
        menuDeconnexion = new JMenuItem("Deconnexion");
        menuDeconnexion.addActionListener(this);
        menuCompte.add(menuConnexion);
        menuCompte.add(menuInscription);
        menuCompte.addSeparator();
        menuCompte.add(menuDeconnexion);
        menuBar.add(menuFichier);
        menuBar.add(menuAffichage);
        menuBar.add(menuCompte);
        menuBar.add(menuAjouter);
        setJMenuBar(menuBar);
    }
    private void buildUI()
    {
        JPanel root = new JPanel(new BorderLayout(5, 5));
        root.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JPanel topPanel = new JPanel(new BorderLayout(8, 0));
        searchField = new JTextField();
        searchField.setToolTipText("Rechercher par nom...");
        JPanel searchPanel = new JPanel(new BorderLayout(4, 0));
        searchPanel.add(new JLabel("  Recherche : "), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchPanel, BorderLayout.CENTER);
        root.add(topPanel, BorderLayout.NORTH);
        JPanel center = new JPanel(new GridLayout(1, 3, 5, 0));
        listJeu = buildListColumn(center, "Jeux");
        listTravail = buildListColumn(center, "Travail");
        listMm = buildListColumn(center, "Multimedia");
        root.add(center, BorderLayout.CENTER);
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        btnParametres = new JButton("Paramètres");
        btnParametres.addActionListener(this);
        btnRow.add(btnParametres);
        String[] cols = {"ID", "Nom", "Date d'ajout", "Type personnalisé", "Chemin"};
        tableModel = new DefaultTableModel(cols, 0)
        {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        dataGrid = new JTable(tableModel);
        dataGrid.getColumnModel().getColumn(0).setPreferredWidth(40);
        dataGrid.getColumnModel().getColumn(1).setPreferredWidth(130);
        dataGrid.getColumnModel().getColumn(2).setPreferredWidth(140);
        dataGrid.getColumnModel().getColumn(3).setPreferredWidth(110);
        dataGrid.getColumnModel().getColumn(4).setPreferredWidth(280);
        JScrollPane tableScroll = new JScrollPane(dataGrid);
        tableScroll.setPreferredSize(new Dimension(0, 170));
        JPanel southStack = new JPanel(new BorderLayout());
        southStack.add(btnRow, BorderLayout.NORTH);
        southStack.add(tableScroll, BorderLayout.CENTER);
        root.add(southStack, BorderLayout.SOUTH);
        setContentPane(root);
    }
    private JList<Item> buildListColumn(JPanel parent, String titre)
    {
        JPanel col = new JPanel(new BorderLayout());
        col.setBorder(BorderFactory.createTitledBorder(titre));
        JList<Item> list = new JList<>(new DefaultListModel<>());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        col.add(new JScrollPane(list), BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("+");
        btnPanel.add(btnAdd);
        col.add(btnPanel, BorderLayout.SOUTH);
        switch (titre)
        {
            case "Jeux":
                btnAjouterJeu = btnAdd;
                btnAjouterJeu.addActionListener(this);
                break;
            case "Travail":
                btnAjouterTravail = btnAdd;
                btnAjouterTravail.addActionListener(this);
                break;
            case "Multimedia":
                btnAjouterMm = btnAdd;
                btnAjouterMm.addActionListener(this);
                break;
        }
        parent.add(col);
        return list;
    }
    private void refreshMenuState()
    {
        boolean loggedIn = authController.isLoggedIn();
        menuSauvegarde.setEnabled(loggedIn);
        menuAffichage.setEnabled(loggedIn);
        menuAjouter.setEnabled(loggedIn);
        menuConnexion.setEnabled(!loggedIn);
        menuInscription.setEnabled(!loggedIn);
        menuDeconnexion.setEnabled(loggedIn);
        btnAjouterMm.setEnabled(loggedIn);
        btnParametres.setEnabled(loggedIn);
        btnAjouterTravail.setEnabled(loggedIn);
        btnAjouterJeu.setEnabled(loggedIn);
    }
    private void refreshAllViews()
    {
        ((DefaultListModel<Item>) listJeu.getModel()).clear();
        ((DefaultListModel<Item>) listTravail.getModel()).clear();
        ((DefaultListModel<Item>) listMm.getModel()).clear();
        for (Item item : mainController.getItemsByCategory("Jeux"))
        {
            ((DefaultListModel<Item>) listJeu.getModel()).addElement(item);
        }
        for (Item item : mainController.getItemsByCategory("Travail"))
        {
            ((DefaultListModel<Item>) listTravail.getModel()).addElement(item);
        }
        for (Item item : mainController.getItemsByCategory("Multimedia"))
        {
            ((DefaultListModel<Item>) listMm.getModel()).addElement(item);
        }
        tableModel.setRowCount(0);
        for (Item item : mainController.getAllItems())
        {
            String date = item.getDateAjoute() != null ? item.getDateAjoute().format(FMT) : "";
            String type = item.getCustomType() != null ? item.getCustomType().getNom() : "";
            tableModel.addRow(new Object[]{
                    item.getId(), item.getNom(), date, type, item.getChemin()
            });
        }
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnAjouterJeu)
        {
            CreateGameDialog dlg = new CreateGameDialog(this);
            if (dlg.showDialog())
            {
                mainController.addJeu(dlg.getNom(), dlg.getChemin(), dlg.getExtra());
                refreshAllViews();
            }
        }
        else if (e.getSource() == btnAjouterTravail)
        {
            CreateTravailDialog dlg = new CreateTravailDialog(this);
            if (dlg.showDialog())
            {
                mainController.addTravail(dlg.getNom(), dlg.getChemin(), dlg.getExtra());
                refreshAllViews();
            }
        }
        else if (e.getSource() == btnAjouterMm)
        {
            CreateMultimediaDialog dlg = new CreateMultimediaDialog(this);
            if (dlg.showDialog())
            {
                mainController.addMultimedia(dlg.getNom(), dlg.getChemin(), dlg.getExtra());
                refreshAllViews();
            }
        }
        else if (e.getSource() == btnParametres)
        {
            SettingsDialog dlg = new SettingsDialog(this, settingsController);
            dlg.showDialog();
        }
        else if (e.getSource() == menuConnexion)
        {
            LoginDialog dlg = new LoginDialog(this, authController);
            if (dlg.showDialog())
            {
                mainController.restoreNextId();
                refreshMenuState();
                refreshAllViews();
            }
        }
        else if (e.getSource() == menuInscription)
        {
            RegisterDialog dlg = new RegisterDialog(this, authController);
            if (dlg.showDialog())
            {
                mainController.restoreNextId();
                refreshMenuState();
                refreshAllViews();
            }
        }
        else if (e.getSource() == menuDeconnexion)
        {
            authController.logout();
            refreshMenuState();
            refreshAllViews();
        }
        else if (e.getSource() == menuSauvegarde)
        {
            mainController.save();
        }
        else if (e.getSource() == menuQuitter)
        {
            System.exit(0);
        }
        else if (e.getSource() == menuType)
        {
            String name = JOptionPane.showInputDialog(this,
                    "Nom du nouveau type :", "Créer un Type", JOptionPane.PLAIN_MESSAGE);
            if (name != null && !name.isBlank())
            {
                TypeClass t = mainController.addCustomType(name);
                if (t != null)
                {
                    JOptionPane.showMessageDialog(this, "Type '" + name + "' ajouté avec succès !");
                }
            }
        }
        else if (e.getSource() == menuModeSombre)
        {
            System.out.println("A implementer");
        }
        else if (e.getSource() == menuDate)
        {
            System.out.println("A implementer");
        }
    }
}