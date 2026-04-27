package ui;

import controllers.SettingsController;
import modeles.UserSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Vue MVC : affichage des paramètres utilisateur.
 * La lecture et la sauvegarde des préférences sont déléguées à SettingsController.
 */
public class SettingsDialog {

    private final JDialog dialog;
    private final SettingsController settingsController;
    private boolean confirmed = false;

    private JRadioButton radioDark, radioLight;
    private JCheckBox checkShowType, checkShowGenre;
    private JRadioButton radioExecOn, radioExecOff;
    private JRadioButton radioNom, radioType, radioGenre;
    private JTextField pathField;

    public SettingsDialog(Frame owner, SettingsController settingsController) {
        this.settingsController = settingsController;
        dialog = new JDialog(owner, "Paramètres", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(owner);
        buildUI();
        loadSettings(); // Charge les valeurs actuelles depuis le contrôleur
    }

    private void buildUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Thème
        panel.add(sectionLabel("Thème"));
        radioDark = new JRadioButton("Mode Sombre");
        radioLight = new JRadioButton("Mode Clair");
        ButtonGroup themeGroup = new ButtonGroup();
        themeGroup.add(radioDark);
        themeGroup.add(radioLight);
        panel.add(radioDark);
        panel.add(radioLight);

        // Affichage
        panel.add(Box.createVerticalStrut(10));
        panel.add(sectionLabel("Affichage"));
        checkShowType = new JCheckBox("Afficher le Type");
        checkShowGenre = new JCheckBox("Afficher le Genre/Langage");
        panel.add(checkShowType);
        panel.add(checkShowGenre);

        // Double-clic
        panel.add(Box.createVerticalStrut(10));
        panel.add(sectionLabel("Exécution au double-clic"));
        radioExecOn = new JRadioButton("Activé");
        radioExecOff = new JRadioButton("Désactivé");
        ButtonGroup execGroup = new ButtonGroup();
        execGroup.add(radioExecOn);
        execGroup.add(radioExecOff);
        panel.add(radioExecOn);
        panel.add(radioExecOff);

        // Recherche
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

        // Chemin par défaut
        panel.add(Box.createVerticalStrut(10));
        panel.add(sectionLabel("Chemin par défaut"));
        pathField = new JTextField();
        pathField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        panel.add(pathField);

        // Boutons
        panel.add(Box.createVerticalStrut(15));
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelBtn = new JButton("Annuler");
        JButton saveBtn = new JButton("Enregistrer");

        cancelBtn.addActionListener(e -> dialog.dispose());
        saveBtn.addActionListener(e -> {
            // Collecte les données de la View et délègue au contrôleur
            int searchType = 0;
            if (radioType.isSelected()) searchType = 1;
            else if (radioGenre.isSelected()) searchType = 2;

            settingsController.saveSettings(
                    radioDark.isSelected(),
                    checkShowType.isSelected(),
                    checkShowGenre.isSelected(),
                    radioExecOn.isSelected(),
                    searchType
            );
            confirmed = true;
            dialog.dispose();
        });

        btnRow.add(cancelBtn);
        btnRow.add(saveBtn);
        panel.add(btnRow);

        dialog.setContentPane(new JScrollPane(panel));
    }

    /**
     * Initialise les widgets avec les valeurs courantes fournies par le contrôleur.
     */
    private void loadSettings() {
        UserSettings prefs = settingsController.getSettings();
        if (prefs.isDarkMode()) radioDark.setSelected(true);
        else radioLight.setSelected(true);

        checkShowType.setSelected(prefs.isShowType());
        checkShowGenre.setSelected(prefs.isShowGenre());

        if (prefs.isDoubleClickToExecute()) radioExecOn.setSelected(true);
        else radioExecOff.setSelected(true);

        switch (prefs.getSearchType()) {
            case 1: radioType.setSelected(true); break;
            case 2: radioGenre.setSelected(true); break;
            default: radioNom.setSelected(true); break;
        }
    }

    private JLabel sectionLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        return lbl;
    }

    public boolean showDialog() {
        dialog.setVisible(true);
        return confirmed;
    }
}
