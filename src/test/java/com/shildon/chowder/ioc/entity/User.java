package com.shildon.chowder.ioc.entity;

import com.shildon.chowder.ioc.annotation.Bean;

@Bean
public class User {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
