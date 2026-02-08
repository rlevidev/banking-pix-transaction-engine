package com.banking.pixkey.domain.exception;

import java.util.UUID;

public class InvalidAccountException extends RuntimeException {
  private final UUID accountId;

  public InvalidAccountException(UUID accountId) {
    super(String.format("Account with ID %s does not exist or is not accessible", accountId));
    this.accountId = accountId;
  }

  public UUID getAccountId() {
    return accountId;
  }
}
