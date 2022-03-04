package com.projectx.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Setter

@NoArgsConstructor
@EqualsAndHashCode
public class ApplicantException {
    private String message;
    private Throwable throwable;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;

    public ApplicantException(String message, Throwable throwable, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
