package com.api.ecomerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.api.ecomerce.dtos.response.ApiResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ApiResponse<String>> handleGenericException(GenericException ex, WebRequest request) {
        log.error("GenericException: {}", ex.getMessage());
        ApiResponse<String> errorResponse = ApiResponse.create(
                ex.getErrorCode().getHttpStatus(), ex.getErrorCode().getMessage());
        return new ResponseEntity<>(
                errorResponse, HttpStatus.valueOf(ex.getErrorCode().getHttpStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        log.error("MethodArgumentNotValidException: {}", ex.getMessage());
        StringBuilder errorMessage = new StringBuilder("Validation failed: ");
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMessage.append(fieldName).append(" ").append(message).append("; ");
        });

        ApiResponse<String> errorResponse = ApiResponse.create(HttpStatus.BAD_REQUEST.value(), errorMessage.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
        ExpiredJwtException.class,
        MalformedJwtException.class,
        SignatureException.class,
        UnsupportedJwtException.class
    })
    public ResponseEntity<ApiResponse<String>> handleJwtException(Exception ex, WebRequest request) {
        log.error("JWT Exception: {}", ex.getMessage());

        String errorMessage = "Invalid or expired token";
        if (ex instanceof ExpiredJwtException) {
            errorMessage = "Token has expired";
        } else if (ex instanceof MalformedJwtException) {
            errorMessage = "Invalid token format";
        } else if (ex instanceof SignatureException) {
            errorMessage = "Invalid token signature";
        }

        ApiResponse<String> errorResponse = ApiResponse.create(HttpStatus.UNAUTHORIZED.value(), errorMessage);

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGlobalException(Exception ex, WebRequest request) {
        log.error("Exception: {}", ex.getMessage());
        ApiResponse<String> errorResponse = ApiResponse.create(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred. Please try again later.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
