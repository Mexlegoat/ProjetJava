package authentication;

import utils.PasswordUtils;

import java.util.HashMap;
import java.util.Map;

public class HandlerLoginMemoire extends HandlerLoginAbstrait {

    private final Map<String, String> credentials = new HashMap<>();

    public HandlerLoginMemoire() {
        credentials.put("admin", "admin123");
        credentials.put("user", "user123");
    }

    public void addCredential(String username, String password) {
        credentials.put(username, PasswordUtils.encrypt(password));
    }

    @Override
    protected Map<String, String> loadCredentials() {
        return credentials;
    }
}
