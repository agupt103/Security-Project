package handlers.adminHandlers;

import java.sql.SQLException;

import com.mysql.jdbc.ResultSet;

import databseHandler.MySQLAccess;

public class ModifyUsersHandler {

	static MySQLAccess sql;

	static { 
		sql = new MySQLAccess();
	}

	public Object requestHandler(String userName,String parameterType) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			Object obj=null;
			obj=(Object)sql.readModifyUser(userName,parameterType);
			sql.close();
			return obj;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	public Object requestManagers() {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			Object obj=null;
			obj= (Object)sql.readSystemManagers();
			sql.close();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public void setloggedin(String Username) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
		
			sql.setisloggeddb(Username);
			sql.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	
	public ResultSet requestCountHandler() {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			ResultSet obj= null;
			obj= (ResultSet)sql.countRequestDatabase();
		//	sql.close();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;


	}
	
	public void updateCountHandler(int count,String Username) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
		
			sql.updatecountDatabase(count,Username);
			//sql.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	public void deleteRequestHandler(String userName,String accountnumber) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			
			sql.deleteUserDetails(userName,accountnumber);
			sql.close();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	public void updateRequestHandler(String[] parameters) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.updateUserDetails(parameters);
			sql.close();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	public void requestModifyHandler(String user, String parameter,String parametertype,String usertype) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.insertintoRequestDatabase(user,parameter,parametertype,usertype);
			sql.close();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	public void updateRequestHandler(String newvalue, String columnname, String user) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			
			sql.updateRequestDatabase(newvalue, columnname, user);
			sql.close();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	public void declineRequestHandler(String newvalue, String columnname,String user) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.declineRequestDatabase(newvalue,columnname,user);
			sql.close();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	public Object viewRequestHandler(String Manager) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {Object obj=null;
			obj =sql.viewRequestDatabase( Manager);
			sql.close();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public Object getaccounttypeHandler(String username, String accountnumber) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			Object obj = null;
			obj = sql.readAccountType(username,accountnumber);
			sql.close();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	public Object updateaccountrequest(String username,String manager,String Accountnumber) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
		 sql.insertAccountChangeRequest(username,manager,Accountnumber);
		 sql.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	

}



