package com.banking.pixkey.api.exception;

import com.banking.pixkey.api.dto.ErrorResponseDTO;
import com.banking.pixkey.domain.exception.InvalidAccountException;
import com.banking.pixkey.domain.exception.PixKeyAlreadyExistsException;
import com.banking.pixkey.domain.exception.PixKeyNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(PixKeyNotFoundException.class)
  public ResponseEntity<ErrorResponseDTO> handlePixKeyNotFound(
          PixKeyNotFoundException ex,
          HttpServletRequest request) {
    log.warn("PIX key not found: {}", ex.getKeyValue());

    ErrorResponseDTO error = ErrorResponseDTO.of(
            HttpStatus.NOT_FOUND.value(),
            "Not Found",
            ex.getMessage(),
            request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(PixKeyAlreadyExistsException.class)
  public ResponseEntity<ErrorResponseDTO> handlePixKeyAlreadyExists(
          PixKeyAlreadyExistsException ex,
          HttpServletRequest request) {
    log.warn("Duplicate PIX key registration attempt: {}", ex.getKeyValue());

    ErrorResponseDTO error = ErrorResponseDTO.of(
            HttpStatus.CONFLICT.value(),
            "Conflict",
            ex.getMessage(),
            request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(InvalidAccountException.class)
  public ResponseEntity<ErrorResponseDTO> handleInvalidAccount(
          InvalidAccountException ex,
          HttpServletRequest request) {
    log.warn("Invalid account ID for PIX key registration: {}", ex.getAccountId());

    ErrorResponseDTO error = ErrorResponseDTO.of(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            "Unprocessable Entity",
            ex.getMessage(),
            request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDTO> handleValidationErrors(
          MethodArgumentNotValidException ex,
          HttpServletRequest request) {
    String message = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .reduce((a, b) -> a + "; " + b)
            .orElse("Validation failed");

    log.warn("Validation error: {}", message);

    ErrorResponseDTO error = ErrorResponseDTO.of(
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            message,
            request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDTO> handleGenericError(
          Exception ex,
          HttpServletRequest request) {
    log.warn("Unexpected error occurred", ex);

    ErrorResponseDTO error = ErrorResponseDTO.of(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            "An unexpected error occurred. Please contact support.",
            request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
