package com.example.spring.exception;

import java.util.logging.Logger;

public abstract class RequestException extends RuntimeException {
    int statusCode;
    protected RequestException(String msg) {
        super(msg);
        Logger logger = Logger.getGlobal();
        logger.severe(msg);

    }


}
