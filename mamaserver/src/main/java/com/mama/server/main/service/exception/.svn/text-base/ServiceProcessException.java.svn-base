package com.mama.server.main.service.exception;

public class ServiceProcessException extends Exception {

  private static final long serialVersionUID = -7346652005533319860L;

  private int code;

  public ServiceProcessException(int code, String message) {
    super(message);
    this.code = code;
  }

  public ServiceProcessException(int code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public ServiceProcessException(String message, Throwable cause) {
    super(message, cause);
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

}
