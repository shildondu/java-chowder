package com.shildon.chowder.proxy;

/**
 * 抽象出方法链中每个个体的具体调用过程。
 * @author shildon<shildondu@gmail.com>
 * @date Oct 10, 2015 6:14:51 PM
 *
 */
public interface MethodInvoke {

	public Object invoke(CglibMethodInvocation methodInvocation);
	
}
