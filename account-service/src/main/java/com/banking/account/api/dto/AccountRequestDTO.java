package com.banking.account.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record AccountRequestDTO(
        @NotBlank(message = "O ID do cliente e obrigatório")
        String customerId,

        @NotNull(message = "O saldo inicial e obrigatório")
        @PositiveOrZero(message = "O saldo inicial não pode ser negativo")
        BigDecimal initialBalance
) {
}
