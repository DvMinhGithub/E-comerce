package com.api.ecomerce.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Authentication errors
    INVALID_CREDENTIALS("AUTH_001", 401),

    // Resource errors
    RESOURCE_NOT_FOUND("RES_001", 404),
    CONFLICT("RES_002", 409),

    // Business logic errors
    INSUFFICIENT_STOCK("BIZ_001", 400),

    // Permission errors
    ACCESS_DENIED("PERM_001", 403),
    ADMIN_REQUIRED("PERM_002", 403),

    // System errors
    INTERNAL_SERVER_ERROR("SYS_001", 500);

    private final String code;
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
