package com.projectx.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ApplicationException extends Exception{
    private String message;
    private Throwable e;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;

}
