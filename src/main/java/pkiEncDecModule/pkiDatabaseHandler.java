package pkiEncDecModule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class pkiDatabaseHandler {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	
	public void getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/software_security?"
						+ "user=root&password=Incredibles9");
	}
	
	public void close() {
		try {
			if (statement != null) {
				statement.close();
			}
			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {}
	}
	
	public void putKeysInDatabase(String username, String privateKey, String publicKey) throws ClassNotFoundException, SQLException{
		getConnection();

		preparedStatement = connect.prepareStatement("UPDATE software_security.tbl_user_authentication "
				+ "SET publickey = ?, privatekey = ?"
				+ "WHERE tbl_user_authentication.username = ?");   
		preparedStatement.setString(1, publicKey);
		preparedStatement.setString(2, privateKey);
		preparedStatement.setString(3, username);
		preparedStatement.executeUpdate();
		close();
	}
	
	public EncryptionKeyPair getKeysFromDatabase(String username) throws Exception{
		EncryptionKeyPair pair = null;
		getConnection();

		try {						
			preparedStatement = connect.prepareStatement("SELECT publickey,privatekey "
					+ "FROM software_security.tbl_user_authentication "
					+ "where tbl_user_authentication.Username = ?");    
			preparedStatement.setString(1, username);    
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String publickey = new String(resultSet.getBytes("publickey"));
				String privatekey = new String(resultSet.getBytes("privatekey"));

				pair = new EncryptionKeyPair(privatekey, publickey);
			}
			resultSet.close();			
		} catch (Exception e) {
			throw e;
		}
		close();

		return pair;
	}
}
