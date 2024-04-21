package com.trantring.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleException(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(AccessDeniedException exception) {
        return new ResponseEntity<>("You are not allowed!", HttpStatus.UNAUTHORIZED);
    }
}
