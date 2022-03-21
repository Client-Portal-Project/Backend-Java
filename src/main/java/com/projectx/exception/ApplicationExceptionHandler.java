package com.projectx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = {ApplicationRequestException.class})
    public ResponseEntity<Object> handleApplicationRequestException(ApplicationRequestException e) {
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }
}
