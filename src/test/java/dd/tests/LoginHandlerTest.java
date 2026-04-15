package tests;

import authentication.HandlerLoginMemoire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginHandlerTest {

    private HandlerLoginMemoire handler;

    @BeforeEach
    void setUp() {
        handler = new HandlerLoginMemoire();
    }

    @Test
    void login_avec_credentials_corrects_retourne_true() {
        assertTrue(handler.login("admin", "admin123"));
    }

    @Test
    void login_avec_mauvais_mot_de_passe_retourne_false() {
        assertFalse(handler.login("admin", "mauvais"));
    }

    @Test
    void login_avec_username_inconnu_retourne_false() {
        assertFalse(handler.login("inconnu", "admin123"));
    }

    @Test
    void login_avec_username_null_retourne_false() {
        assertFalse(handler.login(null, "admin123"));
    }

    @Test
    void login_avec_password_null_retourne_false() {
        assertFalse(handler.login("admin", null));
    }

    @Test
    void login_apres_ajout_dynamique_retourne_true() {
        handler.addCredential("nouveauUser", "monMotDePasse");
        assertTrue(handler.login("nouveauUser", "monMotDePasse"));
    }

    @Test
    void login_user_par_defaut_retourne_true() {
        assertTrue(handler.login("user", "user123"));
    }
}
