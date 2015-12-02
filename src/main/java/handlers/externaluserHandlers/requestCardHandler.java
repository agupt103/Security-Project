package handlers.externaluserHandlers;



import java.sql.ResultSet;
import java.sql.SQLException;

import databseHandler.MySQLAccess;

public class requestCardHandler {
	static MySQLAccess sql;

	static { 
		sql = new MySQLAccess();
	}
	public Object getAllAccountsForSysAdmins() throws Exception {            
		sql.getConnection();

		Object output = (Object)sql.readSysAccountListFromDataBase();

		sql.close();
		return output;
	}
	public ResultSet getExsistingCardRequest(String accountno) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return sql.readExsistingCardRequest(accountno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void requestChangeCard(String username,String accountno) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.requestCardChangetodb(username, accountno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
	public void approveCardChange(String accountno, String status) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.authorizeCardChangetodb(accountno,status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
}
