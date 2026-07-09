package com.orderlist.api.exceptions.customs;

import org.springframework.security.core.AuthenticationException;


public class CustomAuthenticationException extends AuthenticationException {

    private final AuthErrorCode errorCode;

    public CustomAuthenticationException(AuthErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AuthErrorCode getErrorCode() {
        return errorCode;
    }

    public enum AuthErrorCode {
        TOKEN_MISSING("Token not provided"),
        TOKEN_INVALID("Invalid token"),
        TOKEN_EXPIRED("Your session has expired, please log in again");
        private final String message;

        AuthErrorCode(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}