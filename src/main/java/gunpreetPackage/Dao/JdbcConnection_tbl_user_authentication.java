package gunpreetPackage.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class JdbcConnection_tbl_user_authentication 
{	
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private static final Logger LOG = Logger.getLogger(JdbcConnection_tbl_user_authentication.class);
	
	public Connection getConnection()   
	{
		
			try 
			{
				Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException e1) 
			{
				// TODO Auto-generated catch block
				LOG.error("Error with smaking an connection: "+e1.getMessage());
			}
			
			try 
			{
				conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/software_security?" +
								"user=root&password=Incredibles9"
						);
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				LOG.error("Error with smaking an connection: "+e.getMessage());
			}
			return conn;		
	}	

	public void update_tbl_user_authentication(String username, byte[] usercurrentpassword, byte[] useroldpassword) throws Exception
	{
		try
		{
			conn = this.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE tbl_user_authentication "
					+ "SET usercurrentpassword = ? ,"
					+ "useroldpassword = ? "
					+ "WHERE username  = '"+username+"'");    
			preparedStatement.setBytes(1, usercurrentpassword);
			preparedStatement.setBytes(2, useroldpassword);
			preparedStatement.executeUpdate();			
		}
		catch (Exception e) 
		{
			LOG.error("Issue while updating the password"+e.getMessage());
		}
	}

	public ResultSet get_tbl_user_authentication_Details(String username)
	{
		ResultSet resultSet = null;
		try
		{
			conn = this.getConnection();
			preparedStatement = conn.prepareStatement("SELECT "
					+ "usercurrentpassword,"
					+ "useroldpassword "
					+ "FROM tbl_user_authentication "
					+ "WHERE username= ?");
			preparedStatement.setString(1, username); 
			resultSet = preparedStatement.executeQuery();
		}
		catch (Exception e) 
		{
			LOG.error("Issue while updating the password"+e.getMessage());
		}
		return resultSet;
	}
	
	public ResultSet get_Internal_Users_Details()  
	{
		ResultSet resultSet = null;
		try {
			conn = this.getConnection();
			preparedStatement = conn.prepareStatement("SELECT " + "username,"
					+ "firstname," + "lastname " + "FROM tbl_user_details "
					+ "WHERE usertype = 'EMPLOYEE' OR usertype = 'MANAGER'");
			resultSet = preparedStatement.executeQuery();
			
		} catch (Exception e) 
		{			
			LOG.error("Error with getting user details: "+e.getMessage());
		}
		return resultSet;
	}
	
	public ResultSet get_PII() 
	{
		ResultSet resultSet = null;
		try
		{
			conn = this.getConnection();
			preparedStatement = conn.prepareStatement("SELECT * FROM tbl_pii_requests WHERE status = 'REQUESTED_PII'");
			resultSet = preparedStatement.executeQuery();
			
		}
		catch (Exception e) 
		{
			LOG.error("Error with getting PII data: "+e.getMessage());
		}
		return resultSet;
	}
	
	public ResultSet get_UserDet(String columnname, String columnvalue) throws Exception
	{
		ResultSet resultSet = null;
		try
		{
			if(columnname.equalsIgnoreCase("accountnumber"))
			{
				conn = this.getConnection();
				preparedStatement = conn.prepareStatement("SELECT "
						+ "tbl_user_details.username, "
						+ "firstname, "
						+ "lastname, "
						+ "email, "
						+ "phonenumber, "
						+ "ssn, "
						+ "address, "
						+ "accountnumber "
						+ "FROM tbl_user_details "
						+ "INNER JOIN "
						+ "tbl_user_account "
						+ "ON "
						+ "tbl_user_details.username = tbl_user_account.username"
						+ " WHERE tbl_user_account." +columnname+ " = '"+ columnvalue+"'");
			}
			else
			{
				conn = this.getConnection();
				preparedStatement = conn.prepareStatement("SELECT "
						+ "tbl_user_details.username, "
						+ "firstname, "
						+ "lastname, "
						+ "email, "
						+ "phonenumber, "
						+ "ssn, "
						+ "address, "
						+ "accountnumber "
						+ "FROM tbl_user_details "
						+ "INNER JOIN "
						+ "tbl_user_account "
						+ "ON "
						+ "tbl_user_details.username = tbl_user_account.username"
						+ " WHERE tbl_user_details." +columnname+ " = '"+ columnvalue+"'");
			}			
			resultSet = preparedStatement.executeQuery();
			
		}
		catch (Exception e) 
		{
			LOG.error("Error with getting user data: "+e.getMessage());
		}
		return resultSet;
	}

	public ResultSet getAccNo(String username) 
	{
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			preparedStatement = conn.prepareStatement("SELECT accountnumber FROM tbl_user_account WHERE username = '"+ username +"'");
			rs = preparedStatement.executeQuery();			
			return rs;
		}
		catch (Exception e) 
		{
			LOG.error("Error with getting account number: "+e.getMessage());
		}
		return rs;
	}
	
	public void delete_Internal_Users(ArrayList<String> delList) 
	{		
		try
		{
			conn = this.getConnection();
			int len = delList.size();
			for(int i =0; i<len;i++)
			{
				String user = delList.get(i);
				preparedStatement = conn.prepareStatement("DELETE FROM tbl_user_details WHERE username = '"+ user +"' ");
				preparedStatement.executeUpdate();				
			}			
		}
		catch (Exception e) 
		{
			LOG.error("Error with deleting Internal User: "+e.getMessage());
		}		
	}

	public void updatePIIStatus(ArrayList<String> list) throws Exception
	{		
		try
		{
			conn = this.getConnection();
			int len = list.size();
			for(int i =0; i<len;i++)
			{
				String stringPID = list.get(i);				
				preparedStatement = conn.prepareStatement("UPDATE tbl_pii_requests SET status = 'COMPLETED' WHERE pid = ?");
				preparedStatement.setString(1, stringPID);
				preparedStatement.executeUpdate();				
			}			
		}
		catch (Exception e) 
		{
			LOG.error("Error with getting account number: "+e.getMessage());
		}		
	}

	public ResultSet checkgovernmentagency(String firstname,String username, String email) 
	{
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			preparedStatement = conn.prepareStatement("SELECT firstname,username FROM tbl_user_details WHERE"
					+ " firstname = '"+ firstname+"'"
					+ "OR username = '"+ username +"'");
			rs = preparedStatement.executeQuery();
						
		}
		catch (Exception e) 
		{
			LOG.error("Error with checking government agency: "+e.getMessage());
		}
		return rs;
	}
	
	public void addgovernmentagency(String firstname, String username, String email) throws Exception
	{
		try
		{
			conn = this.getConnection();
			preparedStatement = conn.prepareStatement("INSERT INTO tbl_user_details VALUES ("
					+ "'"+ username +"'," //username
					+ "'"+ username +"',"
					+ "'"+ "GOVERNMENT" +"',"
					+ "'"+ "" +"'," //prefix
					+ "'"+ firstname +"',"
					+ "'"+ "" +"',"
					+ "'"+ ""+"',"
					+ "'"+ "" +"'," //gender
					+ "'"+ "" +"',"
					+ "'"+ "" +"',"
					+ "'"+ "" +"',"
					+ "'"+ "" +"'," //passportnumber
					+ "'"+ "" +"',"
					+ "'"+ "" +"',"
					+ "'"+ "" +"'," //businesslicence
					+ "'"+ email +"'," 
					+ "'"+ "" +"')");  //status
			preparedStatement.executeUpdate();	
			
		}
		catch (Exception e) 
		{
			LOG.error("Error with adding government agency: "+e.getMessage());
		}
	}

	public ResultSet checkinternaluser(String ssn) throws Exception
	{
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			preparedStatement = conn.prepareStatement("SELECT * FROM tbl_user_details WHERE"
					+ " ssn = '"+ ssn+"'");
			rs = preparedStatement.executeQuery();	
						
		}
		catch (Exception e) 
		{
			LOG.error("Error with checking internal user: "+e.getMessage());
		}
		return rs;
	}
	
	public void add_internalUser(String username, String firstname,String middlename,String lastname,
									String ssn,String email,String usertype,String phonenumber,
									String dateofbirth,String address,String state,String zip) throws Exception
	{
		try
		{
			conn = this.getConnection();
			preparedStatement = conn.prepareStatement("INSERT INTO tbl_user_details VALUES ("
					+ "'"+ username +"'," //username
					+ "'"+ ssn +"',"
					+ "'"+ usertype +"',"
					+ "'"+ " " +"'," //prefix
					+ "'"+ firstname +"',"
					+ "'"+ middlename +"',"
					+ "'"+ lastname +"',"
					+ "'"+ " " +"'," //gender
					+ "'"+ address +"',"
					+ "'"+ state +"',"
					+ "'"+ zip +"',"
					+ "'"+ " " +"'," //passportnumber
					+ "'"+ phonenumber +"',"
					+ "'"+ dateofbirth +"',"
					+ "'"+ " " +"'," //businesslicence
					+ "'"+ email +"'," 
					+ "'"+ "" +"')");   //status 			
			preparedStatement.executeUpdate();			
		}
		catch (Exception e) 
		{
			LOG.error("Issue while updating internal user details: "+e.getMessage());
		}

	}

	public void modifyinternaluser(String username, String column, String newinfo)
	{
		try
		{
			conn = this.getConnection();
			preparedStatement = conn.prepareStatement("UPDATE tbl_user_details SET "+ column +" = '"+ newinfo +"' WHERE username = '"+ username +"' ");
			preparedStatement.executeUpdate();	
		}
		catch (Exception e) 
		{
			LOG.error("Issue while modifying internal user: "+e.getMessage());
		}
	}

	public ResultSet checkusername(String username) 
	{
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			preparedStatement = conn.prepareStatement("SELECT * FROM tbl_user_authentication WHERE"
					+ " username = '"+ username+"'");
			rs = preparedStatement.executeQuery();	
					
		}
		catch (Exception e) 
		{
			LOG.error("Error with checking username: "+e.getMessage());
		}
		return rs;	
	}

	public void addusername(String username,String password) throws Exception
	{
		try
		{
			conn = this.getConnection();
			preparedStatement = conn.prepareStatement("INSERT INTO tbl_user_authentication VALUES("
							+ "'"+ username +"',"
							+ "'"+password+"',"
							+ "'"+""+"',"
							+ "'"+"0"+"',"
							+ "'"+"0"+"',"
							+ "'"+""+"',"
							+ "'"+""+"',"
							+ "'"+""+"',"
							+ "'"+""+"',"
							+ "'"+"1"+"')");
			preparedStatement.executeUpdate();
						
		}
		catch (Exception e) 
		{
			LOG.error("Error with adding username: "+e.getMessage());
		}
	}

	public ResultSet get_PIIDetails(String pid) throws Exception
	{
		ResultSet resultSet = null;
		try
		{
			conn = this.getConnection();
			preparedStatement = conn.prepareStatement("SELECT username, ssn, passsportnumber FROM tbl_pii_requests WHERE pid = '"+pid+"'");
			resultSet = preparedStatement.executeQuery();
			
		}
		catch (Exception e) 
		{
			LOG.error("Error with getting PII Details: "+e.getMessage());
		}
		return resultSet;
	}

	public ResultSet get_username(String column, String value) 
	{
		ResultSet rs = null;
		try
		{
			conn = this.getConnection();
			preparedStatement = conn.prepareStatement("SELECT tbl_user_details.username, firstname, lastname, email, phonenumber, ssn, address, accountnumber "
													+ "FROM tbl_user_details INNER JOIN "
													+ "tbl_user_account ON "
													+ "tbl_user_details.username = tbl_user_account.username"
													+ " WHERE tbl_user_details." +column+ " = '"+ value+"'");
			rs = preparedStatement.executeQuery();	
						
		}
		catch (Exception e) 
		{
			LOG.error("Error with getting username: "+e.getMessage());
		}
		return rs;
	}

	public ArrayList<String> getEmailList(ArrayList<String> list) 
	{		
		ArrayList<String> emaillist = new ArrayList<String>();
		try
		{
			
			conn = this.getConnection();
			int len = list.size();
			for(int i =0; i<len;i++)
			{
				
				String stringPID = list.get(i);				
				preparedStatement = conn.prepareStatement("SELECT email FROM tbl_user_details WHERE username IN (SELECT username FROM tbl_pii_requests WHERE pid = ?)");
				preparedStatement.setString(1, stringPID);
				ResultSet rs  = null;
				rs =preparedStatement.executeQuery();
				while(rs.next())
				{
					emaillist.add(rs.getString(1));
				}
			}
						
		}
		catch (Exception e) 
		{
			LOG.error("Error with getting emaillist: "+e.getMessage());
		}
		return emaillist;
	}

	public void delete_Internal_Auth(ArrayList<String> delList) 
	{		
		try
		{
			conn = this.getConnection();
			int len = delList.size();
			for(int i =0; i<len;i++)
			{
				String user = delList.get(i);
				preparedStatement = conn.prepareStatement("DELETE FROM tbl_user_authentication WHERE username = '"+ user +"' ");
				preparedStatement.executeUpdate();				
			}			
		}
		catch (Exception e) 
		{
			LOG.error("Error with deleting Internal User: "+e.getMessage());
		}		
	}
	
	public ResultSet getpwd(String username)  
	{
		ResultSet resultSet = null;
		try {
			conn = this.getConnection();
			preparedStatement = conn.prepareStatement("SELECT " + "usercurrentpassword,FROM tbl_user_authentication "
					+ "WHERE username = "+ username );
			resultSet = preparedStatement.executeQuery();
			
		} catch (Exception e) 
		{			
			LOG.error("Error with getting user details: "+e.getMessage());
		}
		return resultSet;
	}
		
	public void addManager(String username,String usertype)
	{
		try
		{
			conn = this.getConnection();
			preparedStatement = conn.prepareStatement("INSERT INTO tbl_user_account_view_request (username, usertype) VALUES(?,?)");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, usertype);
			preparedStatement.executeUpdate();						
		}
		catch (Exception e) 
		{
			LOG.error("Error with adding username: "+e.getMessage());
		}
	}
}
