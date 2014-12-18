package io.craft.armor;

import java.lang.reflect.Proxy;

import io.craft.armor.spi.ArmorProxyFactory;

import org.springframework.stereotype.Component;

/**
 * @author mindwind
 * @version 1.0, Dec 18, 2014
 */
@Component("craft.armor.proxy.factory")
public class DefaultArmorProxyFactory implements ArmorProxyFactory {

	@Override
	public Object getProxy(Object bean, Class<?>[] interfaces) {
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, new ArmorInvocationHandler(bean));
	}

}
