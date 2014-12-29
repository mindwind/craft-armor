package io.craft.armor;

import java.util.concurrent.RejectedExecutionException;

/**
 * Degrade armor filter.
 * When an armor service method be degraded, the invocation is fail fast.
 * 
 * @author mindwind
 * @version 1.0, Dec 29, 2014
 */
public class DegradeArmorFilter extends AbstractArmorFilter {
	

	@Override
	public void doFilter(ArmorInvocation invocation) throws Throwable {
		if (isOff(this.getClass())) { return; }
		
		Class<?>   clazz          = invocation.getDelegateObject().getClass();
		String     method         = invocation.getMethod().getName();
		Class<?>[] parameterTypes = invocation.getParameterTypes();
		boolean degrade = context.isDegraded(clazz, method, parameterTypes);
		if (degrade) {
			throw new RejectedExecutionException(String.format("Method |%s| is degraded.", Armors.getKey(invocation)));
		}
	}

}
