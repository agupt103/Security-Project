package com.sundevils.web.controller;


import handlers.adminHandlers.LoginHandler;
import handlers.adminHandlers.sysadminHandlers;
import handlers.externaluserHandlers.openAccountHandler;
import handlers.externaluserHandlers.requestCardHandler;
import handlers.governmemntHandlers.RequestPIIHandler;
import handlers.individualuserHandlers.AddRecepientHandler;
import handlers.systemmanagerHandlers.authorizeExtUserHandler;
import handlers.systemmanagerHandlers.reviewExtUserHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import soroosh.PaymentFormDataLoader;
import authentication.User;




@Controller
public class SayantanController 
{
	final static Logger logger = Logger.getLogger(SayantanController.class);

	String ssn="";
	String accounttype="";

	String requestid = "";
	authorizeExtUserHandler handler =  new authorizeExtUserHandler();
	AddRecepientHandler handler1 = new AddRecepientHandler();
	LoginHandler lhandler = new LoginHandler();


	//openAccountForm
	@RequestMapping(value="/openAccountForm",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView registerRecepient(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws SQLException
	{

		ModelAndView model = null;
		try
		{
			model = new ModelAndView();
			String regex = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";
			String regex1= "^\\+?[1]\\d{10,10}$";
			String regex2="^[0-9]{5}$";
			String regex3= "^B[A-Z]{2}-[0-9]{3}$";
			String usertype= "";
			String accounttype="";
			String firstname="";
			String lastname="";
			String zip="";
			String passportnumber="";
			String ssn="";
			String email="";
			String phonenumber="";
			String businesslicence="";
			String checkagreement="";
			if(request.getParameter("submit")!=null)

			{
				usertype=request.getParameter("usertype");
				accounttype=request.getParameter("accounttype");
				firstname=request.getParameter("firstname");
				lastname=request.getParameter("lastname");
				zip=request.getParameter("zip");
				passportnumber=request.getParameter("passportnumber");
				ssn=request.getParameter("ssn");

				email=request.getParameter("email");
				phonenumber=request.getParameter("phonenumber");
				businesslicence=request.getParameter("businesslicence");	
				checkagreement=request.getParameter("checkagreement");

				//store the session of SSN
				session.setAttribute("SSN_Value", ssn);
				session.setAttribute("CUSTOMER_NAME", firstname + " " + lastname );
				session.setAttribute("CUSTOMER_EMAIL", email);
				session.setAttribute("ACCOUNT_TYPE", accounttype);


				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(ssn);

				Pattern pattern1 = Pattern.compile(regex1);
				Matcher matcher1 = pattern1.matcher(phonenumber);

				Pattern pattern2 = Pattern.compile(regex2);
				Matcher matcher2 = pattern2.matcher(zip);


				Pattern pattern3 = Pattern.compile(regex3);
				Matcher matcher3 = pattern3.matcher(businesslicence);
				//Check for validation

				if (!matcher.matches())
				{
					model.addObject("emptyFields", "SSN should be of format XXX-XX-XXXX.\n The first three digits called the area number. \nThe area number cannot be 000, 666, or between 900 and 999.Digits four and five are called the group number and range from 01 to 99.\nThe last four digits are serial numbers from 0001 to 9999.");
					model.setViewName("Account.Opening.Form");
				}
				else if (!matcher1.matches())
				{
					model.addObject("emptyFields", "The mobile number should be of format +1XXXXXXXXXX");
					model.setViewName("Account.Opening.Form");
				}
				else if (!matcher2.matches())
				{
					model.addObject("emptyFields", "The ZIP must have 5 digts. No other characters allowed.");
					model.setViewName("Account.Opening.Form");
				}
				else if(usertype.equals("") || accounttype.equals("") ||  firstname.equals("") || lastname.equals("") ||  zip.equals("") ||email.equals("") || ssn.equals("")|| phonenumber.equals("")||checkagreement.equals(""))
				{


					model.addObject("emptyFields", "First Name, Last Name, Zip Code, Email, SSN, Mobile Number, and agreement are mandatory fields");
					model.setViewName("Account.Opening.Form");
				}
				else if (usertype.equals("MERCHANT") && !matcher3.matches())
				{
					model.addObject("emptyFields", "Business Licence is mandatory for the Merchant/Organization. Business Licence should be 7 characters, should start with letter B, first three characters must be in alphabet, fourth character must be dash (-),fifth to seventh character must be numbers");
					model.setViewName("Account.Opening.Form");
				}
				else 
				{
					openAccountHandler handler = new openAccountHandler();
					ResultSet rs =  handler.getExsistingAccount(ssn,accounttype);
					if(rs.next())
					{

						String temp_ssn = rs.getString("ssn");
						String temp_accounttype = rs.getString("accounttype");
						if(temp_ssn.equals(ssn) && temp_accounttype.equals(accounttype))
						{
							model.addObject("ExsistingUser", " Recepient with entered SSN and Account Type already exsists");
							model.setViewName("Account.Opening.Form");
						}
					}
					else {
						boolean flag = handler.openAccount(request.getParameter("usertype"),request.getParameter("accounttype"),request.getParameter("prefix"),request.getParameter("firstname"),request.getParameter("middlename"),request.getParameter("lastname"),request.getParameter("gender"),request.getParameter("address"),request.getParameter("state"),request.getParameter("zip"),request.getParameter("passportnumber"),request.getParameter("ssn"),request.getParameter("email"),request.getParameter("phonenumber"),request.getParameter("dateofbirth"),request.getParameter("documents"),request.getParameter("businesslicence"),"Applied");
						if(flag){
							model.addObject("Successful", "Account Opening Application has been submitted sucessfully");
							//model.setViewName("uploadfile");
							model.setViewName("uploadfilesuccess");						}
						else 
						{
							model.addObject("Successful", "Account Opening Application not successful");
							model.setViewName("Account.Opening.Form");
						}
					}
				}
			}
			else
			{

				model.addObject("Successful","");
				model.setViewName("Account.Opening.Form");
			}

		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/uploadFilesForm", method = {RequestMethod.GET})
	public ModelAndView uploadFilesForm(HttpServletRequest request, HttpServletResponse response,
			HttpSession session)  {
		ModelAndView model = new ModelAndView();

		if(session.getAttribute("registered") != null){
			model.setViewName("uploadfile");
		}

		else{
			model.setViewName("index");
		}

		return model;
	}

	// Government Agency Controller
	@RequestMapping(value="/requestPII",method=RequestMethod.POST)
	public ModelAndView requestPII(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws SQLException
	{
		String role = (String) session.getAttribute("Role");
		if(role == null){
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("GOVERNMENT")){
			ModelAndView model = null;
			try
			{
				model = new ModelAndView();	
				String requesttype="";
				String requestdetails="";
				if(request.getParameter("submit")!=null)
				{
					requesttype=request.getParameter("requesttype");
					requestdetails=request.getParameter("requestdetails");
					if(requesttype.equals("") || requestdetails.equals(""))
					{
						model.addObject("emptyFields", "All fields are mandatory");
						model.setViewName("government");
					}
					else 
					{
						RequestPIIHandler handler = new RequestPIIHandler();
						String usernamename=(String) session.getAttribute("USERNAME");

						ResultSet rs =  handler.getExsistingPIIRequest(usernamename,requesttype,requestdetails);
						if(rs.next())
						{
							String temp_username = rs.getString("username");
							String temp_requesttype = rs.getString("requesttype");
							String temp_requestdetails = rs.getString("requestdetails");
							if(temp_username.equals(usernamename) && temp_requesttype.equals(requesttype) && temp_requestdetails.equals(requestdetails))
							{
								model.addObject("ExsistingUser", " The request type and request details already exsists");
								sysadminHandlers sysadminhandler = new sysadminHandlers();
								model.addObject("sysadmins", sysadminhandler.getAllAccountsForSysAdmins());
								model.setViewName("government");
							}



						}
						else 
						{
							handler.requestPII(usernamename,request.getParameter("requesttype"),request.getParameter("requestdetails"));
							model.addObject("Successful", "Request for PII has been submitted sucessfully");
							model.setViewName("government");

						}
					}
				}
				else
				{

					model.addObject("Successful","");
					model.setViewName("government");
				}

			}
			catch( Exception e )
			{
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			return model;
		}
		else{
			ModelAndView model = new ModelAndView();
			String username = (String) session.getAttribute("USERNAME");
			lhandler.updateLoggedInFlag(username, 0);
			session.invalidate();
			model.setViewName("index");
			return model;
		}
	}
	@RequestMapping(value="/requestPII",method=RequestMethod.GET)
	public ModelAndView GovernmentrequestPII(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) throws IOException  {
		String role = (String) session.getAttribute("Role");
		if(role == null){
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("GOVERNMENT")){
			ModelAndView model = new ModelAndView();
			sysadminHandlers sysadminhandler = new sysadminHandlers();
			try {
				model.addObject("sysadmins", sysadminhandler.getAllAccountsForSysAdmins());

			} catch (Exception e) {

				ArrayList<String> errors = new ArrayList<String>();
				errors.add("Internal error - please try again later");
				model.addObject("errors", errors);
				model.setViewName("government");
				e.printStackTrace();
				return model;

			}
			model.setViewName("government");
			return model;

		}
		else{
			ModelAndView model = new ModelAndView();
			String username = (String) session.getAttribute("USERNAME");
			lhandler.updateLoggedInFlag(username, 0);
			session.invalidate();
			model.setViewName("index");
			return model;
		}
	}
	//Request for Card Replacement
	@RequestMapping(value = {"/replaceCard"}, method = RequestMethod.GET)
	public ModelAndView userCardReplacement(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) throws IOException {

		String role = (String) session.getAttribute("Role");
		if(role == null){
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("MERCHANT") || role.equals("USER")){
			try{
				ModelAndView model = new ModelAndView();
				PaymentFormDataLoader handler = new PaymentFormDataLoader();

				try {
					String username=(String)session.getAttribute("USERNAME");
					//username="anishaallada";
					model.addObject("bankaccounts", handler.getAllAccountsForUserPayment(username));
				} catch (Exception e) {
					ArrayList<String> errors = new ArrayList<String>();
					errors.add("Internal error - please try again later");
					model.addObject("errors", errors);
					model.setViewName("Request.New.Card");
					e.printStackTrace();
					return model;
				}
				model.setViewName("Request.New.Card");
				return model;
			}
			catch(Exception e){
				ModelAndView model = new ModelAndView();
				String username = (String) session.getAttribute("USERNAME");
				lhandler.updateLoggedInFlag(username, 0);
				session.invalidate();
				model.setViewName("index");
				return model;
			}
		}
		else{
			ModelAndView model = new ModelAndView();
			String username = (String) session.getAttribute("USERNAME");
			lhandler.updateLoggedInFlag(username, 0);
			session.invalidate();
			model.setViewName("index");
			return model;
		}
	}
	//Submit Card Replacement
	@RequestMapping(value="/replaceCard",method=RequestMethod.POST)
	public ModelAndView submituserCardReplacement(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws SQLException
	{String role = (String) session.getAttribute("Role");
	ResultSet rs = null;
	if(role == null){
		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		return model;
	}
	else if(role.equals("MERCHANT") || role.equals("USER")){

		ModelAndView model = null;
		try
		{
			model = new ModelAndView();	
			String accountno="";
			String username=(String)session.getAttribute("USERNAME");
			//username="anishaallada";
			if(request.getParameter("submit")!=null)
			{
				accountno=request.getParameter("accountNumber");
				if(accountno.equals(""))
				{
					model.addObject("emptyFields", "Please select the account number");
					model.setViewName("Request.New.Card");
				}
				else 
				{
					requestCardHandler handler = new requestCardHandler();


					rs =  handler.getExsistingCardRequest(accountno);
					if(rs.next())
					{


						model.addObject("ExsistingUser", " You have already made the request for the card replacement");
						model.setViewName("Request.New.Card");

					}
					else 
					{


						handler.requestChangeCard(username,accountno);
						model.addObject("Successful", "Request for card replacement has been submitted sucessfully");
						logger.error(username +" has request new card for" + accountno);
						PaymentFormDataLoader handler1 = new PaymentFormDataLoader();
						model.addObject("bankaccounts", handler1.getAllAccountsForUserPayment(username));
						model.setViewName("Request.New.Card");


					}

				}
			}
			else
			{

				model.addObject("Successful","");
				model.setViewName("Request.New.Card");
			}

		}
		catch( Exception e )
		{
			e.printStackTrace();
			logger.error(e.getMessage());
			//ModelAndView model = new ModelAndView();
			String username = (String) session.getAttribute("USERNAME");
			lhandler.updateLoggedInFlag(username, 0);
			session.invalidate();
			model.setViewName("index");
			return model;
		}
		finally{
			try{
				if(rs!=null){
					rs.close();
				}
			}
			catch(Exception e){
				logger.error("Exception closing a resultset");
			}
		}
		return model;
	}
	else{
		ModelAndView model = new ModelAndView();
		String username = (String) session.getAttribute("USERNAME");
		lhandler.updateLoggedInFlag(username, 0);
		session.invalidate();
		model.setViewName("index");
		return model;
	}
	}


	//Authorize External User Account by System Manager
	@RequestMapping(value="/authorizeExternalUser",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView authorizeExternalUser(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws SQLException
	{
		String role = (String) session.getAttribute("Role");
		if(role == null){
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("MANAGER")){

			ModelAndView model = null;

			try
			{
				model = new ModelAndView();
				String regex = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";
				String ssn="";
				String accounttype="";

				if(request.getParameter("approve")!=null)
				{
					accounttype=request.getParameter("accounttype");

					ssn=request.getParameter("ssn");
					Pattern pattern = Pattern.compile(regex);
					Matcher matcher = pattern.matcher(ssn);
					//Check for validation
					if (!matcher.matches())
					{
						model.addObject("emptyFields", "SSN should be of format XXX-XXX-XXXX.\n The first three digits called the area number. \nThe area number cannot be 000, 666, or between 900 and 999.Digits four and five are called the group number and range from 01 to 99.\nThe last four digits are serial numbers from 0001 to 9999.");
						model.setViewName("System.Manager.Add.External.User");
					}
					else if(accounttype.equals("") || ssn.equals(""))
					{


						model.addObject("emptyFields", "Account type and SSN are mandatory fields");
						model.setViewName("System.Manager.Add.External.User");
					}
					else 
					{
						authorizeExtUserHandler handler = new authorizeExtUserHandler();
						ResultSet rs =  handler.getExsistingAccount(ssn,accounttype);
						ResultSet rs1 =  handler.getExsistingApprovedAccount(ssn,accounttype);
						if(!rs.next())
						{
							model.addObject("ExsistingUser", " Recepient with entered SSN and Account Type does not exists");
							model.setViewName("System.Manager.Add.External.User");



						}
						else if (rs1.next())
						{

							model.addObject("ExsistingUser", " The application has been already approved");
							model.setViewName("System.Manager.Add.External.User");
						}
						else {


							boolean flag = 	handler.approveUser(request.getParameter("ssn"),request.getParameter("accounttype"));
							if(flag == true)
							{model.addObject("Successful", "Application has been appoved");
							model.setViewName("System.Manager.Add.External.User");
							}
							else{
								model.addObject("Successful", "problem in approving application");
								model.setViewName("System.Manager.Add.External.User");
							}


						}

					}
				}
				else if (request.getParameter("Reject")!=null)
				{
					accounttype=request.getParameter("accounttype");
					ssn=request.getParameter("ssn");
					Pattern pattern = Pattern.compile(regex);
					Matcher matcher = pattern.matcher(ssn);
					//Check for validation
					if (!matcher.matches())
					{
						model.addObject("emptyFields", "SSN should be of format XXX-XXX-XXXX.\n The first three digits called the area number. \nThe area number cannot be 000, 666, or between 900 and 999.Digits four and five are called the group number and range from 01 to 99.\nThe last four digits are serial numbers from 0001 to 9999.");
						model.setViewName("System.Manager.Add.External.User");
					}
					else if(accounttype.equals("") || ssn.equals(""))
					{


						model.addObject("emptyFields", "Account type and SSN are mandatory fields");
						model.setViewName("System.Manager.Add.External.User");
					}
					else 
					{
						authorizeExtUserHandler handler = new authorizeExtUserHandler();
						ResultSet rs =  handler.getExsistingAccount(ssn,accounttype);
						if(!rs.next())
						{
							model.addObject("ExsistingUser", " Recepient with entered SSN and Account Type does not exsists");
							model.setViewName("System.Manager.Add.External.User");
						}
						else 
						{

							handler.rejectUser(request.getParameter("ssn"),request.getParameter("accounttype"));
							model.addObject("Successful", "Application has been rejected");
							model.setViewName("System.Manager.Add.External.User");


						}

					}
				}
				else
				{

					model.addObject("Successful","");
					model.setViewName("System.Manager.Add.External.User");
				}

			}
			catch( Exception e )
			{
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			return model;
		}
		else{
			ModelAndView model = new ModelAndView();
			String username = (String) session.getAttribute("USERNAME");
			lhandler.updateLoggedInFlag(username, 0);
			session.invalidate();
			model.setViewName("index");
			return model;
		}
	}
	@RequestMapping(value="/verifyExternalUser",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView authorizeExternalUser1(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws SQLException
	{	
		String role = (String) session.getAttribute("Role");
		ResultSet rs=null;
		ResultSet rs1 = null;
		if(role == null){
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("MANAGER")){
			ModelAndView model = null;

			try
			{
				model = new ModelAndView();
				model.setViewName("System.Manager.Account.Review");
				if(request.getParameter("approve")!=null)
				{
					rs1 =  handler.getExsistingApprovedAccount(ssn,accounttype);
					if (rs1.next())
					{
						model.addObject("ExsistingUser", " The application has been already approved");
						model.setViewName("System.Manager.Account.Review");
					}
					else {


						boolean flag =	handler.approveUser(ssn,accounttype);
						if(flag == true)
						{handler1.updateRequest(requestid,"APPROVE");
						model.addObject("Successful", "Application has been appoved");}
						else{
							handler1.updateRequest(requestid,"pending");
							model.addObject("Successful", "Application not appoved successfully");
						}
						model.setViewName("VerifyExternalUser");
					}
				}
				else if (request.getParameter("decline")!=null)
				{
					System.out.println("Inside Reject Statement");
					System.out.println("SSN: "+ssn);
					System.out.println("Account type: "+accounttype);
					{

						System.out.println("Else RS Next");
						handler.rejectUser(ssn,accounttype);
						handler1.updateRequest(requestid,"DECLINE");

						model.addObject("Successful", "Application has been rejected");
						model.setViewName("VerifyExternalUser");
					}
				}
				else{
					String ssnandaccounttype = request.getParameter("review");
					String[] values = new String[3];
					values = ssnandaccounttype.split("\\?");

					ssn = values[0];
					accounttype = values[1];
					requestid=values[2];

					rs =  handler.getExsistingAccount(ssn,accounttype);
					List<User> users = new ArrayList<User>();
					while(rs.next()){
						User temp = new User();
						temp.setssn(rs.getString("ssn"));
						temp.setaccounttype(rs.getString("accounttype"));
						temp.setusertype(rs.getString("usertype"));
						temp.setfirstname(rs.getString("firstname"));
						temp.setlastname(rs.getString("lastname"));
						temp.setaddress(rs.getString("address"));
						temp.setemail1(rs.getString("email"));
						temp.setphonenumber(rs.getString("phonenumber"));
						users.add(temp);
					}
				//	model = printWelcome(model,ssn,accounttype);
					model.setViewName("System.Manager.Account.Review");
					//downloadFiles(request,response, ssn);
					model.addObject("users", users);
				}
			}
			catch( Exception e )
			{
				e.printStackTrace();
				//ModelAndView model = new ModelAndView();
				String username = (String) session.getAttribute("USERNAME");
				lhandler.updateLoggedInFlag(username, 0);
				session.invalidate();
				model.setViewName("index");
				return model;
			}
			finally{
				try{
					if(rs!=null){
						rs.close();
					}
				}
				catch(Exception e){
					logger.error("Exception closing a resultset");
				}
				try{
					if(rs1!=null){
						rs1.close();
					}
				}
				catch(Exception e){
					logger.error("Exception closing a resultset");
				}
			}
			return model;
		}

		else{
			ModelAndView model = new ModelAndView();
			String username = (String) session.getAttribute("USERNAME");
			lhandler.updateLoggedInFlag(username, 0);
			session.invalidate();
			model.setViewName("index");
			return model;
		}
	}


	//Sayantan: File download controller
	@RequestMapping(value = "/ReviewAccountAppplication", method = RequestMethod.GET)
	public ModelAndView printWelcome(ModelAndView model, String ssn, String accounttype) {
		reviewExtUserHandler handler = new reviewExtUserHandler(); 
		model.addObject("users1", handler.reviewExtUser(ssn,accounttype));
		model.addObject("title", "All users in the database");
		model.addObject("message", "This is protected page!");
		model.setViewName("System.Manager.Account.Review");
		String directorypath="C:\\AccountOpeningDocuments\\"+ssn;
		File folder = new File(directorypath);
		File[] listOfFiles = folder.listFiles();
		if(listOfFiles!=null){

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				model.addObject("file1", listOfFiles[0].getName());
				//model.addAttribute("file1", listOfFiles[0].getName());

			}
		}
		}


		return model;
	}


	@RequestMapping(value = "/DownloadDocuments", method = RequestMethod.GET)
	public @ResponseBody void downloadFiles(HttpServletRequest request,
			HttpServletResponse response,HttpSession session, String ssn) {
		ssn = this.ssn;

		//Get file names
		String directorypath="C:\\AccountOpeningDocuments\\"+ssn;
		File folder = new File(directorypath);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) 
		{
			String filepath= directorypath +"\\"+ listOfFiles[i].getName();

			ServletContext context = request.getServletContext();
			File downloadFile = new File(filepath);
			FileInputStream inputStream = null;
			OutputStream outStream = null;



			try {
				inputStream = new FileInputStream(downloadFile);

				response.setContentLength((int) downloadFile.length());
				response.setContentType(context.getMimeType(filepath));			

				// response header
				String headerKey = "Content-Disposition";
				String headerValue = String.format("attachment; filename=\"%s\"",downloadFile.getName());
				response.setHeader(headerKey, headerValue);

				// Write response
				outStream = response.getOutputStream();
				IOUtils.copy(inputStream, outStream);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != inputStream)
						inputStream.close();
					if (null != inputStream)
						outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

	}

}