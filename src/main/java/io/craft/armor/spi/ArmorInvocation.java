package io.craft.armor.spi;

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
	 * @return armor delegate object
	 */
	Object getDelegateObject();
	
	/**
	 * Set armor delegate object.
	 * 
	 * @param delegateObject
	 */
	void setDelegateObject(Object delegateObject);
	
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
