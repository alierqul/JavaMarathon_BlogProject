package com.bilgeadam.aliergul.utils.exceptions;

public class ExceptionNotAuthorizedError extends Exception {
	private static final long serialVersionUID = -4963573587148149322L;
	
	public ExceptionNotAuthorizedError(String message, Throwable cause) {
		super(message, cause);
		
	}
	
	public ExceptionNotAuthorizedError(String message) {
		super(message);
		
	}
	
	public ExceptionNotAuthorizedError(Throwable cause) {
		super(cause);
		
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}
	
}
