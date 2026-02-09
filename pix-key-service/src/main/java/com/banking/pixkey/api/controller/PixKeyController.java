package com.banking.pixkey.api.controller;

import com.banking.pixkey.api.dto.PixKeyRequestDTO;
import com.banking.pixkey.api.dto.PixKeyResponseDTO;
import com.banking.pixkey.api.mapper.PixKeyMapper;
import com.banking.pixkey.domain.model.PixKey;
import com.banking.pixkey.domain.service.PixKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/pix-keys")
@RequiredArgsConstructor
public class PixKeyController {
  private final PixKeyService pixKeyService;
  private final PixKeyMapper pixKeyMapper;

  @PostMapping
  public ResponseEntity<PixKeyResponseDTO> register(@Valid @RequestBody PixKeyRequestDTO dto) {
    PixKey pixKey = pixKeyService.registerKey(dto);

    return ResponseEntity.status(HttpStatus.CREATED).body(pixKeyMapper.toResponseDTO(pixKey));
  }

  @GetMapping("/{keyValue}")
  public ResponseEntity<PixKeyResponseDTO> getByValue(@PathVariable String keyValue) {
    return ResponseEntity.ok(pixKeyService.findByKeyValue(keyValue));
  }

  @GetMapping
  public ResponseEntity<List<PixKeyResponseDTO>> getByAccountId(@RequestParam UUID accountId) {
    return ResponseEntity.ok(pixKeyService.findByAccountId(accountId));
  }

  @DeleteMapping("/{keyValue}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable String keyValue) {
    pixKeyService.deleteKey(keyValue);
  }
}
