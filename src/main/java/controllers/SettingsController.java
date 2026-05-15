package controllers;

import modeles.entity.UserSettings;
import services.UserService;

import java.util.ArrayList;
import java.util.List;

public class SettingsController
{
    public interface SettingsListener
    {
        void onSettingsChanged(UserSettings settings);
    }

    private final UserService userService;
    private final List<SettingsListener> listeners = new ArrayList<>();

    public SettingsController(UserService userService)
    {
        this.userService = userService;
    }

    public void addListener(SettingsListener listener)
    {
        listeners.add(listener);
    }

    public UserSettings getSettings()
    {
        if (userService.getCurrentUser() == null) return new UserSettings();
        return userService.getCurrentUser().getPreferences();
    }

    public void saveSettings(boolean darkMode, boolean showType, boolean showGenre,
                             boolean doubleClickToExecute, int searchType)
    {
        if (userService.getCurrentUser() == null) return;

        UserSettings prefs = userService.getCurrentUser().getPreferences();
        prefs.setDarkMode(darkMode);
        prefs.setShowType(showType);
        prefs.setShowGenre(showGenre);
        prefs.setDoubleClickToExecute(doubleClickToExecute);
        prefs.setSearchType(searchType);

        userService.save();

        for (SettingsListener listener : listeners)
        {
            listener.onSettingsChanged(prefs);
        }
    }
}