package io.craft.armor;

import io.craft.armor.api.ArmorFactory;
import io.craft.armor.api.ArmorService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author mindwind
 * @version 1.0, Jan 6, 2015
 */
public class BenchmarkArmor {
	
	
	private static final long NANOS_PER_MS  = 1000 * 1000 * 1L ;
	private static final long WARMUP_NUM    = 1000 * 1000 * 1L ;
	private static final long BENCHMARK_NUM = 1000 * 1000 * 1L;
	
	private static ClassPathXmlApplicationContext context     ;
	private static ArmorService                   armorService;
	private static DemoService                    proxyDs     ;
	private static DemoService                    rawDs       ;
	
	
	
	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext(new String[]{"classpath:spring.xml"});
		context.start();
		proxyDs = (DemoService) context.getBean("demoService");
		
		armorService = ArmorFactory.armorService();
		rawDs = (DemoService) armorService.getDelegateObject("demoService");
		
		doBenchmark();
      
        System.out.println("Test completed.");
	}

	private static void doBenchmark() {
		System.out.println("1st Warming up ...");
		loop(proxyDs, WARMUP_NUM);
		loop(rawDs, WARMUP_NUM);
		System.out.println("1st Warming up done.\n");
		
		System.out.println("2nd Warming up ...");
		loop(proxyDs, WARMUP_NUM);
		loop(rawDs, WARMUP_NUM);
		System.out.println("2nd Warming up done.\n");
		
		armorService.off();
		System.out.println("Starting jdk proxy measurement interval ...");
		loop(proxyDs, BENCHMARK_NUM);
        System.out.println("End jdk proxy measurement interval done.\n");
        
        armorService.on();
        System.out.println("Starting armor proxy measurement interval ...");
		loop(proxyDs, BENCHMARK_NUM);
        System.out.println("End armor proxy measurement interval done.\n");
        
        System.out.println("Starting raw measurement interval ...");
		loop(rawDs, BENCHMARK_NUM);
        System.out.println("End raw measurement interval done.");
	}
	
	private static void loop(DemoService ds, long iterations) {
		long sum = 0;
		long startTime = System.nanoTime();
		for (int i = 0; i < iterations; i++) {
			sum += ds.echo(i);
		}
		long elapsedTime = System.nanoTime() - startTime;
		
		System.out.println("Loop sum ->" + sum);
		System.out.println("Elapsed nanoseconds ->" + elapsedTime);
		float millis = elapsedTime / NANOS_PER_MS;
        float itrsPerMs = 0;
        if (millis != 0) {
            itrsPerMs = iterations / millis;
        }
        System.out.println("Iterations per milliseconds ->" + itrsPerMs);
	}
	
}
