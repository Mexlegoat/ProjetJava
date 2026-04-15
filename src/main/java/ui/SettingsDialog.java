package ui;

import javax.swing.*;
import java.awt.*;

public class SettingsDialog
{
    private final JDialog dialog;
    private boolean confirmed = false;

    private JRadioButton radioDark, radioLight;
    private JCheckBox checkShowType, checkShowGenre;
    private JRadioButton radioExecOn, radioExecOff;
    private JRadioButton radioNom, radioType, radioGenre;
    private JTextField pathField;

    public SettingsDialog(Frame owner)
    {
        dialog = new JDialog(owner, "Paramètres", true);
        dialog.setSize(400, 380);
        dialog.setLocationRelativeTo(owner);
        buildUI();
    }

    private void buildUI()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Theme
        panel.add(sectionLabel("Thème"));
        radioDark = new JRadioButton("Mode Sombre");
        radioLight = new JRadioButton("Mode Clair");
        ButtonGroup themeGroup = new ButtonGroup();
        themeGroup.add(radioDark);
        themeGroup.add(radioLight);
        panel.add(radioDark);
        panel.add(radioLight);

        // Visibility
        panel.add(Box.createVerticalStrut(10));
        panel.add(sectionLabel("Affichage"));
        checkShowType = new JCheckBox("Afficher le Type");
        checkShowGenre = new JCheckBox("Afficher le Genre/Langage");
        panel.add(checkShowType);
        panel.add(checkShowGenre);

        // Double-click
        panel.add(Box.createVerticalStrut(10));
        panel.add(sectionLabel("Exécution au double-clic"));
        radioExecOn = new JRadioButton("Activé");
        radioExecOff = new JRadioButton("Désactivé");
        ButtonGroup execGroup = new ButtonGroup();
        execGroup.add(radioExecOn);
        execGroup.add(radioExecOff);
        panel.add(radioExecOn);
        panel.add(radioExecOff);

        // Search type
        panel.add(Box.createVerticalStrut(10));
        panel.add(sectionLabel("Rechercher par"));
        radioNom = new JRadioButton("Nom");
        radioType = new JRadioButton("Type");
        radioGenre = new JRadioButton("Genre/Langage");
        ButtonGroup searchGroup = new ButtonGroup();
        searchGroup.add(radioNom);
        searchGroup.add(radioType);
        searchGroup.add(radioGenre);
        panel.add(radioNom);
        panel.add(radioType);
        panel.add(radioGenre);

        // Default path
        panel.add(Box.createVerticalStrut(10));
        panel.add(sectionLabel("Chemin par défaut"));
        pathField = new JTextField();
        pathField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        panel.add(pathField);

        // Buttons
        panel.add(Box.createVerticalStrut(15));
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Enregistrer");
        JButton cancelBtn = new JButton("Annuler");
        btnRow.add(cancelBtn);
        btnRow.add(saveBtn);
        panel.add(btnRow);

        dialog.setContentPane(new JScrollPane(panel));
    }
    private JLabel sectionLabel(String text)
    {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        return lbl;
    }
    public boolean showDialog()
    {
        dialog.setVisible(true);
        return confirmed;
    }
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            SettingsDialog dialog = new SettingsDialog(frame);
            dialog.showDialog();
        });
    }
}