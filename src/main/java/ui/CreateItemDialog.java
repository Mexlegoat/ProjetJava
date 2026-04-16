package ui;

import javax.swing.*;
import java.awt.*;

public class CreateItemDialog extends JDialog {

    protected JTextField nomField;
    protected JTextField pathField;
    protected JTextField extraField;

    // Donnees recuperables apres fermeture
    private String  nom = "";
    private String  chemin = "";
    private String  extra = "";
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
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        panel.add(new JLabel("Chemin :"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        pathField = new JTextField(15);
        panel.add(pathField, gbc);

        // Bouton Parcourir — sans listener
        JPanel browseRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        browseRow.add(new JButton("Parcourir..."));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(browseRow, gbc);

        // Boutons Confirmer / Annuler — sans listeners
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        btnRow.add(new JButton("Annuler"));
        btnRow.add(new JButton("Confirmer"));
        gbc.gridy = 4;
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
}
