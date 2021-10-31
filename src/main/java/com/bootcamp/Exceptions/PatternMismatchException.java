package com.bootcamp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.CONFLICT)
public class PatternMismatchException extends RuntimeException{
        public PatternMismatchException(String message) {
            super(message);
        }
}
