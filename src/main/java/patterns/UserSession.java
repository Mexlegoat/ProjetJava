package main.java.patterns;

import main.java.modeles.User;

/**
 * Design Pattern : SINGLETON
 * Stocke l'utilisateur connecté de façon unique et accessible partout
 * dans l'application, sans passer currentUser de classe en classe.
 *
 * Usage :
 *   UserSession.getInstance().setCurrentUser(user);
 *   User u = UserSession.getInstance().getCurrentUser();
 */
public class UserSession
{

    private static UserSession instance;
    private User currentUser;

    private UserSession() {}

    public static UserSession getInstance()
    {
        if (instance == null)
        {
            instance = new UserSession();
        }
        return instance;
    }

    public User getCurrentUser()
    {
        return currentUser;
    }

    public void setCurrentUser(User user)
    {
        this.currentUser = user;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void logout() {
        currentUser = null;
    }
}