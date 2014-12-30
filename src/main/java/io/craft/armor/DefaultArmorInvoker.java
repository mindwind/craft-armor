package io.craft.armor;

import io.craft.armor.spi.ArmorFilterChain;
import io.craft.armor.spi.ArmorInvocation;
import io.craft.armor.spi.ArmorListener;
import io.craft.atom.util.Assert;

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
	
	
	private ArmorContext  context  = Armors.defaultContext();
	

	@Override
	public Object invoke(ArmorInvocation invocation) throws Throwable {
		ArmorListener listener = context.listener();
		Assert.notNull(listener);
		try {
			// Armor is off
			if (!context.isOn()) { 
				return rawInvoke(invocation);
			}
			
			// Armor context invoke
			listener.beforeInvoke(invocation);
			Object result = armorInvoke(invocation);
			listener.afterInvoke(invocation, result);
			return result;
		} catch (InvocationTargetException e) {
			listener.errorInvoke(invocation, e.getTargetException());
			throw e.getTargetException();
		} catch (ExecutionException e) {
			listener.errorInvoke(invocation, e.getCause());
			throw e.getCause();   
		} catch (Throwable t) {
			listener.errorInvoke(invocation, t);
			throw t;
		}
	}
	
	private Object rawInvoke(ArmorInvocation invocation) throws Throwable {
		Object   delegate = invocation.getDelegateObject();
		Method   method   = invocation.getMethod();
		Object[] args     = invocation.getParameters();
		return method.invoke(delegate, args);
	}
	
	private Object armorInvoke(ArmorInvocation invocation) throws Throwable {
		boolean inContext = context.isInContext();
		ArmorFilterChain filterChain = context.getMethodFilterChain(invocation);
		if (inContext) {
			return armorCurrentContextInvoke(filterChain, invocation);
		} else {			
			return armorNewContextInvoke(filterChain, invocation);
		}
	}
	
	private Object armorCurrentContextInvoke(ArmorFilterChain filterChain, ArmorInvocation invocation) throws Throwable {
		filterChain.doFilter(invocation);
		return rawInvoke(invocation);
	}
	
	private Object armorNewContextInvoke(ArmorFilterChain filterChain, ArmorInvocation invocation) throws Throwable {
		ExecutorService executor = context.getExecutorService(invocation);
		Future<ArmorResult> future = executor.submit(new ArmorCallable(filterChain, invocation));
		long timeout = context.getTimeoutInMillis(invocation);
		ArmorResult result = future.get(timeout, TimeUnit.MILLISECONDS);
		if (result.hasException()) { throw result.getException(); }
		return result.getValue();
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
				filterChain.doFilter(invocation);
				Object value = rawInvoke(invocation);
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
