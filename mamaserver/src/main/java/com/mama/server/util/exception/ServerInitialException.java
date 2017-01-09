package com.mama.server.util.exception;

public class ServerInitialException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public ServerInitialException() {
  }

  public ServerInitialException(String message) {
    super(message);
  }

  public ServerInitialException(String message, Throwable cause) {
    super(message, cause);
  }
}
