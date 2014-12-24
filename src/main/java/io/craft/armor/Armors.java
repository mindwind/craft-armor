package io.craft.armor;

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
	
	
	static {
		invoker      = new DefaultArmorInvoker();
		proxyFactory = new JdkArmorProxyFactory(invoker);
	}
	
	
	// ~ -------------------------------------------------------------------------------------------------------------
	
	
	public static ArmorProxyFactory newArmorProxyFactory() {
		return proxyFactory;
	}
	
}
