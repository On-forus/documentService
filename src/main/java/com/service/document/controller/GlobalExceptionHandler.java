package com.service.document.controller;

import com.service.document.usecases.dto.ErrorResponse;
import com.service.document.usecases.error.DocumentNotFoundException;
import com.service.document.usecases.error.RequestValueException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String EUROPE_MOSCOW = "Europe/Moscow";


    @ExceptionHandler(DocumentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleRequestNotFound(DocumentNotFoundException exception) {
        return ErrorResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .timeStamp(ZonedDateTime.now().withZoneSameInstant(ZoneId.of(EUROPE_MOSCOW)))
                .build();
    }


    @ExceptionHandler(RequestValueException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handelRequest(RequestValueException exception){
        return ErrorResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .timeStamp(ZonedDateTime.now().withZoneSameInstant(ZoneId.of(EUROPE_MOSCOW)))
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidateException(ConstraintViolationException exception){
        return ErrorResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .timeStamp(ZonedDateTime.now().withZoneSameInstant(ZoneId.of(EUROPE_MOSCOW)))
                .build();
    }

    //todo


}
