package io.craft.armor;

import java.lang.reflect.Method;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mindwind
 * @version 1.0, Dec 23, 2014
 */
public class DefaultArmorInvocation implements ArmorInvocation {

	
	private static final long serialVersionUID = 6735418454783712344L;
	
	
	@Getter @Setter private Object     delegateObject;
	@Getter @Setter private Method     method        ;
	@Getter @Setter private Class<?>[] parameterTypes;
	@Getter @Setter private Object[]   parameters    ;
	
	
	@Override
	public String getKey() {
		StringBuilder buf = new StringBuilder();
		buf.append(delegateObject.getClass().getName());
		buf.append("#");
		buf.append(method.getName());
		if (parameterTypes != null) {
			buf.append("(");
			for (Class<?> clazz : parameterTypes) {
				buf.append(clazz.getName()).append(",");
			}
			buf.append(")");
		}
		return buf.toString();
	}
	
}
