package com.sundevils.web.controller;


import handlers.adminHandlers.LoginHandler;
import handlers.adminHandlers.ModifyUsersHandler;
import handlers.adminHandlers.RequestAuthorize;
import handlers.adminHandlers.UnlockInternalAccountHandler;
import handlers.adminHandlers.ValidateUserhandler;
import handlers.adminHandlers.ViewUsersHandler;
import handlers.adminHandlers.transactionViewRequestHandler;
import handlers.adminHandlers.updateAllowHandler;
import handlers.employeeHandlers.CheckSourceAccountNumberHandler;
import handlers.employeeHandlers.CreateTransactionHandler;
import handlers.employeeHandlers.ViewAccounts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

import passwordSaltModules.SaltModule;
import utilities.CaptchaUtility;
import utilities.OtpUtility;
import utilities.TimeUtility;
import authentication.ModifyUser;

import com.user.info.AccountDetails;
import com.user.info.PersonalDetails;
import com.user.info.Transactions.TransactionDetails;
import com.user.info.Transactions.TransactionRequestDetails;

@Controller
public class TopController {
	private static final Logger LOG = Logger.getLogger(TopController.class);
	long startOtpTime = 0;
	long startEmailTime = 0;
	long startTime = 0;
	int count = 0;
	String otpGenerateTime;
	String otpEnterTime;
	String modelTime="2015/10/24 00:00:00";
	@RequestMapping(value = {"/","/welcome**" }, method = RequestMethod.GET)
	public ModelAndView welcomePage(HttpServletRequest request,HttpServletResponse response) throws IOException {

		ModelAndView model = new ModelAndView();
		try
		{
			model = new ModelAndView();
			request.getSession().setAttribute("Role", "");
			model.addObject("title", "Spring Security Custom Login Form");
			model.addObject("message", "This is welcome page!");
			model.setViewName("index");
			LOG.error("Welcome page accessed");
		}
		catch(Exception e){
			LOG.error("Issue while going to welcome page"+e.getMessage());
		}
		return model;

	}

	@RequestMapping(value = {"/findallproducts" }, method = RequestMethod.GET)
	public ModelAndView products(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelAndView model = new ModelAndView();
		model.setViewName("findallproducts");
		LOG.error("Find all products page accessed");
		return model;
	}

	@RequestMapping(value = {"/accountopening" }, method = RequestMethod.GET)
	public ModelAndView startForm(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelAndView model = new ModelAndView();
		model.setViewName("Account.Opening.Form");
		LOG.error("Account opening page accessed");
		return model;
	}

	@RequestMapping(value = {"/aboutus" }, method = RequestMethod.GET)
	public ModelAndView aboutus(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelAndView model = new ModelAndView();
		model.setViewName("aboutus");
		LOG.error("About us page accessed");
		return model;
	}

	@RequestMapping(value = {"/projects" }, method = RequestMethod.GET)
	public ModelAndView projects(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelAndView model = new ModelAndView();
		model.setViewName("projects");
		LOG.error("Projects page accessed");
		return model;
	}

	@RequestMapping(value = {"/team" }, method = RequestMethod.GET)
	public ModelAndView team(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelAndView model = new ModelAndView();
		model.setViewName("team");
		LOG.error("Team page accessed");
		return model;
	}

	@RequestMapping(value = {"/contact" }, method = RequestMethod.GET)
	public ModelAndView contact(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelAndView model = new ModelAndView();
		model.setViewName("contact");
		LOG.error("Contact us page accessed");
		return model;
	}

	@RequestMapping(value = {"/startbanking" }, method = RequestMethod.GET)
	public ModelAndView startbanking(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelAndView model = new ModelAndView();
		model.setViewName("startbanking");
		LOG.error("Start banking page accessed");
		return model;
	}

	@RequestMapping(value = {"**/changeaccount" }, method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView changeaccount(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {


		String role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("USER")||role.equals("MERCHANT")){
			ModelAndView model = new ModelAndView();
			if(request.getParameter("search")!=null){
				ModifyUsersHandler handler = new ModifyUsersHandler();
				String test = (String)handler.getaccounttypeHandler((String) request.getSession().getAttribute("USERNAME"),request.getParameter("accountnumber"));
				if (!(test.equals("Saving Account")||test.equals("Checking Account"))){
					model.addObject("status", "Invalid account");	
					model.setViewName("searchaccounttochange");
				}
				else{
					model.addObject("account",handler.getaccounttypeHandler((String) request.getSession().getAttribute("USERNAME"),request.getParameter("accountnumber")));
					model.addObject("managers", handler.requestManagers());
					model.addObject("accountnumber",request.getParameter("accountnumber"));
					model.setViewName("changeaccount");}
			}
			else{
				model.setViewName("searchaccounttochange");
			}

			return model;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			LoginHandler handler = new LoginHandler();
			String userName = (String)session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userName,0);
			session.invalidate();
			model.setViewName("index");
			return model;
		}

	}

	@RequestMapping(value = "/viewlogs", method = RequestMethod.GET)
	public ModelAndView printWelcome() {

		ModelAndView model = new ModelAndView();
		model.setViewName("viewlogs");
		return model;
	}


	@RequestMapping(value = "/logsaccess", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView accessLogs(HttpSession session) {

		String role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("ADMIN")){
			ModelAndView model = new ModelAndView();
			String directorypath="C:\\AppLogs\\";
			File folder = new File(directorypath);
			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					model.addObject("file1", listOfFiles[0].getName());
				}
			}
			model.setViewName("viewlogs");
			return model;
		}
		else{
			ModelAndView model = new ModelAndView();
			LoginHandler handler = new LoginHandler();
			String userName = (String)session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userName,0);
			session.invalidate();
			model.setViewName("index");
			return model;
		}
	}

	@RequestMapping(value = "/viewfile", method = RequestMethod.GET)
	public @ResponseBody void downloadFiles(HttpServletRequest request,
			HttpServletResponse response,HttpSession session, String ssn) {
		String directorypath="C:\\AppLogs\\";
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



	@RequestMapping(value = "/Home", method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView getHome(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException  {
		String role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else{
			try{
				ModelAndView model = new ModelAndView();
				if(role.equals("MANAGER")){
					model.setViewName("managerhome");
				}
				else if(role.equals("EMPLOYEE")){
					model.setViewName("employeehome");
				}
				else if(role.equals("ADMIN")){
					model.setViewName("admin");
				}
				else if(role.equals("MERCHANT")){
					model.setViewName("merchanthome");
				}
				else if(role.equals("USER")){
					model.setViewName("customerhome");
				}
				else if(role.equals("GOVERNMENT")){
					model.setViewName("governmenthome");
				}
				else{
					model.addObject("loggedIn", "User is already logged in to the other system");
					model.setViewName("login");
				}

				return model;
			}
			catch(Exception e){
				ModelAndView model = new ModelAndView();
				LoginHandler handler = new LoginHandler();
				String userName = (String)session.getAttribute("USERNAME");
				handler.updateLoggedInFlag(userName,0);
				session.invalidate();
				model.setViewName("index");
				return model;

			}
		}
	}

	@RequestMapping(value = {"**/reqchangeaccount" }, method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView reqchangeaccount(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		String role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}

		else if(role.equals("USER")||role.equals("MERCHANT")){
			ModelAndView model = new ModelAndView();
			ModifyUsersHandler handler = new ModifyUsersHandler();
			if (request.getParameter("accountchange")!=null){
				String Managername = "";
				ModifyUsersHandler handler1 = new ModifyUsersHandler();
				ResultSet rs1 = handler1.requestCountHandler();
				try {
					while(rs1.next())
					{
						Managername = rs1.getString("username");
						count = rs1.getInt("requestcount");
						count = count+1;
						handler.updateCountHandler(count, Managername);
						break;
					}
				} 
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.updateaccountrequest((String) request.getSession().getAttribute("USERNAME"),Managername,request.getParameter("accountnumber"));
				model.addObject("status", "Request Successfully Sent");
				model.setViewName("searchaccounttochange");

			}
			return model;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			LoginHandler handler = new LoginHandler();
			String userName = (String)session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userName,0);
			session.invalidate();
			model.setViewName("index");
			return model;
		}
	}

	@RequestMapping(value = {"/customerquery" },  method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView customerquery(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ModelAndView model = new ModelAndView();
		String query = request.getParameter("query");
		String email = request.getParameter("emailaddress");
		if(request.getParameter("submit")!=null){
			if(query.equals("") || email.equals("")){
				model.addObject("mandatory", "All fields are mandatory");
				model.setViewName("contact");
			}
			else{
				OtpUtility otp = new OtpUtility();
				otp.sendOtp(request, "thakkarharsh90@gmail.com",query,email);
				model.addObject("success", "An email has been sent to the support team. They will contact you within 3-5 working days.");
				model.setViewName("contact");
				LOG.error("Customer query is generated for user"+email);
			}
		}
		return model;
	}

	@RequestMapping(value = {"/transact" }, method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView transactPage(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		String role = (String)session.getAttribute("Role");	
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("EMPLOYEE"))
		{
			ModelAndView model = new ModelAndView();
			CreateTransactionHandler handler = new CreateTransactionHandler();
			String userName = "";
			String transamount = "";
			String sourceacc = "";
			String destacc = "";
			String type = "";

			if(request.getParameter("submit")!=null){
				userName = request.getParameter("username");
				transamount = request.getParameter("transamount");
				sourceacc = request.getParameter("sourceacc");
				destacc = request.getParameter("destacc");


				if(userName.isEmpty() || transamount.isEmpty() || sourceacc.isEmpty() || destacc.isEmpty())
				{
					model.addObject("success_msg", "Error: There are empty fields. Please rectify");
				}
				else if(!destacc.matches("[0-9]+$"))
					model.addObject("success_msg","Enter account number in proper format");
				else if(sourceacc.equals(destacc))
					model.addObject("success_msg","Source and destination account numbers can't be the same");
				else
				{
					CheckSourceAccountNumberHandler accounthandler = new CheckSourceAccountNumberHandler(); 
					String account_match_msg=(String) accounthandler.requestHandler(userName,sourceacc,transamount);
					if(account_match_msg.equals("done"))
						model.addObject("success_msg", handler.transactionHandler(userName,transamount,sourceacc,destacc,type));
					else if(account_match_msg.equals("incorrect"))
						model.addObject("success_msg","Incorrect username or source account no.");
					else if(account_match_msg.equals("negative"))
						model.addObject("success_msg","Enter postive transaction amount");
					else if(account_match_msg.equals("NFE"))
						model.addObject("success_msg","Number format is wrong");
					else
						model.addObject("success_msg","Insufficient balance for the transaction");	
				}

			}

			else
			{
				model.addObject("success_msg","");
			}	

			model.addObject("title", " Create Transaction");
			model.setViewName("create_transactions");
			return model;

		}
		else
		{
			ModelAndView model = new ModelAndView();
			LoginHandler handler = new LoginHandler();
			String userName = (String)session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userName,0);
			session.invalidate();
			model.setViewName("index");
			return model;
		}
	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Custom Login Form");
		model.addObject("message", "This is protected page!");
		model.setViewName("admin");

		return model;

	}

	@RequestMapping(value = "/viewusers", method = RequestMethod.GET)
	public ModelAndView managerPage() {

		ModelAndView model = new ModelAndView();
		ViewUsersHandler handler = new ViewUsersHandler(); 
		model.addObject("users", handler.requestHandler());
		model.addObject("title", "All users in the database");
		model.addObject("message", "This is protected page!");
		model.setViewName("viewusers");
		return model;

	}

	@RequestMapping(value = "**/modifyUs", method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView modifyUsersPage(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException  {
		String role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if (role.equals("EMPLOYEE")||role.equals("MANAGER")){
			ModelAndView model = null;
			try{

				String searchParameter = "";
				String deleteParameter="";
				String accountnumber="";
				String searchParameterType = "";

				model = new ModelAndView();	
				model.setViewName("modifyUsers");
				if (request.getParameter("delete")!= null){
					deleteParameter =request.getParameter("hiddenUser");
					accountnumber = request.getParameter("hiddenUserNumber");
					ModifyUsersHandler handler = new ModifyUsersHandler(); 
					handler.deleteRequestHandler(deleteParameter,accountnumber);
					searchParameter = request.getParameter("hiddenUser");
					searchParameterType = "UserName";

					model.addObject("users", handler.requestHandler(searchParameter,searchParameterType));
					model.addObject("title", "All users in the database");
					model.addObject("status", "User deleted Successfully");
					if (role.equalsIgnoreCase("EMPLOYEE")){
						model.setViewName("modifyUsersemployee");
					}

					else if (role.equalsIgnoreCase("MANAGER")){
						model.setViewName("modifyUsers");
					}

				}
				else if (request.getParameter("submit")!=null){
					if(request.getParameter("username").isEmpty()){
						model.addObject("status", "Invalid account");	
						model.setViewName("modifyUsers");

					}
					else{

						searchParameter = request.getParameter("username");
						searchParameterType = request.getParameter("searchcat");
						ModifyUsersHandler handler = new ModifyUsersHandler(); 
						ArrayList <ModifyUser> test= (ArrayList<ModifyUser>)handler.requestHandler(searchParameter,searchParameterType);
						if (test.size()!=0)
							model.addObject("users", test);
						else							
							model.addObject("status","No user present who satisifies the criteria");	

						model.addObject("title", "All users in the database");
						model.addObject("message", "This is protected page!");
						if (role.equalsIgnoreCase("EMPLOYEE")){
							model.setViewName("modifyUsersemployee");
						}

						else if (role.equalsIgnoreCase("MANAGER")){
							model.setViewName("modifyUsers");
						}
					}


				}

				else if (role.equalsIgnoreCase("EMPLOYEE")){
					model.setViewName("modifyUsersemployee");
				}

				else if (role.equalsIgnoreCase("MANAGER")){
					model.setViewName("modifyUsers");
				}


				else if ((request.getParameter("searchcat").equals("AccountNumber")|| request.getParameter("searchcat").equals("UserName"))&&(!request.getParameter("username").matches("[0-9 ]+"))){
					model.addObject("status", "Invalid Entry");	
					if (role.equalsIgnoreCase("EMPLOYEE")){
						model.setViewName("modifyUsersemployee");
					}

					else if (role.equalsIgnoreCase("MANAGER")){
						model.setViewName("modifyUsers");
					}
				}
				else if (request.getParameter("searchcat").equals("Name")&&(!request.getParameter("username").matches("[a-zA-Z]+(\\s+[a-zA-Z]+)*"))){

					model.addObject("status", "Invalid Entry");	
					if (role.equalsIgnoreCase("EMPLOYEE")){
						model.setViewName("modifyUsersemployee");
					}

					else if (role.equalsIgnoreCase("MANAGER")){
						model.setViewName("modifyUsers");
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			return model;}
		else
		{
			ModelAndView model = new ModelAndView();
			LoginHandler handler = new LoginHandler();
			String userName = (String)session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userName,0);
			session.invalidate();
			model.setViewName("index");
			return model;
		}

	}


	@RequestMapping(value = "**/reqModify", method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView reqPermissionPage(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException  {
		String role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if (role.equals("USER")||role.equals("MERCHANT"))	{
			ModelAndView model = null;
			try{
				String updateParameter = "";
				String ManagerName = "";
				String updateParameterType = "";
				int count = 0;
				model = new ModelAndView();	
				if (request.getParameter("submit")!=null){
					updateParameter = request.getParameter("newvalue");
					updateParameterType = request.getParameter("searchcat");
					if (request.getParameter("newvalue").isEmpty()){
						model.addObject("status", "Field is Empty");	
						model.setViewName("requestpermissionmodify");
					}
					else if ((request.getParameter("searchcat").equals("phonenumber")||request.getParameter("searchcat").equals("zip"))&&(!request.getParameter("newvalue").matches("[0-9]+$"))){

						model.addObject("status", "Invalid Entry");	
						model.setViewName("requestpermissionmodify");
					}

					else if ((request.getParameter("searchcat").equals("address")||request.getParameter("searchcat").equals("businesslicense"))&&(!request.getParameter("newvalue").matches("[\\p{Alnum}\\p{Punct}]*"))){

						model.addObject("status", "Invalid Entry");	
						model.setViewName("requestpermissionmodify");
					}
					else if (request.getParameter("searchcat").equals("email")&&(!request.getParameter("newvalue").matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))){

						model.addObject("status", "Invalid Entry");	
						model.setViewName("requestpermissionmodify");
					}
					else if ((request.getParameter("searchcat").equals("firstname")||request.getParameter("searchcat").equals("lastname")||request.getParameter("searchcat").equals("state"))&&(!request.getParameter("newvalue").matches("[a-zA-Z]+(\\s+[a-zA-Z]+)*"))){

						model.addObject("status", "Invalid Entry");	
						model.setViewName("requestpermissionmodify");
					}
					else{
						ModifyUsersHandler handler = new ModifyUsersHandler();
						ResultSet rs1 = handler.requestCountHandler();
						try {
							while(rs1.next())
							{
								ManagerName = rs1.getString("username");
								count = rs1.getInt("requestcount");
								count = count+1;
								handler.updateCountHandler(count, ManagerName);
								break;
							}
						} 
						catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						handler.requestModifyHandler((String) request.getSession().getAttribute("USERNAME"),updateParameter,updateParameterType,ManagerName);

						if(role.equalsIgnoreCase("USER")){
							model.addObject("status", "Request Successfully Sent");
							model.setViewName("requestpermissionmodify");
						}
						else if(role.equalsIgnoreCase("MERCHANT")){
							model.addObject("status", "Request Successfully Sent");
							model.setViewName("requestpermissionmodify");
						}
					}
				}
				else {
					ModifyUsersHandler handler = new ModifyUsersHandler();
					model.addObject("managers", handler.requestManagers());
					model.setViewName("requestpermissionmodify");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return model;}
		else
		{
			ModelAndView model = new ModelAndView();
			LoginHandler handler = new LoginHandler();
			String userName = (String)session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userName,0);
			session.invalidate();
			model.setViewName("index");
			return model;
		}
	}

	@RequestMapping(value = "**/viewReq", method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView viewPermissionPage(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException  {
		ModelAndView model = null;
		try{
			model = new ModelAndView();	
			if (request.getParameter("approve")!=null){
				ModifyUsersHandler handler = new ModifyUsersHandler();
				handler.updateRequestHandler(request.getParameter("newvalue"),request.getParameter("columnname"),request.getParameter("requestFrom"));
				model.setViewName("managerhome");
			}
			else if(request.getParameter("decline")!=null){
				ModifyUsersHandler handler = new ModifyUsersHandler();
				handler.declineRequestHandler(request.getParameter("newvalue"),request.getParameter("columnname"),request.getParameter("requestFrom"));
				model.setViewName("managerhome");
			}

			else {
				ModifyUsersHandler handler = new ModifyUsersHandler(); 
				model.addObject("requests", handler.viewRequestHandler((String) request.getSession().getAttribute("USERNAME")));
				model.addObject("title", "All users in the database");
				model.addObject("message", "This is protected page!");
				model.setViewName("viewpermissions");
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return model;

	}


	@RequestMapping(value = "/captcha" , method = RequestMethod.GET)
	public ModelAndView generateCaptcha(HttpServletRequest request,HttpServletResponse response) throws IOException{
		ModelAndView model = new ModelAndView();
		CaptchaUtility captcha = new CaptchaUtility();
		captcha.generateCaptcha(request,response);
		model.setViewName("login");
		LOG.error("Captcha generated successfully");
		return model;
	}

	@RequestMapping(value = {"**/login**"}, method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		ModelAndView model = new ModelAndView();
		ResultSet rs = null;
		try{
			boolean flag = false;
			String correct_time = "";
			String userName = "";
			String system_time = "";
			int web_sec = 0;
			int sys_sec = 0;
			correct_time = TimeUtility.generateSysDateMethod();
			system_time = TimeUtility.generateSysDateMethod();
			web_sec = TimeUtility.generateSysSecondsMethod();
			sys_sec = TimeUtility.generateSysSecondsMethod();
			session = request.getSession();
			if (!session.isNew()) {
				LOG.error("New session created");
			} else {
				model = new ModelAndView();
				LoginHandler handler = new LoginHandler(); 
				model.setViewName("index");
				if(userName!=null && !flag)
				{
					userName = (String)session.getAttribute("USERNAME");
					handler.updateLoggedInFlag(userName,0);
					session.invalidate();

					return model;
				}
			}

			if(correct_time.equals(system_time) && Math.abs(web_sec-sys_sec)<1800)
			{
				model= new ModelAndView();
				String password = "";
				String captchaData = "";
				String captchaString = "";
				if(request.getParameter("submit")!=null){
					userName = request.getParameter("username");
					password = request.getParameter("password");
					captchaData = request.getParameter("captcha");
					captchaString=(String)session.getAttribute("CAPTCHA");

					if(userName.equals("") || password.equals("") ||
							captchaData.equals("")){
						flag = true;
						session.setAttribute("FLAG", flag);
						model.addObject("emptyFields", "All fields are mandatory");
						model.setViewName("login");
					}

					else if(!captchaData.equals(captchaString)){
						flag = true;
						session.setAttribute("FLAG", flag);
						model.addObject("wrongCaptcha", "Please re-enter captcha");
						model.setViewName("login");
					}

					else{
						LoginHandler handler = new LoginHandler(); 
						rs = handler.requestLoginHandler(userName);
						if(rs.next()){
							String uName = rs.getString("username");
							String fName = rs.getString("firstname");
							String pass = rs.getString("usercurrentpassword");
							String role = rs.getString("usertype");
							int loggedIn = rs.getInt("isloggedin");
							String ispwdchange = rs.getString("ispasswordchange");
							int lock = rs.getInt("islocked");
							session.setAttribute("USERNAME", userName);
							if(lock == 0){
								boolean correctPass;
								if(ispwdchange.equals("1")){
									correctPass = password.equals(pass);
								}
								else{
									SaltModule saltPass = new SaltModule();
									correctPass = saltPass.isPasswordValid(password,pass);
								}
								if(uName.equals(userName) && correctPass){
									count = 0;
									if(loggedIn == 0){

										if(ispwdchange.equals("1"))
										{
											flag = false;
											session.setAttribute("FLAG", flag);
											model.addObject("user", userName);
											model.setViewName("resetpassword");
											return model;
										}
										handler.updateLoggedInFlag(userName,1);
										request.getSession().setAttribute("Role", role);
										if(role.equals("MANAGER")){
											flag = false;
											session.setAttribute("FLAG", flag);
											session.setAttribute("USERNAME", userName);
											request.getSession().setAttribute("Manager", fName);
											model.setViewName("managerhome");
										}
										else if(role.equals("EMPLOYEE")){
											flag = false;
											session.setAttribute("FLAG", flag);
											session.setAttribute("USERNAME", userName);
											request.getSession().setAttribute("Employee", fName);	
											model.setViewName("employeehome");
										}
										else if(role.equals("ADMIN")){
											flag = false;
											session.setAttribute("FLAG", flag);
											session.setAttribute("USERNAME", userName);
											request.getSession().setAttribute("Admin", fName);	
											model.setViewName("admin");
										}
										else if(role.equals("MERCHANT")){
											flag = false;
											session.setAttribute("FLAG", flag);
											session.setAttribute("USERNAME", userName);
											request.getSession().setAttribute("Merchant", fName);	
											model.setViewName("merchanthome");
										}
										else if(role.equals("USER")){
											flag = false;
											session.setAttribute("FLAG", flag);
											session.setAttribute("USERNAME", userName);
											request.getSession().setAttribute("User", fName);	
											model.setViewName("customerhome");
										}
										else if(role.equals("GOVERNMENT")){
											flag = false;
											session.setAttribute("FLAG", flag);
											session.setAttribute("USERNAME", userName);
											request.getSession().setAttribute("Government", fName);	
											model.setViewName("government");
										}
									}
									else{
										flag = true;
										session.setAttribute("FLAG", flag);
										model.addObject("loggedIn", "User is already logged in to the other system");
										model.setViewName("login");
									}
								}
								else{
									count++;
									if(count>2){
										flag = false;
										session.setAttribute("FLAG", flag);
										model.addObject("user", userName);
										handler.updateLockedFlag(userName,1);
										model.addObject("lock", "Your account has been locked. Please fill in the below details to make a request for unlock account.");
										model.setViewName("unlockaccount");
									}
									else{
										flag = true;
										session.setAttribute("FLAG", flag);
										model.addObject("wrongCredentials", "Username and Password do not match");
										model.setViewName("login");
									}
								}
							}
							else{
								flag = false;
								session.setAttribute("FLAG", flag);
								model.addObject("user", userName);
								model.addObject("lock", "Your account has been locked. Please fill in the below details to make a request for unlock account.");
								model.setViewName("unlockaccount");
							}
						}
						else{
							flag = true;
							session.setAttribute("FLAG", flag);
							model.addObject("wrongCredentials", "Username does not exist. Please enter correct username");
							model.setViewName("login");
						}
					}
				}
				else if(request.getParameter("imgCaptcha")!=null){
					flag = true;
					session.setAttribute("FLAG", flag);
					CaptchaUtility captcha = new CaptchaUtility();
					captcha.generateCaptcha(request,response);
					model.setViewName("login");
				}
				else{
					flag = false;
					session.setAttribute("FLAG", flag);
					LoginHandler handler;
					handler = new LoginHandler();
					String userSessionName = (String) session.getAttribute("USERNAME");
					handler.updateLoggedInFlag(userSessionName,0);
					model.setViewName("login");
				}
			}
			else
			{
				flag = false;
				session.setAttribute("FLAG", flag);
				model = new ModelAndView();
				model.addObject("Timetampering","System time is not updated");
				model.setViewName("login");
			}
		}
		catch(Exception e)
		{
			LoginHandler handler;
			handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			model.setViewName("login");
		}
		finally {
			try {
				if(rs!=null)
				{
					rs.close();
				}
			} catch (SQLException e) {
				LoginHandler handler;
				handler = new LoginHandler();
				String userSessionName = (String) session.getAttribute("USERNAME");
				handler.updateLoggedInFlag(userSessionName,0);
				model.setViewName("login");
			}
		}
		return model;
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET) 
	public ModelAndView formView() { 
		ModelAndView model = new ModelAndView();	
		model.setViewName("form"); return model; 
	} 

	@RequestMapping(value = "/readForm", method = {RequestMethod.POST, RequestMethod.GET}) 
	public ModelAndView formReader(HttpServletRequest request) { 
		ModelAndView model = new ModelAndView(); 
		if(request.getParameter("admin") != null) 
			model.addObject("userType", "Admin"); 
		else if(request.getParameter("user") != null) 
			model.addObject("userType", "User"); 
		Object obj = request.getParameter("userId"); 
		model.addObject("userId", obj); 
		model.setViewName("form"); return model; 
	}

	@RequestMapping(value = "**/logoutusers", method = {RequestMethod.POST, RequestMethod.GET}) 
	public ModelAndView logoutUser(HttpServletRequest request,HttpSession session) { 
		ModelAndView model = new ModelAndView();	
		LoginHandler handler;
		String userName = "";
		handler = new LoginHandler();
		userName=(String)session.getAttribute("USERNAME");
		handler.updateLoggedInFlag(userName,0);
		session.invalidate();
		model.setViewName("logout"); 
		return model;
	}

	/*else{
    	var xhttp = new XMLHttpRequest();
        xhttp.open("GET", "logoutusers", true);
        xhttp.send();
    }*/
	@RequestMapping(value = "/forgotusername", method = {RequestMethod.POST, RequestMethod.GET}) 
	public ModelAndView forgotUserName(HttpServletRequest request,HttpSession session) { 
		ModelAndView model = new ModelAndView();	
		LoginHandler handler = new LoginHandler();
		String userName = (String)session.getAttribute("USERNAME");
		if(userName!=null)
		{
			handler.updateLoggedInFlag(userName,0);
			session.invalidate();	
			model.setViewName("index"); 
			return model;
		}
		model.setViewName("forgotusername"); 
		return model;
	}	



	@RequestMapping(value = "/unlock", method = {RequestMethod.POST, RequestMethod.GET}) 
	public ModelAndView unlockUser(HttpServletRequest request,HttpSession session) { 
		ModelAndView model = new ModelAndView();	
		model.setViewName("unlockaccount"); 
		return model;
	}

	@RequestMapping(value = "/forgotpassword", method = {RequestMethod.POST, RequestMethod.GET}) 
	public ModelAndView forgotPassword(HttpServletRequest request,HttpSession session) { 
		ModelAndView model = new ModelAndView();
		LoginHandler handler = new LoginHandler();
		String userName = (String)session.getAttribute("USERNAME");
		if(userName!=null)
		{
			handler.updateLoggedInFlag(userName,0);
			session.invalidate();	
			model.setViewName("index"); 
			return model;
		}
		model.setViewName("forgotpassword"); 
		return model;
	}

	@RequestMapping(value = "/getusername", method = {RequestMethod.POST, RequestMethod.GET}) 
	public ModelAndView getUserName(HttpServletRequest request,HttpSession session) throws SQLException { 
		ModelAndView model = new ModelAndView();	
		LoginHandler handler;
		String emailAddress = "";
		handler = new LoginHandler();
		emailAddress=request.getParameter("emailAddress");
		if(emailAddress.equals("") || emailAddress == null)
		{
			model.addObject("emptyFields", "Please enter email address");
			model.setViewName("forgotusername");
		}
		else
		{
			ResultSet rs = handler.getUsername(emailAddress);
			String userName = "";
			OtpUtility otp = new OtpUtility(); 
			if(rs.next()){
				userName = rs.getString("username");
				otp.sendUserName(request, userName, emailAddress);
				model.addObject("successusername", "Your username has been mailed to you. Please check your inbox to get the username. You will be automatically redirected to login page within few seconds.");
				model.setViewName("success"); 
			}
			else{
				model.addObject("wrongemail", "Please enter correct email address");
				model.setViewName("forgotusername");
			}
		}	
		return model;
	}

	@RequestMapping(value = "/getpassword**", method = {RequestMethod.POST, RequestMethod.GET}) 
	public ModelAndView getPassword(HttpServletRequest request,HttpSession session) throws SQLException { 
		ModelAndView model = new ModelAndView();	
		LoginHandler handler;
		String emailAddress = "";
		String userName = "";
		handler = new LoginHandler();
		String user = "";
		String email = "";
		if(request.getParameter("submit")!=null)
		{
			emailAddress=request.getParameter("email");
			userName=request.getParameter("username");
			if(emailAddress.equals("") || userName.equals(""))
			{
				model.addObject("emptyFields", "All fields are mandatory");
				model.setViewName("forgotpassword");
			}

			else{
				ResultSet rs = handler.requestLoginHandler(userName);
				if(rs.next()){
					user = rs.getString("username");
					email = rs.getString("email");
					if(user.equals(userName) && email.equals(emailAddress)){
						session.setAttribute("USERNAME", userName);
						model.addObject("user", user);
						model.setViewName("resetpassword");
					}
					else{
						model.addObject("incorrectFields","Either username and/or email address is incorrect");
						model.setViewName("forgotpassword");
					}
				}
				else{
					model.addObject("incorrectFields","Either username and/or email address is incorrect");
					model.setViewName("forgotpassword");
				}
			}
		}
		return model;
	}

	@RequestMapping(value = "/updateAllow", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView updateAllow(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws SQLException {

		String role = (String)session.getAttribute("Role");
		ModelAndView model = null;
		ResultSet result_from = null;
		String[] deleteRequests = null;
		ResultSet result = null;
		String ViewRequests = null;
		try
		{
			if(role == null)
			{
				model = new ModelAndView();
				model.setViewName("index");
				return model;
			}
			else if(role.equals("MANAGER"))
			{
				String toUserName = "";
				String fromUserName = "";
				String firstname = "";
				String lastName = "";
				String middleName = "";
				String firstname_Recipient = "";
				String lastName_Recipient = "";
				String middleName_Recipient = "";
				String userType = "";
				String type = "";
				String status = "";
				String forUser = "";
				String email = "";
				fromUserName = (String)session.getAttribute("USERNAME");
				transactionViewRequestHandler handler_request = new transactionViewRequestHandler(); 
				model = new ModelAndView();
				List<TransactionRequestDetails> transReqstdetails=new ArrayList<TransactionRequestDetails>();
				if(request.getParameter("submitDelete")!=null)
				{
					model = new ModelAndView();
					deleteRequests = request.getParameterValues("check");
					if(deleteRequests ==  null)
					{
						model.setViewName("allowViewRequests");
						model.addObject("Select","No request selected");ResultSet rs = handler_request.getRequestHandler(fromUserName);
						try {
							while(rs.next())
							{
								TransactionRequestDetails view = new TransactionRequestDetails();
								view.setRequstID(rs.getString("requestid"));
								view.setRqstTo(rs.getString("requestto"));
								view.setRqstFrom(rs.getString("requestfrom"));
								view.setRqstFor(rs.getString("requestfor"));
								view.setRqstType(rs.getString("requesttype"));
								view.setRqstTime(rs.getString("requestdate"));
								view.setRqstStatus(rs.getString("requeststatus"));
								transReqstdetails.add(view);
							}
							model.addObject("requestDetails",transReqstdetails);
						} 
						catch (SQLException e) {
							LoginHandler handler_logout;
							handler_logout = new LoginHandler();
							String userSessionName = (String) session.getAttribute("USERNAME");
							handler_logout.updateLoggedInFlag(userSessionName,0);
							model.setViewName("login");
							LOG.error("Issue while accessing the requests"+e.getMessage());
						}
					}
					transactionViewRequestHandler handler = new transactionViewRequestHandler();
					handler.transactionDeleteHandler(deleteRequests);	
					return model;
				}
				if(request.getParameter("submitView")!=null)
				{
					model = new ModelAndView();
					ViewRequests = request.getParameter("radio");
					if(ViewRequests ==  null)
					{
						model.setViewName("allowViewRequests");
						model.addObject("SelectView","No User selected");
						model.setViewName("allowViewRequests");
						ResultSet rs = handler_request.getRequestHandler(fromUserName);
						try {
							while(rs.next())
							{
								TransactionRequestDetails view = new TransactionRequestDetails();
								view.setRequstID(rs.getString("requestid"));
								view.setRqstTo(rs.getString("requestto"));
								view.setRqstFrom(rs.getString("requestfrom"));
								view.setRqstFor(rs.getString("requestfor"));
								view.setRqstType(rs.getString("requesttype"));
								view.setRqstTime(rs.getString("requestdate"));
								view.setRqstStatus(rs.getString("requeststatus"));
								transReqstdetails.add(view);
							}
							model.addObject("requestDetails",transReqstdetails);
						} 
						catch (SQLException e) {
							LoginHandler handler_logout;
							handler_logout = new LoginHandler();
							String userSessionName = (String) session.getAttribute("USERNAME");
							handler_logout.updateLoggedInFlag(userSessionName,0);
							model.setViewName("login");
							LOG.error("Issue while accessing the requests"+e.getMessage());
						}
						return model;
					}
					transactionViewRequestHandler handler = new transactionViewRequestHandler();
					ResultSet rs = handler.transactionViewHandler(ViewRequests);
					try {
						while(rs.next())
						{
							TransactionRequestDetails view = new TransactionRequestDetails();
							view.setUserName(rs.getString("username"));
							view.setTransactionID(rs.getString("transactionid"));
							view.setTransactionAmount(rs.getString("transactionamount"));
							view.setSourceAccount(rs.getString("sourceaccountnumber"));
							view.setDestAccount(rs.getString("destinationaccountnumber"));
							view.setDateandTime(rs.getString("dateandtime"));
							view.setTransferType(rs.getString("transfertype"));
							view.setStatus(rs.getString("status"));
							transReqstdetails.add(view);
						}

						model.addObject("requestView",transReqstdetails);
						//request.setAttribute(", o);
					} 
					catch (SQLException e) {
						// TODO Auto-generated catch block
						LoginHandler handler_logout;
						handler_logout = new LoginHandler();
						String userSessionName = (String) session.getAttribute("USERNAME");
						handler_logout.updateLoggedInFlag(userSessionName,0);
						model.setViewName("login");
						LOG.error("Issue while accessing the requests"+e.getMessage());
					}
					model.setViewName("ViewTransactions");
					return model;
				}
				if(request.getParameter("submit")!=null){
					forUser = request.getParameter("username");
					userType = request.getParameter("userType");
					toUserName = request.getParameter("adminID");
					fromUserName = (String)session.getAttribute("USERNAME");
					ValidateUserhandler validate_Handler = new ValidateUserhandler();
					result = validate_Handler.ValidateHandler(forUser);
					result_from = validate_Handler.ValidateHandler(fromUserName);
					result_from.next();
					if (result.next())
					{
						ResultSet rsStatus = handler_request.getRequestStatusHandler(forUser,fromUserName);
						while(rsStatus.next())
						{
							if(rsStatus.getString("requeststatus").equals("Pending"))
							{
								status = rsStatus.getString("requeststatus");
								break;
							}
							else 
								status = rsStatus.getString("requeststatus");
						}
						type = result.getString("Usertype");
						email = result.getString("Email");
						firstname_Recipient = result.getString("Firstname");
						middleName_Recipient = result.getString("Middlename");
						lastName_Recipient = result.getString("Lastname");
						firstname = result_from.getString("Firstname");
						middleName = result_from.getString("Middlename");
						lastName = result_from.getString("Lastname");
						if (type.equals("USER"))
						{
							if(!status.equals("Pending"))
							{
								updateAllowHandler handler = new updateAllowHandler();
								if(toUserName.isEmpty())
								{
									toUserName = forUser;
									OtpUtility.sendEmailViewRequest(email,role,"",firstname, middleName,lastName,"","","","");
									String date = TimeUtility.generateSysDateMethod();
									handler.requestUpdateHandler(forUser,fromUserName,toUserName, "View",date);
								}
								else
								{
									ResultSet result_next = validate_Handler.ValidateHandler(toUserName);
									if(result_next.next())
									{
										type = result_next.getString("Usertype");
										email = result_next.getString("Email");
										OtpUtility.sendEmailViewRequest(email,role, fromUserName, firstname, middleName,lastName,forUser,firstname_Recipient,middleName_Recipient,lastName_Recipient);
										if (type.equals(userType))
										{
											String date = TimeUtility.generateSysDateMethod();
											handler.requestUpdateHandler(forUser,fromUserName,toUserName, "View",date);
										}
									}
									else
										model.addObject("AdminStatus", "Invalid Admin ID");
								}

							}
							else
							{
								model.addObject("Status", "User already has Pending request");	
							}
						}
						else
						{
							model.addObject("Validity", "Invalid Recipient");
						}
					}
					else
					{
						model.addObject("Validity", "Invalid Recipient");
					}
					//result.next();
				}
				ResultSet rs = handler_request.getRequestHandler(fromUserName);
				try {
					while(rs.next())
					{
						TransactionRequestDetails view = new TransactionRequestDetails();
						view.setRequstID(rs.getString("requestid"));
						view.setRqstTo(rs.getString("requestto"));
						view.setRqstFrom(rs.getString("requestfrom"));
						view.setRqstFor(rs.getString("requestfor"));
						view.setRqstType(rs.getString("requesttype"));
						view.setRqstTime(rs.getString("requestdate"));
						view.setRqstStatus(rs.getString("requeststatus"));
						transReqstdetails.add(view);
					}
					model.addObject("requestDetails",transReqstdetails);
				} 
				catch (SQLException e) {
					LoginHandler handler_logout;
					handler_logout = new LoginHandler();
					String userSessionName = (String) session.getAttribute("USERNAME");
					handler_logout.updateLoggedInFlag(userSessionName,0);
					model.setViewName("login");
					LOG.error("Issue while accessing the requests"+e.getMessage());
				}
				model.setViewName("allowViewRequests");

				return model;
			}
			else
			{
				model = new ModelAndView();
				LoginHandler handler = new LoginHandler();
				String userName = (String)session.getAttribute("USERNAME");
				handler.updateLoggedInFlag(userName,0);
				session.invalidate();
				model.setViewName("index");
				return model;
			}
		}
		catch (Exception e) {
			model = new ModelAndView();
			LoginHandler handler_logout;
			handler_logout = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler_logout.updateLoggedInFlag(userSessionName,0);
			model.setViewName("login");
			LOG.error("Issue while accessing the requests"+e.getMessage());
		}
		finally
		{
			if(result!=null)
			{
				result.close();
			}
			if(result_from!=null)
			{
				result_from.close();
			}
		}
		return model;

	}

	/* For regular employee to view account*/
	@RequestMapping(value = "/AccountView", method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView viewAccount(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException  {
		ModelAndView model = null;
		ResultSet rs = null;
		ResultSet rs_details = null;
		ResultSet rs_details_personal = null;
		String ViewDetails = "";
		String ViewUserSelect = null;
		String role = (String)session.getAttribute("Role");
		try
		{
			if(role == null)
			{
				model = new ModelAndView();
				model.setViewName("index");
				return model;
			}
			else if(role.equals("EMPLOYEE"))
			{
				try{

					model = new ModelAndView();	
					ViewAccounts handler = new ViewAccounts();
					List<AccountDetails> accountReqstdetails=new ArrayList<AccountDetails>();
					rs = handler.requestAccountHandler();
					try {
						while(rs.next())
						{
							AccountDetails view = new AccountDetails();
							view.setUserName(rs.getString("requestfrom"));
							accountReqstdetails.add(view);
						}

						model.addObject("accountView",accountReqstdetails);
						//request.setAttribute(", o);
					} 
					catch (SQLException e) {
						// TODO Auto-generated catch block
						LoginHandler handler_logout;
						handler_logout = new LoginHandler();
						String userSessionName = (String) session.getAttribute("USERNAME");
						handler_logout.updateLoggedInFlag(userSessionName,0);
						model.setViewName("index");
						LOG.error("Issue while viewing the account"+e.getMessage());
					}
					if(request.getParameter("submit")!=null)
					{
						ViewDetails = request.getParameter("Type");
						ViewUserSelect = request.getParameter("radio");
						if(ViewUserSelect == null)
						{
							model.addObject("Select","No User selected");
							model.setViewName("AccountDetails");
							return model;
						}
						List<AccountDetails> accountDetailsView=new ArrayList<AccountDetails>();
						if(ViewDetails.equals("Account"))
						{
							rs_details = handler.requestAccountDetailsHandler(ViewUserSelect);
							try {
								while(rs_details.next())
								{
									AccountDetails view = new AccountDetails();
									view.setUserNameAccount(rs_details.getString("username"));
									view.setAccountNumber(rs_details.getString("accountnumber"));
									view.setAccountType(rs_details.getString("accounttype"));
									view.setBalance(rs_details.getDouble("balance"));
									accountDetailsView.add(view);
								}
								model.addObject("AccountDetails","1");
								model.addObject("accountDetailsView",accountDetailsView);
								//request.setAttribute(", o);
							} 
							catch (SQLException e) {
								// TODO Auto-generated catch block
								LoginHandler handler_logout;
								handler_logout = new LoginHandler();
								String userSessionName = (String) session.getAttribute("USERNAME");
								handler_logout.updateLoggedInFlag(userSessionName,0);
								model.setViewName("index");
								LOG.error("Issue while getting the account details "+e.getMessage());
							}
						}
						if(ViewDetails.equals("Personal"))
						{
							rs_details_personal = handler.requestPersonalDetailsHandler(ViewUserSelect);
							List<PersonalDetails> personalDetailsView=new ArrayList<PersonalDetails>();
							try {
								while(rs_details_personal.next())
								{
									PersonalDetails view = new PersonalDetails();
									view.setFirstName(rs_details_personal.getString("firstname"));
									view.setLastName(rs_details_personal.getString("lastname"));
									view.setAddress(rs_details_personal.getString("address"));
									view.setGender(rs_details_personal.getString("gender"));
									view.setState(rs_details_personal.getString("state"));
									view.setZip(rs_details_personal.getString("zip"));
									view.setPhonenumber(rs_details_personal.getString("phonenumber"));
									view.setDob(rs_details_personal.getString("dateofbirth"));
									view.setEmail(rs_details_personal.getString("email"));
									personalDetailsView.add(view);
								}
								model.addObject("PersonalDetails","1");
								model.addObject("personalDetailsView",personalDetailsView);
								//request.setAttribute(", o);
							} 
							catch (SQLException e) {
								// TODO Auto-generated catch block
								LoginHandler handler_logout;
								handler_logout = new LoginHandler();
								String userSessionName = (String) session.getAttribute("USERNAME");
								handler_logout.updateLoggedInFlag(userSessionName,0);
								model.setViewName("index");
								LOG.error("Issue while getting the personal details "+e.getMessage());
							}
						}
					}
				}
				catch (Exception e) {
					LoginHandler handler_logout;
					handler_logout = new LoginHandler();
					String userSessionName = (String) session.getAttribute("USERNAME");
					handler_logout.updateLoggedInFlag(userSessionName,0);
					model.setViewName("index");
					LOG.error("Issue while getting the personal details "+e.getMessage());
					LOG.error("Issue while viewing the account"+e.getMessage());
				}

				model.setViewName("AccountDetails");
			}
			else
			{
				model = new ModelAndView();
				LoginHandler handler = new LoginHandler();
				String userName = (String)session.getAttribute("USERNAME");
				handler.updateLoggedInFlag(userName,0);
				session.invalidate();
				model.setViewName("index");
			}
		}
		catch (Exception e) {
			LoginHandler handler_logout;
			handler_logout = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler_logout.updateLoggedInFlag(userSessionName,0);
			model.setViewName("index");
			LOG.error("Issue while viewing the account"+e.getMessage());
		}
		finally {
			try {
				if(rs_details!=null)
				{
					rs_details.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(rs_details_personal!=null)
				{
					rs_details_personal.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = "/viewTransactions**",method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView viewTransactions(HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		String User = "";
		String role = "";
		User = request.getParameter("UserName");
		//User = (String)session.getAttribute("User");
		ModelAndView model = new ModelAndView();
		List<TransactionRequestDetails> transReqstdetails=new ArrayList<TransactionRequestDetails>();
		transactionViewRequestHandler handler = new transactionViewRequestHandler();
		role = (String)session.getAttribute("Role");
		if(role == null)
		{
			model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("MANAGER"))
		{
			ResultSet rs = handler.transactionViewHandler(User);
			try {
				while(rs.next())
				{
					TransactionRequestDetails view = new TransactionRequestDetails();
					view.setUserName(rs.getString("username"));
					view.setTransactionID(rs.getString("transactionid"));
					view.setTransactionAmount(rs.getString("transactionamount"));
					view.setSourceAccount(rs.getString("sourceaccountnumber"));
					view.setDestAccount(rs.getString("destinationaccountnumber"));
					view.setDateandTime(rs.getString("dateandtime"));
					view.setTransferType(rs.getString("transfertype"));
					view.setStatus(rs.getString("status"));
					transReqstdetails.add(view);
				}

				model.addObject("requestView",transReqstdetails);
			} 
			catch (Exception e) {
				model = new ModelAndView();
				LoginHandler handler_logout = new LoginHandler();
				String userName = (String)session.getAttribute("USERNAME");
				handler_logout.updateLoggedInFlag(userName,0);
				session.invalidate();
				model.setViewName("index");
				LOG.error("Issue while viewing the transactions"+e.getMessage());
			}	
			model.setViewName("ViewTransactions");
			return model;
		}
		else
		{
			model = new ModelAndView();
			LoginHandler handler_login = new LoginHandler();
			String userName = (String)session.getAttribute("USERNAME");
			handler_login.updateLoggedInFlag(userName,0);
			session.invalidate();
			model.setViewName("index");
			return model;
		}
	}

	@RequestMapping(value = "/unlockinternal**", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView unlockinternalPage(HttpServletRequest request,HttpServletResponse response,HttpSession session) {

		String role = (String)session.getAttribute("Role");		
		if(role!=null && role.equals("ADMIN"))
		{
			ModelAndView model = new ModelAndView();
			UnlockInternalAccountHandler handler = new UnlockInternalAccountHandler();
			model.addObject("unlock_msg","Here are pending unlock requests");
			String usrname="";		
			model.addObject("request_results",handler.readrequestHandler());
			if(request.getParameter("refresh")!=null)
			{
				model.addObject("request_results",handler.readrequestHandler());
			}
			if(request.getParameter("submit")!=null)
			{

				usrname=request.getParameter("username");
				if(usrname.isEmpty())
				{
					model.addObject("unlock_msg","Error empty field");
				}
				else
				{
					boolean res=(Boolean)handler.updaterequestHandler(usrname);
					LoginHandler lg = new LoginHandler();
					lg.updateLockedFlag(usrname, 0);
					if(res)
						model.addObject("unlock_msg",handler.requestHandler(usrname));
					else
						model.addObject("unlock_msg","No pending requests or incorrect username");	
				}

			}		

			model.addObject("title", "Unlock users");
			model.setViewName("unlockinternaluser");
			return model;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			LoginHandler handler = new LoginHandler();
			String userName = (String)session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userName,0);
			session.invalidate();
			model.setViewName("index");
			return model;
		}
	}

	@RequestMapping(value = "/unlockaccount**", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView unlockAccount(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws SQLException { 
		ModelAndView model = new ModelAndView();
		String userNameSession = (String) session.getAttribute("USERNAME");
		model.addObject("user", userNameSession);
		LoginHandler handler;
		handler = new LoginHandler();
		String emailAddress = "";
		String user = "";
		String ssnNumber = "";
		String admin = "";
		String ssn="";
		String email="";
		ViewAccounts acc = new ViewAccounts();
		if(request.getParameter("submit")!=null){

			ssnNumber=request.getParameter("ssn");
			emailAddress = request.getParameter("email");
			if(ssnNumber.equals("") || emailAddress.equals("")){
				model.addObject("emptyFields", "All fields are mandatory");
				model.setViewName("unlockaccount");
			}
			else{
				ResultSet rs = acc.requestPersonalDetailsHandler(userNameSession);
				ResultSet rs1 = handler.requestAdminHandler("ADMIN");
				ResultSet rs2 = handler.checkRequestExist(userNameSession,"unlock","pending");
				if(rs.next() && rs1.next()){
					if(!rs2.next()){
						user = rs.getString("username");
						email = rs.getString("email");
						ssn = rs.getString("ssn");
						admin = rs1.getString("username");
						if(email.equals(emailAddress) && ssn.equals(ssnNumber)){
							handler.insertUnlockRequests(user,"unlock", user, admin,"test", "pending","test","test");
							model.addObject("successunlock","Your request has been generated successfully. You will be notified via email when your account is ready for use. You will be automatically redirected to login page within few seconds.");
							model.setViewName("success");
						}
						else{
							model.addObject("incorrectFields","Either email address and/or ssn is incorrect");
							model.setViewName("unlockaccount");
						}
					}
					else{
						model.addObject("alreadypresent","You have already submitted the request. Please bear with us.");
						model.setViewName("unlockaccount");
					}
				}
				else{
					model.addObject("incorrectFields","Either email address and/or ssn is incorrect");
					model.setViewName("unlockaccount");
				}
			}
		}
		else{
			handler.updateLoggedInFlag(userNameSession,0);
			session.invalidate();
			model.setViewName("index");
		}
		return model;
	}

	@RequestMapping(value = "/authorizationRequest**" , method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView authRequest(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException, SQLException{
		String user="";
		String role = "";
		String requestType = "";
		String[] authRequests = null;
		role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("USER") || role.equals("ADMIN") || role.equals("MERCHANT"))
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("accessRequests");
			List<TransactionRequestDetails> transReqstdetails=new ArrayList<TransactionRequestDetails>();
			RequestAuthorize authorize = new RequestAuthorize();

			if(request.getParameter("submit")!=null)
			{
				authRequests = request.getParameterValues("check");
				if(authRequests == null)
				{
					model.addObject("Select","No user selected");
					if(role.equals("USER"))
					{
						user = (String)session.getAttribute("USERNAME");
					}
					else if(role.equals("ADMIN"))
					{
						user = (String)session.getAttribute("USERNAME");
					}
					ResultSet rs = authorize.getRequestHandler(user);
					try {
						while(rs.next())
						{
							TransactionRequestDetails view = new TransactionRequestDetails();
							view.setRequstID(rs.getString("requestid"));
							view.setRqstFrom(rs.getString("requestfrom"));
							view.setRqstTime(rs.getString("requestdate"));
							view.setRqstStatus(rs.getString("requeststatus"));
							view.setRqstFor(rs.getString("requestfor"));
							transReqstdetails.add(view);
						}

						model.addObject("requestApprove",transReqstdetails);
					} 
					catch (Exception e) {
						model = new ModelAndView();
						LoginHandler handler_logout = new LoginHandler();
						String userName = (String)session.getAttribute("USERNAME");
						handler_logout.updateLoggedInFlag(userName,0);
						session.invalidate();
						model.setViewName("index");
						LOG.error("Issue while authorizing the account"+e.getMessage());
					}
					return model;
				}
				requestType = request.getParameter("Type");
				authorize.updateRequestStatus(requestType, authRequests);
			}
			if(role.equals("USER"))
			{
				user = (String)session.getAttribute("USERNAME");
			}
			else if(role.equals("ADMIN"))
			{
				user = (String)session.getAttribute("USERNAME");
			}
			ResultSet rs = authorize.getRequestHandler(user);
			try {
				while(rs.next())
				{
					TransactionRequestDetails view = new TransactionRequestDetails();
					view.setRequstID(rs.getString("requestid"));
					view.setRqstFrom(rs.getString("requestfrom"));
					view.setRqstTime(rs.getString("requestdate"));
					view.setRqstStatus(rs.getString("requeststatus"));
					view.setRqstFor(rs.getString("requestfor"));
					transReqstdetails.add(view);
				}

				model.addObject("requestApprove",transReqstdetails);
			} 
			catch (Exception e) {
				model = new ModelAndView();
				LoginHandler handler_logout = new LoginHandler();
				String userName = (String)session.getAttribute("USERNAME");
				handler_logout.updateLoggedInFlag(userName,0);
				session.invalidate();
				model.setViewName("index");
				LOG.error("Issue while authorizing the account"+e.getMessage());
			}

			return model;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("login");
			return model;
		}
	}

	@RequestMapping(value = "/criticaltransaction" , method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView criticalTransaction(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException, SQLException{
		String role = "";
		String requestType = "";
		String[] authRequests = null;
		role = (String)session.getAttribute("Role");
		double balance = 0.0;
		boolean destinationFlag = true;
		boolean sourceFlag = true;
		double destinationAmount = 0.0;
		String destinationAccountNumber = "";
		double sourceAmount = 0.0;
		String sourceAccountNumber = "";
		Boolean status = false;
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("MANAGER"))
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("critical");
			List<TransactionDetails> transDetails=new ArrayList<TransactionDetails>();
			RequestAuthorize authorize = new RequestAuthorize();

			if(request.getParameter("submit")!=null)
			{
				authRequests = request.getParameterValues("check");
				requestType = request.getParameter("Type");
				if(authRequests!=null){
					balance = authorize.getBalance(authRequests);

					if(requestType.equals("Approve")){
						if(authRequests.length > 1)
							destinationFlag  = authorize.checkSameDestination(authRequests);
						if(destinationFlag){
							destinationAccountNumber = authorize.getDestinationAccount(authRequests[0]); 
							status = authorize.checkAccountNumber(destinationAccountNumber);
							if(status){
								destinationAmount = authorize.getDestinationBalance(destinationAccountNumber);
								authorize.approveTransaction(requestType,balance + destinationAmount, authRequests);
								model.addObject("success", "The approval of critical transaction/s is successfully done");
							}
							else{
								model.addObject("destinationerror","Destination account does not exist.");
							}
						}
						else{
							model.addObject("duplicateaccount","Transactions belonging to the same destination account should be done at a time while approving.");
						}
					}
					else{
						if(authRequests.length > 1)
							sourceFlag  = authorize.checkSameSource(authRequests);
						if(sourceFlag){
							sourceAccountNumber = authorize.getSourceAccount(authRequests[0]);
							status = authorize.checkAccountNumber(sourceAccountNumber);
							if(status){
								sourceAmount = authorize.getSourceBalance(sourceAccountNumber);
								authorize.rejectTransaction(requestType,balance + sourceAmount, authRequests);
								model.addObject("success", "The rejection of critical transaction/s is successfully done");
							}
							else{
								model.addObject("destinationerror","Destination account does not exist. Please delete the transaction");
							}
						}
						else{
							model.addObject("duplicatesourceaccount","Transactions belonging to the same source account should be done at a time while rejecting.");
						}
					}
				}
				else{
					model.addObject("check", "Please check atleast one checkbox to continue");
				}
			}

			ResultSet rs = authorize.getTransactionHandler("pendingapproval",10000,"PAYMENT");
			try {
				while(rs.next())
				{
					TransactionDetails view = new TransactionDetails();
					view.setUserName(rs.getString("username"));
					view.setTransactionId(rs.getString("transactionid"));
					view.setTransactionAmount(rs.getString("transactionamount"));
					view.setNewAmount(rs.getString("newamount"));
					view.setSourceAccount(rs.getString("sourceaccountnumber"));
					view.setDestAccount(rs.getString("destinationaccountnumber"));
					view.setDateandTime(rs.getString("dateandtime"));
					view.setTransferType(rs.getString("transfertype"));
					view.setStatus(rs.getString("status"));
					transDetails.add(view);
				}

				model.addObject("transactionApprove",transDetails);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}

			return model;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			LoginHandler handler = new LoginHandler();
			String userName = (String)session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userName,0);
			session.invalidate();
			model.setViewName("index");
			return model;
		}
	}

	@RequestMapping(value = "/approvetransaction" , method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView approveTransaction(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException, SQLException{
		String role = "";
		String requestType = "";
		String[] authRequests = null;
		role = (String)session.getAttribute("Role");
		double balance = 0.0;
		boolean destinationFlag = true;
		boolean sourceFlag = true;
		double destinationAmount = 0.0;
		String destinationAccountNumber = "";
		double sourceAmount = 0.0;
		String sourceAccountNumber = "";
		Boolean status = false;
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("EMPLOYEE"))
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("approve");
			List<TransactionDetails> transDetails=new ArrayList<TransactionDetails>();
			RequestAuthorize authorize = new RequestAuthorize();

			if(request.getParameter("submit")!=null)
			{
				authRequests = request.getParameterValues("check");
				requestType = request.getParameter("Type");
				if(authRequests!=null){
					balance = authorize.getBalance(authRequests);

					if(requestType.equals("Approve")){
						if(authRequests.length > 1)
							destinationFlag  = authorize.checkSameDestination(authRequests);
						if(destinationFlag){
							destinationAccountNumber = authorize.getDestinationAccount(authRequests[0]); 
							status = authorize.checkAccountNumber(destinationAccountNumber);
							if(status){
								destinationAmount = authorize.getDestinationBalance(destinationAccountNumber);
								authorize.approveTransaction(requestType,balance + destinationAmount, authRequests);
								model.addObject("success", "The approval of normal transaction/s is successfully done");
							}
							else{
								model.addObject("destinationerror","Destination account does not exist. Please delete the transaction");
							}
						}
						else{
							model.addObject("duplicateaccount","Transactions belonging to the same destination account should be done at a time while approving.");
						}
					}
					else{
						if(authRequests.length > 1)
							sourceFlag  = authorize.checkSameSource(authRequests);
						if(sourceFlag){
							sourceAccountNumber = authorize.getSourceAccount(authRequests[0]);
							status = authorize.checkAccountNumber(sourceAccountNumber);
							if(status){
								sourceAmount = authorize.getSourceBalance(sourceAccountNumber);
								authorize.rejectTransaction(requestType,balance + sourceAmount, authRequests);
								model.addObject("success", "The rejection of normal transaction/s is successfully done");
							}
							else{
								model.addObject("destinationerror","Destination account does not exist. Please delete the transaction");
							}

						}
						else{
							model.addObject("duplicatesourceaccount","Transactions belonging to the same source account should be done at a time while rejecting.");
						}
					}
				}
				else{
					model.addObject("check", "Please check atleast one checkbox to continue");
				}
			}

			ResultSet rs = authorize.getModDelHandler("pendingapproval","PAYMENT",10000);
			try {
				while(rs.next())
				{
					TransactionDetails view = new TransactionDetails();
					view.setUserName(rs.getString("username"));
					view.setTransactionId(rs.getString("transactionid"));
					view.setTransactionAmount(rs.getString("transactionamount"));
					view.setNewAmount(rs.getString("newamount"));
					view.setSourceAccount(rs.getString("sourceaccountnumber"));
					view.setDestAccount(rs.getString("destinationaccountnumber"));
					view.setDateandTime(rs.getString("dateandtime"));
					view.setTransferType(rs.getString("transfertype"));
					view.setStatus(rs.getString("status"));
					transDetails.add(view);
				}

				model.addObject("transactionApprove",transDetails);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}

			return model;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			LoginHandler handler = new LoginHandler();
			String userName = (String)session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userName,0);
			session.invalidate();
			model.setViewName("index");
			return model;
		}
	}

	@RequestMapping(value = "/moddeltransaction" , method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView modifyDeleteTransaction(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException, SQLException{
		String role = "";
		String requestType = "";
		String[] authRequests = null;
		Double newAmount = 0.0;
		String transactionAmount = "";
		role = (String)session.getAttribute("Role");
		double amount = 0.0;
		double balance = 0.0;
		String accountNumber = "";
		double sourceAmount = 0.0;
		String sourceAccountNumber = "";
		boolean sourceFlag = true;
		Boolean status = true;
		Boolean statusSource = true;
		String transactionType="";
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		if(role.equals("EMPLOYEE"))
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("moddeltransaction");
			List<TransactionDetails> transDetails=new ArrayList<TransactionDetails>();
			RequestAuthorize authorize = new RequestAuthorize();

			try{
				if(request.getParameter("submit")!=null)
				{
					authRequests = request.getParameterValues("check");
					requestType = request.getParameter("Type");
					if(authRequests!=null){
						if(requestType.equals("Modify")){

							if(authRequests.length > 1)
								model.addObject("multiplemodify","Please check only one transaction while modifying.");
							else{
								newAmount = Double.parseDouble(request.getParameter(authRequests[0]));
								transactionAmount = (String) session.getAttribute("AMOUNT");
								Double difference = newAmount - Double.parseDouble(transactionAmount);
								if(newAmount > 0 || !newAmount.toString().matches("[0-9]+$")){
									accountNumber = authorize.getDestinationAccount(authRequests[0]); 
									sourceAccountNumber = authorize.getSourceAccount(authRequests[0]);
									transactionType=authorize.getTransactionType(authRequests[0]);
									statusSource = authorize.checkAccountNumber(sourceAccountNumber);
									status = authorize.checkAccountNumber(accountNumber);

									if(transactionType.equalsIgnoreCase("debit")) status=true;
									if(transactionType.equalsIgnoreCase("credit"))statusSource=true;

									if(status && statusSource){
										amount = authorize.getDestinationBalance(accountNumber);
										if(!transactionType.equalsIgnoreCase("credit")){
											sourceAmount = authorize.getSourceBalance(sourceAccountNumber);
										}
										else{
											sourceAmount=difference+1;
										}

										if(!(sourceAmount >= difference)){
											model.addObject("greatervalue","Insufficint funds in the account");
										}
										else{
											authorize.approveModifySourceTransaction(newAmount,"approvedmodify",sourceAmount - difference,authRequests);
											authorize.approveModifyTransaction(newAmount,"approvedmodify",newAmount + amount,authRequests);
											model.addObject("success", "The modifcation of normal transaction/s is successfully done");
										}
									}
									else{
										model.addObject("destinationerror","Destination account does not exist. Please delete the transaction");
									}
								}
								else{
									model.addObject("zeroerror", "Amounts should be positive");
								}
							}
						}
						else if(requestType.equals("Delete")){
							balance = authorize.getBalance(authRequests);
							if(authRequests.length > 1)
								sourceFlag  = authorize.checkSameSource(authRequests);
							if(sourceFlag){
								sourceAccountNumber = authorize.getSourceAccount(authRequests[0]);
								status = authorize.checkAccountNumber(sourceAccountNumber);
								if(status){
									sourceAmount = authorize.getSourceBalance(sourceAccountNumber);
									authorize.rejectTransaction("approveddelete",balance + sourceAmount, authRequests);
									authorize.deleteTransaction(authRequests);
									model.addObject("success", "The deletion of normal transaction/s is successfully done");
								}
								else{
									model.addObject("destinationerror","Destination account does not exist. Please delete the transaction");
								}
							}
						}
					}
					else{
						model.addObject("select","Please select atleast one line item to continue");
					}
				}
			}
			catch(Exception e){
				model.addObject("zeroerror", "Amounts should include numbers only");
			}


			ResultSet rs = authorize.getModDelHandler("pendingapproval","PAYMENT",10000);
			try {
				while(rs.next())
				{
					TransactionDetails view = new TransactionDetails();
					view.setUserName(rs.getString("username"));
					view.setTransactionId(rs.getString("transactionid"));
					view.setTransactionAmount(rs.getString("transactionamount"));
					session.setAttribute("AMOUNT", rs.getString("transactionamount"));
					view.setNewAmount(rs.getString("newamount"));
					view.setSourceAccount(rs.getString("sourceaccountnumber"));
					view.setDestAccount(rs.getString("destinationaccountnumber"));
					view.setDateandTime(rs.getString("dateandtime"));
					view.setTransferType(rs.getString("transfertype"));
					view.setStatus(rs.getString("status"));
					transDetails.add(view);
				}

				model.addObject("transactionApprove",transDetails);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}

			return model;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
	}

}