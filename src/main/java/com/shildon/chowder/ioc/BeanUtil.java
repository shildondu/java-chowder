package com.shildon.chowder.ioc;

public class BeanUtil {
	
	public static Object instantiateBean(Class<?> clazz) throws InstantiationException, IllegalAccessException {
		// TODO 可优化，用策略模式指定实例化策略
		return clazz.newInstance();
	}

}
