package io.craft.armor;

import io.craft.armor.api.Armor;

import org.springframework.stereotype.Service;

/**
 * @author mindwind
 * @version 1.0, Dec 18, 2014
 */
@Armor
@Service("demoService2")
public class DemoServiceImpl2 implements DemoService {
	
	@Override
	public String echo(String in) {
		return in + in;
	}

	@Override
	public String echoCascade(String in) {
		return null;
	}

	@Override
	public int echo(int i) {
		return 0;
	}

	@Override
	public boolean isOk() {
		return false;
	}

	@Override
	public void timeout(int timeoutInMillis) {}

	@Override
	public void throwException() throws IllegalAccessException {}

	@Override
	public void arm() {}

}
