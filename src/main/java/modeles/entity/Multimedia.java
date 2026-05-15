package modeles.entity;

import java.util.Objects;

public class Multimedia extends Item {
    private String genre;
    private float dureeHeures;

    public Multimedia() {}

    public Multimedia(String nom, String chemin, String genre, float dureeHeures) {
        super(nom, chemin);
        this.genre = genre;
        this.dureeHeures = dureeHeures;
    }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public float getDureeHeures() { return dureeHeures; }
    public void setDureeHeures(float d) { this.dureeHeures = d; }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (!(o instanceof Multimedia)) return false;
        Multimedia mm = (Multimedia) o;
        return Float.compare(mm.dureeHeures, dureeHeures) == 0
                && Objects.equals(genre, mm.genre);
    }

}
