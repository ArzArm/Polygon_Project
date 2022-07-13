package com.example.spring.exception;

import org.springframework.http.HttpStatus;

public class FailDataException extends RequestException{
    public FailDataException(String msg){
        super(msg);
        statusCode = HttpStatus.BAD_REQUEST.value();
    }
}
