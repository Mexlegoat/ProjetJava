package view.console;

import modeles.entity.*;
import view.IViewItems;
import view.interfaces.IAuthView;

public class DirectoryConsole implements IViewItems, IAuthView
{

    @Override
    public void showMessage(String message)
    {
        System.out.println(message);
    }

    @Override
    public void showErrorMessage(String message)
    {
        System.out.println(message);
    }

    @Override
    public void showCreated(Jeu e)
    {
        System.out.println("Id attribué : " + e.getId());
        System.out.println("Item créé : " + e.getNom());
        if(!e.getChemin().isBlank() || !e.getChemin().isEmpty())
            System.out.println("Chemin : " + e.getChemin());
        System.out.println("Genre du Jeu: " + e.getGenre());
    }
    public void showCreated(Travail e)
    {
        System.out.println("Id attribué : " + e.getId());
        System.out.println("Item créé : " + e.getNom());
        if(!e.getChemin().isBlank() || !e.getChemin().isEmpty())
            System.out.println("Chemin : " + e.getChemin());
        System.out.println("Langage.s de l'application: " + e.getLangage());
    }
    public void showCreated(Multimedia e)
    {
        System.out.println("Id attribué : " + e.getId());
        System.out.println("Item créé : " + e.getNom());
        if(!e.getChemin().isBlank() || !e.getChemin().isEmpty())
            System.out.println("Chemin : " + e.getChemin());
        System.out.println("Genre de l'application: " + e.getGenre());
    }

    @Override
    public void showUser(User u)
    {
        System.out.println("Utilisateur : " + u.getUsername());
        System.out.println("Mot de passe : " + u.getPassword() + "(crypté)");
    }
}
