
package com.triconinfotech.WealthWise.exception;

import java.util.UUID;


import org.springframework.http.HttpStatus;

/**
 * CustomException represents a custom runtime exception with a UUID and an HTTP status.
 * This exception is typically used to handle custom errors in the application.
 */
public class CustomException extends RuntimeException {
	
	 /** The UUID of the exception. */
	private final String uuid;
	
	/** The HTTP status of the exception. */
	private final HttpStatus status;

    /**
     * Constructs a new CustomException with the specified message and HTTP status.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     * @param status the HTTP status.
     */
	public CustomException(String message, HttpStatus status) {
		super(message);
		this.uuid = UUID.randomUUID().toString();
		this.status = status;
		System.out.println(message);
	}

    /**
     * Returns the UUID of the exception.
     *
     * @return the UUID of the exception.
     */
	public String getUuid() {
		return uuid;
	}

    /**
     * Returns the HTTP status of the exception.
     *
     * @return the HTTP status of the exception.
     */
	public HttpStatus getStatus() {
		return status;
	}
	
}
