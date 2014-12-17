package io.craft.armor;

import io.craft.armor.api.Armor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;

/**
 * A spring factory hook to armed spring-based service.
 * While the service bean is initiating in spring container, this processor is callback to process {@link Armor} annotation
 * and create an armor proxy for the service bean.
 * 
 * @author mindwind
 * @version 1.0, Dec 17, 2014
 */
public class ArmorBeanPostProcessor implements BeanPostProcessor,ApplicationContextAware, Ordered {

	
	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		return null;
	}

}
