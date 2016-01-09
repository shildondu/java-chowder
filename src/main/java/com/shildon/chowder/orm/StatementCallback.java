package com.shildon.chowder.orm;

import java.sql.ResultSet;
import java.sql.Statement;

public interface StatementCallback {
	
	public ResultSet doInStatement(Statement statement);

}
