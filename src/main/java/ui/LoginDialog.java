package ui;

import javax.swing.*;
import java.awt.*;


public class LoginDialog extends JDialog {

    private JTextField    usernameField;
    private JPasswordField passwordField;

    // Donnees saisies recuperables apres fermeture
    private String  saisieUsername = "";
    private String  saisiePassword = "";
    private boolean confirmed      = false;

    public LoginDialog(Frame owner) {
        super(owner, "Connexion", true);
        setSize(360, 200);
        setLocationRelativeTo(owner);
        setResizable(false);
        buildUI();
    }

    private void buildUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 15, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 5, 7, 5);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        // Titre
        JLabel title = new JLabel("StartApp - Connexion", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Nom d'utilisateur
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        panel.add(new JLabel("Utilisateur :"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        // Mot de passe
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        panel.add(new JLabel("Mot de passe :"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // Boutons — sans listeners (eval 1)
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        btnRow.add(new JButton("Annuler"));
        btnRow.add(new JButton("Se connecter"));

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnRow, gbc);

        setContentPane(panel);
    }

    /** Affiche la boite et retourne true si l'utilisateur a confirme */
    public boolean showDialog() {
        setVisible(true);
        return confirmed;
    }

    public String getSaisieUsername() { return saisieUsername; }
    public String getSaisiePassword() { return saisiePassword; }

    /** Main de test autonome (sans JUnit) */
    public static void main(String[] args) {
        LoginDialog dlg = new LoginDialog(null);
        dlg.setVisible(true);
        System.out.println("Username saisi : " + dlg.getSaisieUsername());
        System.out.println("Password saisi : " + dlg.getSaisiePassword());
    }
}
