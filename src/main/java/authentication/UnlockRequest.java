package authentication;

public class UnlockRequest {
	private String username;
	public UnlockRequest(String username, String requestid, String requesttype,
			String requestfrom, String requestto, String modifiedcolumn,
			String approvalstatus, String oldvalue, String newvalue) {

		this.username = username;
		this.requestid = requestid;
		this.requesttype = requesttype;
		this.requestfrom = requestfrom;
		this.requestto = requestto;
		this.modifiedcolumn = modifiedcolumn;
		this.approvalstatus = approvalstatus;
		this.oldvalue = oldvalue;
		this.newvalue = newvalue;
	}

	private String requestid;
	private String requesttype;
	private String requestfrom;
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

	@Override
	public String toString() {
		return "Request [username=" + username + ", requestid=" + requestid
				+ ", requesttype=" + requesttype + ", requestfrom=" + requestfrom + ", requestto=" + requestto + ", modifiedcolumn=" + modifiedcolumn + ", approvalstatus=" + approvalstatus + ", oldvalue=" + oldvalue + ", newvalue=" + newvalue + "]";
	}

}
