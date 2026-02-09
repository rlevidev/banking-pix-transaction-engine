package com.banking.pixkey.domain.service;

import com.banking.pixkey.api.dto.PixKeyRequestDTO;
import com.banking.pixkey.domain.exception.InvalidAccountException;
import com.banking.pixkey.domain.exception.PixKeyAlreadyExistsException;
import com.banking.pixkey.domain.model.PixKey;
import com.banking.pixkey.domain.repository.PixKeyRepository;
import com.banking.pixkey.infrastructure.client.AccountServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PixKeyService {
  private final PixKeyRepository pixKeyRepository;
  private final AccountServiceClient accountServiceClient;

  @Transactional
  public PixKey registerKey(PixKeyRequestDTO dto) {
    // Check if PIX key already exists
    if (pixKeyRepository.existsByKeyValue(dto.keyValue())) {
      throw new PixKeyAlreadyExistsException(dto.keyValue());
    }

    // Check if account exists
    if (!accountServiceClient.accountExists(dto.accountId())) {
      throw new InvalidAccountException(dto.accountId());
    }

    PixKey pixKey = new PixKey();
    pixKey.setKeyValue(dto.keyValue());
    pixKey.setKeyType(dto.keyType());
    pixKey.setAccountId(dto.accountId());

    return pixKeyRepository.save(pixKey);
  }
}
