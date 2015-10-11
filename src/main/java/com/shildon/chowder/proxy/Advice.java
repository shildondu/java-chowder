package com.shildon.chowder.proxy;

import java.lang.reflect.Method;

/**
 * 增强接口。
 * @author shildon<shildondu@gmail.com>
 * @date Oct 10, 2015 5:44:08 PM
 *
 */
public interface Advice {
	
	// 实现方法过滤
	public boolean filter(Class<?> clazz, Method method, Object args);
	
	public void beforeMethod(Class<?> clazz, Method method, Object argd);
	
	public void afterMethod(Class<?> clazz, Method method, Object argd);

	public void afterException(Class<?> clazz, Method method, Object argd);
	
}
