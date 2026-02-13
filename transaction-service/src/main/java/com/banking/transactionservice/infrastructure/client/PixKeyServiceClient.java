package com.banking.transactionservice.infrastructure.client;

import com.banking.transactionservice.infrastructure.client.dto.PixKeyResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PixKeyServiceClient {
  private final RestTemplate restTemplate;

  @Value("${services.pix-key.url:http://localhost:8082}")
  private String pixKeyServiceUrl;

  /**
   * Responsibility: Query the pix-key-service to translate a key into an account ID.
   *
   * @param pixKey
   * @return
   */
  public Optional<UUID> findAccountIdByPixKey(String pixKey) {
    // 1. Build URL (ex: http://localhost:8082/v1/pix-keys/person@gmail.com)
    String url = String.format("%s/v1/pix-keys/%s", pixKeyServiceUrl, pixKey);

    try {
      log.info("Querying destination account for the PIX key: {}", pixKey);

      // 2. Perform HTTP GET and try to read the JSON as PixKeyResponseDTO
      PixKeyResponseDTO pixKeyResponseDTO = restTemplate.getForObject(url, PixKeyResponseDTO.class);

      // 3. Return accountId wrapped in an Optional
      return Optional.ofNullable(pixKeyResponseDTO).map(PixKeyResponseDTO::accountId);

    } catch (HttpClientErrorException.NotFound e) {
      // Case 404: The key does not exist. We return empty so the Service can decide what to do.
      log.warn("PIX key not found: {}", pixKey);

      return Optional.empty();
    } catch (Exception e) {
      // Case 5xx or network error: We throw a technical exception (the engine has stopped!)
      log.error("Error communicating with pix-key-service for the key {}", pixKey, e);

      throw new RuntimeException("PIX Key Service is unavailable");
    }
  }
}
