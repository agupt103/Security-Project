package handlers.employeeHandlers;

import java.sql.SQLException;

import databseHandler.CreateTransactionAccess;


public class CreateTransactionHandler {

	static CreateTransactionAccess sql;

	static { 
		sql = new CreateTransactionAccess();
	}

	public Object transactionHandler(String userName, String transamount,
			String sourceacc, String destacc, String type) {
		// TODO Auto-generated method stub
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			byte[] uname = userName.getBytes();
			byte[] tamount = transamount.getBytes();
			byte[] source = sourceacc.getBytes();
			byte[] destination = destacc.getBytes();
			Object obj=null;
			obj=(Object)sql.insertIntotransDatabase(uname, tamount, source, destination);
			sql.close();
			return obj;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}



}

