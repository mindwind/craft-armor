package io.craft.armor;

/**
 * Armor invoker, launch the armor context invocation.
 * 
 * @author mindwind
 * @version 1.0, Dec 23, 2014
 */
public interface ArmorInvoker {
	
	/**
	 * Execute armor invocation.
	 * 
	 * @param  invocation
	 * @return invocation result object.
	 * @throws exception when any invocation error occurs.
	 */
	Object invoke(ArmorInvocation invocation) throws Throwable;
	
}
