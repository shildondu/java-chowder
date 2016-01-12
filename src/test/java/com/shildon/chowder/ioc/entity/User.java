package com.shildon.chowder.ioc.entity;

import com.shildon.chowder.ioc.annotation.Bean;
import com.shildon.chowder.ioc.annotation.Inject;

@Bean
public class User {
	
	private String name;
	@Inject
	private Department department;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
