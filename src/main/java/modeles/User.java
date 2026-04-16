package modeles;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {
    private String username;
    private String password;
    private List<Categorie> categories = new ArrayList<>();
    private List<TypeClass> userCreatedTypes = new ArrayList<>();
    private UserSettings preferences = new UserSettings();

    public String getUsername() { return username; }
    public void setUsername(String u) { username = u; }
    public String getPassword() { return password; }
    public void setPassword(String p) { password = p; }
    public List<Categorie> getCategories() { return categories; }
    public void setCategories(List<Categorie> c) { categories = c; }
    public List<TypeClass> getUserCreatedTypes() { return userCreatedTypes; }
    public void setUserCreatedTypes(List<TypeClass> t) { userCreatedTypes = t; }
    public UserSettings getPreferences() { return preferences; }
    public void setPreferences(UserSettings s) { preferences = s; }

    public boolean equals(User o)
    {
        if (this == o) return true;
        if (o == null) return false;
        return Objects.equals(this.getUsername(), o.getUsername()) && Objects.equals(this.getPassword(), o.getPassword());
    }
}
