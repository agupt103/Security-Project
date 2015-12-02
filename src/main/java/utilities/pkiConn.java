package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class pkiConn 
{
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	
	public Connection getConnection() throws SQLException 
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/software_security?" +
                "user=root&password=Incredibles9"
				);
		return conn;
	}
	
		
	public void insertData(String username, byte[] privatekey, byte[] publickey, byte[] pkiOtp) throws Exception 
	{
		try
		{
			conn = this.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE tbl_user_authentication "
					+ "SET privatekey = ? ,"
					+ "publickey = ? ,"
					+ "pkiOtp = ? "
					+ "WHERE username  = ?");    
			preparedStatement.setBytes(1, privatekey);
			preparedStatement.setBytes(2, publickey);
			preparedStatement.setBytes(3, pkiOtp);
			preparedStatement.setString(4, username);
			preparedStatement.executeUpdate();			
		}
		catch (Exception e) 
		{
			throw e;
		}
		
	}
	
	public ResultSet getData(String username) throws SQLException 
	{
		try{
			conn = this.getConnection();
			preparedStatement = conn.prepareStatement("SELECT "
					+ "tbl_user_authentication.privatekey, "
					+ "tbl_user_authentication.pkiOtp, "
					+ "tbl_user_details.phonenumber, "
					+ "tbl_user_details.email "
					+ "FROM tbl_user_authentication "
					+ "INNER JOIN "
					+ "tbl_user_details ON "
					+ "tbl_user_authentication.username = tbl_user_details.username WHERE "
					+ "tbl_user_authentication.username = ?");    
			preparedStatement.setString(1, username); 
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet;
		}
		catch (Exception e) 
		{
			throw e;
		}
	}
}
	
	
	

