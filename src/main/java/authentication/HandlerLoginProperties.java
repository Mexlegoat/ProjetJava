package authentication;

import utils.PasswordUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class HandlerLoginProperties extends HandlerLoginAbstrait
{

    private final String filePath;

    public HandlerLoginProperties(String filePath)
    {
        this.filePath = filePath;
    }

    @Override
    public Map<String, String> loadCredentials() {
        Map<String, String> credentialsMap = new HashMap<>();
        File folder = new File("users");

        if (!folder.exists() || !folder.isDirectory()) {
            return credentialsMap;
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".properties"));
        if (files != null) {
            for (File file : files) {
                Properties properties = new Properties();
                try (FileInputStream fis = new FileInputStream(file)) {
                    properties.load(fis);
                    String user = properties.getProperty("username");
                    String pass = properties.getProperty("password");

                    if (user != null && pass != null) {
                        credentialsMap.put(user, pass);
                    }
                } catch (IOException e) {
                    System.err.println("Erreur : " + e.getMessage());
                }
            }
        }
        return credentialsMap;
    }
}