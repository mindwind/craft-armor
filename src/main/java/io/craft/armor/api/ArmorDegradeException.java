package io.craft.armor.api;

/**
 * Exception thrown when armor degrade a method.
 * 
 * @author mindwind
 * @version 1.0, Jan 5, 2015
 */
public class ArmorDegradeException extends RuntimeException {

	
	private static final long serialVersionUID = -3156599947974023180L;

	public ArmorDegradeException() {}

	public ArmorDegradeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ArmorDegradeException(String message) {
		super(message);
	}

	public ArmorDegradeException(Throwable cause) {
		super(cause);
	}
	
}
