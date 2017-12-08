package com.shildon.orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MysqlSqlResolver {
	
	private static final String MYSQL_SAVE = "insert into ?(?) values(?)";
	private static final String MYSQL_DELETE = "delete from ? where ?";
	private static final String MYSQL_QUERY = "select ? from ? where ?";
	private static final String MYSQL_UPDATE = "update ? set ? where ?";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MysqlSqlResolver.class);
	
	public static String getSaveSql(Entity entity) {
		List<Entity.Property> properties = entity.getProperties();
		String sql = MYSQL_SAVE.replaceFirst("\\?", entity.getTablename());
		
		StringBuilder nameBuilder = new StringBuilder();
		StringBuilder valueBuilder = new StringBuilder();
		for (Entity.Property property : properties) {
			nameBuilder.append(property.getName()).append(',');
			valueBuilder.append(property.getValue()).append(',');
		}
		
		sql = sql.replaceFirst("\\?", nameBuilder.substring(0, nameBuilder.length() - 1));
		sql = sql.replaceFirst("\\?", valueBuilder.substring(0, valueBuilder.length() - 1));
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(sql);
		}
		
		return sql;
	}
	
	public static String getDeleteSql(Entity entity) {
		List<Entity.Property> properties = entity.getProperties();
		String sql = MYSQL_DELETE.replaceFirst("\\?", entity.getTablename());
		
		StringBuilder condition = new StringBuilder();
		Entity.Property property = properties.get(0);
		condition.append(property.getName()).append("=").
			append(property.getValue());
		
		sql = sql.replaceFirst("\\?", condition.toString());
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(sql);
		}
		
		return sql;
	}

	public static String getQuerySql(Entity entity) {
		String sql = MYSQL_QUERY.replaceFirst("\\?", entity.getTablename());
		
		return sql;
	}

	public static String getUpdateSql(Entity entity) {
		String sql = MYSQL_UPDATE.replaceFirst("\\?", entity.getTablename());
		
		return sql;
	}

}
