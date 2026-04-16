package modeles;

import java.util.Objects;

public class Jeu extends Item {
    private String genre;
    private float prix;

    public Jeu() {}

    public Jeu(String nom, String chemin, String genre, float prix) {
        super(nom, chemin);
        this.genre = genre;
        this.prix = prix;
    }

    public String getGenre() { return genre; }
    public void setGenre(String g) { this.genre = g; }
    public float getPrix() { return prix; }
    public void setPrix(float prix) { this.prix = prix; }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (!(o instanceof Jeu)) return false;
        Jeu jeu = (Jeu) o;
        return Float.compare(this.getPrix(), jeu.getPrix()) == 0 && Objects.equals(this.getGenre(), jeu.getGenre());
    }

}
