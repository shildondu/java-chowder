package com.shildon.chowder.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Jdk代理工厂。
 * @author shildon<shildondu@gmail.com>
 * @date Oct 11, 2015 10:20:58 PM
 *
 */
public class JdkProxyFactory implements ProxyFactory{
	private Class<?> targetClass;
	private List<MethodInvoke> proxys;
	
	public JdkProxyFactory(Class<?> targetClass, List<MethodInvoke> proxys) {
		this.targetClass = targetClass;
		this.proxys = proxys;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getProxy() {
		return (T) Proxy.newProxyInstance(targetClass.getClassLoader(), targetClass.getInterfaces(), new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				// 这里proxy并不是原来的对象
				return new JdkMethodInvocation(targetClass, targetClass.newInstance(), method, args, proxys).proceed();
			}

		});
	}

}
