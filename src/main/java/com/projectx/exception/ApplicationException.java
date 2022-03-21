package com.projectx.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@AllArgsConstructor
public class ApplicationException extends Exception{
    private final String message;
    private final Throwable e;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;



}
