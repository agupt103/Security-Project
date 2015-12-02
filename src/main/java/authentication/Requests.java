package authentication;

public class Requests {
	private String username;
	private String requestid;
	private String requesttype;
	private String requestfrom;
	private String newaccountnumber;
	public String getNewaccountnumber() {
		return newaccountnumber;
	}
	public void setNewaccountnumber(String newaccountnumber) {
		this.newaccountnumber = newaccountnumber;
	}
	private String requestto;

	private String modifiedcolumn;

	private String approvalstatus;

	private String oldvalue;
	private String newvalue;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRequestid() {
		return requestid;
	}
	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}
	public String getRequesttype() {
		return requesttype;
	}
	public void setRequesttype(String requesttype) {
		this.requesttype = requesttype;
	}
	public String getRequestfrom() {
		return requestfrom;
	}
	public void setRequestfrom(String requestfrom) {
		this.requestfrom = requestfrom;
	}
	public String getRequestto() {
		return requestto;
	}
	public void setRequestto(String requestto) {
		this.requestto = requestto;
	}
	public String getModifiedcolumn() {
		return modifiedcolumn;
	}
	public void setModifiedcolumn(String modifiedcolumn) {
		this.modifiedcolumn = modifiedcolumn;
	}
	public String getApprovalstatus() {
		return approvalstatus;
	}
	public void setApprovalstatus(String approvalstatus) {
		this.approvalstatus = approvalstatus;
	}
	public String getOldvalue() {
		return oldvalue;
	}
	public void setOldvalue(String oldvalue) {
		this.oldvalue = oldvalue;
	}
	public String getNewvalue() {
		return newvalue;
	}
	public void setNewvalue(String newvalue) {
		this.newvalue = newvalue;
	}

}
