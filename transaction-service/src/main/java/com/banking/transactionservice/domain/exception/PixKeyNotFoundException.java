package com.banking.transactionservice.domain.exception;

public class PixKeyNotFoundException extends RuntimeException {
  private final String pixKey;

  public PixKeyNotFoundException(String pixKey) {
    super(String.format("Pix Key with value %s not found", pixKey));
    this.pixKey = pixKey;
  }

  public String getPixKey() {
    return pixKey;
  }
}
