package com.shildon.orm;

import java.sql.ResultSet;
import java.sql.Statement;

public interface StatementCallback {
	
	public ResultSet doInStatement(Statement statement);

}
