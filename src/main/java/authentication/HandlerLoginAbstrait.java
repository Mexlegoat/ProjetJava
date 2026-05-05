package authentication;

import utils.PasswordUtils;

import java.util.Map;

public abstract class HandlerLoginAbstrait {

    public final boolean login(String username, String password) {
        Map<String, String> credentials = loadCredentials();
        String storedPass = credentials.get(username);
        if (storedPass == null) return false;

        // C'est ici qu'on décrypte pour comparer !
        return PasswordUtils.decrypt(storedPass).equals(password);
    }

    protected abstract Map<String, String> loadCredentials();

    protected boolean checkCredentials(Map<String, String> credentials,
                                        String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        String stored = credentials.get(username);

        if (stored != null && stored.equals(password)) {
            return true;
        }

        return false;
    }
}
