package com.shildon.proxy;

/**
 * 抽象出调用方法链。
 * @author shildon<shildondu@gmail.com>
 * @date Oct 10, 2015 5:42:24 PM
 *
 */
public interface MethodInvocation {
	
	public Object proceed(); 

}
