package ru.gitolite.recordmanager.commands;

import ru.gitolite.recordmanager.exception.InvalidArgumentException;
import ru.gitolite.recordmanager.service.StateManager;

import java.util.Map;

public class HelpAction implements Action {
    @Override
    public void apply() throws InvalidArgumentException {
        for (Map.Entry<String, Action> entry : StateManager.getActions().entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
        }
    }

    @Override
    public void apply(Object[] args) throws InvalidArgumentException {
        throw new InvalidArgumentException();
    }

    @Override
    public String toString() {
        return "help";
    }

    public String getDescription() {
        return "Show list of allowed commands";
    }
}
