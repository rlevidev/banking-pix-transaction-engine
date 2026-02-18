package com.banking.authservice.domain.repository;

import com.banking.authservice.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
  boolean existsByEmail(String email);
  Optional<User> findByEmail(String email);
}
