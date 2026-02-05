package com.banking.account.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountResponseDTO(
        UUID id,
        String customerId,
        BigDecimal balance,
        Integer version
) {
}
