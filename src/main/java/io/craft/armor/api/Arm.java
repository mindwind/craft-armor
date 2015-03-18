package io.craft.armor.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates a method is armed, using this on top of service implementor method.
 * 
 * @author mindwind
 * @version 1.0, Feb 26, 2015
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Arm {
	
	/** 
	 * @return true if armed method executed in asynchronous mode.
	 */
	boolean async() default false;
	
	/**
	 * @return timeout in seconds while armed method execution, when set value == 0 using default.
	 */
	long timeout() default 0;
	
	/**
	 * @return execute thread number, when set value == 0 using default.
	 */
	int threads() default 0;
	
}
