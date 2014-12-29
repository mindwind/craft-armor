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
	
	
	private volatile boolean                     on          = true                                           ;
	private          Map<String, ArmorAttribute> attributes  = new ConcurrentHashMap<String, ArmorAttribute>();
	private          ArmorFilterChain            filterChain = Armors.defaultFilterChain()                    ;
	
	
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
		ArmorAttribute aa = attributes.get(getKey(invocation));
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
	public ArmorFilterChain getMethodFilterChain(ArmorInvocation invocation) {
		ArmorAttribute aa = attributes.get(getKey(invocation));
		Assert.notNull(aa);
		ArmorFilterChain chain = aa.getFilterChain();
		if (chain == null) {
			chain = filterChain;
		}
		return chain;
	}

	@Override
	public long getTimeoutInMillis(ArmorInvocation invocation) {
		ArmorAttribute aa = attributes.get(getKey(invocation));
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
		String key = getKey(clazz, method, parameterTypes);
		ArmorAttribute aa = attributes.get(key);
		Assert.notNull(aa);
		aa.setFilterChain(filterChain);
	}

	@Override
	public ArmorFilterChain getMethodFilterChain(Class<?> clazz, String method, Class<?>[] parameterTypes) {
		String key = getKey(clazz, method, parameterTypes);
		ArmorAttribute aa = attributes.get(key);
		Assert.notNull(aa);
		return aa.getFilterChain();
	}
	
	@Override
	public long getTimeoutInMillis(Class<?> clazz, String method, Class<?>[] parameterTypes) {
		String key = getKey(clazz, method, parameterTypes);
		ArmorAttribute aa = attributes.get(key);
		Assert.notNull(aa);
		return aa.getTimeoutInMillis();
	}

	@Override
	public void setTimeoutInMillis(Class<?> clazz, String method, Class<?>[] parameterTypes, long timeoutInMillis) {
		String key = getKey(clazz, method, parameterTypes);
		ArmorAttribute aa = attributes.get(key);
		Assert.notNull(aa);
		aa.setTimeoutInMillis(timeoutInMillis);
	}

	@Override
	public int getThreadSize(Class<?> clazz, String method, Class<?>[] parameterTypes) {
		String key = getKey(clazz, method, parameterTypes);
		ArmorAttribute aa = attributes.get(key);
		Assert.notNull(aa);
		return aa.getThreadSize();
	}

	@Override
	public void setThreadSize(Class<?> clazz, String method, Class<?>[] parameterTypes, int threads) {
		String key = getKey(clazz, method, parameterTypes);
		ArmorAttribute aa = attributes.get(key);
		Assert.notNull(aa);
		aa.setThreadSize(threads);
	}
	
	private String getKey(ArmorInvocation invocation) {
		Class<?> clazz  = invocation.getDelegateObject().getClass();
		String method = invocation.getMethod().getName();
		Class<?>[] parameterTypes = invocation.getParameterTypes();
		return getKey(clazz, method, parameterTypes);
	}
	
	private String getKey(Class<?> clazz, String method, Class<?>[] parameterTypes) {
		StringBuilder buf = new StringBuilder();
		buf.append(clazz.getName());
		buf.append("#");
		buf.append(method);
		if (parameterTypes != null) {
			buf.append("( ");
			for (Class<?> ptype : parameterTypes) {
				buf.append(ptype.getName()).append(" ");
			}
			buf.append(")");
		}
		return buf.toString();
	}

	@Override
	public void degrade(Class<?> clazz, String method, Class<?>[] parameterTypes) {
		String key = getKey(clazz, method, parameterTypes);
		ArmorAttribute aa = attributes.get(key);
		Assert.notNull(aa);
		aa.degrade();
	}

	@Override
	public void upgrade(Class<?> clazz, String method, Class<?>[] parameterTypes) {
		String key = getKey(clazz, method, parameterTypes);
		ArmorAttribute aa = attributes.get(key);
		Assert.notNull(aa);
		aa.upgrade();
	}

	@Override
	public boolean isDegraded(Class<?> clazz, String method, Class<?>[] parameterTypes) {
		String key = getKey(clazz, method, parameterTypes);
		ArmorAttribute aa = attributes.get(key);
		Assert.notNull(aa);
		return aa.isDegraded();
	}

}
