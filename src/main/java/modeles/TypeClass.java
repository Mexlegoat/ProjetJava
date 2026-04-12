package main.java.modeles;
import java.io.Serializable;

public class TypeClass implements Serializable {
    private String nom;

    public TypeClass() {}

    public TypeClass(String nom) {
        this.nom = nom;
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    @Override
    public String toString() { return nom; }
}
