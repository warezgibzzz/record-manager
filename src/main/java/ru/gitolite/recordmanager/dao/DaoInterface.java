package ru.gitolite.recordmanager.dao;

import ru.gitolite.recordmanager.model.User;

import java.util.List;
import java.util.Optional;

public interface DaoInterface {
    Optional<?> findOneBy(String param, Object value);
    List<?> findAll();
}
