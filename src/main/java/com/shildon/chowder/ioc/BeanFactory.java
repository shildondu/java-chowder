package com.shildon.chowder.ioc;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shildon.chowder.ioc.annotation.Bean;

public class BeanFactory {
	
	private AnnotationResolver annotationResolver;
	
	private static final Log log = LogFactory.getLog(BeanFactory.class);
	
	public BeanFactory() {
		annotationResolver = new AnnotationResolver();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> type) {
		List<Class<?>> clazzs = annotationResolver.getAnnotationClazzs(Bean.class);
		T t = null;

		for (Class<?> clazz : clazzs) {
			log.info(clazz.getName());
			if (type == clazz) {
				try {
					t = (T) clazz.newInstance();
					log.info("instantiate success!");
				} catch (InstantiationException | IllegalAccessException e) {
					log.error("Instantiate " + type.getName() + " fail!", e);
				}
			}
		}
		return t;
	}

}
