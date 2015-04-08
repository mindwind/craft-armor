package io.craft.armor;

import io.craft.armor.spi.ArmorInvocation;
import io.craft.armor.spi.ArmorListener;

/**
 * @author mindwind
 * @version 1.0, Apr 8, 2015
 */
public class TimeRecordArmorListener implements ArmorListener {

	
	private volatile long start;
	private volatile long end;
	
	@Override
	public void beforeInvoke(ArmorInvocation invocation) {
		start = System.currentTimeMillis();
	}

	@Override
	public void afterInvoke(ArmorInvocation invocation, Object result) {
		end = System.currentTimeMillis();

	}

	@Override
	public void errorInvoke(ArmorInvocation invocation, Throwable error) {
		end = System.currentTimeMillis();
	}
	
	public long elapse() {
		return end - start;
	}

}
