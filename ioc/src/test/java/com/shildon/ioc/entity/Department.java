package com.shildon.ioc.entity;

import com.shildon.ioc.annotation.Bean;
import com.shildon.ioc.annotation.Inject;

@Bean
public class Department {

	@Inject
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
