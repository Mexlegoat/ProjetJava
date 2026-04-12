package test.java.tests;

import main.java.modeles.Categorie;
import main.java.modeles.Jeu;
import main.java.modeles.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CategorieTest {

    private Categorie categorie;

    @BeforeEach
    void setUp() {
        categorie = new Categorie();
    }

    @Test
    void setNom_stocke_correctement() {
        categorie.setNom("Jeux");
        assertEquals("Jeux", categorie.getNom());
    }

    @Test
    void items_initialises_vides_par_defaut() {
        assertNotNull(categorie.getItems());
        assertEquals(0, categorie.getItems().size());
    }

    @Test
    void ajout_item_dans_categorie() {
        Jeu jeu = new Jeu();
        jeu.setNom("Minecraft");
        jeu.initializeNewItem();
        categorie.getItems().add(jeu);
        assertEquals(1, categorie.getItems().size());
        assertEquals("Minecraft", categorie.getItems().get(0).getNom());
    }

    @Test
    void setItems_stocke_correctement() {
        java.util.List<Item> items = new java.util.ArrayList<>();
        Jeu jeu = new Jeu();
        jeu.setNom("Minecraft");
        items.add(jeu);
        categorie.setItems(items);
        assertEquals(1, categorie.getItems().size());
    }
}