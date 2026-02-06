package com.banking.account.domain.exception;

public class AccountAlreadyExistsException extends RuntimeException {
  private final String customerId;

  public AccountAlreadyExistsException(String customerId) {
    super(String.format("Account with customer ID %s already exists", customerId));
    this.customerId = customerId;
  }

  public String getCustomerId() {
    return customerId;
  }
}
