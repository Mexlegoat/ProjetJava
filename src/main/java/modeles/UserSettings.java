package modeles;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserSettings implements Serializable {
    private boolean isDarkMode = false;
    private boolean showType = false;
    private boolean showGenre = false;
    private boolean doubleClickToExecute = true;
    private String defaultBrowsePath = "C:\\";
    private int searchType = 0; // 0=Name, 1=Type, 2=Genre

    public boolean isDarkMode() { return isDarkMode; }
    public void setDarkMode(boolean b) { isDarkMode = b; }
    public boolean isShowType() { return showType; }
    public void setShowType(boolean b) { showType = b; }
    public boolean isShowGenre() { return showGenre; }
    public void setShowGenre(boolean b) { showGenre = b; }
    public boolean isDoubleClickToExecute() { return doubleClickToExecute; }
    public void setDoubleClickToExecute(boolean b) { doubleClickToExecute = b; }
    public String getDefaultBrowsePath() { return defaultBrowsePath; }
    public void setDefaultBrowsePath(String p) { defaultBrowsePath = p; }
    public int getSearchType() { return searchType; }
    public void setSearchType(int t) { searchType = t; }
}
