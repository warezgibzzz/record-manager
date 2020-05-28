package ru.gitolite.recordmanager.commands;

import ru.gitolite.recordmanager.dao.DaoInterface;
import ru.gitolite.recordmanager.exception.InvalidArgumentException;
import ru.gitolite.recordmanager.service.StateManager;

import java.util.HashMap;
import java.util.Map;

public class ListAction<T> implements Action {
    private final DaoInterface<T> dao;

    public ListAction(DaoInterface<T> dao) {
        this.dao = dao;
    }

    @Override
    public void apply() {
        StateManager.getState().replace("entity", dao);
        Map<String, Action> actionMap = new HashMap<String, Action>();

        ViewAction<T> viewAction = new ViewAction<>(dao);
        DeleteAction<T> deleteAction = new DeleteAction<>(dao);
        actionMap.put("view", viewAction);
        actionMap.put("delete", deleteAction);

        for (Object entry: dao.findAll()) {
            System.out.println(entry.toString());
        }

        StateManager.setActions(StateManager.listContextActions(actionMap));
    }

    @Override
    public void apply(Object[] args) throws InvalidArgumentException {
        throw new InvalidArgumentException();
    }

    @Override
    public String toString() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "Lists selected DAO from DB";
    }
}
