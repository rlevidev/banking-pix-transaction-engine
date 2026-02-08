package com.banking.pixkey.domain.repository;

import com.banking.pixkey.domain.model.PixKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PixKeyRepository extends JpaRepository<PixKey, UUID> {
  /**
   * Search for a PIX key by its value (e.g. joao@email.com, 12345678900).
   *
   * @param keyValue the value of the PIX key
   * @return Optional containing a PixKey if found
   */
  Optional<PixKey> findByKeyValue(String keyValue);

  /**
   * List all PIX keys registered for an account.
   *
   * @param accountId the UUID of the account
   * @return List of PIX keys (maybe empty)
   */
  List<PixKey> findByAccountId(UUID accountId);

  /**
   * Verify if a PIX key already exists in the system.
   *
   * @param keyValue the value of the PIX key
   * @return true if exists, false otherwise
   */
  boolean existsByKeyValue(String keyValue);
}
