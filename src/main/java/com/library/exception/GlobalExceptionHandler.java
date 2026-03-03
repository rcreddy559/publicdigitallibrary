package com.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex, ServerWebExchange exchange) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), exchange.getRequest().getPath().value());
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiError> handleBookNotFound(BookNotFoundException ex, ServerWebExchange exchange) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), exchange.getRequest().getPath().value());
    }

    @ExceptionHandler(DashboardNotFoundException.class)
    public ResponseEntity<ApiError> handleDashboardNotFound(DashboardNotFoundException ex, ServerWebExchange exchange) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), exchange.getRequest().getPath().value());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex, ServerWebExchange exchange) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), exchange.getRequest().getPath().value());
    }

    private ResponseEntity<ApiError> buildError(HttpStatus status, String message, String path) {
        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .build();
        return ResponseEntity.status(status).body(error);
    }
}
