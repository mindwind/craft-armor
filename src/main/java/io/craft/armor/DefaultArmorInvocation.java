package io.craft.armor;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mindwind
 * @version 1.0, Dec 23, 2014
 */
public class DefaultArmorInvocation implements ArmorInvocation {

	
	private static final long serialVersionUID = 6735418454783712344L;
	
	
	@Getter @Setter private Object     delegateObject;
	@Getter @Setter private String     methodName    ;
	@Getter @Setter private Class<?>[] parameterTypes;
	@Getter @Setter private Object[]   parameters    ;
	

}
