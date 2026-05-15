package tests;

import modeles.dao.UserDAO;
import modeles.entity.User;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    private UserDAO userDAO;
    private static final String TEST_FILE = "users.dat";

    @BeforeEach
    void setUp() {
        // Supprimer le fichier s'il existe pour démarrer chaque test à zéro
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
        userDAO = new UserDAO();
    }

    @AfterAll
    static void free() {
        // Nettoyage final après tous les tests
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testInsertAndFind() {
        User user = new User();
        user.setUsername("Alice");
        user.setPassword("password123");

        boolean result = userDAO.insert(user);

        assertTrue(result, "L'insertion devrait réussir");
        Optional<User> found = userDAO.findByUsername("Alice");
        assertTrue(found.isPresent());
        assertEquals("Alice", found.get().getUsername());
    }

    @Test
    void testInsertDuplicate() {
        User user1 = new User();
        user1.setUsername("Bob");
        userDAO.insert(user1);

        User user2 = new User();
        user2.setUsername("Bob");
        boolean result = userDAO.insert(user2);

        assertFalse(result, "L'insertion d'un doublon devrait échouer");
    }

    @Test
    void testUpdate() {
        User user = new User();
        user.setUsername("Charlie");
        user.setPassword("oldPass");
        userDAO.insert(user);

        user.setPassword("newPass");
        boolean updated = userDAO.update(user);

        assertTrue(updated);
        User found = userDAO.findByUsername("Charlie").get();
        assertEquals("newPass", found.getPassword());
    }

    @Test
    void testDelete() {
        User user = new User();
        user.setUsername("David");
        userDAO.insert(user);

        boolean deleted = userDAO.delete("David");

        assertTrue(deleted);
        assertFalse(userDAO.existsByUsername("David"));
    }

    @Test
    void testFindAll() {
        User u1 = new User(); u1.setUsername("User1");
        User u2 = new User(); u2.setUsername("User2");
        userDAO.insert(u1);
        userDAO.insert(u2);

        List<User> users = userDAO.findAll();

        assertEquals(2, users.size());
    }
}