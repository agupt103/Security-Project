package utilities;

import java.util.LinkedHashMap;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import com.plivo.helper.api.client.RestAPI;
import com.plivo.helper.exception.PlivoException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class OtpUtility {

	public void sendOtp(HttpServletRequest request,String email){

		sendEmail(request,email);
	}

	private static ClientResponse sendEmail(HttpServletRequest request,String email) {
		int random = (new Random()).nextInt(900000) + 100000;
		HttpSession session = request.getSession(true);
		session.setAttribute("OTP", Integer.toString(random));
		String messageText = "Your one time password is " + random + ". Please enter this password to continue.";
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api","key-6ccbcefd92ef23373e638edb9fbcb84b"));
		WebResource webResource = client.resource("https://api.mailgun.net/v3/sandbox49500e12fe5b4f679478baf006256263.mailgun.org" + "/messages");
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		formData.add("from", "Sun Devils Bank <mailgun@sandbox49500e12fe5b4f679478baf006256263.mailgun.org>");
		formData.add("to", email);
		formData.add("subject", "One Time Password");
		formData.add("text", messageText);
		return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
				post(ClientResponse.class, formData);
	}

	public void sendUserName(HttpServletRequest request,String userName, String emailAddress){

		sendUserNameInEmail(request,userName,emailAddress);
	}
	
	private static ClientResponse sendUserNameInEmail(HttpServletRequest request,String userName, String emailAddress) {
		String messageText = "Your username is " + userName + ". Please login with this username to continue.";
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api","key-6ccbcefd92ef23373e638edb9fbcb84b"));
		WebResource webResource = client.resource("https://api.mailgun.net/v3/sandbox49500e12fe5b4f679478baf006256263.mailgun.org" + "/messages");
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		formData.add("from", "Sun Devils Bank <mailgun@sandbox49500e12fe5b4f679478baf006256263.mailgun.org>");
		formData.add("to", "thakkarharsh90@gmail.com");
		formData.add("subject", "One Time Password");
		formData.add("text", messageText);
		return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
				post(ClientResponse.class, formData);
	}
	
	public void sendOtp(HttpServletRequest request,String email,String query, String requestorEmail){

		sendEmail(request,email,query,requestorEmail);
	}

	private static ClientResponse sendEmail(HttpServletRequest request,String email,String query, String requestorEmail) {
		String messageText = "The requestor email address is "+requestorEmail+". The customer query is "+query;
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api","key-6ccbcefd92ef23373e638edb9fbcb84b"));
		WebResource webResource = client.resource("https://api.mailgun.net/v3/sandbox49500e12fe5b4f679478baf006256263.mailgun.org" + "/messages");
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		formData.add("from", "Sun Devils Bank <mailgun@sandbox49500e12fe5b4f679478baf006256263.mailgun.org>");
		formData.add("to", email);
		formData.add("subject", "One Time Password");
		formData.add("text", messageText);
		return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
				post(ClientResponse.class, formData);
	}
	
	public ClientResponse sendEmailInvitation(String tmpusername, String tmppwd, String privatekey) {
		
		String messageText = "Your temporary username is " + tmpusername + ". Your temporary password is" + tmppwd + ". Your private key for the future transaction is" + privatekey +". Visit to Sun Devils Bank to change your username and password.";
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api","key-6ccbcefd92ef23373e638edb9fbcb84b"));
		WebResource webResource = client.resource("https://api.mailgun.net/v3/sandbox49500e12fe5b4f679478baf006256263.mailgun.org" + "/messages");
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		formData.add("from", "Sun Devils Bank <mailgun@sandbox49500e12fe5b4f679478baf006256263.mailgun.org>");
		formData.add("to", "sguha3@asu.edu");
		formData.add("subject", "Welcome to SunDevil Bank");
		formData.add("text", messageText);
		return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
	}
	
	public ClientResponse sendEmail(String privatekey,String email) 
	{
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api","key-6ccbcefd92ef23373e638edb9fbcb84b"));
		WebResource webResource = client.resource("https://api.mailgun.net/v3/sandbox49500e12fe5b4f679478baf006256263.mailgun.org" + "/messages");
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		formData.add("from", "Sun Devils Bank <mailgun@sandbox49500e12fe5b4f679478baf006256263.mailgun.org>");
		formData.add("to", email);
		formData.add("subject", "EncodedPrivateKey");
		formData.add("text", "Your encoded privatekey:   "+privatekey);
		return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
	}
	
	public void sendMessage(String otp,String phone) 
	{
		try
		{
			String authId = "MAZDRMMDCXZJDHMGFKNZ";
			String authToken = "YThhNDg0ZDVmYjI3NGVhYWVmMzlhYWQ1OWI4Yzgz";
			RestAPI api = new RestAPI(authId, authToken, "v1");
			LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
			parameters.put("src", "+15202767815"); 
			parameters.put("dst", phone); 
			parameters.put("text", "Your otp is:  "+otp);
			try 
			{
				api.sendMessage(parameters);
			} 
			catch (PlivoException e1) 
			{
				e1.printStackTrace();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/* Method to send the view request notification to the recipient*/
	public static ClientResponse sendEmailViewRequest(String email, String role, String Sender, String firstName, String middleName,String lastName, String forUser,String first_recipient, String middle_recipient, String last_recipient) {
		String messageText="";
		if(middleName==null)
		{
			middleName="";
		}
		if(middle_recipient==null)
		{
			middle_recipient="";
		}
		if(forUser.equals(""))
		{
		messageText = role+" "+firstName+" "+middleName+" "+lastName+" has requested you to authorize him to view your transaction";	
		}
		else
		{
		messageText = role+" "+firstName+" "+middleName+" "+lastName+"("+Sender+")"+" has requested you to authorize him to view the transaction of "+first_recipient+" "+middle_recipient+" "+last_recipient+" with user name "+forUser;	
		}
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api","key-6ccbcefd92ef23373e638edb9fbcb84b"));
		WebResource webResource = client.resource("https://api.mailgun.net/v3/sandbox49500e12fe5b4f679478baf006256263.mailgun.org" + "/messages");
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		formData.add("from", "Sun Devils Bank <mailgun@sandbox49500e12fe5b4f679478baf006256263.mailgun.org>");
		formData.add("to", email);
		formData.add("subject", "View Trasaction Request");
		formData.add("text", messageText);
		return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
				post(ClientResponse.class, formData);
	}

}
