package io.craft.armor.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that an annotated class is a service to be armed.
 * The service class should implements a service interface, otherwise it can not be armed.
 * 
 * @author warden
 * @author mindwind
 * @version 1.0, Dec 17, 2014
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Armor {

}
