package com.youcode.aptio.exception;

public class UserAccountLockedException extends RuntimeException {
  public UserAccountLockedException(String message) {
    super(message);
  }
}
