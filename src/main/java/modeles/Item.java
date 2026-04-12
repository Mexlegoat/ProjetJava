package main.java.modeles;
import java.awt.Desktop;
import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Item implements Serializable {
    public static int nextId = 1;

    private int id;
    private String nom;
    private String chemin;
    private String iconPath;
    private LocalDateTime dateAjoute;
    private TypeClass customType;

    public Item() {}

    public void initializeNewItem() {
        this.id = nextId++;
        this.dateAjoute = LocalDateTime.now();
    }

    public static void setNextId(int newId) { nextId = newId; }

    public void launch() {
        if (chemin != null && !chemin.isBlank()) {
            try {
                Desktop.getDesktop().open(new File(chemin));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Getters / Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getChemin() { return chemin; }
    public void setChemin(String chemin) { this.chemin = chemin; }
    public String getIconPath() { return iconPath; }
    public void setIconPath(String iconPath) { this.iconPath = iconPath; }
    public LocalDateTime getDateAjoute() { return dateAjoute; }
    public void setDateAjoute(LocalDateTime d) { this.dateAjoute = d; }
    public TypeClass getCustomType() { return customType; }
    public void setCustomType(TypeClass t) { this.customType = t; }

    @Override
    public String toString() { return nom; }
}
