package io.craft.armor;

import io.craft.armor.spi.ArmorFilter;
import io.craft.armor.spi.ArmorFilterChain;
import io.craft.armor.spi.ArmorInvocation;
import io.craft.armor.spi.ArmorListener;
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
	
	
	private volatile boolean                     on          = true                                           ;
	private volatile ArmorListener               listener    = Armors.defaultListener()                       ;
	private          Map<String, Object>         objects     = new ConcurrentHashMap<String, Object>()        ;
	private          Map<String, ArmorAttribute> attributes  = new ConcurrentHashMap<String, ArmorAttribute>();
	private          Map<String, Boolean>        filterTypes = new ConcurrentHashMap<String, Boolean>()       ;
	private          Map<Object, Object>         transfers   = new ConcurrentHashMap<Object, Object>()        ;
	private          ArmorFilterChain            filterChain = Armors.defaultFilterChain()                    ;
	
	
	// ~ ------------------------------------------------------------------------------------------------------------
	
	
	public DefaultArmorContext(ArmorListener listener, ArmorFilterChain filterChain) {
		this.listener    = listener;
		this.filterChain = filterChain;
	}
	
	
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
	public void setAttribute(Class<?> clazz, String method, Class<?>[] parameterTypes, ArmorAttribute attribute) {
		Assert.notNull(attribute);
		String key = Armors.getKey(clazz, method, parameterTypes);
		attributes.put(key, attribute);
	}
	
	@Override
	public ArmorAttribute getAttribute(ArmorInvocation invocation) {
		return attributes.get(Armors.getKey(invocation));
	}

	@Override
	public ExecutorService getExecutorService(ArmorInvocation invocation) {
		ArmorAttribute aa = attributes.get(Armors.getKey(invocation));
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
	public void on() {
		this.on = true;
	}

	@Override
	public void off() {
		this.on = false;
	}

	@Override
	public ArmorFilterChain getMethodFilterChain(ArmorInvocation invocation) {
		ArmorAttribute aa = attributes.get(Armors.getKey(invocation));
		Assert.notNull(aa);
		ArmorFilterChain chain = aa.getFilterChain();
		if (chain == null) {
			chain = filterChain;
		}
		return chain;
	}

	@Override
	public long getTimeoutInMillis(ArmorInvocation invocation) {
		ArmorAttribute aa = attributes.get(Armors.getKey(invocation));
		Assert.notNull(aa);
		return aa.getTimeoutInMillis();
	}

	@Override
	public void setDefaultFilterChain(ArmorFilterChain filterChain) {
		this.filterChain = filterChain;
	}

	@Override
	public ArmorFilterChain getDefaultFilterChain() {
		return filterChain;
	}

	@Override
	public void setMethodFilterChain(Class<?> clazz, String method, Class<?>[] parameterTypes, ArmorFilterChain filterChain) {
		String key = Armors.getKey(clazz, method, parameterTypes);
		ArmorAttribute aa = attributes.get(key);
		Assert.notNull(aa);
		aa.setFilterChain(filterChain);
	}

	@Override
	public ArmorFilterChain getMethodFilterChain(Class<?> clazz, String method, Class<?>[] parameterTypes) {
		String key = Armors.getKey(clazz, method, parameterTypes);
		ArmorAttribute aa = attributes.get(key);
		Assert.notNull(aa);
		return aa.getFilterChain();
	}
	
	@Override
	public long getTimeoutInMillis(Class<?> clazz, String method, Class<?>[] parameterTypes) {
		String key = Armors.getKey(clazz, method, parameterTypes);
		ArmorAttribute aa = attributes.get(key);
		Assert.notNull(aa);
		return aa.getTimeoutInMillis();
	}

	@Override
	public void setTimeoutInMillis(Class<?> clazz, String method, Class<?>[] parameterTypes, long timeoutInMillis) {
		String key = Armors.getKey(clazz, method, parameterTypes);
		ArmorAttribute aa = attributes.get(key);
		Assert.notNull(aa);
		aa.setTimeoutInMillis(timeoutInMillis);
	}

	@Override
	public int getThreadSize(Class<?> clazz, String method, Class<?>[] parameterTypes) {
		String key = Armors.getKey(clazz, method, parameterTypes);
		ArmorAttribute aa = attributes.get(key);
		Assert.notNull(aa);
		return aa.getThreadSize();
	}

	@Override
	public void setThreadSize(Class<?> clazz, String method, Class<?>[] parameterTypes, int threads) {
		String key = Armors.getKey(clazz, method, parameterTypes);
		ArmorAttribute aa = attributes.get(key);
		Assert.notNull(aa);
		aa.setThreadSize(threads);
	}

	@Override
	public void degrade(Class<?> clazz, String method, Class<?>[] parameterTypes) {
		String key = Armors.getKey(clazz, method, parameterTypes);
		ArmorAttribute aa = attributes.get(key);
		Assert.notNull(aa);
		aa.degrade();
	}

	@Override
	public void upgrade(Class<?> clazz, String method, Class<?>[] parameterTypes) {
		String key = Armors.getKey(clazz, method, parameterTypes);
		ArmorAttribute aa = attributes.get(key);
		Assert.notNull(aa);
		aa.upgrade();
	}

	@Override
	public boolean isDegraded(Class<?> clazz, String method, Class<?>[] parameterTypes) {
		String key = Armors.getKey(clazz, method, parameterTypes);
		ArmorAttribute aa = attributes.get(key);
		Assert.notNull(aa);
		return aa.isDegraded();
	}

	@Override
	public boolean isOn(Class<? extends ArmorFilter> filterType) {
		Assert.notNull(filterType);
		boolean on = true;
		Boolean b = filterTypes.get(filterType.getName());
		if (b == null) {
			on = true;
		} else {
			on = b;
		}
		return on;
	}

	@Override
	public void on(Class<? extends ArmorFilter> filterType) {
		Assert.notNull(filterType);
		filterTypes.put(filterType.getName(), true);
	}

	@Override
	public void off(Class<? extends ArmorFilter> filterType) {
		Assert.notNull(filterType);
		filterTypes.put(filterType.getName(), false);
	}

	@Override
	public void register(ArmorListener listener) {
		Assert.notNull(listener);
		this.listener = listener;
	}

	@Override
	public void unregister(ArmorListener listener) {
		Assert.notNull(listener);
		this.listener = Armors.defaultListener();
	}

	@Override
	public ArmorListener listener() {
		return listener;
	}

	@Override
	public Object getTransferObject(Object delegateObject) {
		Assert.notNull(delegateObject);
		return transfers.get(delegateObject);
	}

	@Override
	public void setTransferObject(Object delegateObject, Object transferObject) {
		Assert.notNull(delegateObject);
		Assert.notNull(transferObject);
		transfers.put(delegateObject, transferObject);
	}

	@Override
	public void removeTransferObject(Object delegateObject) {
		Assert.notNull(delegateObject);
		transfers.remove(delegateObject);
	}


	@Override
	public Object getDelegateObject(String name) {
		Assert.notNull(name);
		return objects.get(name);
	}


	@Override
	public void registerDelegateObject(String name, Object delegateObject) {
		Assert.notNull(name);
		Assert.notNull(delegateObject);
		objects.put(name, delegateObject);
	}


	@Override
	public void setAsync(Class<?> clazz, String method, Class<?>[] parameterTypes) {
		String key = Armors.getKey(clazz, method, parameterTypes);
		ArmorAttribute aa = attributes.get(key);
		Assert.notNull(aa);
		aa.setAsync();
	}

	@Override
	public boolean isAsync(Class<?> clazz, String method, Class<?>[] parameterTypes) {
		String key = Armors.getKey(clazz, method, parameterTypes);
		ArmorAttribute aa = attributes.get(key);
		Assert.notNull(aa);
		return aa.isAsync();
	}

}
