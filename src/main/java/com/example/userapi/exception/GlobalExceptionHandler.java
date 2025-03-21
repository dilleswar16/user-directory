package com.example.userapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle validation errors (e.g., @Valid validation failures)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        log.warn("Validation failed: {}", errors);

        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation failed", errors, request);
    }

    /**
     * Handle API-specific exceptions thrown via ResponseStatusException
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(
            ResponseStatusException ex, HttpServletRequest request) {

        log.warn("API error: {}", ex.getMessage());

        return buildErrorResponse(ex.getStatus(), ex.getReason(), null, request);
    }

    
    /**
     * Handle custom UserNotFoundException.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(
            UserNotFoundException ex, HttpServletRequest request) {

        log.warn("User not found: {}", ex.getMessage());

        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null, request);
    }

    /**
     * Utility method to build a structured error response
     */
    private ResponseEntity<Map<String, Object>> buildErrorResponse(
            HttpStatus status, String message, Map<String, String> errors, HttpServletRequest request) {

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("message", message);
        errorResponse.put("path", request.getRequestURI());

        if (errors != null) {
            errorResponse.put("errors", errors);
        }

        return new ResponseEntity<>(errorResponse, status);
    }
}
