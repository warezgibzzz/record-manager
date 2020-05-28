package ru.gitolite.recordmanager.commands;

import ru.gitolite.recordmanager.exception.InvalidArgumentException;

public interface Action {
    void apply() throws InvalidArgumentException;
    void apply(Object[] args) throws InvalidArgumentException;
    String toString();
}
