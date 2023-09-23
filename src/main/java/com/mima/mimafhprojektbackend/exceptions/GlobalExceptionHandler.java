package com.mima.mimafhprojektbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // 409 Conflict
    public String handleUserNameTakenException(EmailAlreadyRegisteredException ex) {
        return ex.getMessage();
    }

    // You can add more exception handlers here for other types of exceptions
}
