package databaseEncryptionModules;

public class PIIRequest {
	private int pid;
	private String username;
	private String requesttype;
	private String requestdetails;
	private String authorizedto;
	private String status;
	
	public PIIRequest(int pid, String username, String requesttype, String requestdetails, 
			String authorizedto, String status){
		this.username = username;
		this.pid = pid;
		this.requesttype = requesttype;
		this.requestdetails = requestdetails;
		this.authorizedto = authorizedto;
		this.status = status; 
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRequesttype() {
		return requesttype;
	}

	public void setRequesttype(String requesttype) {
		this.requesttype = requesttype;
	}

	public String getRequestdetails() {
		return requestdetails;
	}

	public void setRequestdetails(String requestdetails) {
		this.requestdetails = requestdetails;
	}

	public String getAuthorizedto() {
		return authorizedto;
	}

	public void setAuthorizedto(String authorizedto) {
		this.authorizedto = authorizedto;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
