package com.api.ecomerce.exceptions.factory;

import com.api.ecomerce.exceptions.ErrorCode;
import com.api.ecomerce.exceptions.GenericException;

public final class ExceptionFactory {

    private ExceptionFactory() {
        // Utility class
    }

    // ==================== AUTHENTICATION ====================
    public static GenericException duplicateEmail(String email) {
        return new GenericException(ErrorCode.DUPLICATE_EMAIL, email);
    }

    public static GenericException invalidCredentials() {
        return new GenericException(ErrorCode.INVALID_CREDENTIALS);
    }

    // ==================== RESOURCE ====================
    public static GenericException resourceNotFound(String resourceName, String fieldName, Object fieldValue) {
        return new GenericException(
                ErrorCode.RESOURCE_NOT_FOUND,
                String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
    }

    public static GenericException conflict(String resourceName, String fieldName, Object fieldValue) {
        return new GenericException(
                ErrorCode.CONFLICT,
                String.format("%s with %s: '%s' already exists", resourceName, fieldName, fieldValue));
    }

    // ==================== BUSINESS ====================
    public static GenericException insufficientStock(String productName, int available, int requested) {
        return new GenericException(
                ErrorCode.INSUFFICIENT_STOCK,
                String.format(
                        "Insufficient stock for '%s'. Available: %d, Requested: %d",
                        productName, available, requested));
    }

    // ==================== PERMISSION ====================
    public static GenericException accessDenied() {
        return new GenericException(ErrorCode.ACCESS_DENIED);
    }

    public static GenericException adminRequired() {
        return new GenericException(ErrorCode.ADMIN_REQUIRED);
    }
}
