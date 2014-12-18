package io.craft.armor.api;

/**
 * Provide armor internal monitor and management API.
 * 
 * @author mindwind
 * @version 1.0, Dec 18, 2014
 */
public interface ArmorService {
	
	/**
	 * Tell armor whether is on or not.
	 * 
	 * @return true when the armor is turn on.
	 */
	boolean isOn();
	
	/**
	 * Turn on armor master switch, default it is off you should invoke this to turn it on.
	 */
	void on();
	
	/**
	 * Turn off armor master switch.
	 */
	void off();
	
	/**
	 * Tell armor whether is on or not for specified armed service method.
	 * 
	 * @param clazz          armed service class object.
	 * @param method         armed service method name.
	 * @param parameterTypes armed service method parameter types.
	 * @return true when the armor is turn on.
	 */
	boolean isOn(Class<?> clazz, String method, Class<?>[] parameterTypes);
	
	/**
	 * Turn on armor method switch, default it is off you should invoke this to turn it on.
	 * 
	 * @param clazz          armed service class object.
	 * @param method         armed service method name.
	 * @param parameterTypes armed service method parameter types.
	 * @return true when the armor is turn on.
	 */
	void on(Class<?> clazz, String method, Class<?>[] parameterTypes);
	
	/**
	 * Turn off armor method switch.
	 * 
	 * @param clazz          armed service class object.
	 * @param method         armed service method name.
	 * @param parameterTypes armed service method parameter types.
	 * @return true when the armor is turn on.
	 */
	void off(Class<?> clazz, String method, Class<?>[] parameterTypes);
	
	
}
