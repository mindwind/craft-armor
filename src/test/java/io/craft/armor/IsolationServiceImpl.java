package io.craft.armor;

import io.craft.armor.api.Arm;
import io.craft.armor.api.Armor;

import java.util.Random;

import org.springframework.stereotype.Service;

/**
 * @author mindwind
 * @version 1.0, Apr 27, 2015
 */
@Armor
@Service("isolationService")
public class IsolationServiceImpl implements IsolationService {

	
	@Override
	@Arm(threads = 100)
	public int normal(int i) {
		return i;
	}

	@Override
	@Arm(async = true, threads = 100)
	public void timeout(boolean timeout) {
		if (!timeout) return;
		try {
			Random rand = new Random();
			Thread.sleep((rand.nextInt(10) + 1) * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Arm(async = true, threads = 100)
	public void error(boolean error) {
		if (!error) return;
		throw new UnsupportedOperationException();
	}

}
