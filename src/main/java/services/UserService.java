package services;

import modeles.dao.UserDAO;
import authentication.HandlerLoginProperties;
import modeles.entity.User;
import patterns.UserSession;
import utils.PasswordUtils;

import java.io.*;
import java.util.Optional;
import java.util.Properties;

public class UserService {

    private final UserDAO userDAO = new UserDAO();
    private static final String PROPERTIES_FOLDER = "resources";
    private final HandlerLoginProperties handler;

    public UserService() {
        this.handler = new HandlerLoginProperties(PROPERTIES_FOLDER);
    }
    public User getCurrentUser() {
        return UserSession.getInstance().getCurrentUser();
    }

    /**
     * Tente de connecter l'utilisateur en utilisant le DAO (fichier .dat)
     */
    public boolean login(String username, String password) {
        if (!handler.login(username, password)) {
            return false;
        }

        User user = userDAO.findByUsername(username).orElse(null);

        if (user == null) {
            user = loadFromProperties(username).orElse(null);
        }

        if (user == null) {
            user = new User();
            user.setUsername(username);
        }

        UserSession.getInstance().setCurrentUser(user);
        return true;
    }

    /**
     * Inscrit l'utilisateur dans le .dat ET crée le fichier .properties
     */
    public boolean register(String username, String password) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(PasswordUtils.encrypt(password));

        boolean insertedInDao = userDAO.insert(newUser);

        if (insertedInDao) {
            saveToProperties(newUser);

            UserSession.getInstance().setCurrentUser(newUser);
            return true;
        }

        return false;
    }

    /**
     * Charge un utilisateur depuis son fichier .properties
     */
    public Optional<User> loadFromProperties(String username) {
        File userFile = new File(PROPERTIES_FOLDER, username + ".properties");
        if (!userFile.exists()) {
            System.out.println("Fichier non trouvé : " + userFile.getAbsolutePath());
            return Optional.empty();
        }

        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(userFile)) {
            props.load(in);
            User user = new User();
            user.setUsername(props.getProperty("username"));
            user.setPassword(props.getProperty("password"));
            return Optional.of(user);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Crée le fichier .properties individuel
     */
    private boolean saveToProperties(User user) {
        Properties props = new Properties();
        props.setProperty("username", user.getUsername());
        props.setProperty("password", user.getPassword());

        File dir = new File(PROPERTIES_FOLDER);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            System.out.println("Création du dossier 'resources' : " + created);
        }

        File userFile = new File(dir, user.getUsername() + ".properties");

        try (FileOutputStream out = new FileOutputStream(userFile)) {
            props.store(out, "User Data Backup");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean userExisting(String username) {
        return userDAO.existsByUsername(username) || new File(PROPERTIES_FOLDER, username + ".properties").exists();
    }

    public void save() {
        User current = UserSession.getInstance().getCurrentUser();
        if (current != null) {
            userDAO.update(current);
            saveToProperties(current);
        }
    }

    public void logout() {
        UserSession.getInstance().logout();
    }
}