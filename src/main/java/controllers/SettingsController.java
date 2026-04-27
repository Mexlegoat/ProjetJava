package controllers;

import modeles.UserSettings;
import services.UserService;

public class SettingsController {

    private final UserService userService;

    public SettingsController(UserService userService) {
        this.userService = userService;
    }

    public UserSettings getSettings() {
        if (userService.getCurrentUser() == null) return new UserSettings();
        return userService.getCurrentUser().getPreferences();
    }

    public void saveSettings(boolean darkMode, boolean showType, boolean showGenre,
                              boolean doubleClickToExecute, int searchType) {
        if (userService.getCurrentUser() == null) return;

        UserSettings prefs = userService.getCurrentUser().getPreferences();
        prefs.setDarkMode(darkMode);
        prefs.setShowType(showType);
        prefs.setShowGenre(showGenre);
        prefs.setDoubleClickToExecute(doubleClickToExecute);
        prefs.setSearchType(searchType);

        userService.save();
    }
}
