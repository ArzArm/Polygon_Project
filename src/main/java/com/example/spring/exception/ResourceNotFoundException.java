package com.example.spring.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RequestException  {

    public ResourceNotFoundException(String msg){
        super(msg);
        statusCode =  HttpStatus.NOT_FOUND.value();
    }
}
