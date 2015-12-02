package authentication;

public class UserAccount {
private String username;
private String accountnumber;
private String accounttype;
private String balance;

public UserAccount(String username, String accountnumber, String accounttype,
		String balance) {
	this.username = username;
	this.accountnumber = accountnumber;
	this.accounttype = accounttype;
	this.balance = balance;
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

@Override
public String toString() {
	return "UserAccount [username=" + username + ", accountnumber=" + accountnumber
			+ ", accountype=" + accounttype + ", balance=" + balance + "]";
}

}
