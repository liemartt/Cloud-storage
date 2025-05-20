package org.liemartt.filestorage.exception;

public class EmptyObjectNameException extends RuntimeException {
  public EmptyObjectNameException() {
    super("The name cannot be empty");
  }
}
