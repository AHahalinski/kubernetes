package com.epam.learning.kubernetes.exception;

public class ExternalServiceUnavailable extends RuntimeException {
    public ExternalServiceUnavailable(String message) {
        super(message);
    }
}
