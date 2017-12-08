package com.shildon.orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	
	private static final String driverName = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/shildon";
	private static final String username = "shildon";
	private static final String password = "duxiaodong11";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DBUtil.class);
	
	//线程不安全的做法
	private static Connection connection;
	
	public static void initConnection() {
		try {
			Class.forName(driverName);
			connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			LOGGER.error("Can not find driver: " + driverName, e);
		} catch (SQLException e) {
			LOGGER.error("Can not connect to database", e);
		}
	}
	
	public static Connection getConnection() {
		initConnection();
		return connection;
	}
	
}
