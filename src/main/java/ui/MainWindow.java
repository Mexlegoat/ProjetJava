    package ui;
    import Application.Main;
    import com.formdev.flatlaf.FlatDarculaLaf;
    import com.formdev.flatlaf.FlatLightLaf;
    import controllers.AuthController;
    import controllers.MainController;
    import controllers.SettingsController;
    import modeles.*;
    import javax.swing.*;
    import javax.swing.event.DocumentEvent;
    import javax.swing.event.DocumentListener;
    import javax.swing.filechooser.FileSystemView;
    import javax.swing.table.DefaultTableModel;
    import javax.swing.table.TableRowSorter;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.awt.event.MouseAdapter;
    import java.awt.event.MouseEvent;
    import java.io.File;
    import java.time.format.DateTimeFormatter;
    import java.util.ArrayList;
    import java.util.List;

    public class MainWindow extends JFrame implements ActionListener
    {

        private final MainController mainController;
        private final AuthController authController;
        private final SettingsController settingsController;

        private JMenu menuAffichage;
        private JMenu menuAjouter;
        private JMenuItem menuQuitter;
        private JMenuItem menuType;
        private JMenuItem menuConnexion;
        private JMenuItem menuInscription;
        private JMenuItem menuDeconnexion;
        private JMenuItem menuSauvegarde;
        private JMenuItem menuDate;

        private JMenuItem assignTypeItem;
        private JMenuItem deleteItem;

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

        private DateTimeFormatter currentFMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        private ItemCellRenderer itemCellRenderer;

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
            settingsController.addListener(settings ->
            {
                applyTheme(settings.isDarkMode());
                showType(settings.isShowType());
                showGenre(settings.isShowGenre());
            });
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
            searchField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    setSearchField(settingsController.getSettings().getSearchType(), searchField.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    setSearchField(settingsController.getSettings().getSearchType(), searchField.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    setSearchField(settingsController.getSettings().getSearchType(), searchField.getText());
                }
            });
            JPanel searchPanel = new JPanel(new BorderLayout(4, 0));
            searchPanel.add(new JLabel("  Recherche : "), BorderLayout.WEST);
            searchPanel.add(searchField, BorderLayout.CENTER);
            topPanel.add(searchPanel, BorderLayout.CENTER);
            root.add(topPanel, BorderLayout.NORTH);
            itemCellRenderer = new ItemCellRenderer(false, false, true);
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
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
            dataGrid.setRowSorter(sorter);
            sorter.setSortKeys(java.util.List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
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

            JPopupMenu popup = new JPopupMenu();
            assignTypeItem = new JMenuItem("Assigner un type...");
            assignTypeItem.addActionListener(e -> {
                Item selected = list.getSelectedValue();
                if (selected != null) {
                    setAssignTypeItem(selected);
                } else {
                    JOptionPane.showMessageDialog(this, "Aucun élément sélectionné.");
                }
            });
            deleteItem = new JMenuItem("Supprimer");
            deleteItem.setForeground(Color.RED);
            deleteItem.addActionListener(e -> {
                Item selected = list.getSelectedValue();
                if (selected != null)
                {
                    deleteItem(selected);
                }
            });
            popup.add(assignTypeItem);
            popup.add(deleteItem);
            list.setComponentPopupMenu(popup);
            list.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        int index = list.locationToIndex(e.getPoint());
                        if (index != -1 && list.getCellBounds(index, index).contains(e.getPoint())) {
                            // Désélectionner les autres listes pour éviter les conflits
                            clearAllSelectionsExcept(list);
                            list.setSelectedIndex(index);
                            popup.show(list, e.getX(), e.getY());
                        }
                    }
                    if (e.getClickCount() == 2 && settingsController.getSettings().isDoubleClickToExecute())
                    {
                        Item item = list.getSelectedValue();
                        if (item != null)
                        {
                            if(item.getChemin() != null && !item.getChemin().isBlank())
                            {
                                item.launch();
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(MainWindow.this,
                                        "L'application '" + item.getNom() + "' n'est pas exécutable!"
                                );
                            }
                        }
                    }
                }
            });
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


        private void clearAllSelectionsExcept(JList<Item> currentList) {
            if (currentList != listJeu) listJeu.clearSelection();
            if (currentList != listTravail) listTravail.clearSelection();
            if (currentList != listMm) listMm.clearSelection();
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

        private void refreshAllLists()
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
                String date;
                if (item.getDateAjoute() != null)
                {
                    date = item.getDateAjoute().format(currentFMT);
                }
                else
                {
                    date = "";
                }
                String type;
                if (item.getCustomType() != null)
                {
                    type = item.getCustomType().getNom();
                }
                else
                {
                    type = "";
                }
                tableModel.addRow(new Object[]{
                        item.getId(), item.getNom(), date, type, item.getChemin()
                });
            }
        }


        private void applyTheme(boolean darkMode)
        {
            try
            {
                if (darkMode)
                {
                    FlatDarculaLaf.setup();
                }
                else
                {
                    FlatLightLaf.setup();
                }
                for (Window window : Window.getWindows())
                {
                    SwingUtilities.updateComponentTreeUI(window);
                    window.repaint();
                }
                itemCellRenderer.setDarkMode(darkMode);
                refreshCellRenderer();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }


        private void setAssignTypeItem(Item item)
        {

            List<TypeClass>types = new ArrayList<>();
            types.add(new TypeClass("Aucun"));
            types.addAll(mainController.getCustomTypes());

            TypeClass choisi =(TypeClass)JOptionPane.showInputDialog(this, "Choisir un type: " + item.getNom(),
                    "Assigner un type", JOptionPane.QUESTION_MESSAGE, null, types.toArray(), item.getCustomType());
            if (choisi != null)
            {
                if (choisi.getNom().equals("Aucun"))
                {
                    item.setCustomType(null);
                }
                else
                    item.setCustomType(choisi);
                listJeu.repaint();
                listTravail.repaint();
                listMm.repaint();
                refreshAllLists();
            }
        }
        private void deleteItem(Item item)
        {
            if (item == null)
                return;
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Supprimer definitivement '" + item.getNom() + "' ?", "Attention",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION)
            {
                mainController.deleteItem(item);
                refreshAllLists();
                JOptionPane.showMessageDialog(this, "Element supprimé.");
            }
        }


        private void setSearchField(int searchField, String text)
        {
            if (text == null || text.isBlank()) {
                refreshAllLists();
                return;
            }
            List<Item> search = mainController.getAllItems();
            List<Item> filtre = new ArrayList<>();
            String lowerText = text.toLowerCase().trim();
            for (Item i : search)
            {
                switch(searchField)
                {
                    case 0:
                        if (i.getNom().toLowerCase().contains(lowerText))
                            filtre.add(i);
                        break;
                    case 1:
                        if (i.getCustomType() != null)
                        {
                            if (i.getCustomType().getNom().toLowerCase().contains(lowerText))
                            {
                                filtre.add(i);
                            }
                        }
                        break;
                    case 2:
                        String valeurATester = null;

                        if (i instanceof Jeu) {
                            valeurATester = ((Jeu) i).getGenre();
                        } else if (i instanceof Multimedia) {
                            valeurATester = ((Multimedia) i).getGenre();
                        } else if (i instanceof Travail) {
                            valeurATester = ((Travail) i).getLangage();
                        }

                        if (valeurATester != null && valeurATester.toLowerCase().contains(lowerText)) {
                            filtre.add(i);
                        }
                        break;
                }
            }

            updateTable(filtre);
            updateLists(filtre);
        }
        private void updateLists(List<Item> filteredList)
        {
            DefaultListModel<Item> modelJeu = (DefaultListModel<Item>) listJeu.getModel();
            DefaultListModel<Item> modelTravail = (DefaultListModel<Item>) listTravail.getModel();
            DefaultListModel<Item> modelMm = (DefaultListModel<Item>) listMm.getModel();

            modelJeu.clear();
            modelTravail.clear();
            modelMm.clear();

            for (Item item : filteredList)
            {
                if (item instanceof Jeu)
                {
                    modelJeu.addElement(item);
                }
                else if (item instanceof Travail)
                {
                    modelTravail.addElement(item);
                }
                else if (item instanceof Multimedia)
                {
                    modelMm.addElement(item);
                }
            }
        }
        private void updateTable(List<Item> list)
        {
            tableModel.setRowCount(0);
            for (Item item: list)
            {
                String date = "";
                String type = "";

                if (item.getDateAjoute() != null)
                {
                    date = item.getDateAjoute().format(currentFMT);
                }
                if (item.getCustomType() != null)
                {
                    type = item.getCustomType().getNom();
                }
                tableModel.addRow(new Object[]{
                        item.getId(),
                        item.getNom(),
                        date,
                        type,
                        item.getChemin()
                });
            }
        }


        private void showType(boolean show)
        {
            itemCellRenderer.setShowType(show);
            refreshCellRenderer();
        }

        private void showGenre(boolean show)
        {
            itemCellRenderer.setShowGenre(show);
            refreshCellRenderer();
        }

        private void refreshCellRenderer()
        {
            listJeu.setCellRenderer(null);
            listJeu.setCellRenderer(itemCellRenderer);
            listTravail.setCellRenderer(null);
            listTravail.setCellRenderer(itemCellRenderer);
            listMm.setCellRenderer(null);
            listMm.setCellRenderer(itemCellRenderer);
        }

        private static class ItemCellRenderer extends JPanel implements ListCellRenderer<Item>
        {
            private final JLabel labelNom = new JLabel();
            private final JLabel labelType = new JLabel();
            private final JLabel labelGenre = new JLabel();
            private boolean showType;
            private boolean showGenre;
            private boolean darkMode;

            public ItemCellRenderer(boolean showType, boolean showGenre, boolean darkMode)
            {
                this.showType = showType;
                this.showGenre = showGenre;
                this.darkMode = darkMode;
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
                setFocusable(false);
                labelNom.setFocusable(false);
                labelType.setFocusable(false);
                labelGenre.setFocusable(false);
                labelType.setFont(labelType.getFont().deriveFont(Font.PLAIN, 10f));
                labelGenre.setFont(labelGenre.getFont().deriveFont(Font.PLAIN, 10f));
                add(labelNom);
                add(labelType);
                add(labelGenre);
            }

            public void setShowType(boolean showType)
            {
                this.showType = showType;
            }

            public void setShowGenre(boolean showGenre)
            {
                this.showGenre = showGenre;
            }

            public void setDarkMode(boolean darkMode)
            {
                this.darkMode = darkMode;
            }

            @Override
            public Component getListCellRendererComponent(JList<? extends Item> list,
                                                          Item item, int index, boolean isSelected, boolean cellHasFocus)
            {
                labelNom.setText(item.getNom());
                labelNom.setIcon(null);

                if (item.getChemin() != null && !item.getChemin().isEmpty()) {
                    File file = new File(item.getChemin());
                    if (file.exists()) {
                        Icon icon = FileSystemView.getFileSystemView().getSystemIcon(file);
                        labelNom.setIcon(icon);
                        labelNom.setIconTextGap(10);
                    }
                }
                labelType.setVisible(showType);
                if (showType)
                {
                    if (item.getCustomType() != null)
                    {
                        labelType.setText("Type : " + item.getCustomType().getNom());
                    }
                    else
                    {
                        labelType.setText("Type : -");
                    }
                }
                labelGenre.setVisible(showGenre);
                if (showGenre)
                {
                    if (item instanceof Jeu)
                    {
                        labelGenre.setText("Genre : " + ((Jeu) item).getGenre());
                    }
                    else if (item instanceof Multimedia)
                    {
                        labelGenre.setText("Genre : " + ((Multimedia) item).getGenre());
                    }
                    else if (item instanceof Travail)
                    {
                        labelGenre.setText("Langage : " + ((Travail) item).getLangage());
                    }
                    else
                    {
                        labelGenre.setText("");
                    }
                }
                if (isSelected)
                {
                    setOpaque(true);
                    if (darkMode)
                    {
                        setBackground(new Color(100, 100, 120));
                    }
                    else
                    {
                        setBackground(new Color(70, 70, 70));
                    }
                    labelNom.setForeground(Color.WHITE);
                    labelType.setForeground(Color.WHITE);
                    labelGenre.setForeground(Color.WHITE);
                }
                else
                {
                    setOpaque(false);
                    labelNom.setForeground(list.getForeground());
                    labelType.setForeground(Color.GRAY);
                    labelGenre.setForeground(Color.GRAY);
                }
                labelNom.setOpaque(false);
                labelType.setOpaque(false);
                labelGenre.setOpaque(false);
                return this;
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
                    mainController.addJeu(dlg.getNom(), dlg.getChemin(), dlg.getExtra(), dlg.getDateCreation());
                    refreshAllLists();
                }
            }
            else if (e.getSource() == btnAjouterTravail)
            {
                CreateTravailDialog dlg = new CreateTravailDialog(this);
                if (dlg.showDialog())
                {
                    mainController.addTravail(dlg.getNom(), dlg.getChemin(), dlg.getExtra(), dlg.getDateCreation());
                    refreshAllLists();
                }
            }
            else if (e.getSource() == btnAjouterMm)
            {
                CreateMultimediaDialog dlg = new CreateMultimediaDialog(this);
                if (dlg.showDialog())
                {
                    mainController.addMultimedia(dlg.getNom(), dlg.getChemin(), dlg.getExtra(), dlg.getDateCreation());
                    refreshAllLists();
                }
            }
            else if (e.getSource() == btnParametres)
            {
                SettingsDialog dlg = new SettingsDialog(this, settingsController);
                if (dlg.showDialog())
                {
                    applyTheme(settingsController.getSettings().isDarkMode());
                    itemCellRenderer.setShowType(settingsController.getSettings().isShowType());
                    itemCellRenderer.setShowGenre(settingsController.getSettings().isShowGenre());
                    refreshAllLists();
                    refreshCellRenderer();
                }
            }
            else if (e.getSource() == menuConnexion)
            {
                LoginDialog dlg = new LoginDialog(this, authController);
                if (dlg.showDialog())
                {
                    mainController.restoreNextId();
                    refreshMenuState();
                    refreshAllLists();
                    applyTheme(settingsController.getSettings().isDarkMode());
                    showType(settingsController.getSettings().isShowType());
                    showGenre(settingsController.getSettings().isShowGenre());
                }
            }
            else if (e.getSource() == menuInscription)
            {
                RegisterDialog dlg = new RegisterDialog(this, authController);
                if (dlg.showDialog())
                {
                    mainController.restoreNextId();
                    refreshMenuState();
                    refreshAllLists();
                    showType(settingsController.getSettings().isShowType());
                }
            }
            else if (e.getSource() == menuDeconnexion)
            {
                authController.logout();
                refreshMenuState();
                refreshAllLists();
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
            else if (e.getSource() == menuDate)
            {
                String[] options = {"Standard (JJ/MM)", "ISO (AAAA-MM-JJ)", "Complet", "Annuler"};

                int choix = JOptionPane.showOptionDialog(
                        this,                             // 'this' plutôt que 'null' pour centrer sur la MainWindow
                        "Choisissez un format d'affichage pour les dates :",
                        "Configuration du format",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (choix != JOptionPane.CLOSED_OPTION && choix != 3)
                {
                    String pattern = switch (choix)
                    {
                        case 0 -> "dd/MM/yyyy HH:mm";
                        case 1 -> "yyyy-MM-dd HH:mm";
                        case 2 -> "EEEE d MMMM yyyy HH:mm";
                        default -> "dd/MM/yyyy HH:mm";
                    };

                    this.currentFMT = DateTimeFormatter.ofPattern(pattern);

                    refreshAllLists();

                    JOptionPane.showMessageDialog(this, "Format mis à jour !");
                }
            }
        }
    }