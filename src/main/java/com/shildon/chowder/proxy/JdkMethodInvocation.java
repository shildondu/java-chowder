package com.shildon.chowder.proxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 抽象出Jdk代理的调用方法链。
 * @author shildon<shildondu@gmail.com>
 * @date Oct 11, 2015 10:30:48 PM
 *
 */
public class JdkMethodInvocation extends AbstractMethodInvocation{

	public JdkMethodInvocation(Class<?> targetClass, Object targetObject, Method targetMethod,
			Object[] targetParams, List<MethodInvoke> proxys) {
		this.targetClass = targetClass;
		this.targetObject = targetObject;
		this.targetMethod = targetMethod;
		this.targetParams = targetParams;
		this.proxys = proxys;
	}
	
	@Override
	protected Object execute() {
		Object result = null;
		try {
			result = this.targetMethod.invoke(this.targetObject, this.targetParams);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}

}