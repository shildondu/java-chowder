package com.shildon.chowder.proxy;

import java.lang.reflect.Method;

/**
 * 抽象代理类。
 * @author shildon<shildondu@gmail.com>
 * @date Oct 10, 2015 5:48:19 PM
 *
 */
public abstract class AbstractProxy implements MethodInvoke, Advice{

	@Override
	public Object invoke(CglibMethodInvocation methodInvocation) {
		Class<?> targetClass = methodInvocation.getTargetClass();
		Method targetMethod = methodInvocation.getTargetMethod();
		Object[] targetParams = methodInvocation.getTargetParams();
		
		Object result = null;
		
		try {
			if (filter(targetClass, targetMethod, targetParams)) {
				beforeMethod(targetClass, targetMethod, targetParams);
				result = methodInvocation.proceed();
				afterMethod(targetClass, targetMethod, targetParams);
			} else {
				result = methodInvocation.proceed();
			}
		} catch(Throwable e) {
			afterException(targetClass, targetMethod, targetParams);
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean filter(Class<?> clazz, Method method, Object args) {
		return true;
	}
	
	public void beforeMethod(Class<?> clazz, Method method, Object argd) {
		
	}
	
	public void afterMethod(Class<?> clazz, Method method, Object argd) {
		
	}

	public void afterException(Class<?> clazz, Method method, Object argd) {
		
	}
		
}
