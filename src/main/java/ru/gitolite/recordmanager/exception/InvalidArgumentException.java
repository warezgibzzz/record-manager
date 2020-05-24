package ru.gitolite.recordmanager.exception;

public class InvalidArgumentException extends Exception {
    private final String detailMessage;

    public InvalidArgumentException() {
        super();
        this.detailMessage = "Incorrect syntax of command!";
    }

    public String getMessage() {
        return detailMessage;
    }
}
