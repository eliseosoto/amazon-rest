package com.supervaca.amazonrest.domain;

public class Error {
	private String code;
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Error [code=").append(code).append(", message=").append(message).append("]");
		return builder.toString();
	}
}
