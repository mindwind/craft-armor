package io.craft.armor;

import io.craft.armor.api.Armor;

import org.springframework.stereotype.Service;

/**
 * @author mindwind
 * @version 1.0, Jan 5, 2015
 */
@Armor
@Service("demoChildService")
public class DemoChildServiceImpl implements DemoChildService {

	@Override
	public String echo(String in) {
		return in;
	}

}
