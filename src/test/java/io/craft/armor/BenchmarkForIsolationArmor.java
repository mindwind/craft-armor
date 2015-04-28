package io.craft.armor;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author mindwind
 * @version 1.0, Jan 6, 2015
 */
public class BenchmarkForIsolationArmor {
	
	
	private static final long NANOS_PER_MS  = 1000 * 1000 * 1L ;
	private static final long WARMUP_NUM    = 1000 * 1000 * 1L ;
	private static final long BENCHMARK_NUM = 1000 * 1000 * 1L ;
	
	private static ClassPathXmlApplicationContext context     ;
	private static IsolationService               is          ;
	
	
	
	public static void main(String[] args) throws InterruptedException {
		context = new ClassPathXmlApplicationContext(new String[]{"classpath:spring.xml"});
		context.start();
		is = (IsolationService) context.getBean("isolationService");
		
		doBenchmark();
        System.out.println("Test completed.");
	}

	private static void doBenchmark() throws InterruptedException {
		System.out.println("1st Warming up ...");
		loopNormalAndTimeout(is, WARMUP_NUM, false);
		loopNormalAndError(is, WARMUP_NUM, false);
		System.out.println("1st Warming up done.\n");
		
		System.out.println("2nd Warming up ...");
		loopNormalAndTimeout(is, WARMUP_NUM, false);
		loopNormalAndError(is, WARMUP_NUM, false);
		System.out.println("2nd Warming up done.\n");
        
        System.out.println("Starting armor normal without timeout interval ...");
		loopNormalAndTimeout(is, BENCHMARK_NUM, false);
        System.out.println("End armor normal without timeout interval done.\n");
        
        System.out.println("Starting armor normal with timeout interval ...");
		loopNormalAndTimeout(is, BENCHMARK_NUM, true);
        System.out.println("End armor normal with timeout interval done.");
        
        Thread.sleep(10000);
        
        System.out.println("Starting armor normal without error interval ...");
        loopNormalAndError(is, WARMUP_NUM, false);
        System.out.println("End armor normal without error interval done.");
        
        System.out.println("Starting armor normal with error interval ...");
        loopNormalAndError(is, WARMUP_NUM, true);
        System.out.println("End armor normal with error interval done.");
	}
	
	private static void loopNormalAndTimeout(IsolationService is, long iterations, boolean timeout) {
		long sum = 0;
		long startTime = System.nanoTime();
		for (int i = 0; i < iterations; i++) {
			try {
				sum += is.normal(i);
				is.timeout(timeout); 
			} catch (Exception e) {}
		}
		long elapsedTime = System.nanoTime() - startTime;
		
		System.out.println("Loop sum ->" + sum);
		System.out.println("Elapsed miliseconds ->" + elapsedTime / NANOS_PER_MS);
		float millis = elapsedTime / NANOS_PER_MS;
        float itrsPerMs = 0;
        if (millis != 0) {
            itrsPerMs = iterations / millis;
        }
        System.out.println("Iterations per milliseconds ->" + itrsPerMs);
	}
	
	private static void loopNormalAndError(IsolationService is, long iterations, boolean error) {
		long sum = 0;
		long startTime = System.nanoTime();
		for (int i = 0; i < iterations; i++) {
			try {
				sum += is.normal(i);
				is.error(error);
			} catch (Exception e) {}
		}
		long elapsedTime = System.nanoTime() - startTime;
		
		System.out.println("Loop sum ->" + sum);
		System.out.println("Elapsed miliseconds ->" + elapsedTime / NANOS_PER_MS);
		float millis = elapsedTime / NANOS_PER_MS;
        float itrsPerMs = 0;
        if (millis != 0) {
            itrsPerMs = iterations / millis;
        }
        System.out.println("Iterations per milliseconds ->" + itrsPerMs);
	}
	
}
