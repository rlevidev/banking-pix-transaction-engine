package com.banking.transactionservice.domain.model;

/**
 * Transaction status.
 *
 * Each status has specific rules:
 * - PENDING: The transaction is pending confirmation.
 * - PROCESSING: The transaction is being processed.
 * - COMPLETED: The transaction has been successfully completed.
 * - FAILED: The transaction has failed.
 * - REFUNDED: The transaction has been refunded.
 */
public enum TransactionStatus {
  PENDING,
  PROCESSING,
  COMPLETED,
  FAILED,
  REFUNDED
}
