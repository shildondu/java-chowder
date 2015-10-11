package com.shildon.chowder.proxy;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class TestCglibProxy {
	
	public static void main(String[] args) {
		List<MethodInvoke> invokes = new LinkedList<MethodInvoke>();
		AbstractProxy abstractProxy = new Before();
		AbstractProxy abstractProxy2 = new After();
		invokes.add(abstractProxy);
		invokes.add(abstractProxy2);

		CglibProxyFactory cglibProxyFactory = new CglibProxyFactory(Hello.class, invokes);
		Hello hello = cglibProxyFactory.getProxy();
		hello.say();
	}
	
}

class Hello {
	public void say() {
		System.out.println("hello world!");
	}
}

class Before extends AbstractProxy {
	@Override
	public void beforeMethod(Class<?> clazz, Method method, Object argd) {
		System.out.println(method.getName());
		System.out.println("before");
	}
}

class After extends AbstractProxy {
	@Override
	public void afterMethod(Class<?> clazz, Method method, Object argd) {
		System.out.println("after");
	}
}