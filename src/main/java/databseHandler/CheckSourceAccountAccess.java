package databseHandler;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import authentication.UserAccount;

public class CheckSourceAccountAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private static final Logger LOG = Logger.getLogger(CheckSourceAccountAccess.class);
	public void getConnection() throws ClassNotFoundException, SQLException{
		// This will load the MySQL driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/software_security?"
						+ "user=root&password=Incredibles9");
	}


	public String readDataBaseAccount(String username,String fromaccnumber,String transamount) throws Exception {
		try {
			Double newbal=0.0;
			String newbalance="";
			preparedStatement = connect.prepareStatement("select * from software_security.tbl_user_account WHERE username=? and accountnumber=?");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, fromaccnumber);
			ResultSet resultSet2 = preparedStatement.executeQuery();
			UserAccount useracc = writeResultSetAccount(resultSet2);
			resultSet2.close();			
			if(Double.parseDouble(transamount)<0)
				return "negative";
			if(useracc!=null && useracc.getAccountnumber().equals(fromaccnumber))
			{  preparedStatement = connect
			.prepareStatement("update software_security.tbl_user_account set balance=? WHERE username=?");
			newbal=Double.parseDouble(useracc.getBalance())-Double.parseDouble(transamount);
			if(newbal<0)
				return "insufficient";
			newbalance=newbal.toString();
			preparedStatement.setString(1, newbalance);
			preparedStatement.setString(2,username);
			preparedStatement.executeUpdate();
			return "done";
			}
			else
				return "incorrect";

		} 

		catch(NumberFormatException ne){
			LOG.error("Number format exception" +ne.getMessage());
			return "NFE";
		}
		catch (Exception e) {
			LOG.error("Exception updating balance on source account" +e.getMessage());
			return "exception";
		}
	}

	private UserAccount writeResultSetAccount(ResultSet resultSet) throws SQLException, NoSuchAlgorithmException {
		// ResultSet is initially before the first data set
		UserAccount useracc = null;

		while (resultSet.next()) {			

			byte[] username = resultSet.getBytes("username");
			byte[] accountnumber = resultSet.getBytes("accountnumber");
			byte[] accounttype = resultSet.getBytes("accounttype");
			byte[] balance = resultSet.getBytes("balance");				

			useracc=new UserAccount(new String(username), new String(accountnumber), new String(accounttype), 
					new String(balance));			
		}

		return useracc;
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

