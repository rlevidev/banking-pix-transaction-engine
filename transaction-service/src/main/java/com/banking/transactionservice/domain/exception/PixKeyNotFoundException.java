package com.banking.transactionservice.domain.exception;

public class PixKeyNotFoundException extends RuntimeException {
  public PixKeyNotFoundException(String pixKey) {
    super(String.format("Pix Key with value %s not found", pixKey));
  }
}
