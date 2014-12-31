package io.craft.armor;

import io.craft.armor.spi.ArmorFilter;

/**
 * @author mindwind
 * @version 1.0, Dec 29, 2014
 */
abstract public class AbstractArmorFilter implements ArmorFilter {

	
	protected ArmorContext context = Armors.defaultContext();
	
	
	public AbstractArmorFilter(ArmorContext context) {
		this.context = context;
	}

	
	protected boolean isOff(Class<? extends ArmorFilter> filterType) {
		return !context.isOn(filterType);
	}
	
}
