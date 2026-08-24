package com.orderlist.api.exceptions.customs;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
