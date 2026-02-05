package com.banking.account.domain.repository;

import com.banking.account.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
  // query: SELECT * FROM accounts WHERE customer_id = ?
  Optional<Account> findByCustomerId(String customerId);
}
