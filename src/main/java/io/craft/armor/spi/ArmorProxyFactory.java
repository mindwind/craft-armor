package io.craft.armor.spi;



/**
 * Armor proxy factory provides method for getting or creating dynamic proxy instance.
 * 
 * @see RpcInvoker
 * @author mindwind
 * @version 1.0, Aug 20, 2014
 */
public interface ArmorProxyFactory {
	
	/**
	 * Get an instance of proxy class for the specified interfaces.
	 * 
	 * @param  bean       raw bean be proxied.
	 * @param  interfaces implemented by the bean.
	 * @return a proxy instance that implements the specified interfaces.
	 */
	Object getProxy(Object bean, Class<?>[] interfaces);
	
	/**
	 * Set armor invoker. Proxy instance created by factory would use {@link ArmorInvoker} to launch a armor proxy invocation.
	 * 
	 * @param invoker
	 */
	void setInvoker(ArmorInvoker invoker);

}
