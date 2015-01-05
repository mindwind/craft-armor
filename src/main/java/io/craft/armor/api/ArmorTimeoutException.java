package io.craft.armor.api;

/**
 * Exception thrown when armor invocation timeout.
 * 
 * @author mindwind
 * @version 1.0, Jan 5, 2015
 */
public class ArmorTimeoutException extends RuntimeException {

	
	private static final long serialVersionUID = -3156599947974023180L;

	public ArmorTimeoutException() {}

	public ArmorTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

	public ArmorTimeoutException(String message) {
		super(message);
	}

	public ArmorTimeoutException(Throwable cause) {
		super(cause);
	}
	
}
