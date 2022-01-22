package com.shoppingcart.exceptions;

public class InvalidInfoException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InvalidInfoException(String msg) {
        super(msg);
    }

    public InvalidInfoException(String msg, Throwable cause) {
        super(msg, cause);
    }
	
}
