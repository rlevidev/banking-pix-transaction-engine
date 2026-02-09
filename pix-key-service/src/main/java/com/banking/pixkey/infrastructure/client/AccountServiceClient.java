package com.banking.pixkey.infrastructure.client;

import com.banking.pixkey.infrastructure.client.dto.AccountResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountServiceClient {
  private final RestTemplate restTemplate;

  @Value("${services.account.url:http://localhost:8081}")
  private String accountServiceUrl;

  public boolean accountExists(UUID accountId) {
    String url = accountServiceUrl + "/v1/accounts/" + accountId;

    try {
      log.debug("Calling Account Service to validate account: {}", accountId);

      // Send a GET request
      AccountResponseDTO accountResponseDTO = restTemplate.getForObject(url, AccountResponseDTO.class);

      return accountResponseDTO != null;
    } catch (HttpClientErrorException.NotFound e) {
      // 404 Not Found: Account not found (expected behavior)
      log.debug("Account {} not found", accountId);

      return false;
    } catch (HttpClientErrorException e) {
      // Others errors 4xx (400, 401, 403, etc)
      log.error("Client error when validation account {}: {} {}",
              accountId, e.getStatusCode(), e.getMessage());

      throw new RuntimeException("Failed to validate account: " + e.getMessage(), e);
    } catch (Exception e) {
      // Errors 5xx (500, 502, et
      log.error("Unexpected error when validation account {}: {}",
              accountId, e.getMessage());

      throw new RuntimeException("Account Service is unavailable", e);
    }
  }
}
