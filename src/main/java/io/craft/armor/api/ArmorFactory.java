package io.craft.armor.api;

import io.craft.armor.Armors;

/**
 * Armor factory, which provides static factory method and builder to create {@link ArmorService} instance.
 * 
 * @author mindwind
 * @version 1.0, Jan 4, 2015
 */
public class ArmorFactory {
	
	/**
	 * @return {@link ArmorService} instance.
	 */
	public static ArmorService newArmorService() {
		return Armors.armorService();
	}
	
}
