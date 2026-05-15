package view.interfaces;

import modeles.entity.User;

public interface IAuthView {
    void showMessage(String message);
    void showErrorMessage(String message);
    void showUser(User user);
}
