package ru.gitolite.recordmanager.commands;

import ru.gitolite.recordmanager.dao.DaoInterface;
import ru.gitolite.recordmanager.exception.InvalidArgumentException;
import ru.gitolite.recordmanager.service.StateManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ListAction implements Action {
    private final DaoInterface dao;

    public ListAction(DaoInterface dao) {
        this.dao = dao;
    }

    @Override
    public void apply() {
        StateManager.getState().replace("entity", dao);
        Map<String, Action> actionMap = new HashMap<String, Action>();

        for (Object entry: dao.findAll()) {
            System.out.println(entry.toString());
        }

        StateManager.setActions(StateManager.listContextActions(actionMap));
    }

    @Override
    public void apply(Object[] args) throws InvalidArgumentException {
        throw new InvalidArgumentException();
    }
}
