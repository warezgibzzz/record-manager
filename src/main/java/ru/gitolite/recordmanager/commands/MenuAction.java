package ru.gitolite.recordmanager.commands;

import ru.gitolite.recordmanager.exception.InvalidArgumentException;

public class MenuAction implements Action {
    @Override
    public void apply() {

    }

    @Override
    public void apply(Object[] args) throws InvalidArgumentException {
        throw new InvalidArgumentException();
    }
}
