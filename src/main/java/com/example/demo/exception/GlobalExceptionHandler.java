package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NewsNotFoundException.class)
    public ResponseEntity<Error> handleNewsNotFoundException(NewsNotFoundException ex) {
        Error error = new Error();
        error.setStatusCod(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        error.setTimestamp(new Date());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NewsBodyNotGood.class)
    public ResponseEntity<Error> handleNewsBodyNotGood(NewsBodyNotGood ex2) {
        Error error = new Error();
        error.setStatusCod(HttpStatus.NOT_ACCEPTABLE.value());
        error.setMessage(ex2.getMessage());
        error.setTimestamp(new Date());

        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(InvalidDateFormatException.class)
    public ResponseEntity<Error> handleInvalidDateFormatException(InvalidDateFormatException ex) {
        Error error = new Error();
        error.setStatusCod(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        error.setTimestamp(new Date());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
