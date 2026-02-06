package com.banking.account.domain.exception;

import java.math.BigDecimal;
import java.util.UUID;

public class InsufficientBalanceException extends RuntimeException {
  private final UUID accountId;
  private final BigDecimal requestedAmount;
  private final BigDecimal currentBalance;

  public InsufficientBalanceException(UUID accountId, BigDecimal requestedAmount, BigDecimal currentBalance) {
    super("Insufficient balance to perform the operation");
    this.accountId = accountId;
    this.requestedAmount = requestedAmount;
    this.currentBalance = currentBalance;
  }

  public UUID getAccountId() {
    return accountId;
  }

  public BigDecimal getRequestedAmount() {
    return requestedAmount;
  }

  public BigDecimal getCurrentBalance() {
    return currentBalance;
  }

  public String getDetailedMessage() {
    return String.format(
            "Account %s tried to debit %.2f but only has %.2f",
            accountId, requestedAmount, currentBalance
    );
  }
}
