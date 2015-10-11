package com.shildon.chowder.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


/**
 * Cglib代理工厂。
 * @author shildon<shildondu@gmail.com>
 * @date Oct 11, 2015 9:40:44 PM
 *
 */
public class CglibProxyFactory {
	private Class<?> targetClass;
	private List<MethodInvoke> proxys;
	
	public CglibProxyFactory(Class<?> targetClass, List<MethodInvoke> proxys) {
		this.targetClass = targetClass;
		this.proxys = proxys;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getProxy() {
		return (T) Enhancer.create(targetClass, new MethodInterceptor() {
			
			@Override
			public Object intercept(Object obj, Method method, Object[] args,
					MethodProxy proxy) throws Throwable {
				return new CglibMethodInvocation(targetClass, obj, method, args, proxy, proxys).proceed();
			}

		});
	}
	
}
