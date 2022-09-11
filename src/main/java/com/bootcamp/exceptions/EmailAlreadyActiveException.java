package com.bootcamp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyActiveException extends RuntimeException{

    public EmailAlreadyActiveException(String message) {
        super(message);
    }
}
