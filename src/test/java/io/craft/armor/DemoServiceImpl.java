package io.craft.armor;

import io.craft.armor.api.Armor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mindwind
 * @version 1.0, Dec 18, 2014
 */
@Armor
@Service("demoService")
public class DemoServiceImpl implements DemoService {
	
	
	@Autowired protected DemoChildService demoChildService;
	           protected boolean          ok              ;
	
	
	@PostConstruct
	void init() {
		ok = true;
	}

	@Override
	public String echo(String in) {
		return in;
	}

	@Override
	public boolean isOk() {
		return ok;
	}

	@Override
	public String echoCascade(String in) {
		return demoChildService.echo(in);
	}

	@Override
	public void timeout(int timeoutInMillis) {
		try {
			Thread.sleep(timeoutInMillis);
		} catch (InterruptedException e) {
		}
	}

	@Override
	public int echo(int i) {
		return i;
	}

}
