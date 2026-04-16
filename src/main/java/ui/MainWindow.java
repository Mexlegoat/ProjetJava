package ui;

import modeles.*;
import patterns.UserSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class MainWindow extends JFrame {

    private JMenu menuAffichage;
    private JMenu menuAjouter;

    private JMenuItem menuConnexion;
    private JMenuItem menuInscription;
    private JMenuItem menuDeconnexion;
    private JMenuItem menuSauvegarde;

    private JButton btnParametres;
    private JButton btnAjouterJeu;
    private JButton btnAjouterTravail;
    private JButton btnAjouterMm;


    private JTextField searchField;
    private JComboBox<String> filterCombo;
    private JTable dataGrid;
    private DefaultTableModel tableModel;
    private JList<Item> listJeu;
    private JList<Item> listTravail;
    private JList<Item> listMm;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public MainWindow() {
        setTitle("StartApp");
        setSize(950, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buildMenuBar();
        buildUI();
        // updateUI();
    }

    private void buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFichier = new JMenu("Fichier");
        menuSauvegarde = new JMenuItem("Sauvegarder");
        menuFichier.add(menuSauvegarde);
        menuFichier.addSeparator();
        menuFichier.add(new JMenuItem("Quitter"));

        menuAffichage = new JMenu("Affichage");
        menuAffichage.add(new JCheckBoxMenuItem("Mode sombre"));
        
        menuAjouter = new JMenu("Ajouter");
        menuAjouter.add(new JMenuItem("Type"));

        JMenu menuCompte = new JMenu("Compte");
        menuConnexion = new JMenuItem("Connexion");
        menuInscription = new JMenuItem("Inscription");
        menuDeconnexion = new JMenuItem("Deconnexion");
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

    // -------------------------------------------------------------------
    // INTERFACE PRINCIPALE
    // -------------------------------------------------------------------
    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout(5, 5));
        root.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // NORD : recherche
        JPanel topPanel = new JPanel(new BorderLayout(8, 0));

        // Champ de recherche
        searchField = new JTextField();
        searchField.setToolTipText("Rechercher par nom...");
        JPanel searchPanel = new JPanel(new BorderLayout(4, 0));
        searchPanel.add(new JLabel("  Recherche : "), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchPanel, BorderLayout.CENTER);

        root.add(topPanel, BorderLayout.NORTH);

        // CENTRE : trois colonnes de listes
        JPanel center = new JPanel(new GridLayout(1, 3, 5, 0));
        listJeu = buildListColumn(center, "Jeux");
        listTravail = buildListColumn(center, "Travail");
        listMm = buildListColumn(center, "Multimedia");
        root.add(center, BorderLayout.CENTER);

        // SUD : boutons + JTable
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        btnParametres = new JButton("Paramètres");
        btnRow.add(btnParametres);

        String[] cols = {"ID", "Nom", "Date d'ajout", "Type personnalise", "Chemin"};
        tableModel = new DefaultTableModel(cols, 0) {
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


    private JList<Item> buildListColumn(JPanel parent, String titre) {
        JPanel col = new JPanel(new BorderLayout());
        col.setBorder(BorderFactory.createTitledBorder(titre));

        JList<Item> list = new JList<>(new DefaultListModel<>());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        col.add(new JScrollPane(list), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdd = new JButton("+");
        btnPanel.add(btnAdd);
        col.add(btnPanel, BorderLayout.SOUTH);

        switch(titre)
        {
            case "Jeux": btnAjouterJeu = btnAdd;
            case "Travail": btnAjouterTravail = btnAdd;
            case "Multimedia": btnAjouterMm = btnAdd;
        }
        parent.add(col);
        return list;
    }
    private void updateUI()
    {
        boolean loggedIn = UserSession.getInstance().isLoggedIn();

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

}
