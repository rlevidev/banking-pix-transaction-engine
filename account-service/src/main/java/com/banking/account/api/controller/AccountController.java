package com.banking.account.api.controller;

import com.banking.account.api.dto.AccountRequestDTO;
import com.banking.account.api.dto.AccountResponseDTO;
import com.banking.account.api.dto.TransactionMovementDTO;
import com.banking.account.api.mapper.AccountMapper;
import com.banking.account.domain.model.Account;
import com.banking.account.domain.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
  private final AccountService accountService;
  private final AccountMapper accountMapper;

  @PostMapping
  public ResponseEntity<AccountResponseDTO> create(@Valid @RequestBody AccountRequestDTO requestDTO) {
    Account account = accountService.createAccount(requestDTO);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(accountMapper.toResponseDTO(account));
  }

  @GetMapping("/{id}")
  public ResponseEntity<AccountResponseDTO> getById(@PathVariable UUID id) {
    Account account = accountService.getAccountById(id);
    return ResponseEntity.ok(accountMapper.toResponseDTO(account));
  }

  @PatchMapping("/{id}/debit")
  public ResponseEntity<Void> debit(@PathVariable UUID id, @Valid @RequestBody TransactionMovementDTO movementDTO) {
    accountService.debit(id, movementDTO.amount());

    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/credit")
  public ResponseEntity<Void> credit(@PathVariable UUID id, @Valid @RequestBody TransactionMovementDTO movementDTO) {
    accountService.credit(id, movementDTO.amount());

    return ResponseEntity.noContent().build();
  }
}
