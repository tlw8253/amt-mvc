package com.amt.exception;

public class AuthenticationFailureException extends Exception {
	private static final long serialVersionUID = 1L;

	public AuthenticationFailureException() {
		super();
	}

	public AuthenticationFailureException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AuthenticationFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenticationFailureException(String message) {
		super(message);
	}

	public AuthenticationFailureException(Throwable cause) {
		super(cause);
	}

}
