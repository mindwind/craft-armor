package io.craft.armor;

import java.io.Serializable;

/**
 * Represents armor context invocation object.
 * 
 * @author mindwind
 * @version 1.0, Dec 23, 2014
 */
public interface ArmorInvocation extends Serializable {
	
	/**
	 * @return armor delegate object
	 */
	Object getDelegateObject();
	
	/**
	 * @return method name
	 */
	String getMethodName();
	
	/**
	 * @return method parameter types.
	 */
	Class<?>[] getParameterTypes();
	
	/**
	 * @return method parameters.
	 */
	Object[] getParameters();
	
}
