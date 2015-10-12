package com.shildon.chowder.proxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 
 * @author shildon<shildondu@gmail.com>
 * @date Oct 12, 2015 7:51:27 PM
 *
 */
public abstract class AbstractMethodInvocation implements MethodInvocation {
	
	protected Class<?> targetClass;
	protected Object targetObject;
	protected Method targetMethod;
	protected Object[] targetParams;
	
	protected List<MethodInvoke> proxys;
	protected int index = -1;

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
	
	protected abstract Object execute();

	/* --------------- getter and setter --------------- */
	public Class<?> getTargetClass() {
		return targetClass;
	}

	public MethodInvocation setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
		return this;
	}

	public Object getTargetObject() {
		return targetObject;
	}

	public MethodInvocation setTargetObject(Object targetObject) {
		this.targetObject = targetObject;
		return this;
	}

	public Method getTargetMethod() {
		return targetMethod;
	}

	public MethodInvocation setTargetMethod(Method targetMethod) {
		this.targetMethod = targetMethod;
		return this;
	}

	public Object[] getTargetParams() {
		return targetParams;
	}

	public MethodInvocation setTargetParams(Object[] targetParams) {
		this.targetParams = targetParams;
		return this;
	}

	public List<MethodInvoke> getProxys() {
		return proxys;
	}

	public MethodInvocation setProxys(List<MethodInvoke> proxys) {
		this.proxys = proxys;
		return this;
	}

}
