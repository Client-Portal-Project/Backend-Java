package com.projectx.helper;

public class IncorrectMethodNameException extends Exception { 
	private static final long serialVersionUID = 1L;

	public IncorrectMethodNameException(String errorMessage) {
        super(errorMessage);
    }
}
