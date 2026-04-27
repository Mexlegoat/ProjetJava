package Application;

import controllers.AuthController;
import controllers.MainController;
import controllers.SettingsController;
import services.UserService;
import ui.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            // Model
            UserService userService = new UserService();

            // Controllers
            MainController mainController = new MainController(userService);
            AuthController authController = new AuthController(userService);
            SettingsController settingsController = new SettingsController(userService);

            // View
            MainWindow window = new MainWindow(mainController, authController, settingsController);
            window.setVisible(true);
        });
    }
}
