package io.craft.armor;

import io.craft.armor.spi.ArmorFilter;
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
	 * Get armor filter chain for specified invocation.
	 * It find the method filter chain at first, if it does not exist return default filter chain.
	 * 
	 * @param  invocation current invocation.
	 * @return an armor filter chain for specified invocation.
	 */
	ArmorFilterChain getMethodFilterChain(ArmorInvocation invocation);
	
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
	 * Get specified invocation timeout in milliseconds.
	 * 
	 * @param  invocation
	 * @return timeout in milliseconds
	 */
	long getTimeoutInMillis(ArmorInvocation invocation);
	
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
	
	/**
	 * Degrade armor method.
	 * 
	 * @param clazz           armed service class object.
	 * @param method          armed service method name.
	 * @param parameterTypes  armed service method parameter types.
	 */
	void degrade(Class<?> clazz, String method, Class<?>[] parameterTypes);
	
	/**
	 * Upgrade armor method, it is an inverse operation of {@link #degrade(Class, String, Class[])}.
	 * To a normal method which never be degraded, invoke this has no effect.
	 * 
	 * @param clazz           armed service class object.
	 * @param method          armed service method name.
	 * @param parameterTypes  armed service method parameter types.
	 */
	void upgrade(Class<?> clazz, String method, Class<?>[] parameterTypes);
	
	/**
	 * Tell the method is degraded or not.
	 * 
	 * @param clazz           armed service class object.
	 * @param method          armed service method name.
	 * @param parameterTypes  armed service method parameter types.
	 * @return true when it is degraded.
	 */
	boolean isDegraded(Class<?> clazz, String method, Class<?>[] parameterTypes);
	
}
