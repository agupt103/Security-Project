package handlers.systemmanagerHandlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import databseHandler.MySQLAccess;




public class authorizeExtUserHandler {

	static MySQLAccess sql;

	static { 
		sql = new MySQLAccess();
	}

	public ResultSet getExsistingAccount(String ssn, String accounttype) {
		
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return sql.getExsistingAccount(ssn, accounttype);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public ResultSet getExsistingApprovedAccount(String ssn, String accounttype) {
		
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return sql.getExsistingApprovedAccount(ssn, accounttype);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean approveUser(String ssn, String accounttype) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return sql.approveUser(ssn, accounttype );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public void rejectUser(String ssn, String accounttype) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.rejectUser(ssn, accounttype );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

}


