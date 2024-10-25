package com.epam.learning.kubernetes.exception;

public class UserEntityNotFoundException extends RuntimeException {

    public UserEntityNotFoundException(String message) {
        super(message);
    }
}
