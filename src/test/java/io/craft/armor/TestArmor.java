package io.craft.armor;

import io.craft.armor.api.ArmorDegradeException;
import io.craft.armor.api.ArmorFactory;
import io.craft.armor.api.ArmorService;
import io.craft.armor.api.ArmorTimeoutException;
import io.craft.armor.spi.ArmorInvocation;
import io.craft.armor.spi.ArmorListener;
import io.craft.atom.test.CaseCounter;

import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author mindwind
 * @version 1.0, Dec 18, 2014
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class TestArmor extends AbstractJUnit4SpringContextTests {
	
	           
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
		System.out.println(String.format("[CRAFT-ARMOR] (^_^)  <%s>  Case -> test armor startup. ", CaseCounter.incr(2)));
	}
	
	@Test
	public void testArmorDegrade() {
		armorService.degrade(DemoServiceImpl.class, "echo", new Class<?>[] { String.class });
		String in = "test";
		try {
			demoService.echo(in);
			Assert.fail();
		} catch (ArmorDegradeException e) {
			Assert.assertTrue(true);
		}
		armorService.upgrade(DemoServiceImpl.class, "echo", new Class<?>[] { String.class });
		
		String out = demoService.echo(in);
		Assert.assertEquals(out, in);
		System.out.println(String.format("[CRAFT-ARMOR] (^_^)  <%s>  Case -> test armor degrade. ", CaseCounter.incr(2)));
	}
	
	@Test
	public void testArmorTransfer() {
		DemoService ds1 = (DemoService) armorService.getDelegateObject("demoService");
		DemoService ds2 = (DemoService) armorService.getDelegateObject("demoService2");
		boolean isProxy = Proxy.isProxyClass(ds1.getClass()) || Proxy.isProxyClass(ds2.getClass());
		Assert.assertFalse(isProxy);
		String in = "test";
		String out = demoService.echo(in);
		Assert.assertEquals(out, in);
		armorService.setTransferObject(ds1, ds2);
		out = demoService.echo(in);
		Assert.assertEquals(out, in + in);
		armorService.removeTransferObject(ds1);
		System.out.println(String.format("[CRAFT-ARMOR] (^_^)  <%s>  Case -> test armor transfer. ", CaseCounter.incr(3)));
	}
	
	@Test
	public void testArmorCascade() {
		String in = "test";
		String out = demoService.echoCascade(in);
		Assert.assertEquals(out, in);
		System.out.println(String.format("[CRAFT-ARMOR] (^_^)  <%s>  Case -> test armor cascade. ", CaseCounter.incr(1)));
	}
	
	@Test
	public void testArmorTimeout() {
		armorService.setTimeoutInMillis(DemoServiceImpl.class, "timeout", new Class<?>[] { int.class }, 25);
		try {
			demoService.timeout(50);
		} catch (ArmorTimeoutException e) {
			Assert.assertTrue(true);
		}
		armorService.setTimeoutInMillis(DemoServiceImpl.class, "timeout", new Class<?>[] { int.class }, 3000);
		System.out.println(String.format("[CRAFT-ARMOR] (^_^)  <%s>  Case -> test armor timeout. ", CaseCounter.incr(1)));
	}
	
	@Test
	public void testArmorThrowException() {
		try {
			demoService.throwException();
			Assert.fail();
		} catch (IllegalAccessException e) {
			Assert.assertTrue(true);
		}
		System.out.println(String.format("[CRAFT-ARMOR] (^_^)  <%s>  Case -> test armor throw exception. ", CaseCounter.incr(1)));
	}
	
	@Test
	public void testArmorAsync() {
		armorService.setAsync(DemoServiceImpl.class, "timeout", new Class<?>[] { int.class }, true);
		try {
			demoService.timeout(10 * 1000);
		} catch (ArmorTimeoutException e) {
			Assert.fail();
		}
		armorService.setAsync(DemoServiceImpl.class, "timeout", new Class<?>[] { int.class }, false);
		System.out.println(String.format("[CRAFT-ARMOR] (^_^)  <%s>  Case -> test armor async. ", CaseCounter.incr(1)));
	}
	
	@Test
	public void testArmAnnotation() {
		boolean async = armorService.isAsync(DemoServiceImpl.class, "arm", new Class<?>[] {});
		int threads = armorService.getThreadSize(DemoServiceImpl.class, "arm", new Class<?>[] {});
		long timeoutInMillis = armorService.getTimeoutInMillis(DemoServiceImpl.class, "arm", new Class<?>[] {});
		Assert.assertEquals(true, async);
		Assert.assertEquals(10, threads);
		Assert.assertEquals(5 * 1000, timeoutInMillis);
		System.out.println(String.format("[CRAFT-ARMOR] (^_^)  <%s>  Case -> test arm annotation. ", CaseCounter.incr(3)));
	}
	
	@Test
	public void testArmorListener() throws InterruptedException {
		TimeRecordArmorListener listener = new TimeRecordArmorListener();
		armorService.register(listener);
		demoService.timeout(100);
		Assert.assertTrue(listener.elapse() >= 100);
		armorService.setAsync(DemoServiceImpl.class, "timeout", new Class<?>[] { int.class }, true);
		demoService.timeout(100);
		Thread.sleep(110);
		Assert.assertTrue(listener.elapse() >= 100);
		armorService.setAsync(DemoServiceImpl.class, "timeout", new Class<?>[] { int.class }, false);
		System.out.println(String.format("[CRAFT-ARMOR] (^_^)  <%s>  Case -> test armor listener. ", CaseCounter.incr(2)));
	}
	
	@Test
	public void testArmorListenerInAsyncMode() {
		final AtomicInteger err = new AtomicInteger();
		armorService.register(new ArmorListener() {
			
			@Override
			public void errorInvoke(ArmorInvocation invocation, Throwable error) {
				err.incrementAndGet();
			}
			
			@Override
			public void beforeInvoke(ArmorInvocation invocation) {}
			
			@Override
			public void afterInvoke(ArmorInvocation invocation, Object result) {}
		});
		
		armorService.setAsync(DemoServiceImpl.class, "throwException", new Class<?>[] {}, true);
		try {
			demoService.throwException();
			Thread.sleep(100);
		} catch (Exception e) {
			Assert.fail();
		}
		Assert.assertTrue(err.get() > 0);
		System.out.println(String.format("[CRAFT-ARMOR] (^_^)  <%s>  Case -> test armor listener in async mode. ", CaseCounter.incr(2)));
	}

}
