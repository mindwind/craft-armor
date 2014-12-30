package io.craft.armor;

import io.craft.armor.spi.ArmorInvocation;
import io.craft.armor.spi.ArmorListener;

/**
 * @author mindwind
 * @version 1.0, Dec 30, 2014
 */
public class NoopArmorListener implements ArmorListener {

	@Override
	public void beforeInvoke(ArmorInvocation invocation) {}

	@Override
	public void afterInvoke(ArmorInvocation invocation, Object result) {}

	@Override
	public void errorInvoke(ArmorInvocation invocation, Throwable error) {}

}
