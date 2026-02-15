package com.banking.transactionservice.domain.service;

import com.banking.transactionservice.api.dto.TransactionRequestDTO;
import com.banking.transactionservice.domain.model.Transaction;
import com.banking.transactionservice.domain.model.TransactionStatus;
import com.banking.transactionservice.domain.repository.TransactionRepository;
import com.banking.transactionservice.infrastructure.client.AccountServiceClient;
import com.banking.transactionservice.infrastructure.client.PixKeyServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionService {
  private final TransactionRepository transactionRepository;
  private final PixKeyServiceClient pixKeyServiceClient;
  private final AccountServiceClient accountServiceClient;

  public Transaction processTransaction(TransactionRequestDTO dto) {
    return transactionRepository.findByIdempotencyKey(dto.idempotencyKey())
            .map(existing -> {
              log.info("Idempotency: Transação {} já processada.", dto.idempotencyKey());
              return existing;
            })
            .orElseGet(() -> executeNewTransaction(dto));
  }

  private Transaction executeNewTransaction(TransactionRequestDTO dto) {
    Transaction transaction = Transaction.builder()
            .idempotencyKey(dto.idempotencyKey())
            .sourceAccountId(dto.sourceAccountId())
            .amount(dto.amount())
            .pixKey(dto.pixKey())
            .status(TransactionStatus.PENDING)
            .build();

    transaction = transactionRepository.save(transaction);
    log.info("Transação iniciada com status PENDING: {}", transaction.getId());

    try {
      UUID destinationAccountId = pixKeyServiceClient.findAccountIdByPixKey(dto.pixKey())
              .orElseThrow(() -> new RuntimeException("Chave PIX não encontrada"));

      transaction.setDestinationAccountId(destinationAccountId);
      transaction.setStatus(TransactionStatus.PROCESSING);
      transactionRepository.save(transaction);

      accountServiceClient.debit(transaction.getSourceAccountId(), transaction.getAmount());
      log.info("Débito realizado com sucesso para a transação: {}", transaction.getId());

      try {
        accountServiceClient.credit(transaction.getDestinationAccountId(), transaction.getAmount());

        transaction.setStatus(TransactionStatus.COMPLETED);
        log.info("Crédito realizado com sucesso. Transação {} COMPLETED.", transaction.getId());
      } catch (Exception e) {
        log.error("Falha no crédito da transação {}. Iniciando ESTORNO (Refund)...", transaction.getId());
        accountServiceClient.credit(transaction.getSourceAccountId(), transaction.getAmount());

        transaction.setStatus(TransactionStatus.REFUNDED);
        log.warn("Transação {} estornada com sucesso (REFUNDED).", transaction.getId());
        throw new RuntimeException("Falha no destino. Dinheiro estornado.");
      }
    } catch (Exception e) {
      if (transaction.getStatus() != TransactionStatus.REFUNDED) {
        transaction.setStatus(TransactionStatus.FAILED);
      }

      throw e;
    } finally {
      return transactionRepository.save(transaction);
    }
  }
}
