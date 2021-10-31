package com.bootcamp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PasswordExpiredException extends RuntimeException{
    public PasswordExpiredException(String message) {
        super(message);
    }

}
