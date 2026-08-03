package repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    List<T> findAll();
    Optional<T> findById(ID id);
    T save(T entity);
    List<T> saveAll(List<T> entities);
    boolean deleteById(ID id);
}
