package gunpreetPackage.BusinessLogic;


import gunpreetPackage.Dao.JdbcConnection_tbl_user_authentication;
import gunpreetPackage.Model.Authentication;

import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import passwordSaltModules.SaltModule;

public class CheckAuthentication {

	private static final Logger LOG = Logger.getLogger(CheckAuthentication.class);

	public String check(String username,String newpassword, String confirmpassword)
	{
		String result = "";
		try{
			JdbcConnection_tbl_user_authentication objJdbc = new JdbcConnection_tbl_user_authentication();	
			ResultSet details = objJdbc.get_tbl_user_authentication_Details(username);
			details.next();
			Authentication objAuthentication = new Authentication();
			String current = new String(details.getBytes(1), "UTF-8");
			String old = new String(details.getBytes(2), "UTF-8");
			String regex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(newpassword);
			if(!newpassword.equals(current) || !newpassword.equals(old))
			{
				if(matcher.matches())
				{
					if(newpassword.equals(confirmpassword))
					{
						SaltModule saltPass = new SaltModule();
						String hashed = saltPass.getHashedPassword(newpassword);
						objAuthentication.setUsername(username);
						objAuthentication.setUseroldpassword(current);
						objAuthentication.setUsercurrentpassword(hashed);
						
						objJdbc.update_tbl_user_authentication
						(
								objAuthentication.getUsername(), 
								objAuthentication.getUsercurrentpassword().getBytes(), 
								objAuthentication.getUseroldpassword().getBytes()
								);
						result = "";
					}
					else
					{
						result = "New and Confirm password do not match!";
					}
				}
				else{
					result = "The password should be 6 to 20 characters string with at least one digit, one upper case letter, one lower case letter and one special symbol (“@#$%”).";
				}
			}
			else
			{
				result = "This new password has already been used!";
			}
		}	
		catch(Exception e){
			LOG.error("Issue while reset password"+e.getMessage());
		}
		return result;

	}
}
