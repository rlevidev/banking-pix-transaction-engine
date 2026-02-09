package com.banking.pixkey.infrastructure.client.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO that represents the response of the Account Service.
 *
 * This is a MIRROR of the AccountResponseDTO from the account-service.
 * We duplicate it to maintain independence between microservices.
 * @param id
 * @param customerId
 * @param balance
 * @param version
 */
public record AccountResponseDTO(
        UUID id,
        String customerId,
        BigDecimal balance,
        Integer version
) {
}
