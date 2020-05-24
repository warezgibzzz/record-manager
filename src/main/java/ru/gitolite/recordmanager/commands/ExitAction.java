package ru.gitolite.recordmanager.commands;

import ru.gitolite.recordmanager.exception.InvalidArgumentException;

public class ExitAction implements Action {
    @Override
    public void apply() {
        System.out.println("Exiting");
        System.exit(0);
    }

    @Override
    public void apply(Object[] args) throws InvalidArgumentException {
        throw new InvalidArgumentException();
    }
}
