package com.epam.learning.kubernetes.controller;

import com.epam.learning.kubernetes.exception.ExternalServiceUnavailable;
import com.epam.learning.kubernetes.exception.EntityNotFoundException;
import com.epam.learning.kubernetes.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorMessage handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleValidationException(ValidationException ex) {
        return new ErrorMessage(ex.getMessages(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExternalServiceUnavailable.class)
    @ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorMessage handleExternalServiceUnavailable(ExternalServiceUnavailable ex) {
        return new ErrorMessage(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage convertUnmanagedExceptionToInternalServerError(Exception ex) {
        return new ErrorMessage(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
