package io.craft.armor;

/**
 * @author mindwind
 * @version 1.0, Dec 24, 2014
 */
public interface ArmorResult {
	
	/**
	 * Get invoke result.
	 * 
	 * @return result. if no result return null.
	 */
	Object getValue();
	
	/**
	 * Get invoke exception.
	 * 
	 * @return exception. if no exception return null.
	 */
	Throwable getException();

}
