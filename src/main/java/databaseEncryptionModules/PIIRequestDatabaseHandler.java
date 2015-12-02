package databaseEncryptionModules;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PIIRequestDatabaseHandler {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;

	public void getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/test?"
						+ "user=root&password=Incredibles9");
	}

	public void addNewEntry( String username, String requesttype, String requestdetails, 
			String status) throws Exception {
		
		EncDecModule encrypter = new EncDecModule();

		byte[] encryptedRequesttype = encrypter.encrypt(requesttype);
		byte[] encryptedRequestdetails = encrypter.encrypt(requestdetails);
		

		getConnection();

		preparedStatement = connect
				.prepareStatement("insert into  software_security.tbl_pii_requests values (?, ?, ?, ?, ?, ?)");
		preparedStatement.setBytes(1, null);
		preparedStatement.setBytes(2, username.getBytes());
		preparedStatement.setBytes(3, encryptedRequesttype);
		preparedStatement.setBytes(4, encryptedRequestdetails);
		preparedStatement.setBytes(5, "Yo".getBytes());
		preparedStatement.setBytes(6, status.getBytes());

		preparedStatement.executeUpdate();

		close();
	}

	public PIIRequest getEntry(String username) throws Exception {		
		PIIRequest user = null;
		EncDecModule encrypter = new EncDecModule();

		getConnection();
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_pii_requests "
					+ "where tbl_pii_requests.username = ?");    
			preparedStatement.setString(1, username);    
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				int pid = resultSet.getInt("pid");
				String requesttype = new String(encrypter.decrypt(resultSet.getBytes("requesttype")));
				String requestdetails = new String(encrypter.decrypt(resultSet.getBytes("requestdetails")));				
				String status = new String(encrypter.decrypt(resultSet.getBytes("status")));

				user = new PIIRequest(pid, username, requesttype, requestdetails, "", status);
			}
			resultSet.close();
			close();
			return user;
		} catch (Exception e) {
			throw e;
		}
	}

	// You need to close the resultSet
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
