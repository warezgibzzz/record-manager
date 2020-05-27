package ru.gitolite.recordmanager.dao;

import java.util.List;
import java.util.Optional;

public interface DaoInterface<T> {
    Optional<T> findOneBy(String param, Object value);

    List<T> findAll();

    void save(T object);

    void update(T object);

    void delete(T object);
}
