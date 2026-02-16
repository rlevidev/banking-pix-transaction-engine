package com.banking.transactionservice.api.exception;

import com.banking.transactionservice.api.dto.ErrorResponseDTO;
import com.banking.transactionservice.domain.exception.PixKeyNotFoundException;
import com.banking.transactionservice.domain.exception.TransactionNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(TransactionNotFoundException.class)
  public ResponseEntity<ErrorResponseDTO> handleNotFound(
          TransactionNotFoundException ex,
          HttpServletRequest request) {
    log.warn("Transaction not found: {}", ex.getTransactionId());

    ErrorResponseDTO error = ErrorResponseDTO.of(
            404,
            "Not Found",
            ex.getMessage(),
            request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(PixKeyNotFoundException.class)
  public ResponseEntity<ErrorResponseDTO> handlePixKeyNotFound(
          PixKeyNotFoundException ex,
          HttpServletRequest request) {
    log.warn("Pix Key not found: {}", ex.getPixKey());

    ErrorResponseDTO error = ErrorResponseDTO.of(
            HttpStatus.UNPROCESSABLE_ENTITY.value(), // 422
            "Unprocessable Entity",
            ex.getMessage(),
            request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
  }

  @ExceptionHandler(HttpClientErrorException.class)
  public ResponseEntity<ErrorResponseDTO> handleClientErrorException(
          HttpClientErrorException ex,
          HttpServletRequest request) {
    log.warn("Client Error: {}", ex.getMessage());

    ErrorResponseDTO error = ErrorResponseDTO.of(
            ex.getStatusCode().value(),
            "Integration Error",
            ex.getResponseBodyAsString(),
            request.getRequestURI()
    );

    return ResponseEntity.status(ex.getStatusCode()).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDTO> handleValidationErrors(
          MethodArgumentNotValidException ex,
          HttpServletRequest request) {
    String errors = ex.getBindingResult().getFieldErrors().stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .collect(Collectors.joining("; "));

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponseDTO.of(400, "Bad Request", errors, request.getRequestURI())
    );
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDTO> handleGenericException(
          Exception ex,
          HttpServletRequest request) {
    log.error("Unexpected error: {}", ex);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ErrorResponseDTO.of(500, "Internal Server Error", "An unexpected error occurred", request.getRequestURI())
    );
  }
}
