package com.banking.transactionservice.infrastructure.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class PixKeyServiceClient {
  private final RestTemplate restTemplate;

  @Value("${services.pix-key.url:http://localhost:8082}")
  private String pixKeyServiceUrl;
}
