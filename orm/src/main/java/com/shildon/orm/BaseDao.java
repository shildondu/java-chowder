package com.shildon.orm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseDao {
	
	private static Log log = LogFactory.getLog(BaseDao.class);
	
	public void save(Object target) {
		
		final String sql = MysqlSqlResolver.getSaveSql(OrmUtil.initEntity(target));
		execute(new StatementCallback() {
			
			@Override
			public ResultSet doInStatement(Statement statement) {
				try {
					statement.execute(sql);
				} catch (SQLException e) {
					log.error(e);
				}
				return null;
			}
		});
		
	}
	
	// 使用回调机制，复用代码
	public ResultSet execute(StatementCallback callback) {
		Connection connection = DBUtil.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			
			statement = connection.createStatement();
			connection.setAutoCommit(false);
			// 调用回调函数
			resultSet = callback.doInStatement(statement);
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

		return resultSet;
	}

}
