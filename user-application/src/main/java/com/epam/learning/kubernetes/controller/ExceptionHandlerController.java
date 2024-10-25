package com.epam.learning.kubernetes.controller;

import com.epam.learning.kubernetes.exception.UserEntityNotFoundException;
import com.epam.learning.kubernetes.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(UserEntityNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorMessage handleEntityNotFoundException(UserEntityNotFoundException ex) {
        return new ErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleValidationException(ValidationException ex) {
        return new ErrorMessage(ex.getMessages(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage convertUnmanagedExceptionToInternalServerError(Exception ex) {
        return new ErrorMessage(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
