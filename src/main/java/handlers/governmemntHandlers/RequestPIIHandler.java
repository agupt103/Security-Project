package handlers.governmemntHandlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import databseHandler.MySQLAccess;

public class RequestPIIHandler {

	static MySQLAccess sql;

	static { 
		sql = new MySQLAccess();
	}

	public ResultSet getExsistingPIIRequest(String username, String requesttype,String requestdetails) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return sql.readExsistingPIIRequest(username, requesttype, requestdetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
}

	public void requestPII(String username,String requesttype,String requestdetails) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.requestPIItodb(username, requesttype, requestdetails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
}

}


