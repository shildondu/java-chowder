package com.shildon.chowder.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

/**
 * 抽象出调用方法链。
 * @author shildon<shildondu@gmail.com>
 * @date Oct 10, 2015 5:42:24 PM
 *
 */
public class CglibMethodInvocation implements MethodInvocation{
	
	private Class<?> targetClass;
	private Object targetObject;
	private Method targetMethod;
	private Object[] targetParams;
	private MethodProxy methodProxy;
	
	private List<MethodInvoke> proxys;
	private int index = -1;
	
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
	public Object proceed() {
		if (this.index == this.proxys.size() - 1) {
			return execute();
		} else {
			if (this.index < this.proxys.size()) {
				return this.proxys.get(++this.index).invoke(this);
			} else {
				return execute();
			}
		}
	}
	
	private Object execute() {
		Object result = null;
		try {
			result =  this.methodProxy.invokeSuper(this.targetObject, this.targetParams);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}

	/* --------------- getter and setter --------------- */
	public Class<?> getTargetClass() {
		return targetClass;
	}

	public CglibMethodInvocation setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
		return this;
	}

	public Object getTargetObject() {
		return targetObject;
	}

	public CglibMethodInvocation setTargetObject(Object targetObject) {
		this.targetObject = targetObject;
		return this;
	}

	public Method getTargetMethod() {
		return targetMethod;
	}

	public CglibMethodInvocation setTargetMethod(Method targetMethod) {
		this.targetMethod = targetMethod;
		return this;
	}

	public Object[] getTargetParams() {
		return targetParams;
	}

	public CglibMethodInvocation setTargetParams(Object[] targetParams) {
		this.targetParams = targetParams;
		return this;
	}

	public MethodProxy getMethodProxy() {
		return methodProxy;
	}

	public CglibMethodInvocation setMethodProxy(MethodProxy methodProxy) {
		this.methodProxy = methodProxy;
		return this;
	}

	public List<MethodInvoke> getProxys() {
		return proxys;
	}

	public CglibMethodInvocation setProxys(List<MethodInvoke> proxys) {
		this.proxys = proxys;
		return this;
	}

}
