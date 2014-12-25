package io.craft.armor;

import io.craft.armor.spi.ArmorFilterChain;

import java.util.concurrent.ExecutorService;

/**
 * Central interface to provide configuration for an armed service.
 * 
 * @author mindwind
 * @version 1.0, Dec 24, 2014
 */
public interface ArmorContext {
	
	/**
	 * Set armor context flag.
	 */
	void set();
	
	/**
	 * Remove armor context flag.
	 */
	void remove();
	
	/**
	 * @return true if current invocation to be executed in an armor context.
	 */
	boolean isInContext();
	
	/**
	 * Get an armor provided {@link ExecutorService} object to execute current invocation.
	 * 
	 * @param  invocation current invocation.
	 * @return an {@link ExecutorService} object.
	 */
	ExecutorService getExecutorService(ArmorInvocation invocation);
	
	/**
	 * Tell armor whether is on or not.
	 * 
	 * @return true when the armor is turn on.
	 */
	boolean isOn();
	
	/**
	 * Get armor filter chain for specified invocation.
	 * 
	 * @param  invocation current invocation.
	 * @return an armor filter chain.
	 */
	ArmorFilterChain getFilterChain(ArmorInvocation invocation);
	
	/**
	 * Get specified invocation timeout in milliseconds.
	 * 
	 * @param  invocation
	 * @return timeout in milliseconds
	 */
	long getTimeoutInMillis(ArmorInvocation invocation);
	
}
