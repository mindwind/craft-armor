package io.craft.armor;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import lombok.Getter;
import lombok.Setter;

/**
 * @author warden
 * @author mindwind
 * @version 1.0, Dec 18, 2014
 */
public class JdkArmorInvocationHandler implements InvocationHandler {
	
	
	@Getter @Setter private ArmorInvoker invoker ;
	@Getter @Setter private Object       delegate;
	
	
	public JdkArmorInvocationHandler(Object delegate, ArmorInvoker invoker) {
		 this.delegate = delegate;
		 this.invoker  = invoker;
	}

	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		DefaultArmorInvocation invo = new DefaultArmorInvocation();
		invo.setDelegateObject(delegate);
		invo.setMethodName(method.getName());
		invo.setParameterTypes(method.getParameterTypes());
		invo.setParameters(args);
		return invoker.invoke(invo);
	}
	
	
//	private Object       delegate    ;
//	private ArmorService armorService;
//	
//	
//	public JdkArmorInvocationHandler(Object delegate) {
//		 this.delegate = delegate;
//	}
//
//	
//	@Override
//	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//		// Armor master switch is off
//		if (!armorService.isOn()) { 
//			return rawInvoke(method, args);
//		}
//		
//		// Armor method switch is off
//		if (!armorService.isOn(delegate.getClass(), method.getName(), method.getParameterTypes())) {  
//			return rawInvoke(method, args);
//		}
//		
//		
//		// Armor context invoke
//		return armorInvoke(method, args);
//	}
//	
//	private Object rawInvoke(Method method, Object[] args) throws Throwable {
//		return method.invoke(delegate, args);
//	}
//	
//	private Object armorInvoke(Method method, Object[] args) throws Throwable {
//		return null; // TODO
//	}

}
