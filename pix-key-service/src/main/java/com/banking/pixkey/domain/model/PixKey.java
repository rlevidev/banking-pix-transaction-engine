package com.banking.pixkey.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pix_keys", indexes = {
        @Index(name = "idx_key_value", columnList = "key_value", unique = true),
        @Index(name = "idx_account_id", columnList = "account_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PixKey {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "key_value", nullable = false, unique = true, length = 77)
  private String keyValue;

  @Enumerated(EnumType.STRING)
  @Column(name = "key_type", nullable = false, length = 10)
  private PixKeyType keyType;

  @Column(name = "account_id", nullable = false)
  private UUID accountId;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }
}
