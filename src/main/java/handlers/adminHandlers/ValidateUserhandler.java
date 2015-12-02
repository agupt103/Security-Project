package handlers.adminHandlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import databseHandler.MySQLAccess;

public class ValidateUserhandler {

	static MySQLAccess sql;
	
	static { 
		sql = new MySQLAccess();
	}
	
	public ResultSet ValidateHandler(String User) {
		
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			return (ResultSet)sql.validateUserInfo(User);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	
	
}
