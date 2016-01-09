package com.shildon.chowder.orm;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MysqlSqlResolver {
	
	private static final String MYSQL_SAVE = "insert into ";
	
	private static Log log = LogFactory.getLog(MysqlSqlResolver.class);

	// TODO 需要优化
	public static String getSql(Entity entity) {
		List<Entity.Property> properties = entity.getProperties();
		String sql = MYSQL_SAVE + entity.getTablename() + "(";
		
		for (Entity.Property property : properties) {
			sql += property.getName() + ",";
		}
		
		sql = sql.substring(0, sql.length() - 1);
		
		sql += ") values(";
		
		for (Entity.Property property : properties) {
			sql += "'" + property.getValue() + "',";
		}
		
		sql = sql.substring(0, sql.length() - 1);
		
		sql += ")";
		
		if (log.isDebugEnabled()) {
			log.debug(sql);
		}
		
		return sql;
	}
	
}
