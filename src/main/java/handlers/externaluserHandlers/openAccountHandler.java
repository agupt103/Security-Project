package handlers.externaluserHandlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import databseHandler.MySQLAccess;

public class openAccountHandler {

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
			return sql.readAccountDetails(ssn, accounttype);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public boolean openAccount(String usertype, String accounttype, String prefix, String firstname, String middlename, String lastname, String gender, String address,String state, String zip, String passportnumber, String ssn, String email, String phonenumber, String dateofbirth, String documents, String businesslicence, String status) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return sql.openAccountodb( usertype, accounttype, prefix, firstname, middlename, lastname,gender, address,state, zip, passportnumber, ssn, email, phonenumber, dateofbirth, documents, businesslicence, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}


