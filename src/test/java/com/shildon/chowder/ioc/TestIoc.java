package com.shildon.chowder.ioc;

import org.junit.Test;

import com.shildon.chowder.ioc.entity.User;

public class TestIoc {

	@Test
	public void test() {
		BeanFactory beanFactory = new BeanFactory();
		User user = beanFactory.getBean(User.class);
		user.setName("shildon");
		System.out.println(user.getName());
	}
	
}
