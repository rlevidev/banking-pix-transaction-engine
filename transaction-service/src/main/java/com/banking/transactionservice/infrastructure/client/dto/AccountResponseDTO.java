package com.banking.transactionservice.infrastructure.client.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountResponseDTO(
        UUID id,
        BigDecimal balance,
        Integer version
) {
}
