package test.java.tests;

import main.java.modeles.TypeClass;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TypeClassTest {

    @Test
    void constructeur_avec_nom_stocke_correctement() {
        TypeClass type = new TypeClass("FPS");
        assertEquals("FPS", type.getNom());
    }

    @Test
    void constructeur_vide_nom_est_null() {
        TypeClass type = new TypeClass();
        assertNull(type.getNom());
    }

    @Test
    void setNom_stocke_correctement() {
        TypeClass type = new TypeClass();
        type.setNom("RPG");
        assertEquals("RPG", type.getNom());
    }

    @Test
    void toString_retourne_le_nom() {
        TypeClass type = new TypeClass("Strategie");
        assertEquals("Strategie", type.toString());
    }
}