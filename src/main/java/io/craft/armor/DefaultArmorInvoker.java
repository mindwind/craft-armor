package io.craft.armor;

import io.craft.armor.api.ArmorService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * @author mindwind
 * @version 1.0, Dec 23, 2014
 */
public class DefaultArmorInvoker implements ArmorInvoker {
	
	
	private ArmorService armorService;

	@Override
	public Object invoke(ArmorInvocation invocation) throws Throwable {
		Object   delegate = invocation.getDelegateObject();
		Method   method   = invocation.getMethod();
		Object[] args     = invocation.getParameters();
		
		// Armor master switch is off
		if (!armorService.isOn()) { 
			return rawInvoke(delegate, method, args);
		}
		
		// Armor method switch is off
		if (!armorService.isOn(delegate.getClass(), method.getName(), method.getParameterTypes())) {  
			return rawInvoke(delegate, method, args);
		}
		
		// Armor context invoke
		return armorInvoke(invocation);
	}
	
	private Object rawInvoke(Object delegate, Method method, Object[] args) throws Throwable {
		try {
			return method.invoke(delegate, args);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}
	
	
	private Object armorInvoke(ArmorInvocation invocation) throws Throwable {
		return null; // TODO
	}

}
