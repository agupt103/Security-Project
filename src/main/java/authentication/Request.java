package authentication;


public class Request  {

	private String requestFrom;
	private String columnname;
    private String oldvalue;
    private String newvalue;
    
 
    public Request(){}
    
    public Request(String requestFrom, String columnname, String oldvalue, String newvalue){
    	this.requestFrom = requestFrom;
    	this.columnname= columnname;
    	this.oldvalue = oldvalue;
    	this.newvalue = newvalue;
    	
    }
    
    
    public String getRequestFrom() {
        return requestFrom;
    }
 
    public void setRequestFrom(String requestFrom) {
        this.requestFrom = requestFrom;
    }
     
  
    public String getcolumnname() {
        return columnname;
    }
 
    public void setcolumnname(String columnname) {
        this.columnname = columnname;
    }
 
    public String getoldvalue() {
        return oldvalue;
    }
 
    public void setOldValue(String oldvalue) {
        this.oldvalue = oldvalue;
    }
    public String getnewvalue() {
        return newvalue;
    }
 
    public void setNewValue(String newvalue) {
        this.newvalue = newvalue;
    }
 
  
    
}
