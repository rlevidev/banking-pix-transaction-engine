package com.banking.pixkey.domain.exception;

public class PixKeyAlreadyExistsException extends RuntimeException {
  private final String keyValue;

  public PixKeyAlreadyExistsException(String keyValue) {
    super(String.format("PIX key '%s' is already registered in the system", keyValue));
    this.keyValue = keyValue;
  }

  public String getKeyValue() {
    return keyValue;
  }
}
