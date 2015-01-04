package io.craft.armor;

import io.craft.armor.api.ArmorService;
import io.craft.armor.spi.ArmorFilterChain;
import io.craft.armor.spi.ArmorInvocation;
import io.craft.armor.spi.ArmorListener;
import io.craft.armor.spi.ArmorProxyFactory;
import io.craft.atom.util.Assert;


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
	private static final ArmorListener     listener    ;
	
	
	static {
		filterChain  = new DefaultArmorFilterChain();
		listener     = new NoopArmorListener();
		context      = new DefaultArmorContext(listener, filterChain);
		invoker      = new DefaultArmorInvoker(context);
		proxyFactory = new JdkArmorProxyFactory(invoker);
		filterChain.addLast(new DegradeArmorFilter(context));
		filterChain.addLast(new TransferArmorFilter(context));
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
	
	public static ArmorListener defaultListener() {
		return listener;
	}
	
	public static ArmorService armorService() {
		return context;
	}
	
	static String getKey(ArmorInvocation invocation) {
		Assert.notNull(invocation);
		Class<?> clazz  = invocation.getDelegateObject().getClass();
		String method = invocation.getMethod().getName();
		Class<?>[] parameterTypes = invocation.getParameterTypes();
		return getKey(clazz, method, parameterTypes);
	}
	
	static String getKey(Class<?> clazz, String method, Class<?>[] parameterTypes) {
		Assert.notNull(clazz);
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
	
}
