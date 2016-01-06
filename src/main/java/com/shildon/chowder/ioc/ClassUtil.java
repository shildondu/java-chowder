package com.shildon.chowder.ioc;

import java.util.List;

public class ClassUtil {
	
	private static AnnotationResolver annotationResolver;
	
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
		return null;
	}
	
}
