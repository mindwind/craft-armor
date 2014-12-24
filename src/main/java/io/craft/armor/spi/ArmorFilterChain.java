package io.craft.armor.spi;

import io.craft.armor.ArmorContext;
import io.craft.armor.ArmorInvocation;


/**
 * A container of {@link ArmorFilter}.
 * Each {@link ArmorContext} has its own {@link ArmorFilterChain}
 * 
 * @author mindwind
 * @version 1.0, Dec 22, 2014
 */
public interface ArmorFilterChain {

	/**
	 * Filter the invocation through the filter chain.
	 * 
	 * @param  invocation current invocation.
	 * @return filtered invocation result object.
	 * @throws exception when any invocation error occurs.
	 */
	Object doFilter(ArmorInvocation invocation) throws Throwable;
	
}
