package modeles;

import java.util.Objects;

public class Travail extends Item {
    private String langage;
    private float avancement;

    public Travail() {}

    public Travail(String nom, String chemin, String langage, float avancement) {
        super(nom, chemin);
        this.langage = langage;
        this.avancement = avancement;
    }

    public String getLangage() { return langage; }
    public void setLangage(String l) { this.langage = l; }
    public float getAvancement() { return avancement; }
    public void setAvancement(float a) { this.avancement = a; }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (!(o instanceof Travail)) return false;
        Travail t = (Travail) o;
        return Float.compare(t.avancement, avancement) == 0
                && Objects.equals(langage, t.langage);
    }

}
