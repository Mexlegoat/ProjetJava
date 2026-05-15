package modeles.interfaces;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, ID> {
    List<T> findAll();
    Optional<T> findById(ID id);
    boolean insert(T entity);
    boolean update(T entity);
    boolean delete(ID id);
}
