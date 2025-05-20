package org.liemartt.filestorage.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("A user with that name already exists");
    }
}
