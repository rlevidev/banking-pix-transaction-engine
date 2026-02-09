package com.banking.pixkey.infrastructure.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder
            // Connection timeout: if connection fails within 3 seconds, fail
            .connectTimeout(Duration.ofSeconds(3))

            // Read timeout: if response does not arrive within 5 seconds, fail
            .readTimeout(Duration.ofSeconds(5))
            .build();
  }
}
