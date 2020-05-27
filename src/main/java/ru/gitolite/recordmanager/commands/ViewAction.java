package ru.gitolite.recordmanager.commands;

import ru.gitolite.recordmanager.dao.DaoInterface;
import ru.gitolite.recordmanager.exception.InvalidArgumentException;
import ru.gitolite.recordmanager.service.StateManager;

import java.util.HashMap;
import java.util.Map;

public class ViewAction implements Action {
    @Override
    public void apply() {

        Map<String, Action> actionMap = new HashMap<String, Action>();
        actionMap.put("list", new ListAction((DaoInterface) StateManager.getState().get("entity")));
        StateManager.setActions(StateManager.viewContextActions(actionMap));
    }

    @Override
    public void apply(Object[] args) throws InvalidArgumentException {

    }
}
