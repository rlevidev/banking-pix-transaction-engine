package com.banking.transactionservice.domain.exception;

import java.util.UUID;

public class TransactionNotFoundException extends RuntimeException {
  private final UUID transactionId;
  public TransactionNotFoundException(UUID transactionId) {
    super(String.format("Transaction with ID %s not found", transactionId));
    this.transactionId = transactionId;
  }

  public UUID getTransactionId() {
    return transactionId;
  }
}
