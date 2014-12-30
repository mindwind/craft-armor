package io.craft.armor.spi;


import java.util.EventListener;

/**
 * Represents an event occurs to trigger a notification.
 * 
 * @author mindwind
 * @version 1.0, Dec 30, 2014
 */
public interface ArmorListener extends EventListener {

	/**
	 * Callback this method before armor invocation execution.
	 * 
	 * @param invocation
	 */
	void beforeInvoke(ArmorInvocation invocation);
	
	/**
	 * Callback this method after armor invocation execution successfully.
	 * 
	 * @param invocation
	 * @param result return result object.
	 */
	void afterInvoke(ArmorInvocation invocation, Object result);
	
	/**
	 * Callback this method when armor invocation execution error.
	 * 
	 * @param invocation
	 * @param error a exception object.
	 */
	void errorInvoke(ArmorInvocation invocation, Throwable error);
	
}
