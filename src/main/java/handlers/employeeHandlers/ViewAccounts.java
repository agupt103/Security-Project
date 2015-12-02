package handlers.employeeHandlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import databseHandler.MySQLAccess;

public class ViewAccounts {

	static MySQLAccess sql;

	static { 
		sql = new MySQLAccess();
	}

	public ResultSet requestAccountHandler() {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return (ResultSet)sql.readRequestDataBase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	public ResultSet requestAccountDetailsHandler(String Username) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return (ResultSet)sql.readRequestAccountDataBase(Username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public ResultSet requestPersonalDetailsHandler(String Username) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return (ResultSet)sql.readRequestPersonalDataBase(Username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}