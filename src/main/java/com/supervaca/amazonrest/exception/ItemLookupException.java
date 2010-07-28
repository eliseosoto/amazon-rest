package com.supervaca.amazonrest.exception;

public class ItemLookupException extends AmazonRequestException {
	private static final long serialVersionUID = -906697276810313212L;

	public ItemLookupException(String message) {
		super(message);
	}

	public ItemLookupException(String message, Throwable cause) {
		super(message, cause);
	}
}
