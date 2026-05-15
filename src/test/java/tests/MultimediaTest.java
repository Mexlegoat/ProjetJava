package tests;

import modeles.entity.Multimedia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MultimediaTest {

    private Multimedia multimedia;

    @BeforeEach
    void setUp() {
        multimedia = new Multimedia();
    }

    @Test
    void setNom_stocke_correctement() {
        multimedia.setNom("Film");
        assertEquals("Film", multimedia.getNom());
    }

    @Test
    void setGenre_stocke_correctement() {
        multimedia.setGenre("Action");
        assertEquals("Action", multimedia.getGenre());
    }
    @Test
    void setdureeheure_stocke_correctement()
    {
        multimedia.setDureeHeures(1.5F);
        assertEquals(1.5F, multimedia.getDureeHeures());
    }
    @Test
    void setChemin_stocke_correctement() {
        multimedia.setChemin("C:/videos/film.mp4");
        assertEquals("C:/videos/film.mp4", multimedia.getChemin());
    }

    @Test
    void initializeNewItem_assigne_un_id_positif() {
        multimedia.initializeNewItem();
        assertTrue(multimedia.getId() > 0);
    }

    @Test
    void initializeNewItem_assigne_une_date() {
        multimedia.initializeNewItem();
        assertNotNull(multimedia.getDateAjoute());
    }

    @Test
    void toString_retourne_le_nom() {
        multimedia.setNom("Film");
        assertEquals("Film", multimedia.toString());
    }
}