package io.craft.armor;

import io.craft.armor.spi.ArmorInvoker;
import io.craft.armor.spi.ArmorProxyFactory;

import java.lang.reflect.Proxy;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mindwind
 * @version 1.0, Dec 18, 2014
 */
public class JdkArmorProxyFactory implements ArmorProxyFactory {
	
	
	@Getter @Setter private ArmorInvoker invoker;
	

	@Override
	public Object getProxy(Object bean, Class<?>[] interfaces) {
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, new JdkArmorInvocationHandler(bean, invoker));
	}


}
