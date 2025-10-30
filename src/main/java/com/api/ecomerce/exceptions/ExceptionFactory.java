package com.api.ecomerce.exceptions;

/**
 * Factory class for creating exceptions with consistent error messages.
 * All exception messages are defined here for easy maintenance.
 */
public final class ExceptionFactory {

    private ExceptionFactory() {
        // Utility class
    }

    // ==================== AUTHENTICATION ====================

    public static GenericException invalidCredentials() {
        return new GenericException(ErrorCode.INVALID_CREDENTIALS, "Invalid email or password.");
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

    // ==================== BUSINESS LOGIC ====================

    public static GenericException insufficientStock(String productName, int available, int requested) {
        return new GenericException(
                ErrorCode.INSUFFICIENT_STOCK,
                String.format(
                        "Insufficient stock for '%s'. Available: %d, Requested: %d",
                        productName, available, requested));
    }

    // ==================== PERMISSION ====================

    public static GenericException accessDenied() {
        return new GenericException(ErrorCode.ACCESS_DENIED, "Access denied.");
    }

    public static GenericException adminRequired() {
        return new GenericException(ErrorCode.ADMIN_REQUIRED, "Admin privileges required.");
    }

    // ==================== SYSTEM ====================

    public static GenericException internalServerError() {
        return new GenericException(
                ErrorCode.INTERNAL_SERVER_ERROR, "An unexpected error occurred. Please try again later.");
    }
}
