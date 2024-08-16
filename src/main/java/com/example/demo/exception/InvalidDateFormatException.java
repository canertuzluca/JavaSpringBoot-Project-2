package com.example.demo.exception;

public class InvalidDateFormatException extends RuntimeException {

    public InvalidDateFormatException(String message) {
        super(message);
    }
}
