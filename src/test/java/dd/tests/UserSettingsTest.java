package tests;

import modeles.UserSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserSettingsTest {

    private UserSettings settings;

    @BeforeEach
    void setUp() {
        settings = new UserSettings();
    }

    @Test
    void darkMode_est_false_par_defaut() {
        assertFalse(settings.isDarkMode());
    }

    @Test
    void setDarkMode_stocke_correctement() {
        settings.setDarkMode(true);
        assertTrue(settings.isDarkMode());
    }

    @Test
    void doubleClickToExecute_est_true_par_defaut() {
        assertTrue(settings.isDoubleClickToExecute());
    }

    @Test
    void setDoubleClickToExecute_stocke_correctement() {
        settings.setDoubleClickToExecute(false);
        assertFalse(settings.isDoubleClickToExecute());
    }

    @Test
    void searchType_est_0_par_defaut() {
        assertEquals(0, settings.getSearchType());
    }

    @Test
    void setSearchType_stocke_correctement() {
        settings.setSearchType(2);
        assertEquals(2, settings.getSearchType());
    }

    @Test
    void defaultBrowsePath_est_C_par_defaut() {
        assertEquals("C:\\", settings.getDefaultBrowsePath());
    }

    @Test
    void setDefaultBrowsePath_stocke_correctement() {
        settings.setDefaultBrowsePath("D:\\projets");
        assertEquals("D:\\projets", settings.getDefaultBrowsePath());
    }
}