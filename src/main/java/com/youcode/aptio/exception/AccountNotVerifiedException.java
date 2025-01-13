package com.youcode.aptio.exception;

public class AccountNotVerifiedException extends RuntimeException {
  public AccountNotVerifiedException(String message) {
    super(message);
  }
}
