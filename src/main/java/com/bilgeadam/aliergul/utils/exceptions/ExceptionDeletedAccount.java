package com.bilgeadam.aliergul.utils.exceptions;

public class ExceptionDeletedAccount extends Exception {
	
	private static final long serialVersionUID = -6022046608150900576L;
	
	public ExceptionDeletedAccount(String message, Throwable cause) {
		super(message, cause);
		
	}
	
	public ExceptionDeletedAccount(String message) {
		super(message);
		
	}
	
}
