package com.shildon.chowder.ioc.entity;

import com.shildon.chowder.ioc.annotation.Bean;
import com.shildon.chowder.ioc.annotation.Inject;

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
