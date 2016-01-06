package com.shildon.chowder.ioc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shildon.chowder.ioc.annotation.Bean;

/**
 * Bean工厂，生产bean。
 * @author shildon<shildondu@gmail.com>
 * @date Jan 6, 2016 2:49:00 PM
 *
 */
public class BeanFactory {
	
	private static final Log log = LogFactory.getLog(BeanFactory.class);
	
	public <T> T getBean(Class<T> type) {
		Class<T> clazz = ClassUtil.getClass(type, Bean.class);
		T t = null;
		
		if (null != clazz) {
			try {
				t = (T) clazz.newInstance();
				log.info("instantiate success!");
			} catch (InstantiationException | IllegalAccessException e) {
				log.error("Instantiate " + type.getName() + " fail!", e);
			}
		}
		return t;
	}

}
