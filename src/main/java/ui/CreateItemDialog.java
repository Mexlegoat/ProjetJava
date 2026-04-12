package main.java.ui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/** Generic dialog for Travail / Multimedia (name + path + extra field) */
public class CreateItemDialog
{
    private final JDialog dialog;
    private JTextField nomField, pathField, extraField;
    private boolean confirmed = false;

    private String nom, chemin, extra;

    public CreateItemDialog(Frame owner, String category, String extraLabel)
    {
        dialog = new JDialog(owner, "Ajouter " + category, true);
        dialog.setSize(400, 260);
        dialog.setLocationRelativeTo(owner);
        buildUI(extraLabel);
    }

    private void buildUI(String extraLabel)
    {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Ligne 0 : Nom
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        panel.add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1; nomField = new JTextField();
        panel.add(nomField, gbc);

        // Ligne 1 : Extra (Genre, etc.)
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel(extraLabel + " :"), gbc);
        gbc.gridx = 1; extraField = new JTextField();
        panel.add(extraField, gbc);

        // Ligne 2 : Checkbox
        JCheckBox execCheck = new JCheckBox("Application executable?");
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(execCheck, gbc);

        // Ligne 3 : Le groupe Chemin + Parcourir (Caché par défaut)
        JPanel pathPanel = new JPanel(new BorderLayout(5, 0));
        pathField = new JTextField();
        JButton browseBtn = new JButton("Parcourir...");
        pathPanel.add(pathField, BorderLayout.CENTER);
        pathPanel.add(browseBtn, BorderLayout.EAST);

        pathPanel.setVisible(false); // On cache le groupe entier
        gbc.gridy = 3;
        panel.add(pathPanel, gbc);

        // --- Logique d'affichage ---
        execCheck.addActionListener(e ->
        {
            pathPanel.setVisible(execCheck.isSelected());
            dialog.revalidate();
            dialog.repaint();
            dialog.pack();
        });

        browseBtn.addActionListener(e -> browse());

        // Ligne 4 : Boutons Confirmer / Annuler
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton ok = new JButton("Confirmer");
        JButton cancel = new JButton("Annuler");
        ok.addActionListener(e -> confirm());
        cancel.addActionListener(e -> dialog.dispose());
        btnRow.add(cancel); btnRow.add(ok);

        gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(btnRow, gbc);

        dialog.setContentPane(panel);
    }

    private void browse()
    {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Exécutables (*.exe, *.bat, *.lnk)", "exe", "bat", "cmd", "lnk", "url"));
        if (fc.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
            pathField.setText(fc.getSelectedFile().getAbsolutePath());
        }
    }

    private void confirm()
    {
        if (nomField.getText().isBlank() || extraField.getText().isBlank())
        {
            JOptionPane.showMessageDialog(dialog, "Veuillez remplir tous les champs !");
            return;
        }
        nom = nomField.getText().trim();
        extra = extraField.getText().trim();
        chemin = pathField.getText().trim();
        confirmed = true;
        dialog.dispose();
    }

    public boolean showDialog()
    {
        dialog.setVisible(true);
        return confirmed;
    }

    public String getNom()    { return nom; }
    public String getChemin() { return chemin; }
    public String getExtra()  { return extra; }
}
