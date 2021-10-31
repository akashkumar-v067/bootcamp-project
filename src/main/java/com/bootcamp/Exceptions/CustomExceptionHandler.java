package com.bootcamp.Exceptions;

import com.bootcamp.Exceptions.EmailAlreadyActiveException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(EmailAlreadyActiveException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(EmailAlreadyActiveException ex, WebRequest request) {
        String details = ex.getLocalizedMessage();
        ExceptionResponse error = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleUserAlreadyExistsException(NotFoundException ex, WebRequest request) {
        String details = ex.getLocalizedMessage();
        ExceptionResponse error = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(error, HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(NullException.class)
    public final ResponseEntity<Object> handleWeekPasswordExException(NullException ex, WebRequest request) {
        String details = ex.getLocalizedMessage();
        ExceptionResponse error = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(PatternMismatchException.class)
    public final ResponseEntity<Object> handleProductNotFoundException(PatternMismatchException ex, WebRequest request) {
        String details = ex.getLocalizedMessage();
        ExceptionResponse error = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public final ResponseEntity<Object> handleProductNotFoundException(TokenNotFoundException ex, WebRequest request) {
        String details = ex.getLocalizedMessage();
        ExceptionResponse error = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleProductNotFoundException(UserNotFoundException ex, WebRequest request) {
        String details = ex.getLocalizedMessage();
        ExceptionResponse error = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleUserBadRequestException(BadRequestException ex, WebRequest request) {
        String details = ex.getLocalizedMessage();
        ExceptionResponse error = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}