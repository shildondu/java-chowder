package com.shildon.chowder.ioc;

import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Annotation解析器。
 * @author shildon<shildondu@gmail.com>
 * @date Jan 2, 2016 4:03:17 PM
 *
 */
public class AnnotationResolver {
	
	private AnnotationLoader annotationLoader;
	
	private static final Log log = LogFactory.getLog(AnnotationResolver.class);
	
	public AnnotationResolver() {
		annotationLoader = new AnnotationLoader();
	}
	
	public List<Class<?>> getAnnotationClazzs(Class<?> annotationClass) {
		List<Class<?>> clazzs = null;
		List<Class<?>> annotationClazzs = new LinkedList<Class<?>>();
		try {
			clazzs = annotationLoader.loadResource();
		} catch (FileNotFoundException e) {
			log.error("Can not load resource", e);
		}
		for (Class<?> clazz : clazzs) {
			Annotation[] annotations = clazz.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotationClass == annotation.annotationType()) {
					annotationClazzs.add(clazz);
				}
			}
		}
		return annotationClazzs;
	}

}
