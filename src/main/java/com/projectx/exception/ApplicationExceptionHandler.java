package com.projectx.exception;

import com.projectx.models.Application;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLOutput;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = {ApplicationRequestException.class})
    public ResponseEntity<Object> handleApplicationRequestException(ApplicationRequestException e) {
        System.err.println(e.getHttpStatus() + " : " + e.getTimestamp());
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }
}
