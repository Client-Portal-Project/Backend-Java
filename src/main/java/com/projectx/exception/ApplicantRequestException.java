package com.projectx.exception;

public class ApplicantRequestException extends RuntimeException{

    public ApplicantRequestException(String message){
        super(message);
    }

    public ApplicantRequestException(String message, Throwable throwable){
        super(message, throwable);
    }
}

