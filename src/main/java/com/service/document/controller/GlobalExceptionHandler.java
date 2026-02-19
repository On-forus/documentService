package com.service.document.controller;

import com.service.document.usecases.dto.ErrorResponse;
import com.service.document.usecases.error.DocumentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String RUSSIA_MOSCOW = "Europe/Moscow";


    @ExceptionHandler(DocumentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleRequestNotFound(DocumentNotFoundException exception){
        return ErrorResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .timeStamp(ZonedDateTime.now().withZoneSameInstant(ZoneId.of(RUSSIA_MOSCOW)))
                .build();
    }
}
