package com.shildon.orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class OrmUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrmUtil.class);
	
	
	public static Entity initEntity(Object target) {
		Entity entity = new Entity();
		Class<?> clazz = target.getClass();
		entity.setTablename(getTableName(clazz));
		String[] propertyNames = getPropertyNames(clazz);
		String[] propertyValues = getPropertyValues(target);
		
		if (propertyNames.length != propertyValues.length) {
			LOGGER.warn("Something error in initializing Entity.");
		}
		
		for (int i = 0; i < propertyNames.length; i++) {
			entity.addProperty(propertyNames[i], propertyValues[i]);
		}
		
		return entity;
	}
	
	private static String[] getPropertyNames(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		String[] propertyNames = new String[fields.length];

		for (int i = 0; i < fields.length; i++) {
			propertyNames[i] = fields[i].getName();
			
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(propertyNames[i]);
			}
		}

		return propertyNames;
	}
	
	private static String[] getPropertyValues(Object target) {
		Class<?> clazz = target.getClass();
		Field[] fields = clazz.getDeclaredFields();
		String[] propertyValues = new String[fields.length];
		
		for (int i = 0; i < fields.length; i++) {
			String getMethodName = getMethodName(fields[i].getName());

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(getMethodName);
			}

			try {
				
				Method getMethod = clazz.getDeclaredMethod(getMethodName);
				Object result = getMethod.invoke(target);
				propertyValues[i] = result.toString();
				
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(propertyValues[i]);
				}
				
				
			} catch (NoSuchMethodException e) {
				LOGGER.error("", e);
			} catch (SecurityException e) {
				LOGGER.error("", e);
			} catch (IllegalAccessException e) {
				LOGGER.error("", e);
			} catch (IllegalArgumentException e) {
				LOGGER.error("", e);
			} catch (InvocationTargetException e) {
				LOGGER.error("",e);
			}
		}
		
		return propertyValues;
	}
	
	private static String getMethodName(String fieldName) {
		String methodName = "get" + fieldName.substring(0, 1).toUpperCase() +
				fieldName.substring(1);
		return methodName;
	}
	
	private static String getTableName(Class<?> clazz) {
		return clazz.getSimpleName();
	}
	
}
