package com.alpha.community.exception;

public class CustomizeException extends RuntimeException{

	private String message;
	
	public CustomizeException(CustomizeErrorCode code) {
		this.message = code.getMessage();
	}

	public String getMessage() {
		return message;
	}
	
	
}
