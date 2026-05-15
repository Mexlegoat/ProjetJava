package Application;

import com.formdev.flatlaf.FlatDarculaLaf;
import controllers.AuthController;
import controllers.MainController;
import controllers.SettingsController;
import services.UserService;
import view.GUI.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            FlatDarculaLaf.setup();
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
        }

        SwingUtilities.invokeLater(() -> {


            // Model
            UserService userService = new UserService();
            // Controllers
            MainController mainController = new MainController(userService);
            // On injecte le service ET le nouveau handler d'authentification
            AuthController authController = new AuthController(userService);
            SettingsController settingsController = new SettingsController(userService);

            // View
            MainWindow window = new MainWindow(mainController, authController, settingsController);
            window.setVisible(true);
        });
    }
}