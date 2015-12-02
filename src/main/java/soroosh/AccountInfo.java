package soroosh;

public class AccountInfo {
	private String username;
	private String accountNumber;
	private String accountType;
	private double balance;
	
	public AccountInfo(String username, String accountNumber, String accountType, double balance) {
		this.username = username;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.balance = balance;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	} 
}
