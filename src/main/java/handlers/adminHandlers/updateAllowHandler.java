package handlers.adminHandlers;

import java.sql.SQLException;

import databseHandler.MySQLAccess;

public class updateAllowHandler {

	static MySQLAccess sql;
	
	static { 
		sql = new MySQLAccess();
	}
	
	public void requestUpdateHandler(String forUserName,String fromUserName,String toUserName,String type,String date) {
		
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			sql.updateAllowDataBase(forUserName,fromUserName,toUserName,type,date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
}
