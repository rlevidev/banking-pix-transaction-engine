package com.banking.pixkey.api.dto;

import com.banking.pixkey.domain.model.PixKeyType;

import java.time.LocalDateTime;
import java.util.UUID;

public record PixKeyResponseDTO(
        UUID id,
        String keyValue,
        PixKeyType keyType,
        UUID accountId,
        LocalDateTime createdAt
) {
}
