package com.serviq.api.exception;

import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends ServiceException {

    public ServiceUnavailableException(String message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE.value());
    }
}
