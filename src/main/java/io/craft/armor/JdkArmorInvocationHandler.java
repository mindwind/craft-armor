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
		invo.setMethod(method);
		invo.setParameterTypes(method.getParameterTypes());
		invo.setParameters(args);
		return invoker.invoke(invo);
	}

}
