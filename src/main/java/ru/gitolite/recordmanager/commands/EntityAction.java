package ru.gitolite.recordmanager.commands;

import ru.gitolite.recordmanager.dao.DaoInterface;

import java.util.Optional;

public abstract class EntityAction<T> implements Action {
    private DaoInterface<T> dao;

    public EntityAction() {

    }

    public EntityAction(DaoInterface<T> dao) {
        this.dao = dao;
    }

    public T getEntity(Object searchVal) {
        Optional<T> objectById = Optional.empty();
        try {
            objectById = dao.findById(Integer.parseInt((String) searchVal));
        } catch (NumberFormatException ignored) {
        }
        Optional<T> objectByName = dao.findByName((String) searchVal);

        T object = null;
        if (objectById.isPresent()) {
            object = objectById.get();
        } else if (objectByName.isPresent()) {
            object = objectByName.get();
        }

        return object;
    }
}
