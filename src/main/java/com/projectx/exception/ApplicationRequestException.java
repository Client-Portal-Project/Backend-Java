package com.projectx.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class ApplicationRequestException extends RuntimeException{

    private String message;
    private Throwable e;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;

    public ApplicationRequestException(String message){
        super(message);
    }

    public ApplicationRequestException(String message, Throwable cause){
        super(message, cause);
    }

    public ApplicationRequestException() {

    }
}
