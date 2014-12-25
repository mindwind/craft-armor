package io.craft.armor;

import io.craft.armor.spi.ArmorFilterChain;

import java.util.concurrent.ExecutorService;

/**
 * Represents armor configuration attribute.
 * 
 * @author mindwind
 * @version 1.0, Dec 25, 2014
 */
public interface ArmorAttribute {
	
	
	ExecutorService  getExecutorService();
	ArmorFilterChain getFilterChain();
	void             setFilterChain(ArmorFilterChain filterChain);
	long             getTimeoutInMillis();
	boolean          isOn();
}
