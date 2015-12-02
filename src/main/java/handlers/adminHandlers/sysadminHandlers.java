package handlers.adminHandlers;

import databseHandler.MySQLAccess;

public class sysadminHandlers {
static MySQLAccess sql;
	
	static { 
		sql = new MySQLAccess();
	}
	public Object getAllAccountsForSysAdmins() throws Exception {            
        sql.getConnection();
        Object output = (Object)sql.readSysAccountListFromDataBase();
        
        sql.close();
        return output;
    }

}
