package com.banking.transactionservice.api.dto;

import com.banking.transactionservice.domain.model.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponseDTO(
        UUID id,
        String idempotencyKey,
        UUID sourceAccountId,
        UUID destinationAccountId,
        BigDecimal amount,
        TransactionStatus status,
        LocalDateTime createdAt
) {
}
