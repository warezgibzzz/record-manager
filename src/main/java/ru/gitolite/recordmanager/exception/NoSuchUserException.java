package ru.gitolite.recordmanager.exception;

public class NoSuchUserException extends Exception {
    private final String detailMessage;

    public NoSuchUserException() {
        super();
        this.detailMessage = "No such user!";
    }

    public String getMessage() {
        return detailMessage;
    }
}
