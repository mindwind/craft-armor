package io.craft.armor;

import io.craft.armor.spi.ArmorFilter;
import io.craft.armor.spi.ArmorFilterChain;
import io.craft.armor.spi.ArmorInvocation;
import io.craft.atom.util.Assert;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author mindwind
 * @version 1.0, Dec 26, 2014
 */
public class DefaultArmorFilterChain implements ArmorFilterChain {
	
	
	private AtomicReference<LinkedList<ArmorFilter>> chainRef = new AtomicReference<LinkedList<ArmorFilter>>(new LinkedList<ArmorFilter>());
	
	
	// ~ -------------------------------------------------------------------------------------------------------------

	
	@Override
	public void doFilter(ArmorInvocation invocation) throws Throwable {
		LinkedList<ArmorFilter> chain = chainRef.get();
		for (ArmorFilter filter : chain) {
			filter.doFilter(invocation);
		}
	}

	@Override
	public void addBefore(Class<? extends ArmorFilter> filterType, ArmorFilter filter) {
		Assert.notNull(filter);
		LinkedList<ArmorFilter> chain = copy();
		ArmorFilter af = get(chain, filterType);
		if (af == null) {
			chain.addFirst(filter);
		} else {
			int index = chain.indexOf(af);
			chain.add(index, filter);
		}
		chainRef.set(chain);
	}

	@Override
	public void addAfter(Class<? extends ArmorFilter> filterType, ArmorFilter filter) {
		Assert.notNull(filter);
		LinkedList<ArmorFilter> chain = copy();
		ArmorFilter af = get(chain, filterType);
		if (af == null) {
			chain.addLast(filter);
		} else {
			int index = chain.indexOf(af);
			chain.add(index + 1, filter);
			
		}
		chainRef.set(chain);
	}

	@Override
	public void addFirst(ArmorFilter filter) {
		Assert.notNull(filter);
		LinkedList<ArmorFilter> chain = copy();
		chain.addFirst(filter);
		chainRef.set(chain);
	}

	@Override
	public void addLast(ArmorFilter filter) {
		LinkedList<ArmorFilter> chain = copy();
		chain.addLast(filter);
		chainRef.set(chain);
	}

	@Override
	public ArmorFilter remove(Class<? extends ArmorFilter> filterType) {
		LinkedList<ArmorFilter> chain = copy();
		ArmorFilter filter = get(chain, filterType);
		if (filter == null) { throw new IllegalArgumentException("Filter not found: " + filterType.getName()); }
		
		chain.remove(filter);
		chainRef.set(chain);
		return filter;
	}

	@Override
	public ArmorFilter removeLast() {
		LinkedList<ArmorFilter> chain = copy();
		ArmorFilter filter = chain.removeLast();
		chainRef.set(chain);
		return filter;
	}

	@Override
	public ArmorFilter removeFirst() {
		LinkedList<ArmorFilter> chain = copy();
		ArmorFilter filter = chain.removeFirst();
		chainRef.set(chain);
		return filter;
	}
	
	private ArmorFilter get(LinkedList<ArmorFilter> chain, Class<? extends ArmorFilter> filterType) {
		ArmorFilter filter = null;
		for (ArmorFilter af : chain) {
			if (filterType.isAssignableFrom(af.getClass())) {
				filter = af;
				break;
			}
		}
		return filter;
	}
	
	private LinkedList<ArmorFilter> copy() {
		LinkedList<ArmorFilter> chain = new LinkedList<ArmorFilter>();
		chain.addAll(chainRef.get());
		return chain;
	}

}
