package io.craft.armor;

import io.craft.armor.spi.ArmorFilterChain;

import java.util.concurrent.ExecutorService;

/**
 * @author mindwind
 * @version 1.0, Dec 24, 2014
 */
public class DefaultArmorContext implements ArmorContext {
	
	
	private static final ThreadLocal<ArmorContext> CTX = new ThreadLocal<ArmorContext>();
	
	
	// ~ ------------------------------------------------------------------------------------------------------------
	
	
	@Override
	public void set() {
		CTX.set(this);
	}
	
	@Override
	public void remove() {
		CTX.remove();
	}

	@Override
	public boolean isInContext() {
		return CTX.get() != null;
	}

	@Override
	public ExecutorService getExecutorService(ArmorInvocation invocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOn(ArmorInvocation invocation) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArmorFilterChain getFilterChain(ArmorInvocation invocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getTimeoutInMillis(ArmorInvocation invocation) {
		// TODO Auto-generated method stub
		return 0;
	}

}
