package com.projectx.exception;

public class ApplicationRequestException extends Throwable{
    public ApplicationRequestException(String message){
        super(message);
    }

    public ApplicationRequestException(String message, Throwable cause){
        super(message, cause);
    }
}
