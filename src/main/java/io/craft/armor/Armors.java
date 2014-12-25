package io.craft.armor;

import io.craft.armor.spi.ArmorFilterChain;
import io.craft.armor.spi.ArmorProxyFactory;


/**
 * Factory and utility methods for all internal armor object.
 * 
 * @author mindwind
 * @version 1.0, Dec 24, 2014
 */
public class Armors {

	
	private static final ArmorInvoker      invoker     ;
	private static final ArmorProxyFactory proxyFactory;
	private static final ArmorContext      context     ;
	private static final ArmorFilterChain  filterChain ;
	
	
	static {
		invoker      = new DefaultArmorInvoker();
		proxyFactory = new JdkArmorProxyFactory(invoker);
		context      = new DefaultArmorContext();
		filterChain  = null; // TODO
	}
	
	
	// ~ -------------------------------------------------------------------------------------------------------------
	
	
	public static ArmorProxyFactory defaultProxyFactory() {
		return proxyFactory;
	}
	
	public static ArmorContext defaultContext() {
		return context;
	}
	
	public static ArmorFilterChain defaultFilterChain() {
		return filterChain;
	}
	
}
