package modeles.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.io.Serializable;

public class Categorie implements Serializable {
    private String nom;
    private List<Item> items = new ArrayList<>();

    public Categorie() {}

    public Categorie(String nom) {
        this.nom = nom;
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }

    @Override
    public String toString() { return nom; }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Categorie)) return false;
        Categorie c = (Categorie) o;
        return Objects.equals(nom, c.nom);
    }

}
