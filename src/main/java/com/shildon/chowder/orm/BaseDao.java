package com.shildon.chowder.orm;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseDao {
	
	private static Log log = LogFactory.getLog(BaseDao.class);
	
	public void save(Object target) {
		Connection connection = DBUtil.getConnection();
		Statement statement = null;
		
		try {
			
			statement = connection.createStatement();
			connection.setAutoCommit(false);
			String sql = MysqlSqlResolver.getSql(OrmUtil.initEntity(target));
			statement.execute(sql);
			connection.commit();
			
		} catch (SQLException e) {
			log.error(e);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error(e);
			}
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				log.error(e);
			}
			
		}
	}

}
