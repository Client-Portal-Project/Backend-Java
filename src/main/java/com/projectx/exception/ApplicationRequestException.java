package com.projectx.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;


public class ApplicationRequestException extends RuntimeException{

    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

    public ApplicationRequestException(String message){
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.timestamp = ZonedDateTime.now();
    }

    public ApplicationRequestException(String message, Throwable cause){
        super(message, cause);
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.timestamp = ZonedDateTime.now();
    }

}
