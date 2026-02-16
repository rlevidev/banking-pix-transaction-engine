package com.banking.transactionservice.api.controller;

import com.banking.transactionservice.api.dto.TransactionRequestDTO;
import com.banking.transactionservice.api.dto.TransactionResponseDTO;
import com.banking.transactionservice.api.mapper.TransactionMapper;
import com.banking.transactionservice.domain.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
  private final TransactionService transactionService;
  private final TransactionMapper transactionMapper;

  @PostMapping
  public ResponseEntity<TransactionResponseDTO> create(@Valid @RequestBody TransactionRequestDTO dto) {
    return ResponseEntity.ok(transactionMapper.toResponseDTO(transactionService.processTransaction(dto)));
  }
}
