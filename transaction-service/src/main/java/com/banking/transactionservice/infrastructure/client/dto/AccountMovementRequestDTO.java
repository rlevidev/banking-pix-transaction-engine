package com.banking.transactionservice.infrastructure.client.dto;

import java.math.BigDecimal;

public record AccountMovementRequestDTO(
        BigDecimal amount
) {
}
