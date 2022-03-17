package com.projectx.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class ApplicationException{
    private String message;
    private Throwable e;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;

}
