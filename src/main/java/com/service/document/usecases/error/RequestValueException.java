package com.service.document.usecases.error;

public class RequestValueException extends RuntimeException {
    public RequestValueException(String s) {
        super(s);
    }
}
