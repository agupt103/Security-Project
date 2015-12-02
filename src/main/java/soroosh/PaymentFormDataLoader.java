package soroosh;

public class PaymentFormDataLoader {

	SorooshDatabaseConnection sql = new SorooshDatabaseConnection();

	public Object getAllMerchantsForUserPayment() throws Exception {	
		sql.getConnection();
		Object output = (Object)sql.readMarchantListFromDataBase(); 
		sql.close();
		return output;
	}

	public Object getAllUsersForMerchantPayment() throws Exception {
		sql.getConnection();
		Object output = (Object)sql.readUserListFromDatabase();
		sql.close();
		return output;	
	}

	public Object getAllAccountsForUserPayment(String username) throws Exception {			
		sql.getConnection();
		Object output = (Object)sql.readAccountListFromDataBase(username);
		sql.close();
		return output;
	}

	public Object getAllPyamentsToMerchant(String merchantUsername) throws Exception {		
		sql.getConnection();
		Object output = (Object)sql.readAllPaymentsToMerchant(merchantUsername);
		sql.close();
		return output;
	}
	
	public Object getAllPyamentsFromMerchant(String merchantUsername) throws Exception {
		sql.getConnection();
		Object output = (Object)sql.readAllPaymentsFromMerchant(merchantUsername);
		sql.close();
		return output;
	}

	public Object getAllPyamentsToCustomer(String username) throws Exception {
		sql.getConnection();
		Object output = (Object)sql.readAllPaymentsToUser(username);
		sql.close();
		return output;
	}
	
	public String getEmailAddressForUsername(String username) throws Exception {
		sql.getConnection();
		String email = sql.getUserDetailsForUsername(username);
		sql.close();
		return email;
	}


}
