package ui;

import controllers.AuthController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterDialog extends JDialog implements ActionListener {

    private final AuthController authController;
    private JLabel labelErreur;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmField;
    private JButton Register;
    private JButton Cancel;
    private boolean confirmed = false;

    public RegisterDialog(Frame owner, AuthController authController) {
        super(owner, "Inscription", true);
        this.authController = authController;
        setSize(360, 230);
        setLocationRelativeTo(owner);
        setResizable(false);
        buildUI();
    }

    private void buildUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 15, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 5, 7, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("StartApp - Inscription", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        panel.add(new JLabel("Utilisateur :"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        panel.add(new JLabel("Mot de passe :"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.3;
        panel.add(new JLabel("Confirmer Mot de passe :"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        confirmField = new JPasswordField(15);
        panel.add(confirmField, gbc);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        Cancel = new JButton("Annuler");
        Cancel.addActionListener(this);
        btnRow.add(Cancel);
        Register = new JButton("S'inscrire");
        Register.addActionListener(this);
        btnRow.add(Register);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(btnRow, gbc);


        setContentPane(panel);
    }

    public boolean showDialog() {
        setVisible(true);
        return confirmed;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Cancel) {
            dispose();
        } else if (e.getSource() == Register) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmField.getPassword());

            String erreur = authController.register(username, password, confirmPassword);
            if (erreur == null) {
                confirmed = true;
                JOptionPane.showMessageDialog(this, "Vous êtes inscrit!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, erreur);
            }
        }
    }
}
