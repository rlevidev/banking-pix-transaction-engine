package com.banking.transactionservice.domain.exception;

public class TransactionFailureException extends RuntimeException {
  public TransactionFailureException(String message) {
    super(message);
  }

  public TransactionFailureException(String message, Throwable cause) {
    super(message, cause);
  }
}
