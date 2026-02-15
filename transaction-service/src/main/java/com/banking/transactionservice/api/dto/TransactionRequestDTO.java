package com.banking.transactionservice.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequestDTO(
        @NotBlank
        String idempotencyKey,

        @NotNull
        UUID sourceAccountId,

        @NotBlank
        String pixKey,

        @NotNull
        @Positive
        BigDecimal amount
) {
}
