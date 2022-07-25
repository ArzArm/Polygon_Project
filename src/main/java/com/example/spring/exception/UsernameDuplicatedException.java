package com.example.spring.exception;

import org.springframework.http.HttpStatus;

public class UsernameDuplicatedException extends RequestException{
    public UsernameDuplicatedException(String msg) {
        super(msg);
        statusCode =  HttpStatus.CONFLICT.value();
    }
}
