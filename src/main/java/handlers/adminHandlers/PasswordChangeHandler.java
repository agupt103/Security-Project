package handlers.adminHandlers;

import java.sql.SQLException;

import databseHandler.PasswordChangeAccess;




public class PasswordChangeHandler {

	static PasswordChangeAccess sql;
	
	static { 
		sql = new PasswordChangeAccess();
	}
	
	public Object requestHandler(String username) {
		
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			sql.UpdatePass(username);
			sql.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
}