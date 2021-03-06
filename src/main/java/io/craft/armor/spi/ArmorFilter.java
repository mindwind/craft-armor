package io.craft.armor.spi;


/**
 * A filter which intercepts armed service invocation.
 * Filters can be used for these purposes:
 * 
 * <ul>
 *   <li>Invocation logging</li>
 *   <li>Invocation transfer</li>
 *   <li>Degrade protection</li>
 *   <li>Overload control</li>
 *   <li>and many more.</li>
 * </ul>
 * 
 * @author mindwind
 * @version 1.0, Dec 22, 2014
 */
public interface ArmorFilter {
	
	/**
	 * Filter the invocation through the filter.
	 * 
	 * @param  invocation current invocation
	 * @throws Throwable exception when any invocation error occurs.
	 */
	void doFilter(ArmorInvocation invocation) throws Throwable;
	
}
