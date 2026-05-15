package view.GUI;

import controllers.SettingsController;
import modeles.entity.UserSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Vue MVC : affichage des paramètres utilisateur.
 * La lecture et la sauvegarde des préférences sont déléguées à SettingsController.
 */
public class SettingsDialog implements ActionListener {

    private final JDialog dialog;
    private final SettingsController settingsController;
    private boolean confirmed = false;

    private JButton cancelBtn, saveBtn;
    private JRadioButton radioDark, radioLight;
    private JCheckBox checkShowType, checkShowGenre;
    private JRadioButton radioExecOn, radioExecOff;
    private JRadioButton radioNom, radioType, radioGenre;

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
        radioGenre = new JRadioButton("Genre");
        ButtonGroup searchGroup = new ButtonGroup();
        searchGroup.add(radioNom);
        searchGroup.add(radioType);
        searchGroup.add(radioGenre);
        panel.add(radioNom);
        panel.add(radioType);
        panel.add(radioGenre);

        // Boutons
        panel.add(Box.createVerticalStrut(15));
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cancelBtn = new JButton("Annuler");
        saveBtn = new JButton("Enregistrer");

        cancelBtn.addActionListener(this);
        saveBtn.addActionListener(this);

        btnRow.add(cancelBtn);
        btnRow.add(saveBtn);
        panel.add(btnRow);

        dialog.setContentPane(new JScrollPane(panel));
    }

    private void loadSettings() {
        UserSettings prefs = settingsController.getSettings();
        if (prefs.isDarkMode()) radioDark.setSelected(true);
        else radioLight.setSelected(true);

        checkShowType.setSelected(prefs.isShowType());
        checkShowGenre.setSelected(prefs.isShowGenre());

        if (prefs.isDoubleClickToExecute()) radioExecOn.setSelected(true);
        else radioExecOff.setSelected(true);

        if (prefs.getSearchType() == 1) {
            radioType.setSelected(true);
        } else if(prefs.getSearchType() == 0) {
            radioNom.setSelected(true);
        } else
            radioGenre.setSelected(true);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelBtn)
        {
            dialog.dispose();
        }
        else if (e.getSource() == saveBtn)
        {
            int searchType = 0;
            if (radioType.isSelected())
                searchType = 1;
            else if (radioGenre.isSelected())
                searchType = 2;

            settingsController.saveSettings(
                    radioDark.isSelected(),
                    checkShowType.isSelected(),
                    checkShowGenre.isSelected(),
                    radioExecOn.isSelected(),
                    searchType
            );
            confirmed = true;
            dialog.dispose();
        }
    }
}
