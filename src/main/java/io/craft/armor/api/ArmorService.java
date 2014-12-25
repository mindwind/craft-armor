package io.craft.armor.api;

import io.craft.armor.spi.ArmorFilter;
import io.craft.armor.spi.ArmorFilterChain;

/**
 * Provide armor internal monitor and management API.
 * 
 * @author mindwind
 * @version 1.0, Dec 18, 2014
 */
public interface ArmorService {
	
	/**
	 * Tell armor whether is on or not.
	 * 
	 * @return true when the armor is turn on.
	 */
	boolean isOn();
	
	/**
	 * Turn on armor master switch, default it is off you should invoke this to turn it on.
	 */
	void on();
	
	/**
	 * Turn off armor master switch.
	 */
	void off();
	
	/**
	 * Tell the specified type filter is on or not.
	 * 
	 * @param filterType The filter class
	 * @return true when this type filter is on.
	 */
	boolean isOn(Class<? extends ArmorFilter> filterType);
	
	/**
	 * Turn on the specified type filter.
	 * 
	 * @param filterType The filter class to turn on.
	 */
	void on(Class<? extends ArmorFilter> filterType);
	
	/**
	 * Turn off the specified type filter.
	 * 
	 * @param filterType The filter class to turn off.
	 */
	void off(Class<? extends ArmorFilter> filterType);
	
	/**
	 * Set armor default filter chain, it is global for all the methods.
	 * 
	 * @param filterChain default global filter chain.
	 */
	void setDefaultFilterChain(ArmorFilterChain filterChain);
	
	/**
	 * Get armor default filter chain.
	 * 
	 * @return default global filter chain.
	 */
	ArmorFilterChain getDefaultFilterChain();
	
	/**
	 * Set armor method filter chain.
	 * 
	 * @param clazz          armed service class object.
	 * @param method         armed service method name.
	 * @param parameterTypes armed service method parameter types.
	 * @param filterChain    filter chain of the armed service method.
	 */
	void setMethodFilterChain(Class<?> clazz, String method, Class<?>[] parameterTypes, ArmorFilterChain filterChain);
	
	/**
	 * Get armor method filter chain.
	 * 
	 * @param clazz          armed service class object.
	 * @param method         armed service method name.
	 * @param parameterTypes armed service method parameter types.
	 * @return filter chain of the armed service method, if method has no specified chain return <tt>null</tt>.
	 */
	ArmorFilterChain getMethodFilterChain(Class<?> clazz, String method, Class<?>[] parameterTypes);
	
	/**
	 * Get armor method timeout in milliseconds.
	 * 
	 * @param clazz          armed service class object.
	 * @param method         armed service method name.
	 * @param parameterTypes armed service method parameter types.
	 * @return timeout in milliseconds
	 */
	long getTimeoutInMillis(Class<?> clazz, String method, Class<?>[] parameterTypes);
	
	/**
	 * Set armor method invocation timeout in milliseconds.
	 * 
	 * @param clazz           armed service class object.
	 * @param method          armed service method name.
	 * @param parameterTypes  armed service method parameter types.
	 * @param timeoutInMillis armed service method timeout in milliseconds.
	 */
	void setTimeoutInMillis(Class<?> clazz, String method, Class<?>[] parameterTypes, long timeoutInMillis);
	
	/**
	 * Get armor method thread size.
	 * 
	 * @param clazz           armed service class object.
	 * @param method          armed service method name.
	 * @param parameterTypes  armed service method parameter types.
	 * @return thread size.
	 */
	int getThreadSize(Class<?> clazz, String method, Class<?>[] parameterTypes);
	
	/**
	 * Set armor method thread size.
	 * 
	 * @param clazz           armed service class object.
	 * @param method          armed service method name.
	 * @param parameterTypes  armed service method parameter types.
	 * @param threads         armed service method thread size.
	 */
	void setThreadSize(Class<?> clazz, String method, Class<?>[] parameterTypes, int threads);
}
