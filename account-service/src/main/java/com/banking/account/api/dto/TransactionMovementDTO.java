package com.banking.account.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionMovementDTO(
        @NotNull
        @Positive
        BigDecimal amount
) {
}
