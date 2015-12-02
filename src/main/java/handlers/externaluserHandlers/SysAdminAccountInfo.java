package handlers.externaluserHandlers;

public class SysAdminAccountInfo {
	private String username;
	private String firstname;
	private String lastname;
	
	public SysAdminAccountInfo(String username, String firstname, String lastname) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		
	}
	public String getusername() {
		return username;
		
	}
	public void setusername(String username) {
		this.username = username;
	}
	public String getfirstname() {
		return firstname;
	}
	public void setfirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getlastname() {
		return lastname;
	}
	public void setlastname(String lastname) {
		this.lastname = lastname;
	}
	
	
}
