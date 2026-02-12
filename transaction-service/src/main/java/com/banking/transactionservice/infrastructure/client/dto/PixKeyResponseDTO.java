package com.banking.transactionservice.infrastructure.client.dto;

import com.banking.transactionservice.domain.model.PixKeyType;

import java.util.UUID;

public record PixKeyResponseDTO(
        UUID id,
        String keyValue,
        PixKeyType keyType,
        UUID accountId
) {
}
