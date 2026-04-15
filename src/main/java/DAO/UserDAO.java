package DAO;

import modeles.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO {

    private static final String FILE_PATH = "users.dat";

    public List<User> findAll() {
        return readData();
    }

    public Optional<User> findByUsername(String username) {
        List<User> users = findAll();
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }

    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }

    public boolean insert(User user) {
        if (existsByUsername(user.getUsername())) {
            return false;
        }
        List<User> users = findAll();
        users.add(user);
        writeData(users);
        return true;
    }

    public boolean update(User updated) {
        List<User> users = findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(updated.getUsername())) {
                users.set(i, updated);
                writeData(users);
                return true;
            }
        }
        return false;
    }

    public boolean delete(String username) {
        List<User> users = findAll();
        User toRemove = null;
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                toRemove = u;
                break;
            }
        }
        if (toRemove != null) {
            users.remove(toRemove);
            writeData(users);
            return true;
        }
        return false;
    }

    // --- Sérialisation Java ---

    private void writeData(List<User> users) {
        try {
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(users);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<User> readData() {
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            ObjectInputStream ois = new ObjectInputStream(fis);
            List<User> users = (List<User>) ois.readObject();
            ois.close();
            fis.close();
            return users;
        } catch (IOException e) {
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}