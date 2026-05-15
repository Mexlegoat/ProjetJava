package controllers;

import modeles.entity.*;
import services.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainController
{
    private final UserService userService;

    public MainController(UserService userService)
    {
        this.userService = userService;
    }

    public Jeu addJeu(String nom, String chemin, String genre, LocalDateTime dateCreation)
    {
        if (userService.getCurrentUser() == null) return null;

        Jeu j = new Jeu();
        j.setNom(nom);
        j.setChemin(chemin);
        j.setGenre(genre);
        j.setIconPath(chemin);
        j.setDateAjoute(dateCreation);
        j.initializeNewItem();

        addToCategory("Jeux", j);
        return j;
    }

    public Travail addTravail(String nom, String chemin, String langage, LocalDateTime dateCreation)
    {
        if (userService.getCurrentUser() == null) return null;

        Travail t = new Travail();
        t.setNom(nom);
        t.setChemin(chemin);
        t.setLangage(langage);
        t.setIconPath(chemin);
        t.initializeNewItem();
        t.setDateAjoute(dateCreation);
        addToCategory("Travail", t);
        return t;
    }

    public Multimedia addMultimedia(String nom, String chemin, String genre, LocalDateTime dateCreation)
    {
        if (userService.getCurrentUser() == null) return null;

        Multimedia m = new Multimedia();
        m.setNom(nom);
        m.setChemin(chemin);
        m.setGenre(genre);
        m.setIconPath(chemin);
        m.setDateAjoute(dateCreation);
        m.initializeNewItem();
        addToCategory("Multimedia", m);
        return m;
    }

    public List<Item> getAllItems()
    {
        List<Item> tous = new ArrayList<>();
        User user = userService.getCurrentUser();
        if (user == null) return tous;

        for (Categorie c : user.getCategories())
        {
            tous.addAll(c.getItems());
        }
        return tous;
    }

    public List<Item> getItemsByCategory(String nomCategorie)
    {
        User user = userService.getCurrentUser();
        if (user == null) return new ArrayList<>();

        for (Categorie c : user.getCategories())
        {
            if (c.getNom().equals(nomCategorie))
            {
                return c.getItems();
            }
        }
        return new ArrayList<>();
    }
    public void deleteItem(Item item) {
        User user = userService.getCurrentUser();
        if (user == null || item == null) return;

        for (Categorie c : user.getCategories()) {
            c.getItems().remove(item);
        }
    }
    public TypeClass addCustomType(String name)
    {
        User user = userService.getCurrentUser();
        if (user == null || name == null || name.isBlank()) return null;

        TypeClass t = new TypeClass(name);
        user.getUserCreatedTypes().add(t);
        userService.save();
        return t;
    }

    public List<TypeClass> getCustomTypes()
    {
        User user = userService.getCurrentUser();
        if (user == null) return new ArrayList<>();
        return user.getUserCreatedTypes();
    }

    public void restoreNextId()
    {
        int maxId = 0;
        for (Item item : getAllItems())
        {
            if (item.getId() > maxId)
            {
                maxId = item.getId();
            }
        }
        Item.setNextId(maxId + 1);
    }

    public void save()
    {
        userService.save();
    }

    public boolean isLoggedIn()
    {
        return userService.getCurrentUser() != null;
    }

    private void addToCategory(String nomCategorie, Item item)
    {
        User user = userService.getCurrentUser();
        if (user == null) return;

        Categorie cat = null;
        for (Categorie c : user.getCategories())
        {
            if (c.getNom().equals(nomCategorie))
            {
                cat = c;
                break;
            }
        }

        if (cat == null)
        {
            cat = new Categorie(nomCategorie);
            user.getCategories().add(cat);
        }

        cat.getItems().add(item);
    }
}