package tests;

import modeles.Categorie;
import modeles.TypeClass;
import modeles.User;
import modeles.UserSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void setUsername_stocke_correctement() {
        user.setUsername("alice");
        assertEquals("alice", user.getUsername());
    }

    @Test
    void setPassword_stocke_correctement() {
        user.setPassword("motdepasse");
        assertEquals("motdepasse", user.getPassword());
    }

    @Test
    void categories_initialisees_vides_par_defaut() {
        assertNotNull(user.getCategories());
        assertEquals(0, user.getCategories().size());
    }

    @Test
    void userCreatedTypes_initialises_vides_par_defaut() {
        assertNotNull(user.getUserCreatedTypes());
        assertEquals(0, user.getUserCreatedTypes().size());
    }

    @Test
    void userCreatedTypes_stocke_correctement()
    {
        java.util.List<TypeClass> types = new java.util.ArrayList<>();
        TypeClass typ = new TypeClass();
        typ.setNom("test");
        types.add(typ);
        user.setUserCreatedTypes(types);
        assertEquals(1, user.getUserCreatedTypes().size());
        assertEquals("test", user.getUserCreatedTypes().getFirst().getNom());
    }

    @Test
    void preferences_initialisees_par_defaut() {
        assertNotNull(user.getPreferences());
    }

    @Test
    void setCategories_stocke_correctement() {
        java.util.List<Categorie> categories = new java.util.ArrayList<>();
        Categorie cat = new Categorie();
        cat.setNom("Jeux");
        categories.add(cat);
        user.setCategories(categories);
        assertEquals(1, user.getCategories().size());
        assertEquals("Jeux", user.getCategories().getFirst().getNom());
    }

    @Test
    void setPreferences_stocke_correctement() {
        UserSettings settings = new UserSettings();
        settings.setDarkMode(true);
        user.setPreferences(settings);
        assertTrue(user.getPreferences().isDarkMode());
    }
    @Test
    void setUser_equals()
    {
        user.setPassword("ADMIN123");
        user.setUsername("ADMIN");
        User u = new User();
        u.setUsername("ADMIN");
        u.setPassword("ADMIN123");
        assertTrue(user.equals(u));
    }
}