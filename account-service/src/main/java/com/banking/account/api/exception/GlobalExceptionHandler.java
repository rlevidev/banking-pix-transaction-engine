package com.banking.account.api.exception;

import com.banking.account.api.dto.ErrorResponseDTO;
import com.banking.account.domain.exception.AccountAlreadyExistsException;
import com.banking.account.domain.exception.AccountNotFoundException;
import com.banking.account.domain.exception.InsufficientBalanceException;
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
  @ExceptionHandler(AccountNotFoundException.class)
  public ResponseEntity<ErrorResponseDTO> handleAccountNotFound(
          AccountNotFoundException ex,
          HttpServletRequest request) {
    log.warn("Account not found: {}", ex.getAccountId());

    ErrorResponseDTO error = ErrorResponseDTO.of(
            HttpStatus.NOT_FOUND.value(),
            "Not Found",
            ex.getMessage(),
            request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(AccountAlreadyExistsException.class)
  public ResponseEntity<ErrorResponseDTO> handleAccountAlreadyExists(
          AccountAlreadyExistsException ex,
          HttpServletRequest request) {
    log.warn("Duplicate account creation attempt for customer: {}", ex.getCustomerId());

    ErrorResponseDTO error = ErrorResponseDTO.of(
            HttpStatus.CONFLICT.value(),
            "Conflict",
            ex.getMessage(),
            request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(InsufficientBalanceException.class)
  public ResponseEntity<ErrorResponseDTO> handleInsufficientBalance(
          InsufficientBalanceException ex,
          HttpServletRequest request) {
    log.warn("Insufficient balance: {}", ex.getDetailedMessage());

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

    log.warn("Unexpected error: {}", ex);

    ErrorResponseDTO error = ErrorResponseDTO.of(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            "An unexpected error occurred. Please contact support.",
            request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
