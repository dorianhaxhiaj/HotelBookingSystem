package com.databridgeproject.error;

/**
 * Custom exception that is thrown when in case
 * a resource be it an image or an entity is not found,
 * resulting in a HTTP 404 response.
 * 
 * @author Dorian Haxhiaj
 *
 */
@SuppressWarnings("serial")
public class ResourceNotFoundException extends Exception {

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
