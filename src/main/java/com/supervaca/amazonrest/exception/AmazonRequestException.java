package com.supervaca.amazonrest.exception;

public class AmazonRequestException extends RuntimeException {
	private static final long serialVersionUID = -8304071901239666696L;

	public AmazonRequestException(String message) {
		super(message);
	}

	public AmazonRequestException(String message, Throwable cause) {
		super(message, cause);
	}
}
