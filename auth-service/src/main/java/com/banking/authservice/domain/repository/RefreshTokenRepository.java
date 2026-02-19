package com.banking.authservice.domain.repository;

import com.banking.authservice.domain.model.RefreshToken;
import com.banking.authservice.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
  Optional<RefreshToken> findByToken(String token);
  void deleteByToken(User user);
  void deleteAllByExpiresAtBefore(Instant now);
}
