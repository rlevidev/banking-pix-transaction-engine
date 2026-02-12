package com.banking.transactionservice.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class PixKeyServiceClient {
  private final RestTemplate restTemplate;

  @Value("${services.pix-key.url:http://localhost:8082}")

  private String pixKeyServiceUrl;
  public PixKeyServiceClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }
}
