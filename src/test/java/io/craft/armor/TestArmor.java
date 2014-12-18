package io.craft.armor;

import io.craft.atom.test.CaseCounter;

import java.lang.reflect.Proxy;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author mindwind
 * @version 1.0, Dec 18, 2014
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class TestArmor {
	
	
	@Autowired private DemoService demoService;
	
	
	@Test
	public void testArmorStartup() {
		boolean isProxy = Proxy.isProxyClass(demoService.getClass());
		Assert.assertTrue(isProxy);
		boolean isOk = demoService.isOk();
		Assert.assertTrue(isOk);
		System.out.println(String.format("[CRAFT-ATOM-NIO] (^_^)  <%s>  Case -> test armor startup. ", CaseCounter.incr(2)));
	}
	
}
