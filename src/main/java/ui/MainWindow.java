package main.java.ui;

import main.java.modeles.*;
import main.java.services.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class MainWindow extends JFrame
{
    private final UserService userService;
    private final User currentUser;

    private DefaultListModel<Item> modelJeu = new DefaultListModel<>();
    private DefaultListModel<Item> modelTravail = new DefaultListModel<>();
    private DefaultListModel<Item> modelMm = new DefaultListModel<>();

    private JList<Item> listJeu, listTravail, listMm;
    private JTextField searchField;
    private DefaultTableModel tableModel;
    private JTable dataGrid;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public MainWindow(User user, UserService service)
    {
        this.currentUser = user;
        this.userService = service;
        setTitle("StartApp");
        setSize(950, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buildUI();
        loadUserData();
    }

    private void buildUI()
    {
        JPanel root = new JPanel(new BorderLayout(5, 5));
        root.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // --- Search bar (top) ---
        searchField = new JTextField();
        searchField.setToolTipText("Rechercher une application...");
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener()
        {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { applyFilter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { applyFilter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { applyFilter(); }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("  Recherche: "), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        root.add(topPanel, BorderLayout.NORTH);

        // --- Three columns center ---
        JPanel center = new JPanel(new GridLayout(1, 3, 5, 0));
        listJeu     = buildListPanel(center, "Jeux",       modelJeu,     "Jeux");
        listTravail = buildListPanel(center, "Travail",    modelTravail, "Travail");
        listMm      = buildListPanel(center, "Multimédia", modelMm,      "Multimedia");
        root.add(center, BorderLayout.CENTER);

        // --- Bottom: table ---
        String[] cols = {"ID", "Nom", "Date Ajout", "Type Perso", "Chemin"};
        tableModel = new DefaultTableModel(cols, 0)
        {
            @Override
            public boolean isCellEditable(int r, int c)
            {
                return false;
            }
        };
        dataGrid = new JTable(tableModel);
        dataGrid.getColumnModel().getColumn(0).setPreferredWidth(40);
        dataGrid.getColumnModel().getColumn(1).setPreferredWidth(120);
        dataGrid.getColumnModel().getColumn(2).setPreferredWidth(130);
        dataGrid.getColumnModel().getColumn(3).setPreferredWidth(100);
        dataGrid.getColumnModel().getColumn(4).setPreferredWidth(250);

        JScrollPane tableScroll = new JScrollPane(dataGrid);
        tableScroll.setPreferredSize(new Dimension(0, 180));

        // --- Button row ---
        JPanel btnRow = new JPanel(new BorderLayout());

        JButton settingsBtn = new JButton("Paramètres");

        JButton addTypeBtn = new JButton("+ Type");
        addTypeBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                addType();
            }
        });

        btnRow.add(settingsBtn, BorderLayout.WEST);
        btnRow.add(addTypeBtn, BorderLayout.EAST);

        JPanel southStack = new JPanel(new BorderLayout());
        southStack.add(btnRow, BorderLayout.NORTH);
        southStack.add(tableScroll, BorderLayout.CENTER);
        root.add(southStack, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private JList<Item> buildListPanel(JPanel parent, String title,
                                       DefaultListModel<Item> model,
                                       String catName)
    {
        JPanel col = new JPanel(new BorderLayout());
        col.setBorder(BorderFactory.createTitledBorder(title));

        JList<Item> list = new JList<>(model);
        list.setCellRenderer(new ItemCellRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        list.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 2 && currentUser.getPreferences().isDoubleClickToExecute())
                {
                    Item item = list.getSelectedValue();
                    if (item != null)
                    {
                        if (item.getChemin() != null && !item.getChemin().isBlank())
                        {
                            item.launch();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(MainWindow.this,
                                    "L'application " + item.getNom() + " n'est pas exécutable !");
                        }
                    }
                }
            }
        });

        JPopupMenu popup = new JPopupMenu();

        JMenuItem assignTypeItem = new JMenuItem("Assigner un type...");
        assignTypeItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                assignType(list.getSelectedValue());
            }
        });

        JMenuItem deleteItem = new JMenuItem("Supprimer");
        deleteItem.setForeground(Color.RED);
        deleteItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                deleteItem(list.getSelectedValue(), model);
            }
        });

        popup.add(assignTypeItem);
        popup.add(deleteItem);
        list.setComponentPopupMenu(popup);

        col.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton addBtn = new JButton("+");
        addBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (catName.equals("Jeux"))
                {
                    openCreateGame();
                }
                else if (catName.equals("Travail"))
                {
                    openCreateTravail();
                }
                else
                {
                    openCreateMm();
                }
            }
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(addBtn);
        col.add(btnPanel, BorderLayout.SOUTH);

        parent.add(col);
        return list;
    }

    private void loadUserData()
    {
        modelJeu.clear();
        modelTravail.clear();
        modelMm.clear();

        if (currentUser.getCategories() != null)
        {
            for (Categorie cat : currentUser.getCategories())
            {
                for (Item item : cat.getItems())
                {
                    switch (cat.getNom())
                    {
                        case "Jeux":
                            modelJeu.addElement(item);
                            break;
                        case "Travail":
                            modelTravail.addElement(item);
                            break;
                        case "Multimedia":
                            modelMm.addElement(item);
                            break;
                    }
                }
            }
        }
        syncIdCounter();
        refreshTable(allItems());
    }

    private List<Item> allItems()
    {
        List<Item> all = new ArrayList<>();
        for (int i = 0; i < modelJeu.size(); i++)
        {
            all.add(modelJeu.get(i));
        }
        for (int i = 0; i < modelTravail.size(); i++)
        {
            all.add(modelTravail.get(i));
        }
        for (int i = 0; i < modelMm.size(); i++)
        {
            all.add(modelMm.get(i));
        }
        return all;
    }

    private void refreshTable(List<Item> items)
    {
        tableModel.setRowCount(0);
        for (Item it : items)
        {
            String typeNom = "";
            if (it.getCustomType() != null)
            {
                typeNom = it.getCustomType().getNom();
            }
            String dateStr = "";
            if (it.getDateAjoute() != null)
            {
                dateStr = it.getDateAjoute().format(FMT);
            }
            tableModel.addRow(new Object[]{it.getId(), it.getNom(), dateStr, typeNom, it.getChemin()});
        }
    }

    private void applyFilter()
    {
        String filtre = searchField.getText().toLowerCase();
        if (filtre.isBlank())
        {
            refreshTable(allItems());
        }
        else
        {
            List<Item> filtered = new ArrayList<>();
            for (Item item : allItems())
            {
                if (matchesFilter(item, filtre))
                {
                    filtered.add(item);
                }
            }
            refreshTable(filtered);
        }
    }

    private boolean matchesFilter(Item app, String text)
    {
        int searchType = currentUser.getPreferences().getSearchType();

        if (searchType == 1)
        {
            return app.getCustomType() != null &&
                    app.getCustomType().getNom().toLowerCase().contains(text);
        }
        else if (searchType == 2)
        {
            if (app instanceof Jeu)
            {
                Jeu j = (Jeu) app;
                if (j.getGenre() != null)
                {
                    return j.getGenre().toLowerCase().contains(text);
                }
            }
            if (app instanceof Multimedia)
            {
                Multimedia mm = (Multimedia) app;
                if (mm.getGenre() != null)
                {
                    return mm.getGenre().toLowerCase().contains(text);
                }
            }
            if (app instanceof Travail)
            {
                Travail t = (Travail) app;
                if (t.getLangage() != null)
                {
                    return t.getLangage().toLowerCase().contains(text);
                }
            }
            return false;
        }
        else
        {
            return app.getNom() != null && app.getNom().toLowerCase().contains(text);
        }
    }

    private void openCreateGame()
    {
        CreateGameDialog dlg = new CreateGameDialog(this);
        if (dlg.showDialog())
        {
            Jeu j = new Jeu();
            j.setNom(dlg.getNom());
            j.setChemin(dlg.getChemin());
            j.setGenre(dlg.getExtra());
            j.setIconPath(dlg.getChemin());
            j.initializeNewItem();
            modelJeu.addElement(j);
            addToCategory("Jeux", j);
            userService.save();
            refreshTable(allItems());
        }
    }

    private void openCreateTravail()
    {
        CreateItemDialog dlg = new CreateItemDialog(this, "Travail", "Langage");
        if (dlg.showDialog())
        {
            Travail t = new Travail();
            t.setNom(dlg.getNom());
            t.setChemin(dlg.getChemin());
            t.setLangage(dlg.getExtra());
            t.setIconPath(dlg.getChemin());
            t.initializeNewItem();
            modelTravail.addElement(t);
            addToCategory("Travail", t);
            userService.save();
            refreshTable(allItems());
        }
    }

    private void openCreateMm()
    {
        CreateItemDialog dlg = new CreateItemDialog(this, "Multimédia", "Genre/Type");
        if (dlg.showDialog())
        {
            Multimedia mm = new Multimedia();
            mm.setNom(dlg.getNom());
            mm.setChemin(dlg.getChemin());
            mm.setGenre(dlg.getExtra());
            mm.setIconPath(dlg.getChemin());
            mm.initializeNewItem();
            modelMm.addElement(mm);
            addToCategory("Multimedia", mm);
            userService.save();
            refreshTable(allItems());
        }
    }

    private void addToCategory(String catName, Item item)
    {
        Categorie cat = null;
        for (Categorie c : currentUser.getCategories())
        {
            if (c.getNom().equals(catName))
            {
                cat = c;
                break;
            }
        }
        if (cat == null)
        {
            cat = new Categorie();
            cat.setNom(catName);
            currentUser.getCategories().add(cat);
        }
        cat.getItems().add(item);
    }

    private void deleteItem(Item item, DefaultListModel<Item> model)
    {
        if (item == null)
        {
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Supprimer définitivement '" + item.getNom() + "' ?",
                "Attention", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION)
        {
            model.removeElement(item);
            for (Categorie c : currentUser.getCategories())
            {
                c.getItems().remove(item);
            }
            userService.save();
            refreshTable(allItems());
        }
    }

    private void assignType(Item item)
    {
        if (item == null)
        {
            return;
        }
        List<TypeClass> types = new ArrayList<>();
        types.add(new TypeClass("(Aucun)"));
        for (TypeClass tc : currentUser.getUserCreatedTypes())
        {
            types.add(tc);
        }

        TypeClass chosen = (TypeClass) JOptionPane.showInputDialog(
                this, "Choisir un type pour : " + item.getNom(),
                "Assigner un type", JOptionPane.QUESTION_MESSAGE, null,
                types.toArray(), item.getCustomType());

        if (chosen != null)
        {
            if (chosen.getNom().equals("(Aucun)"))
            {
                item.setCustomType(null);
            }
            else
            {
                item.setCustomType(chosen);
            }
            userService.save();
            listJeu.repaint();
            listTravail.repaint();
            listMm.repaint();
            refreshTable(allItems());
        }
    }

    private void addType()
    {
        String name = JOptionPane.showInputDialog(this, "Nom du nouveau type :", "Créer un Type", JOptionPane.PLAIN_MESSAGE);
        if (name != null && !name.isBlank())
        {
            TypeClass t = new TypeClass(name);
            currentUser.getUserCreatedTypes().add(t);
            userService.save();
            JOptionPane.showMessageDialog(this, "Type '" + name + "' ajouté avec succès !");
        }
    }


    private void syncIdCounter()
    {
        int maxId = 0;
        List<Item> items = allItems();
        for (int i = 0; i < items.size(); i++)
        {
            if (items.get(i).getId() > maxId)
            {
                maxId = items.get(i).getId();
            }
        }
        Item.setNextId(maxId + 1);
    }

    static class ItemCellRenderer extends DefaultListCellRenderer
    {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                                                      int index, boolean isSelected, boolean hasFocus)
        {
            JPanel panel = new JPanel(new BorderLayout(5, 2));
            panel.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));

            if (value instanceof Item)
            {
                Item item = (Item) value;

                JLabel nameLbl = new JLabel(item.getNom());
                nameLbl.setFont(new Font("Arial", Font.BOLD, 13));
                String sub = "";

                if (item instanceof Jeu)
                {
                    Jeu j = (Jeu) item;
                    if (j.getGenre() != null)
                    {
                        sub = "Genre: " + j.getGenre();
                    }
                }
                else if (item instanceof Travail)
                {
                    Travail t = (Travail) item;
                    if (t.getLangage() != null)
                    {
                        sub = "Langage: " + t.getLangage();
                    }
                }
                else if (item instanceof Multimedia)
                {
                    Multimedia mm = (Multimedia) item;
                    if (mm.getGenre() != null)
                    {
                        sub = "Genre: " + mm.getGenre();
                    }
                }

                if (item.getCustomType() != null)
                {
                    if (sub.isEmpty())
                    {
                        sub = "Type: " + item.getCustomType().getNom();
                    }
                    else
                    {
                        sub = sub + "  |  Type: " + item.getCustomType().getNom();
                    }
                }

                JLabel subLbl = new JLabel(sub);
                subLbl.setFont(new Font("Arial", Font.PLAIN, 10));
                subLbl.setForeground(Color.GRAY);
                panel.add(nameLbl, BorderLayout.CENTER);
                panel.add(subLbl, BorderLayout.SOUTH);

                if (isSelected)
                {
                    panel.setBackground(list.getSelectionBackground());
                    nameLbl.setForeground(list.getSelectionForeground());
                    subLbl.setForeground(list.getSelectionForeground());
                }
                else
                {
                    panel.setBackground(list.getBackground());
                    nameLbl.setForeground(list.getForeground());
                    subLbl.setForeground(Color.GRAY);
                }
            }

            return panel;
        }
    }
}