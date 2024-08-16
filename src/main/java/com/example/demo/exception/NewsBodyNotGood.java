package com.example.demo.exception;

public class NewsBodyNotGood extends RuntimeException{

    public NewsBodyNotGood(String message) {
        super(message);
    }

}
