package com.shildon.orm;

import org.junit.Test;

public class MysqlSqlResolverTest {
	
	public void testSaveSql() {
		Entity entity = new Entity();
		entity.setTablename("user");
		entity.addProperty("firstname", "shildon");
		entity.addProperty("lastname", "du");
		System.out.println(MysqlSqlResolver.getSaveSql(entity));
	}
	
	@Test
	public void testDeleteSql() {
		Entity entity = new Entity();
		entity.setTablename("user");
		entity.addProperty("firstname", "shildon");
		System.out.println(MysqlSqlResolver.getDeleteSql(entity));
	}
	
}
