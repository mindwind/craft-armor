package io.craft.armor;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Represents armor context invocation object.
 * 
 * @author mindwind
 * @version 1.0, Dec 23, 2014
 */
public interface ArmorInvocation extends Serializable {
	
	/**
	 * @return key string represents this invocation.
	 */
	String getKey();
	
	/**
	 * @return armor delegate object
	 */
	Object getDelegateObject();
	
	/**
	 * @return method
	 */
	Method getMethod();
	
	/**
	 * @return method parameter types.
	 */
	Class<?>[] getParameterTypes();
	
	/**
	 * @return method parameters.
	 */
	Object[] getParameters();
	
}
