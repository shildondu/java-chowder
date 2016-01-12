package com.shildon.chowder.ioc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class ReflectUtil {
	
	public static Field[] getAnnotationFields(Class<?> clazz, Class<?> annotationClass) {
		Field[] fields = clazz.getDeclaredFields();
		List<Field> annotationFields = new LinkedList<Field>();
		int i = 0;
		
		for (Field field : fields) {
			Annotation[] annotations = field.getAnnotations();
			
			for (Annotation annotation : annotations) {
				
				if (annotation.annotationType() == annotationClass) {
					// 设置私有域可访问
					field.setAccessible(true);
					annotationFields.add(field);
					i++;
					break;
				}
			}
		}
		Field[] returnFields = new Field[i];
		return annotationFields.toArray(returnFields);
	}

}
