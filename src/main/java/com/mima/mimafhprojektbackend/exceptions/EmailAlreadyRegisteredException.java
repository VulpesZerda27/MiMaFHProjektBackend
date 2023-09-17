package com.mima.mimafhprojektbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Email already taken")
public class EmailAlreadyRegisteredException extends RuntimeException {
    // Your exception code here
    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
}
