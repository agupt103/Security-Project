package handlers.individualuserHandlers;

public class UserAccounts {
	private String username;
	private String accountnumber;
	private String accounttype;
	private String userdebitcard;
	//private String userdebitcard2;
	public String getUserdebitcard() {
		return userdebitcard;
	}
	public void setUserdebitcard1(String userdebitcard1) {
		this.userdebitcard = userdebitcard1;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	public String getAccounttype() {
		return accounttype;
	}
	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String balance;
}
