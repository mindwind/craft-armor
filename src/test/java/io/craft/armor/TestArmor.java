package io.craft.armor;

import io.craft.armor.api.ArmorFactory;
import io.craft.armor.api.ArmorService;
import io.craft.atom.test.CaseCounter;

import java.lang.reflect.Proxy;

import junit.framework.Assert;

import org.junit.Before;
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
	
	
	@Autowired private DemoService  demoService ;
               private ArmorService armorService;
               
    @Before
    public void before() {
    	armorService = ArmorFactory.armorService();
    }
	
	@Test
	public void testArmorStartup() {
		boolean isProxy = Proxy.isProxyClass(demoService.getClass());
		Assert.assertTrue(isProxy);
		boolean isOk = demoService.isOk();
		Assert.assertTrue(isOk);
		System.out.println(String.format("[CRAFT-ATOM-NIO] (^_^)  <%s>  Case -> test armor startup. ", CaseCounter.incr(2)));
	}
	
	@Test
	public void testArmorDegrade() {
		armorService.degrade(DemoServiceImpl.class, "echo", new Class<?>[] { String.class });
		String in = "test";
		try {
			demoService.echo(in);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
		armorService.upgrade(DemoServiceImpl.class, "echo", new Class<?>[] { String.class });
		
		String out = demoService.echo(in);
		Assert.assertEquals(out, in);
		System.out.println(String.format("[CRAFT-ATOM-NIO] (^_^)  <%s>  Case -> test armor degrade. ", CaseCounter.incr(2)));
	}
	
	@Test
	public void testArmorTransfer() {
		
	}
}
