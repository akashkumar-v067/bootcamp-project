package com.bootcamp.exceptions;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


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
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
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


    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse error = new ExceptionResponse(new Date(), "Method Type is Not Valid",
                request.getDescription(false));
        return new ResponseEntity(error, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse error = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(error, status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse error = new ExceptionResponse(new Date(),"Method parameter is null or mismatched" ,
                request.getDescription(false));
        return new ResponseEntity(error, status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse error = new ExceptionResponse(new Date(),"Method parameter is null or mismatched" ,
                request.getDescription(false));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }
}