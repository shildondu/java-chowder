package com.shildon.chowder.orm;

import org.junit.Test;

public class OrmTest {
	
	@Test
	public void test() {
		User user = new User();
		user.setId("89757");
		user.setName("shildon");
		BaseDao baseDao = new BaseDao();
		baseDao.save(user);
	}

}
