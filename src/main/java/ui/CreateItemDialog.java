package ui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class CreateItemDialog extends JDialog implements ActionListener {

    protected JTextField nomField;
    protected JTextField pathField;
    protected JTextField extraField;
    protected JButton Confirm;
    protected JButton Cancel;
    protected JCheckBox Lancer;

    private JPanel browseRow;
    protected JButton browseBtn;
    private JLabel labelChemin;

    // Donnees recuperables apres fermeture
    private String  nom = "";
    private String  chemin = "";
    private String  extra = "";
    private LocalDateTime dateCreation;
    private boolean confirmed = false;

    public CreateItemDialog(Frame owner, String categorie, String extraLabel) {
        super(owner, "Ajouter " + categorie, true);
        setSize(400, 240);
        setLocationRelativeTo(owner);
        buildUI(extraLabel);
    }

    private void buildUI(String extraLabel) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        // Nom
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        panel.add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        nomField = new JTextField(15);
        panel.add(nomField, gbc);

        // Champ extra (Genre, Langage, ...)
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        panel.add(new JLabel(extraLabel + " :"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        extraField = new JTextField(15);
        panel.add(extraField, gbc);

        // Chemin
        // Checkbox chemin
        Lancer = new JCheckBox("Ajouter un chemin");
        Lancer.addActionListener(this);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(Lancer, gbc);

// Chemin
        gbc.gridwidth = 1;
        labelChemin = new JLabel("Chemin :");
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.3;
        panel.add(labelChemin, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        pathField = new JTextField(15);
        panel.add(pathField, gbc);

        browseRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        browseBtn = new JButton("Parcourir..."); // On le stocke ici
        browseBtn.addActionListener(this);       // On ajoute le listener
        browseRow.add(browseBtn);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(browseRow, gbc);

        labelChemin.setVisible(false);
        pathField.setVisible(false);
        browseRow.setVisible(false);

        // Boutons Confirmer / Annuler — sans listeners
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        Cancel = new JButton("Annuler");
        Cancel.addActionListener(this);
        btnRow.add(Cancel);
        Confirm = new JButton("Confirmer");
        Confirm.addActionListener(this);
        btnRow.add(Confirm);
        gbc.gridy = 5;
        panel.add(btnRow, gbc);

        setContentPane(panel);
    }

    public boolean showDialog() {
        setVisible(true);
        return confirmed;
    }

    public String getNom()    { return nom; }
    public String getChemin() { return chemin; }
    public String getExtra()  { return extra; }
    public LocalDateTime getDateCreation() {return dateCreation;}

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == Cancel)
        {
            dispose();
        }
        else if (e.getSource() == Lancer)
        {
            boolean visible = Lancer.isSelected();
            labelChemin.setVisible(visible);
            pathField.setVisible(visible);
            browseRow.setVisible(visible);
            pack();
        }
        else if (e.getSource() == Confirm)
        {
            nom = nomField.getText().trim();
            extra = extraField.getText().trim();

            if (nom.isEmpty() || extra.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Veuillez remplir les champs nécéssaires.");
                return;
            }

            if (Lancer.isSelected())
            {
                chemin = pathField.getText().trim();
                if (chemin.isEmpty())
                {
                    JOptionPane.showMessageDialog(this, "Veuillez remplir le champ: chemin");
                    return;
                }
            }
            dateCreation = LocalDateTime.now();

            confirmed = true;
            dispose();
        }
        else if (e.getSource() == browseBtn)
        {
            JFileChooser choix = new JFileChooser();
            choix.setDialogTitle("Choisir un fichier ou un raccourci");

            FileNameExtensionFilter filter =
                    new FileNameExtensionFilter("Applications & Raccourcis", "exe", "lnk", "bat");
            choix.setFileFilter(filter);
            int result = choix.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String selectedPath = choix.getSelectedFile().getAbsolutePath();
                pathField.setText(selectedPath);

                if (nomField.getText().trim().isEmpty()) {
                    String fileName = choix.getSelectedFile().getName();
                    if (fileName.contains(".")) {
                        fileName = fileName.substring(0, fileName.lastIndexOf('.'));
                    }
                    nomField.setText(fileName);

                }
            }
        }
    }
}
