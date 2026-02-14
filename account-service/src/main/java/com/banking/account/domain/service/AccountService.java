package com.banking.account.domain.service;

import com.banking.account.api.dto.AccountRequestDTO;
import com.banking.account.domain.exception.AccountAlreadyExistsException;
import com.banking.account.domain.exception.AccountNotFoundException;
import com.banking.account.domain.model.Account;
import com.banking.account.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
  private final AccountRepository accountRepository;

  @Transactional
  public Account createAccount(AccountRequestDTO dto) {
    accountRepository.findByCustomerId(dto.customerId())
            .ifPresent(account -> {
              throw new AccountAlreadyExistsException(dto.customerId());
            });

    // Mapper DTO -> Entity
    Account account = new Account();
    account.setCustomerId(dto.customerId());
    account.setBalance(dto.initialBalance());

    return accountRepository.save(account);
  }

  public Account getAccountById(UUID id) {
    return accountRepository.findById(id)
            .orElseThrow(() -> new AccountNotFoundException(id));
  }

  @Transactional
  public void debit(UUID id, BigDecimal amount) {
    Account account = getAccountById(id);
    account.debit(amount);

    accountRepository.save(account);
  }

  @Transactional
  public void credit(UUID id, BigDecimal amount) {
    Account account = getAccountById(id);
    account.credit(amount);

    accountRepository.save(account);
  }
}
