package com.projectx.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class ApplicationRequestException extends RuntimeException{

    public ApplicationRequestException(String message){
        super(message);
    }

    public ApplicationRequestException(String message, Throwable cause){
        super(message, cause);
    }

}
