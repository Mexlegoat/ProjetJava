package main.java.modeles;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Categorie implements Serializable {
    private String nom;
    private List<Item> items = new ArrayList<>();

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
}
