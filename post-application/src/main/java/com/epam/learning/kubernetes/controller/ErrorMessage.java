package com.epam.learning.kubernetes.controller;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorMessage {

    private final int status;
    private final String code;

    private final Object messages;

    private final LocalDateTime timestamp;

    public ErrorMessage(Object messages, HttpStatus status) {
        this.status = status.value();
        this.code = status.name();
        this.messages = messages;
        timestamp = LocalDateTime.now();
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public Object getMessages() {
        return messages;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

