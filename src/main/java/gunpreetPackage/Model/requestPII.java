package gunpreetPackage.Model;

public class requestPII 
{
	private String pid;
	private String govusername;
	private String columnname;	
	private String columnvalue;
	
	public String getGovusername() {
		return govusername;
	}
	public void setGovusername(String govusername) {
		this.govusername = govusername;
	}
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getColumnname() {
		return columnname;
	}
	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}
	public String getColumnvalue() {
		return columnvalue;
	}
	public void setColumnvalue(String columnvalue) {
		this.columnvalue = columnvalue;
	}
	
}
