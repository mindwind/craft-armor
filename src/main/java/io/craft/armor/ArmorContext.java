package io.craft.armor;

import io.craft.armor.api.ArmorService;
import io.craft.armor.spi.ArmorFilterChain;
import io.craft.armor.spi.ArmorInvocation;
import io.craft.armor.spi.ArmorListener;

import java.util.concurrent.ExecutorService;

/**
 * Central interface to provide configuration for an armed service.
 * 
 * @author mindwind
 * @version 1.0, Dec 24, 2014
 */
public interface ArmorContext extends ArmorService {
	
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
	 * Set armor method attribute.
	 * 
	 * @param clazz
	 * @param method
	 * @param parameterTypes
	 * @param attribute
	 */
	void setAttribute(Class<?> clazz, String method, Class<?>[] parameterTypes, ArmorAttribute attribute);
	
	/**
	 * Get an armor provided {@link ExecutorService} object to execute current invocation.
	 * 
	 * @param  invocation current invocation.
	 * @return an {@link ExecutorService} object.
	 */
	ExecutorService getExecutorService(ArmorInvocation invocation);
	
	/**
	 * Get armor filter chain for specified invocation.
	 * It find the method filter chain at first, if it does not exist return default filter chain.
	 * 
	 * @param  invocation current invocation.
	 * @return an armor filter chain for specified invocation.
	 */
	ArmorFilterChain getMethodFilterChain(ArmorInvocation invocation);
	
	/**
	 * Get specified invocation timeout in milliseconds.
	 * 
	 * @param  invocation
	 * @return timeout in milliseconds
	 */
	long getTimeoutInMillis(ArmorInvocation invocation);
	
	/**
	 * @return registered armor listner.
	 */
	ArmorListener listener();
	
	/**
	 * Get transfer object by original delegate object.
	 * 
	 * @param  delegateObject
	 * @return transfer object.
	 */
	Object getTransferObject(Object delegateObject);
	
}
