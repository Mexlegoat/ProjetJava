package main.java.Application;

import main.java.ui.MainWindow;
import main.java.modeles.User;
import main.java.services.UserService;

import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {
        User user = new User();
        user.setUsername("test");

        UserService service = new UserService();

        new MainWindow(user, service).setVisible(true);
    }
}