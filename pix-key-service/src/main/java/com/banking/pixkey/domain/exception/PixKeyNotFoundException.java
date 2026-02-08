package com.banking.pixkey.domain.exception;

public class PixKeyNotFoundException extends RuntimeException {
  private final String keyValue;

  public PixKeyNotFoundException(String keyValue) {
    super(String.format("PIX key '%s' not found in the system", keyValue));
    this.keyValue = keyValue;
  }

  public String getKeyValue() {
    return keyValue;
  }
}
