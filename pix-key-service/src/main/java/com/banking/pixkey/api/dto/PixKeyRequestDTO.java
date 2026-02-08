package com.banking.pixkey.api.dto;

import com.banking.pixkey.domain.model.PixKeyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record PixKeyRequestDTO(
        @NotBlank(message = "O valor da chave é obrigatório")
        @Size(max = 77, message = "A chave não pode ter mais de 77 caracteres")
        String keyValue,

        @NotNull(message = "O tipo da chave é obrigatório")
        PixKeyType keyType,

        @NotNull(message = "O ID da conta é obrigatório")
        UUID accountId
) {
}
