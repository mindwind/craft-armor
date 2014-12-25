package io.craft.armor.spi;

import io.craft.armor.ArmorInvocation;


/**
 * A container of {@link ArmorFilter}.
 * All the armor service method has its own {@link ArmorFilterChain}.
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
	
	/**
	 * Add the specified filter just before the filter type in this chain.
     * 
	 * @param filterType the target filter type
	 * @param filter     the filter to add
	 */
	void addBefore(Class<? extends ArmorFilter> filterType, ArmorFilter filter);
	
	/**
	 * Add the specified filter just after the filter type in this chain.
	 * 
	 * @param filterType the target filter type
	 * @param filter     the filter to add
	 */
	void addAfter(Class<? extends ArmorFilter> filterType, ArmorFilter filter);
	
	/**
     * Add the specified filter at the beginning of this chain.
     * 
     * @param filter the filter to add
     */
    void addFirst(ArmorFilter filter);
    
    /**
     * Add the specified filter at the end of this chain.
     * 
     * @param filter the filter to add
     */
    void addLast(ArmorFilter filter);
    
    /**
     * Remove the filter of the specified type.
     * If there's more than one filter with the specified type, the first match will be removed.
     * 
     * @param filterType the target filter type
     */
    void remove(Class<? extends ArmorFilter> filterType);
    
    /**
     * Remove the specified filter at the end of this chain.
     */
    void removeLast();
    
    /**
     * Remove the specified filter at the beginning of this chain.
     */
    void removeFirst();
	
}
