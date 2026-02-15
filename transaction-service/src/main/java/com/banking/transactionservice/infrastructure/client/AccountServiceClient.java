package com.banking.transactionservice.infrastructure.client;

import com.banking.transactionservice.infrastructure.client.dto.AccountMovementRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountServiceClient {
  private final RestTemplate restTemplate;

  @Value("${services.account.url:http://localhost:8081}")
  private String accountServiceUrl;

  /**
   * Debits the specified amount from the account.
   *
   * @param accountId the account ID
   * @param amount    the amount to debit
   */
  public void debit(UUID accountId, BigDecimal amount) {
    String url = accountServiceUrl + "/v1/accounts/" + accountId + "/debit";

    AccountMovementRequestDTO accountMovementRequestDTO = new AccountMovementRequestDTO(amount);

    try {
      log.info("Calling Account Service to debit account {} with amount {}", accountId, amount);

      restTemplate.exchange(
              url,
              HttpMethod.PATCH,
              new HttpEntity<>(accountMovementRequestDTO),
              Void.class
      );

      log.info("Debit successful for account: {}", accountId);
    } catch (HttpClientErrorException e) {
      // 4xx (Insufficient balance, account blocked, etc...)
      log.warn("Business failure when debiting account {}: {}", accountId, e.getResponseBodyAsString());

      throw e;
    } catch (Exception e) {
      // Network errors or 5xx
      log.error("Error when performing debit for account {}: {}", accountId, e.getMessage());

      throw new RuntimeException("Error communicating with the accounts service");
    }
  }

  /**
   * Credits the specified amount to the account.
   *
   * @param accountId the account ID
   * @param amount    the amount to credit
   */
  public void credit(UUID accountId, BigDecimal amount) {
    String url = accountServiceUrl + "/v1/accounts/" + accountId + "/credit";

    AccountMovementRequestDTO accountMovementRequestDTO = new AccountMovementRequestDTO(amount);

    try {
      log.info("Calling Account Service to credit account {} with amount {}", accountId, amount);

      restTemplate.exchange(
              url,
              HttpMethod.PATCH,
              new HttpEntity<>(accountMovementRequestDTO),
              Void.class
      );

      log.info("Credit successful for account: {}", accountId);
    } catch (HttpClientErrorException e) {
      // 4xx (Insufficient balance, account blocked, etc...)
      log.warn("Business failure when crediting account {}: {}", accountId, e.getResponseBodyAsString());

      throw e;
    } catch (Exception e) {
      // Network errors or 5xx
      log.error("Error when performing credit for account {}: {}", accountId, e.getMessage());

      throw new RuntimeException("Error communicating with the accounts service");
    }
  }
}
