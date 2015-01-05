package io.craft.armor;


/**
 * @author mindwind
 * @version 1.0, Dec 18, 2014
 */
public interface DemoService {
	
	String echo(String in);
	String echoCascade(String in);
	boolean isOk();
	void timeout(int timeoutInMillis);
	
}
