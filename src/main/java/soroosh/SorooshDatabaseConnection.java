package soroosh;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import utilities.TimeUtility;

public class SorooshDatabaseConnection {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;

	public void getConnection() throws ClassNotFoundException, SQLException{
		// This will load the MySQL driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/software_security?"
						+ "user=root&password=Incredibles9");
	}

	public ArrayList<String> readMarchantListFromDataBase() throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_user_details "
					+ "where tbl_user_details.Usertype = ?");    
			preparedStatement.setString(1, "MERCHANT");    
			ResultSet resultSet = preparedStatement.executeQuery();

			ArrayList<String> merchants = new ArrayList<String>();
			while (resultSet.next()) {
				byte[] username = resultSet.getBytes("username");
				merchants.add(new String(username));
			}
			resultSet.close();
			return merchants;

		} catch (Exception e) {
			throw e;
		}
	}

	public Object readUserListFromDatabase() throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_user_details "
					+ "where tbl_user_details.Usertype = ?");    
			preparedStatement.setString(1, "USER");    
			ResultSet resultSet = preparedStatement.executeQuery();

			ArrayList<String> users = new ArrayList<String>();
			while (resultSet.next()) {
				byte[] username = resultSet.getBytes("username");
				users.add(new String(username));
			}
			resultSet.close();
			return users;
		} catch (Exception e) {
			throw e;
		}
	}

	public ArrayList<AccountInfo> readAccountListFromDataBase(String username) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_user_account "
					+ "where tbl_user_account.username = ?");    
			preparedStatement.setString(1, username);    
			ResultSet resultSet = preparedStatement.executeQuery();

			ArrayList<AccountInfo> accountsInfo = new ArrayList<AccountInfo>();
			while (resultSet.next()) {
				byte[] accountNum = resultSet.getBytes("Accountnumber");
				byte[] accountType = resultSet.getBytes("Accounttype");
				byte[] balance = resultSet.getBytes("Balance");

				AccountInfo info = new AccountInfo(username, new String(accountNum), new String(accountType), 
						Double.parseDouble(new String(balance)));
				accountsInfo.add(info);				
			}
			resultSet.close();
			return accountsInfo;

		} catch (Exception e) {
			throw e;
		}
	}

	public void putCustomerPaymentInDatabase(String username, String transactionamount, 
			String sourceaccountnumber, String destinationaccountnumber, String dateandtime, 
			String transfertype, String status) throws SQLException, ClassNotFoundException{
		getConnection();

		preparedStatement = connect
				.prepareStatement("insert into  software_security.tbl_transactions values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		// Parameters start with 1
		preparedStatement.setBytes(1, username.getBytes());
		preparedStatement.setBytes(2, null);
		preparedStatement.setBytes(3, transactionamount.getBytes());
		preparedStatement.setBytes(4, transactionamount.getBytes());
		preparedStatement.setBytes(5, sourceaccountnumber.getBytes());
		preparedStatement.setBytes(6, destinationaccountnumber.getBytes());
		preparedStatement.setBytes(7, dateandtime.getBytes());
		preparedStatement.setBytes(8, transfertype.getBytes());
		preparedStatement.setBytes(9, status.getBytes());

		preparedStatement.executeUpdate();
		close();
	}

	public ArrayList<String> putCustomerPaymentInDatabase(PaymentInfo payment) 
			throws SQLException, ClassNotFoundException{
		String date = TimeUtility.generateSysDateMethod();
		double currentBalance = 0;
		ArrayList<String> errors = new ArrayList<String>();

		getConnection();
		preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_user_account "
				+ "where tbl_user_account.Accountnumber = ?");
		preparedStatement.setString(1, payment.getSourceAccountNumber());
		ResultSet resultSet = preparedStatement.executeQuery();

		if(resultSet.next()){
			currentBalance = Double.parseDouble((new String(resultSet.getBytes("Balance"))));
		}

		if(currentBalance < payment.getAmount()){
			errors.add("Not enough balance");
			close();
			return errors;
		}

		double newbalance = currentBalance - payment.getAmount();

		preparedStatement = connect.prepareStatement("UPDATE software_security.tbl_user_account "
				+ "SET Balance = ?"
				+ "WHERE tbl_user_account.Accountnumber = ?");   
		preparedStatement.setString(1, Double.toString(newbalance));
		preparedStatement.setString(2, payment.getSourceAccountNumber());
		preparedStatement.executeUpdate();
		close();


		putCustomerPaymentInDatabase(payment.getUsername(), Double.toString(payment.getAmount()), 
				payment.getSourceAccountNumber(), payment.getDestinationAccountNumber(), date.toString(), 
				"PAYMENT", "WAITING_MERCHANT");

		return errors;
	}

	public void putMerchantPaymentRequestInDatabase(String payer, String amount, 
			String accountNumber, String username) 
					throws ClassNotFoundException, SQLException {
		String date = TimeUtility.generateSysDateMethod();
		putCustomerPaymentInDatabase(username, amount, payer, accountNumber, date.toString(), "PAYMENT", "WAITING_PAYER");
	}

	public ArrayList<PaymentInfo> readAllPaymentsFromMerchant(String merchantUsername) throws Exception{
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_transactions "
					+ "where (tbl_transactions.Sourceaccountnumber = ?) "
					+ "AND (tbl_transactions.Status = ?) " 
					+ "AND (tbl_transactions.Transfertype = ?)");    

			preparedStatement.setString(1, merchantUsername);
			preparedStatement.setString(2, "WAITING_PAYER");
			preparedStatement.setString(3, "PAYMENT");

			ResultSet resultSet = preparedStatement.executeQuery();

			ArrayList<PaymentInfo> submittedPayments = new ArrayList<PaymentInfo>();
			while (resultSet.next()) {

				int id = resultSet.getInt("Transactionid");
				String username = new String(resultSet.getBytes("Username"));
				String amount = new String(resultSet.getBytes("Transactionamount"));
				String source = new String(resultSet.getBytes("Sourceaccountnumber"));
				String destination = new String(resultSet.getBytes("Destinationaccountnumber"));
				Date date = resultSet.getDate("Dateandtime");

				submittedPayments.add(new PaymentInfo(id, username, source, destination, 
						Double.parseDouble(amount), date));
			}
			resultSet.close();
			return submittedPayments;

		} catch (Exception e) {
			throw e;
		}	}

	public ArrayList<PaymentInfo> readAllPaymentsToMerchant(String merchantUsername) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_transactions "
					+ "where (tbl_transactions.Destinationaccountnumber = ?) "
					+ "AND (tbl_transactions.Status = ?) " 
					+ "AND (tbl_transactions.Transfertype = ?)");    

			preparedStatement.setString(1, merchantUsername);
			preparedStatement.setString(2, "WAITING_MERCHANT");
			preparedStatement.setString(3, "PAYMENT");

			ResultSet resultSet = preparedStatement.executeQuery();

			ArrayList<PaymentInfo> submittedPayments = new ArrayList<PaymentInfo>();
			while (resultSet.next()) {

				int id = resultSet.getInt("Transactionid");
				String username = new String(resultSet.getBytes("Username"));
				String amount = new String(resultSet.getBytes("Transactionamount"));
				String source = new String(resultSet.getBytes("Sourceaccountnumber"));
				String destination = new String(resultSet.getBytes("Destinationaccountnumber"));
				Date date = resultSet.getDate("Dateandtime");

				submittedPayments.add(new PaymentInfo(id, username, source, destination, 
						Double.parseDouble(amount), date));
			}
			resultSet.close();
			return submittedPayments;

		} catch (Exception e) {
			throw e;
		}
	}

	public ArrayList<PaymentInfo> readAllPaymentsToUser(String customerUsername) throws Exception {
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_transactions "
					+ "where (tbl_transactions.Sourceaccountnumber = ?) "
					+ "AND (tbl_transactions.Status = ?) " 
					+ "AND (tbl_transactions.Transfertype = ?)");    

			preparedStatement.setString(1, customerUsername);
			preparedStatement.setString(2, "WAITING_PAYER");
			preparedStatement.setString(3, "PAYMENT");

			ResultSet resultSet = preparedStatement.executeQuery();

			ArrayList<PaymentInfo> submittedPayments = new ArrayList<PaymentInfo>();
			while (resultSet.next()) {

				int id = resultSet.getInt("Transactionid");
				String username = new String(resultSet.getBytes("Username"));
				String amount = new String(resultSet.getBytes("Transactionamount"));
				String source = new String(resultSet.getBytes("Sourceaccountnumber"));
				String destination = new String(resultSet.getBytes("Destinationaccountnumber"));
				Date date = resultSet.getDate("Dateandtime");

				submittedPayments.add(new PaymentInfo(id, username, source, destination, 
						Double.parseDouble(amount), date));
			}
			resultSet.close();
			return submittedPayments;

		} catch (Exception e) {
			throw e;
		}
	}

	public void receivePaymentDatabaseUpdate(int paymentId, String bankAccount) throws ClassNotFoundException, SQLException{
		getConnection();

		preparedStatement = connect.prepareStatement("UPDATE software_security.tbl_transactions "
				+ "SET Status = ?, Destinationaccountnumber = ?"
				+ "WHERE tbl_transactions.Transactionid = ?");    

		preparedStatement.setString(1, "pendingapproval");
		preparedStatement.setString(2, bankAccount);
		preparedStatement.setInt(3, paymentId);

		preparedStatement.executeUpdate();
		close();

	}

	public void updatePaymentByMerchant(int paymentId, boolean accept) 
			throws SQLException, ClassNotFoundException{
		getConnection();

		preparedStatement = connect.prepareStatement("UPDATE software_security.tbl_transactions "
				+ "SET Status = ?"
				+ "WHERE tbl_transactions.Transactionid = ?");    

		if(accept)
			preparedStatement.setString(1, "pendingapproval");
		else preparedStatement.setString(1, "REJECT");
		preparedStatement.setInt(2, paymentId);

		preparedStatement.executeUpdate();

		//money back
		if(!accept){
			preparedStatement = connect.prepareStatement("Select * FROM software_security.tbl_transactions "
					+ "WHERE tbl_transactions.Transactionid = ?");
			preparedStatement.setInt(1, paymentId);
			ResultSet resultSet = preparedStatement.executeQuery();

			double amount = 0;
			double currentBalance = 0;
			String accountNumber = null;

			if(resultSet.next()){
				amount = Double.parseDouble((new String(resultSet.getBytes("Transactionamount"))));
				accountNumber = new String(resultSet.getBytes("Sourceaccountnumber"));
			}

			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_user_account "
					+ "where tbl_user_account.Accountnumber = ?");
			preparedStatement.setString(1, accountNumber);
			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()){
				currentBalance = Double.parseDouble((new String(resultSet.getBytes("Balance"))));
			}

			double newbalance = currentBalance + amount;

			preparedStatement = connect.prepareStatement("UPDATE software_security.tbl_user_account "
					+ "SET Balance = ?"
					+ "WHERE tbl_user_account.Accountnumber = ?");   
			preparedStatement.setString(1, Double.toString(newbalance));
			preparedStatement.setString(2, accountNumber);
			preparedStatement.executeUpdate();
		}

		close();
	}

	public void updatePaymentByPayer(int paymentId, boolean accept) 
			throws SQLException, ClassNotFoundException{
		getConnection();

		preparedStatement = connect.prepareStatement("UPDATE software_security.tbl_transactions "
				+ "SET Status = ?"
				+ "WHERE tbl_transactions.Transactionid = ?");    

		if(accept)
			preparedStatement.setString(1, "pendingapproval");
		else preparedStatement.setString(1, "REJECT");
		preparedStatement.setInt(2, paymentId);

		preparedStatement.executeUpdate();

		close();		
	}

	public ArrayList<String> acceptPaymentDatabaseUpdate(PaymentInfo payment, String accountNumber, String username) 
			throws SQLException, ClassNotFoundException{
		getConnection();

		ArrayList<String> errors = new ArrayList<String>();
		double currentBalance = 0;

		preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_user_account "
				+ "where tbl_user_account.Accountnumber = ?");
		preparedStatement.setString(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();

		if(resultSet.next()){
			currentBalance = Double.parseDouble((new String(resultSet.getBytes("Balance"))));
		}

		if(currentBalance < payment.getAmount()){
			errors.add("Not enough balance");
			close();
			return errors;
		}

		double newbalance = currentBalance - payment.getAmount();

		preparedStatement = connect.prepareStatement("UPDATE software_security.tbl_user_account "
				+ "SET Balance = ?"
				+ "WHERE tbl_user_account.Accountnumber = ?");   
		preparedStatement.setString(1, Double.toString(newbalance));
		preparedStatement.setString(2, accountNumber);
		preparedStatement.executeUpdate();


		preparedStatement = connect.prepareStatement("UPDATE software_security.tbl_transactions "
				+ "SET Status = ?, Sourceaccountnumber = ?"
				+ "WHERE tbl_transactions.Transactionid = ?");    
		preparedStatement.setString(1, "pendingapproval");
		preparedStatement.setString(2, accountNumber);
		preparedStatement.setInt(3, payment.getPaymentId());
		preparedStatement.executeUpdate();


		close();
		return errors;
	}

	public String getUserDetailsForUsername(String username) throws Exception {
		String email = null;
		try {						
			preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_user_details "
					+ "where tbl_user_details.Username = ?");    
			preparedStatement.setString(1, username);    
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				email = new String(resultSet.getBytes("Email"));
			}
			resultSet.close();			
		} catch (Exception e) {
			throw e;
		}

		return email;
	}

	public boolean merchantExists(String merchantName) throws SQLException, ClassNotFoundException {
		getConnection();
		preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_user_authentication "
				+ "where tbl_user_authentication.Username = ?");    
		preparedStatement.setString(1, merchantName);    
		ResultSet resultSet = preparedStatement.executeQuery();

		if(resultSet.next()){
			close();
			return true;
		}
		close();

		return false;
	}

	public boolean userExists(String payerName) throws ClassNotFoundException, SQLException {
		getConnection();
		preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_user_authentication "
				+ "where tbl_user_authentication.Username = ?");    
		preparedStatement.setString(1, payerName);    
		ResultSet resultSet = preparedStatement.executeQuery();

		if(resultSet.next()){
			close();
			return true;
		}
		close();

		return false;
	}

	public boolean accountValid(String accountNumber, String username) throws SQLException, ClassNotFoundException {
		getConnection();
		preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_user_account "
				+ "where (tbl_user_account.Username = ?) AND (tbl_user_account.Accountnumber = ?)");    
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();

		if(resultSet.next()){
			close();
			return true;
		}
		close();

		return false;
	}

	public boolean balanceAvailable(String accountNumber, double doubleAmount) throws SQLException, ClassNotFoundException {
		getConnection();
		preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_user_account "
				+ "where tbl_user_account.Accountnumber = ?");
		preparedStatement.setString(1, accountNumber);
		ResultSet resultSet = preparedStatement.executeQuery();

		if(resultSet.next()){
			double balance = Double.parseDouble((new String(resultSet.getBytes("balance"))));
			if(balance > doubleAmount){
				close();
				return true;
			}
		}

		close();
		return false;
	}

	public boolean signatureValid(String username, String signature) throws ClassNotFoundException, SQLException {
		getConnection();
		preparedStatement = connect.prepareStatement("SELECT * FROM software_security.tbl_user_authentication "
				+ "where tbl_user_authentication.Username = ?");
		preparedStatement.setString(1, username);
		ResultSet resultSet = preparedStatement.executeQuery();

		if(resultSet.next()){
			String privateKey = new String(resultSet.getBytes("publicKey"));
			if(privateKey.equalsIgnoreCase(signature)){
				close();
				return true;
			}
		}

		close();
		return false;
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
