package services;

import DAO.UserDAO;
import modeles.User;
import patterns.UserSession;
import utils.PasswordUtils;

public class UserService
{

    private final UserDAO userDAO = new UserDAO();

    public User getCurrentUser()
    {
        return UserSession.getInstance().getCurrentUser(); // Singleton
    }

    public boolean login(String username, String password)
    {
        return userDAO.findByUsername(username)
                .filter(u -> PasswordUtils.decrypt(u.getPassword()).equals(password))
                .map(u ->
                {
                    UserSession.getInstance().setCurrentUser(u); // Singleton
                    return true;
                })
                .orElse(false);
    }

    public boolean register(String username, String password)
    {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(PasswordUtils.encrypt(password));
        boolean inserted = userDAO.insert(newUser);
        if (inserted) UserSession.getInstance().setCurrentUser(newUser); // Singleton
        return inserted;
    }

    public void save()
    {
        User current = UserSession.getInstance().getCurrentUser();
        if (current != null)
        {
            userDAO.update(current);
        }
    }

    public void logout()
    {
        UserSession.getInstance().logout(); // Singleton
    }
}