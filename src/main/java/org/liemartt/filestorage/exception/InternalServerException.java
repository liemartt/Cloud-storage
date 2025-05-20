package org.liemartt.filestorage.exception;

public class InternalServerException extends RuntimeException {
    public InternalServerException() {
        super("Internal server error, try again later");
    }
    public InternalServerException(String message) {
        super(message);
    }
}
