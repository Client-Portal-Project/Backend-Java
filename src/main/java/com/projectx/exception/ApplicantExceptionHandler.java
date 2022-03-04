package com.projectx.exception;

import com.projectx.exception.ApplicantException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApplicantExceptionHandler {

    @ExceptionHandler(value = {ApplicantRequestException.class})
    public ResponseEntity<Object> handleRequestException(ApplicantRequestException e){

        ApplicantException applicantException = new ApplicantException(
                e.getMessage(),
                e,
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(applicantException, HttpStatus.BAD_REQUEST);
    }
}
