package com.orderlist.api.exceptions.customs;

public class AccessDeniedException extends org.springframework.security.access.AccessDeniedException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
