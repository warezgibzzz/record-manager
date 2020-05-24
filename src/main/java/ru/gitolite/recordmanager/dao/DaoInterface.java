package ru.gitolite.recordmanager.dao;

import java.util.Optional;

public interface DaoInterface {
    Optional<?> findOneBy(String param, Object value);
}
