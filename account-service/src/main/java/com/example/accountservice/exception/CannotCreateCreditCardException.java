package com.example.accountservice.exception;

public class CannotCreateCreditCardException extends RuntimeException {
    public CannotCreateCreditCardException(String message) {
        super(message);
    }
}
