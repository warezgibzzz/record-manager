package ru.gitolite.recordmanager.commands;

import ru.gitolite.recordmanager.dao.*;
import ru.gitolite.recordmanager.exception.InvalidArgumentException;
import ru.gitolite.recordmanager.service.StateManager;

import java.util.HashMap;
import java.util.Map;

public class MenuAction implements Action {
    private final Map<String, Action> CRUDServiceMap;

    public MenuAction() {
        CRUDServiceMap = new HashMap<String, Action>();

        DaoInterface users = new UserDao();

        CRUDServiceMap.put("users", (new ListAction(users)));
    }

    @Override
    public void apply() {
        Map<String, Action> actionMap = StateManager.listContextActions(CRUDServiceMap);
        StateManager.setActions(actionMap);
        StateManager.getState().replace("entity", null);
        listActions();
    }

    @Override
    public void apply(Object[] args) throws InvalidArgumentException {
        throw new InvalidArgumentException();
    }

    protected void listActions()
    {
        System.out.println("You are in menu. Allowed commands are:");
        for (Map.Entry<String, Action> entry : StateManager.getActions().entrySet()) {
            System.out.println(entry.getKey());
        }
    }
}
