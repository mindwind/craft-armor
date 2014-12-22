package io.craft.armor;

import io.craft.armor.api.ArmorService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author warden
 * @author mindwind
 * @version 1.0, Dec 18, 2014
 */
public class ArmorInvocationHandler implements InvocationHandler {
	
	
	private Object       delegate    ;
	private ArmorService armorService;
	
	
	public ArmorInvocationHandler(Object delegate) {
		 this.delegate = delegate;
	}

	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// Armor master switch is off
		if (!armorService.isOn()) { 
			return rawInvoke(method, args);
		}
		
		// Armor method switch is off
		if (!armorService.isOn(delegate.getClass(), method.getName(), method.getParameterTypes())) {  
			return rawInvoke(method, args);
		}
		
		
		// Armor context invoke
		return armorInvoke(method, args);
	}
	
	private Object rawInvoke(Method method, Object[] args) throws Throwable {
		return method.invoke(delegate, args);
	}
	
	private Object armorInvoke(Method method, Object[] args) throws Throwable {
		return null; // TODO
	}

}
