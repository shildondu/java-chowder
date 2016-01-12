package com.shildon.chowder.ioc;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Annotation解析器。
 * @author shildon<shildondu@gmail.com>
 * @date Jan 2, 2016 4:03:17 PM
 *
 */
public class AnnotationResolver {
	
	private ClassScaner annotationLoader;
	
	public AnnotationResolver() {
		annotationLoader = new ClassScaner();
	}
	
	public Map<String, Class<?>> getAnnotationClazzs(Class<?> annotationClass) {
		List<Class<?>> clazzs = null;
		Map<String, Class<?>> annotationClazzs = new HashMap<String, Class<?>>();
		clazzs = annotationLoader.loadClass();

		for (Class<?> clazz : clazzs) {
			Annotation[] annotations = clazz.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotationClass == annotation.annotationType()) {
					annotationClazzs.put(clazz.getName(), clazz);
					break;
				}
			}
		}
		return annotationClazzs;
	}

}
