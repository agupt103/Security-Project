package handlers.adminHandlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import databseHandler.MySQLAccess;

public class transactionViewRequestHandler {

	static MySQLAccess sql;
	
	static { 
		sql = new MySQLAccess();
	}
	
	public ResultSet getRequestHandler(String fromUser) {
		
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			return (ResultSet)sql.getRequestInfo(fromUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
public ResultSet getRequestStatusHandler(String toUSer,String fromUser) {
		
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			return (ResultSet)sql.getRequestStatusInfo(toUSer,fromUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
public ResultSet transactionViewHandler(String User) {
	
	try {
		sql.getConnection();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	try {
		return (ResultSet)sql.getTransaction(User);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}

public void transactionDeleteHandler(String[] Request) {
	
	try {
		sql.getConnection();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	try {
		sql.deleteRequest(Request);
	} catch (Exception e) {
		e.printStackTrace();
	}

}
	
	
}
