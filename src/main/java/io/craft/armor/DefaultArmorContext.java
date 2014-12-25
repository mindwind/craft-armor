package io.craft.armor;

import io.craft.armor.spi.ArmorFilterChain;
import io.craft.atom.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * @author mindwind
 * @version 1.0, Dec 24, 2014
 */
public class DefaultArmorContext implements ArmorContext {
	
	
	private static final ThreadLocal<ArmorContext> CTX = new ThreadLocal<ArmorContext>();
	
	
	private boolean                     on          = true                                           ;
	private Map<String, ArmorAttribute> attributes  = new ConcurrentHashMap<String, ArmorAttribute>();
	private ArmorFilterChain            filterChain = Armors.defaultFilterChain()                    ;
	
	
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
		ArmorAttribute aa = attributes.get(invocation.getKey());
		Assert.notNull(aa);
		ExecutorService es = aa.getExecutorService();
		Assert.notNull(es);
		return es;
	}

	@Override
	public boolean isOn() {
		return on;
	}

	@Override
	public ArmorFilterChain getFilterChain(ArmorInvocation invocation) {
		ArmorAttribute aa = attributes.get(invocation.getKey());
		Assert.notNull(aa);
		ArmorFilterChain chain = aa.getFilterChain();
		if (chain == null) {
			chain = filterChain;
		}
		return chain;
	}

	@Override
	public long getTimeoutInMillis(ArmorInvocation invocation) {
		ArmorAttribute aa = attributes.get(invocation.getKey());
		Assert.notNull(aa);
		return aa.getTimeoutInMillis();
	}

}
