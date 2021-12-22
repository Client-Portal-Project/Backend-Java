package com.projectx.exceptions;

public class ResourceDoesNotExist extends RuntimeException{
    public ResourceDoesNotExist(String message){
        super(message);
    }
}
