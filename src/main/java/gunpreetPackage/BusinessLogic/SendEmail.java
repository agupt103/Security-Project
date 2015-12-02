package gunpreetPackage.BusinessLogic;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class SendEmail 
{
	private static final Logger LOG = Logger.getLogger(SendEmail.class);
	
	public ClientResponse sendEmail(String govemail,String username, String accountnumber, String firstname, String lastname, 
			String email,String phonenumber, String address)
	{
		Client client = Client.create();
		WebResource webResource = client.resource("https://api.mailgun.net/v3/sandbox49500e12fe5b4f679478baf006256263.mailgun.org" + "/messages");
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		try {
			String messageText = "Your requested PII request. "
							   + "Username: " + username + " "
							   + "AccountNumber: " + accountnumber + " "
							   + "First Name: " + firstname + " "
							   + "Last Name: " + lastname + " "
							   + "Email: " + email + " "
							   + "Phonenumber: " + phonenumber + " "
							   + "Address: " + address + " ";
							  
			
			client.addFilter(new HTTPBasicAuthFilter("api","key-6ccbcefd92ef23373e638edb9fbcb84b"));			
			formData.add("from", "Sun Devils Bank <mailgun@sandbox49500e12fe5b4f679478baf006256263.mailgun.org>");
			formData.add("to", govemail);
			formData.add("subject", "PII Request Info:");
			formData.add("text", messageText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("Error with sending approve email to government agency: "+e.getMessage());
		}
		return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
	}
	
	public ClientResponse sendRejectEmail(String govemail)
	{
		Client client = Client.create();
		WebResource webResource = client.resource("https://api.mailgun.net/v3/sandbox49500e12fe5b4f679478baf006256263.mailgun.org" + "/messages");
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		try {
			String messageText = "No records were found for your request!";		
			client.addFilter(new HTTPBasicAuthFilter("api","key-6ccbcefd92ef23373e638edb9fbcb84b"));			
			formData.add("from", "Sun Devils Bank <mailgun@sandbox49500e12fe5b4f679478baf006256263.mailgun.org>");
			formData.add("to", govemail);
			formData.add("subject", "PII Request Info:");
			formData.add("text", messageText);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			LOG.error("Error with sending reject email to government agency: "+e.getMessage());
		}
		return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
	}
	
	public ClientResponse sendInternalEmail(String email, String username, String password)
	{
		Client client = Client.create();
		WebResource webResource = client.resource("https://api.mailgun.net/v3/sandbox49500e12fe5b4f679478baf006256263.mailgun.org" + "/messages");
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		try 
		{
			String messageText = "Your username is : " +username+ " and your initial password is: "+password; 			
			client.addFilter(new HTTPBasicAuthFilter("api","key-6ccbcefd92ef23373e638edb9fbcb84b"));			
			formData.add("from", "Sun Devils Bank <mailgun@sandbox49500e12fe5b4f679478baf006256263.mailgun.org>");
			formData.add("to", email);
			formData.add("subject", "Login Info:");
			formData.add("text", messageText);			
		} 
		catch (Exception e) 
		{
			LOG.error("Error with sending reject email to government agency: "+e.getMessage());
		}
		return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
	}
}
