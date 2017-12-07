package com.shildon.proxy;

/**
 * 代理工厂接口。
 * @author shildon<shildondu@gmail.com>
 * @date Oct 11, 2015 10:29:30 PM
 *
 */
public interface ProxyFactory {
	
	public <T> T getProxy();

}
