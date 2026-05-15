package tests;

import modeles.entity.Travail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TravailTest {

    private Travail travail;

    @BeforeEach
    void setUp() {
        travail = new Travail();
    }

    @Test
    void setNom_stocke_correctement() {
        travail.setNom("ProjetJava");
        assertEquals("ProjetJava", travail.getNom());
    }

    @Test
    void setLangage_stocke_correctement() {
        travail.setLangage("Java");
        assertEquals("Java", travail.getLangage());
    }

    @Test
    void setChemin_stocke_correctement() {
        travail.setChemin("C:/projets/java");
        assertEquals("C:/projets/java", travail.getChemin());
    }

    @Test
    void initializeNewItem_assigne_un_id_positif() {
        travail.initializeNewItem();
        assertTrue(travail.getId() > 0);
    }

    @Test
    void initializeNewItem_assigne_une_date() {
        travail.initializeNewItem();
        assertNotNull(travail.getDateAjoute());
    }

    @Test
    void toString_retourne_le_nom() {
        travail.setNom("ProjetJava");
        assertEquals("ProjetJava", travail.toString());
    }
}