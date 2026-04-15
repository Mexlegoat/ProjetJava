package authentication;

import java.util.Map;

public abstract class HandlerLoginAbstrait {

    public final boolean login(String username, String password) {
        Map<String, String> credentials = loadCredentials();
        return checkCredentials(credentials, username, password);
    }

    protected abstract Map<String, String> loadCredentials();

    protected boolean checkCredentials(Map<String, String> credentials,
                                        String username, String password) {
        if (username == null || password == null) return false;
        String stored = credentials.get(username);
        return stored != null && stored.equals(password);
    }
}
