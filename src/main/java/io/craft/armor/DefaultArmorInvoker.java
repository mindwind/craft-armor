package io.craft.armor;

import io.craft.armor.spi.ArmorFilterChain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


/**
 * @author mindwind
 * @version 1.0, Dec 23, 2014
 */
public class DefaultArmorInvoker implements ArmorInvoker {
	
	
	private ArmorContext context = Armors.newArmorContext();
	

	@Override
	public Object invoke(ArmorInvocation invocation) throws Throwable {
		// Armor master switch is off
		if (!context.isOn()) { 
			return rawInvoke(invocation);
		}
		
		// Armor method switch is off
		if (!context.isOn(invocation)) {  
			return rawInvoke(invocation);
		}
		
		// Armor context invoke
		return armorInvoke(invocation);
	}
	
	private Object rawInvoke(ArmorInvocation invocation) throws Throwable {
		try {
			Object   delegate = invocation.getDelegateObject();
			Method   method   = invocation.getMethod();
			Object[] args     = invocation.getParameters();
			return method.invoke(delegate, args);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}
	
	
	private Object armorInvoke(final ArmorInvocation invocation) throws Throwable {
		boolean inContext = context.isInContext();
		final ArmorFilterChain filterChain = context.getFilterChain(invocation);
		if (inContext) {
			return filterChain.doFilter(invocation);
		} else {			
			try {
				ExecutorService executor = context.getExecutorService(invocation);
				Future<ArmorResult> future = executor.submit(new ArmorCallable(filterChain, invocation));
				long timeout = context.getTimeoutInMillis(invocation);
				ArmorResult result = future.get(timeout, TimeUnit.MILLISECONDS);
				Throwable t = result.getException();
				if (t != null) { throw t; }
				return result.getValue();
			} catch (ExecutionException e) {
				throw e.getCause();   
			}
		}
	}
	
	private class ArmorCallable implements Callable<ArmorResult> {
		
		private ArmorFilterChain filterChain;
		private ArmorInvocation  invocation ;
		
		
		public ArmorCallable(ArmorFilterChain filterChain, ArmorInvocation invocation) {
			this.filterChain = filterChain;
			this.invocation  = invocation;
		}
		
		
		@Override
		public ArmorResult call() throws Exception {
			DefaultArmorResult result = new DefaultArmorResult();
			try {
				context.set();
				Object value = filterChain.doFilter(invocation);
				result.setValue(value);
			} catch (Throwable t) {
				result.setException(t);
			} finally {
				context.remove();
			}
			return result;
		}
	}

}
