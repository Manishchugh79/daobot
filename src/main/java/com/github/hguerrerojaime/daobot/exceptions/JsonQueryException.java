package com.github.hguerrerojaime.daobot.exceptions;

public class JsonQueryException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5882683433626635116L;

	public JsonQueryException() {
		
	}

	public JsonQueryException(String message) {
		super(message);
	}

	public JsonQueryException(Throwable cause) {
		super(cause);
	}

	public JsonQueryException(String message, Throwable cause) {
		super(message, cause);
	}

}
