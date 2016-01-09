package com.shildon.chowder.ioc;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ClassUtil {
	
	private static AnnotationResolver annotationResolver;
	
	private static final Log log = LogFactory.getLog(ClassUtil.class);
	
	static {
		annotationResolver = new AnnotationResolver();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClass(Class<T> clazz, Class<?> annotation) {
		List<Class<?>> clazzs = annotationResolver.getAnnotationClazzs(annotation);
		
		for (Class<?> c : clazzs) {
			if (clazz == c) {
				return (Class<T>) c;
			}
		}
		log.info("No class found!");
		return null;
	}
	
}
