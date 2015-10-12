package com.shildon.chowder.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

/**
 * 抽象出Cglib代理的调用方法链。
 * @author shildon<shildondu@gmail.com>
 * @date Oct 10, 2015 5:42:24 PM
 *
 */
public class CglibMethodInvocation extends AbstractMethodInvocation{
	
	private MethodProxy methodProxy;
	
	public CglibMethodInvocation(Class<?> targetClass, Object targetObject, Method targetMethod,
			Object[] targetParams, MethodProxy methodProxy, List<MethodInvoke> proxys) {
		this.targetClass = targetClass;
		this.targetObject = targetObject;
		this.targetMethod = targetMethod;
		this.targetParams = targetParams;
		this.methodProxy = methodProxy;
		this.proxys = proxys;
	}
	
	@Override
	protected Object execute() {
		Object result = null;
		try {
			result =  this.methodProxy.invokeSuper(this.targetObject, this.targetParams);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}

	/* --------------- getter and setter --------------- */
	public MethodProxy getMethodProxy() {
		return methodProxy;
	}

	public CglibMethodInvocation setMethodProxy(MethodProxy methodProxy) {
		this.methodProxy = methodProxy;
		return this;
	}

}
