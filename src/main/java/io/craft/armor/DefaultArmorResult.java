package io.craft.armor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author mindwind
 * @version 1.0, Dec 24, 2014
 */
@ToString
public class DefaultArmorResult implements ArmorResult {
	
	
	@Getter @Setter private Object    value    ;
	@Getter @Setter private Throwable exception;
	
	@Override
	public boolean hasException() {
		return exception != null;
	}


}
