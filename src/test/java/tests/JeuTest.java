package tests;

import modeles.entity.Jeu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JeuTest {

    private Jeu jeu;

    @BeforeEach
    void setUp() {
        jeu = new Jeu();
    }

    @Test
    void setNom_setPrix_stockent_correctement() {
        jeu.setNom("Minecraft");
        jeu.setPrix(24);
        assertEquals("Minecraft", jeu.getNom());
        assertEquals(24, jeu.getPrix());
    }

    @Test
    void setGenre_stocke_correctement() {
        jeu.setGenre("Sandbox");
        assertEquals("Sandbox", jeu.getGenre());
    }

    @Test
    void setChemin_stocke_correctement() {
        jeu.setChemin("C:/jeux/minecraft.exe");
        assertEquals("C:/jeux/minecraft.exe", jeu.getChemin());
    }

    @Test
    void initializeNewItem_assigne_un_id_positif() {
        jeu.initializeNewItem();
        assertTrue(jeu.getId() > 0);
    }

    @Test
    void initializeNewItem_assigne_une_date() {
        jeu.initializeNewItem();
        assertNotNull(jeu.getDateAjoute());
    }

    @Test
    void deux_jeux_ont_des_ids_differents() {
        Jeu jeu1 = new Jeu();
        jeu1.initializeNewItem();
        Jeu jeu2 = new Jeu();
        jeu2.initializeNewItem();
        assertNotEquals(jeu1.getId(), jeu2.getId());
    }

    @Test
    void toString_retourne_le_nom() {
        jeu.setNom("Minecraft");
        assertEquals("Minecraft", jeu.toString());
    }
}