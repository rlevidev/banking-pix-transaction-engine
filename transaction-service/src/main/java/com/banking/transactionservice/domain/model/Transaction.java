package com.banking.transactionservice.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions", indexes = {
        // Idempotency: Most critical to prevent double spending
        @Index(name = "idx_transaction_idempotency", columnList = "idempotency_key", unique = true),

        // Performance: For checking the sender's account statement
        @Index(name = "idx_transaction_source_account", columnList = "source_account_id"),

        // Performance: For checking the recipient's account statement
        @Index(name = "idx_transaction_dest_account", columnList = "destination_account_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  // Ensures that an operation is never executed twice.
  @Column(name = "idempotency_key", nullable = false, unique = true, updatable = false)
  private String idempotencyKey;

  // Identifies from which account the money should come out.
  @Column(name = "source_account_id", nullable = false, updatable = false)
  private UUID sourceAccountId;

  // Identifies to which account the money should go.
  @Column(name = "destination_account_id")
  private UUID destinationAccountId;

  @Column(nullable = false, precision = 19, scale = 2, updatable = false)
  private BigDecimal amount;

  // Store the key used (CPF, Email, etc.) for purposes of receipt and history
  @Column(name = "pix_key", nullable = false, updatable = false)
  private String pixKey;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionStatus status;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }
}
