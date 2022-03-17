package com.projectx.exception;

public class ApplicationRequestException extends RuntimeException{
    public ApplicationRequestException(String message){
        super(message);
    }

    public ApplicationRequestException(String message, Throwable cause){
        super(message, cause);
    }

    public ApplicationRequestException() {

    }
}
