package com.shildon.orm;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class DBUtilTest {

	@Test
	public void testCreateTable() {
		Connection connection = DBUtil.getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			String sql = "create table user(id char(10))";
			statement.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
