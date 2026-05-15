package modeles.interfaces;

import modeles.entity.User;

public interface UserDAL extends GenericDAO<User, String> {
    boolean existsByUsername(String username);
}
