package controllers;

import authentication.HandlerLoginAbstrait;
import services.UserService;
import view.console.DirectoryConsole;

public class AuthController {
    private DirectoryConsole cons;
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
        this.cons = new DirectoryConsole();
    }

    public String login(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            return "Veuillez remplir tous les champs.";
        }
        if (!userService.login(username, password)) {
            return "Erreur lors du chargement du profil utilisateur.";
        }

        return null; // succès
    }

    public String register(String username, String password, String confirmPassword) {
        if (username == null || username.trim().isEmpty()
                || password == null || password.isEmpty()
                || confirmPassword == null || confirmPassword.isEmpty()) {
            return "Veuillez remplir tous les champs.";
        }
        if (!password.equals(confirmPassword)) {
            return "Les mots de passe ne correspondent pas.";
        }

        if (userService.userExisting(username)) {
            return "Cet utilisateur existe déjà.";
        }
        if (!userService.register(username, password)) {
            return "Erreur lors de l'inscription.";
        }
        cons.showUser(userService.getCurrentUser());
        return null; // succès
    }

    public void logout() {
        userService.logout();
    }

    public boolean isLoggedIn() {
        return userService.getCurrentUser() != null;
    }
}