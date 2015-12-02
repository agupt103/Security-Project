package handlers.adminHandlers;

import java.sql.SQLException;

import databseHandler.UnlockInternalAccountAccess;


public class UnlockInternalAccountHandler {

	static UnlockInternalAccountAccess sql;

	static { 
		sql = new UnlockInternalAccountAccess();
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
			Object obj=null;
			obj=(Object)sql.UpdateDataBaseUserAuthentication(username);
			sql.close();
			return obj;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public Object readrequestHandler() {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			Object obj=null;
			obj=(Object)sql.readDataBaseRequest();
			sql.close();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public Object updaterequestHandler(String username) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			Object obj=null;
			obj=(Object)sql.UpdateDataBaseRequest(username);
			sql.close();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}


}

