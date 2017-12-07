package com.shildon.ioc;

import org.junit.Test;

import com.shildon.ioc.entity.Department;
import com.shildon.ioc.entity.User;

public class TestIoc {

	public void test() {
		BeanFactory beanFactory = new BeanFactory();
		User user = beanFactory.getBean(User.class);
		user.setName("shildon");
		System.out.println(user.getName());
		System.out.println(null == user.getDepartment());
		
		Department department = beanFactory.getBean(Department.class);
		System.out.println(user.getDepartment() == department);
		System.out.println(department.getUser() == user);
	}
	
	@Test
	public void testSomething() {

	}
	
}
