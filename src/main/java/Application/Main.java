package Application;

import ui.LoginDialog;
import ui.MainWindow;

import javax.swing.*;

/**
 * Point d'entree de l'application.
 * Affiche d'abord la boite de dialogue de login, puis la fenetre principale.
 * Aucune logique metier ici (eval 1 : coquille).
 */
public class Main {
    public static void main(String[] args) {
        new MainWindow().setVisible(true);
    }
}
