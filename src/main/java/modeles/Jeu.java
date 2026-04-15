package modeles;

import java.util.Objects;

public class Jeu extends Item {
    private String genre;
    private float note; // note du jeu sur 20

    public Jeu() {}

    public Jeu(String nom, String chemin, String genre, float note) {
        super(nom, chemin);
        this.genre = genre;
        this.note  = note;
    }

    public String getGenre()         { return genre; }
    public void setGenre(String g)   { this.genre = g; }
    public float getNote()           { return note; }
    public void setNote(float note)  { this.note = note; }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (!(o instanceof Jeu)) return false;
        Jeu jeu = (Jeu) o;
        return Float.compare(jeu.note, note) == 0 && Objects.equals(genre, jeu.genre);
    }

}
