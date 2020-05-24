package ru.gitolite.recordmanager.exception;

public class InvalidPasswordException extends Exception {
    private final String detailMessage;

    public InvalidPasswordException() {
        super();
        this.detailMessage = "Invalid password!";
    }

    public String getMessage() {
        return detailMessage;
    }
}
