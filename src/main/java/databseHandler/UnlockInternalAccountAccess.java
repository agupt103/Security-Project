package databseHandler;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import authentication.UnlockRequest;

public class UnlockInternalAccountAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private static final Logger LOG = Logger.getLogger(UnlockInternalAccountAccess.class);
	public void getConnection() throws ClassNotFoundException, SQLException{
		// This will load the MySQL driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/software_security?"
						+ "user=root&password=Incredibles9");
	}
	
	public String UpdateDataBaseUserAuthentication(String username) throws Exception {
		try {						
			// Statements allow to issue SQL queries to the database
			
			preparedStatement = connect
					.prepareStatement("UPDATE software_security.tbl_user_authentication SET Islocked=? WHERE username=?");
			preparedStatement.setString(1, "0");
			preparedStatement.setString(2, username);
			int count = preparedStatement.executeUpdate();
			//add other update statements after integrating locking mechanism.
			if(count>0)
			return "unlocked "+username+" refresh to update view";
			else
				return "error unlocking, username doesn't exist";
			
		} catch (Exception e) {
			LOG.error("MYSQL exception has occured, failed to unlock user" +e.getMessage());
			throw e;
		}
	}
	
	
	public ArrayList<UnlockRequest> readDataBaseRequest() throws Exception {
		try {						
			// Statements allow to issue SQL queries to the database
			preparedStatement = connect
					.prepareStatement("select * from software_security.tbl_requests where software_security.tbl_requests.requesttype=? and software_security.tbl_requests.approvalstatus=?");
			// Parameters start with 1
			preparedStatement.setString(1,"unlock");
			preparedStatement.setString(2,"pending");
			// Result set get the result of the SQL query
			ResultSet resultSet2 = preparedStatement.executeQuery();
			ArrayList<UnlockRequest> request_tbl = writeResultSetRequest(resultSet2);
			resultSet2.close();
			LOG.error("Reading unlock user account requests");
			return request_tbl;
			
		} catch (Exception e) {
			LOG.error("Error unlocking user account" +e.getMessage());
			throw e;
		}
	}
	
	private ArrayList<UnlockRequest> writeResultSetRequest(ResultSet resultSet) throws SQLException, NoSuchAlgorithmException {
		// ResultSet is initially before the first data set
		ArrayList<UnlockRequest> request_tbl = new ArrayList<UnlockRequest>();
		
		while (resultSet.next()) {			

				byte[] username = resultSet.getBytes("username");
				Integer requestid = resultSet.getInt("requestid");
				byte[] requesttype = resultSet.getBytes("requesttype");
				byte[] requestfrom = resultSet.getBytes("requestfrom");
				byte[] requestto = resultSet.getBytes("requestto");
				byte[] modifiedcolumn = resultSet.getBytes("modifiedcolumn");
				byte[] approvalstatus = resultSet.getBytes("approvalstatus");
				byte[] oldvalue = resultSet.getBytes("oldvalue");
				byte[] newvalue = resultSet.getBytes("newvalue");

				request_tbl.add(new UnlockRequest(new String(username), requestid.toString(), new String(requesttype),new String(requestfrom),new String(requestto),new String(modifiedcolumn), 
						new String(approvalstatus),new String(oldvalue),new String(newvalue)));			
		}
		
		return request_tbl;
	}
	
	
	public boolean UpdateDataBaseRequest(String username) throws Exception {
		try {						
			// Statements allow to issue SQL queries to the database
			preparedStatement = connect
					.prepareStatement("update software_security.tbl_requests set software_security.tbl_requests.approvalstatus=? where software_security.tbl_requests.username=? and software_security.tbl_requests.approvalstatus=? and software_security.tbl_requests.requesttype=?");
			// Parameters start with 1
			preparedStatement.setString(1,"approved");
			preparedStatement.setString(2,username);
			preparedStatement.setString(3,"pending");
			preparedStatement.setString(4,"unlock");
			
			// Result set get the result of the SQL query
			int count = preparedStatement.executeUpdate();
			
			if(count>0)
			{
				LOG.error("Updated unlock status in request table for user" +username);
				return true;
			}
			else
				return false;
			
		} catch (Exception e) {
			LOG.error("Error updating lock status of user" +e.getMessage());
			throw e;
		}
	}
	
	public void close() {
		try {
			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}
}