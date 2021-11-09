package com.bilgeadam.aliergul.utils.exceptions;

public class ExceptionIncorrectPasswordBlockedStatus extends Exception {
	private static final long serialVersionUID = -4963573587148149322L;
	
	public ExceptionIncorrectPasswordBlockedStatus(String message, Throwable cause) {
		super(message, cause);
		
	}
	
	public ExceptionIncorrectPasswordBlockedStatus(String message) {
		super(message);
		
	}
	
	public ExceptionIncorrectPasswordBlockedStatus(Throwable cause) {
		super(cause);
		
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}
	
}
