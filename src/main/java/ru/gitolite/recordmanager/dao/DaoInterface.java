package ru.gitolite.recordmanager.dao;

import java.util.List;
import java.util.Optional;

public interface DaoInterface<T> {
    Optional<T> findOneBy(String param, Object value);
    Optional<T> findById(int id);
    Optional<T> findByName(String name);

    List<T> findAll();

    void save(T object);

    void update(T object);

    void delete(T object);
}
