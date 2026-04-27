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
            User user = userDAO.findByUsername(username).orElse(null);

            if (user == null)
            {
                return false;
            }

            if (!PasswordUtils.decrypt(user.getPassword()).equals(password))
            {
                return false;
            }

            UserSession.getInstance().setCurrentUser(user);
            return true;
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
    public boolean userExisting(String username)
    {
        return userDAO.existsByUsername(username);
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