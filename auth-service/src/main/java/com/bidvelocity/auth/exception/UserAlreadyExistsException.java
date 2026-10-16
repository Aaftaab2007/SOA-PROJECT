package com.bidvelocity.auth.exception;

public class UserAlreadyExistsException
		extends RuntimeException {

	public UserAlreadyExistsException(String message) {
		super(message);
	}
}