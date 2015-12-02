package handlers.systemmanagerHandlers;

import java.sql.SQLException;

import databseHandler.MySQLAccess;




public class reviewExtUserHandler {

static MySQLAccess sql;
	
	static { 
		sql = new MySQLAccess();
	}
	
	public Object reviewExtUser(String ssn, String accounttype) {
		
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			return (Object)sql.reviewExtUser(ssn,accounttype);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	

}


