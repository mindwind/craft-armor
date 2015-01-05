package io.craft.armor.api;

/**
 * Exception thrown when armor execution error.
 * 
 * @author mindwind
 * @version 1.0, Jan 5, 2015
 */
public class ArmorExecutionException extends RuntimeException {

	
	private static final long serialVersionUID = -3156599947974023180L;

	public ArmorExecutionException() {}

	public ArmorExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ArmorExecutionException(String message) {
		super(message);
	}

	public ArmorExecutionException(Throwable cause) {
		super(cause);
	}
	
}
