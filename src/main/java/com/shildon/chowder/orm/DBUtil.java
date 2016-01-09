package com.shildon.chowder.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBUtil {
	
	private static final String driverName = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/shildon";
	private static final String username = "shildon";
	private static final String password = "duxiaodong11";
	
	private static final Log log = LogFactory.getLog(DBUtil.class);
	
	//线程不安全的做法
	private static Connection connection;
	
	public static void initConnection() {
		try {
			Class.forName(driverName);
			connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			log.error("Can not find driver: " + driverName, e);
		} catch (SQLException e) {
			log.error("Can not connect to database", e);
		}
	}
	
	public static Connection getConnection() {
		initConnection();
		return connection;
	}
	
}
