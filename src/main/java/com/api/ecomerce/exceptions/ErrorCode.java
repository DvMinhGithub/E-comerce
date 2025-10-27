package com.api.ecomerce.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Authentication errors
    DUPLICATE_EMAIL("EMAIL_001", "Email already exists. Please choose a different email.", 409),
    INVALID_CREDENTIALS("AUTH_001", "Invalid email or password.", 401),

    // Resource errors
    RESOURCE_NOT_FOUND("RES_001", "Resource not found.", 404),
    CONFLICT("RES_002", "Resource already exists.", 409),

    // Business logic errors
    INSUFFICIENT_STOCK("BIZ_001", "Insufficient stock.", 400),

    // Permission errors
    ACCESS_DENIED("PERM_001", "Access denied.", 403),
    ADMIN_REQUIRED("PERM_002", "Admin privileges required.", 403),

    // System errors
    INTERNAL_SERVER_ERROR("SYS_001", "An unexpected error occurred. Please try again later.", 500);

    private final String code;
    private final String message;
    private final int httpStatus;

    public static ErrorCode fromCode(String code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode;
            }
        }
        return INTERNAL_SERVER_ERROR;
    }
}
