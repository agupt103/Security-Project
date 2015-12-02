package com.sundevils.web.controller;

import gunpreetPackage.BusinessLogic.CheckAuthentication;
import gunpreetPackage.BusinessLogic.SendEmail;
import gunpreetPackage.Dao.JdbcConnection_tbl_user_authentication;
import gunpreetPackage.Model.InternalUser;
import gunpreetPackage.Model.PIIShow;
import gunpreetPackage.Model.requestPII;
import gunpreetPackage.Model.userDetails;
import handlers.adminHandlers.LoginHandler;
import handlers.adminHandlers.PasswordChangeHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import utilities.GenerateRSAKeys;
import databaseEncryptionModules.EncDecModule;

@Controller
public class ControllerClass {
	
	private static final Logger LOG = Logger.getLogger(ControllerClass.class);
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


	PIIShow show = new PIIShow();
	ArrayList<PIIShow> showList = new ArrayList<PIIShow>();
	
	public static boolean validate(String emailStr) 
	{
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
	}

	@RequestMapping("/reset")
	public ModelAndView resetPasswordFunc(HttpSession session)
	{
		String role = "";
		role = (String)session.getAttribute("Role");		
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			LoginHandler handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			return model;
		}
		else if(role.equals("ADMIN") || role.equals("MANAGER") || role.equals("EMPLOYEE") || role.equals("USER")|| role.equals("MERCHANT") && role.equals("GOVERNMENT"))
		{
			ModelAndView modelandview = new ModelAndView("resetpassword");
			try 
			{
				
				String userName = (String)session.getAttribute("USERNAME");
				modelandview.addObject("user", userName);
				
			} 
			catch (Exception e) 
			{				
				LOG.error("Error with reseting the password: "+e.getMessage());
				ModelAndView model = new ModelAndView();
				
				LoginHandler handler = new LoginHandler();
				String userSessionName = (String) session.getAttribute("USERNAME");
				handler.updateLoggedInFlag(userSessionName,0);
				model.setViewName("index");
				return model;
			}
			return modelandview;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			LoginHandler handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			model.setViewName("index");
			return model;
		}
		
	}	

	@RequestMapping(value="/resetButton", method = RequestMethod.POST)
	public ModelAndView reset(
			@RequestParam (value="new") String newpassword,
			@RequestParam (value="confirm") String confirmpassword,HttpSession session) throws ClassNotFoundException{

		String userName = (String)session.getAttribute("USERNAME");
		ModelAndView model = new ModelAndView();
		model.addObject("user", userName);
		CheckAuthentication auth= new CheckAuthentication();
		if(newpassword.equals("") || confirmpassword.equals("")){
			model.addObject("emptyFields", "All fields are mandatory");
			model.setViewName("resetpassword");
		}
		else{
			String result = auth.check(userName, newpassword, confirmpassword);
			if(!result.equals("")){
				model.addObject("errormessage", result);
				model.setViewName("resetpassword");
			}
			else{
				PasswordChangeHandler handler = new PasswordChangeHandler();
				handler.requestHandler(userName);
				LoginHandler loginHandler;
				loginHandler = new LoginHandler();
				loginHandler.updateLoggedInFlag(userName,0);
				model.addObject("successpassword", "Your password has been successfully changed.  You will be automatically redirected to login page within few seconds.");
				model.setViewName("success");
			}
		}
		return model;
	}	

	@RequestMapping(value="/showInternal",method = RequestMethod.GET)
	public ModelAndView ShowInternalUsers(HttpSession session) throws SQLException 
	{
		String role = "";
		role = (String)session.getAttribute("Role");
		ResultSet resultset = null;
		JdbcConnection_tbl_user_authentication objConn = new JdbcConnection_tbl_user_authentication();
		ArrayList<InternalUser> list = new ArrayList<InternalUser>();
		
			if(role == null)
			{
				ModelAndView model = new ModelAndView();
				LoginHandler handler = new LoginHandler();
				String userSessionName = (String) session.getAttribute("USERNAME");
				handler.updateLoggedInFlag(userSessionName,0);
				model.setViewName("index");
				return model;
			}
			else if(role.equals("ADMIN"))
			{
				ModelAndView modelandview = new ModelAndView();
				try 
				{
					resultset = objConn.get_Internal_Users_Details();	
					if(resultset.next())
					{
						resultset.beforeFirst();
						while(resultset.next())
						{
							InternalUser user = new InternalUser();
							String name = new String(resultset.getBytes(1), "UTF-8");
							user.setUsername(name);
							String firstname = new String(resultset.getBytes(2), "UTF-8");
							user.setFirstname(firstname);
							String lastname = new String(resultset.getBytes(3), "UTF-8");
							user.setLastname(lastname);
							list.add(user);							
						}
						modelandview.addObject("list",list);
						modelandview.setViewName("deleteInternalUser");
						LOG.error("Generating the list of internal users to delete" + list );	
						resultset.close();
					}
					else
					{
						modelandview.addObject("nouser","No internal users found!");
						modelandview.setViewName("deleteInternalUser");
						resultset.close();
						
					}					
				}
				catch (Exception e) 
				{
					LOG.error("Error with viewing internal users: "+e.getMessage());
					ModelAndView m = new ModelAndView();
					LoginHandler handler = new LoginHandler();
					String userSessionName = (String) session.getAttribute("USERNAME");
					handler.updateLoggedInFlag(userSessionName,0);
					m.setViewName("index");
					return m;
				}				
				return modelandview;								
			}
			else
			{
				ModelAndView model = new ModelAndView();
				model.setViewName("index");
				LoginHandler handler = new LoginHandler();
				String userSessionName = (String) session.getAttribute("USERNAME");
				handler.updateLoggedInFlag(userSessionName,0);
				return model;
			}			
	} 

	@RequestMapping(value="/piirequest", method = RequestMethod.GET)
	public ModelAndView showRequest(HttpSession session) 
	{
		String role = "";
		role = (String)session.getAttribute("Role");
		ModelAndView modelandview = new ModelAndView();	
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			LoginHandler handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			model.setViewName("index");
			return model;
		}
		else if(role.equals("ADMIN"))
		{
			ResultSet resultset = null;
			ResultSet rs = null;
			ArrayList<requestPII> piilist = new ArrayList<requestPII>();
			ArrayList<userDetails> userlist = new ArrayList<userDetails>();
			showList = new ArrayList<PIIShow>();
			JdbcConnection_tbl_user_authentication objConn = new JdbcConnection_tbl_user_authentication();
			
			try 
			{	EncDecModule encrypter = new EncDecModule();
				resultset = objConn.get_PII();	
				if(resultset.next())
				{
					resultset.beforeFirst();
					while(resultset.next())
					{
						requestPII requestObj = new requestPII();
						String pid = resultset.getString("pid");
						String gusername = resultset.getString("username");						
						String column = new String(encrypter.decrypt(resultset.getBytes("requesttype")));
						String value = new String(encrypter.decrypt(resultset.getBytes("requestdetails")));
						requestObj.setGovusername(gusername);
						requestObj.setPid(pid);
						requestObj.setColumnname(column);
						requestObj.setColumnvalue(value);				
						piilist.add(requestObj);				
					}			
					for(int i=0; i<piilist.size(); i++)
					{
						String columnname = piilist.get(i).getColumnname();
						String columnvalue = piilist.get(i).getColumnvalue();				
						rs = objConn.get_UserDet(columnname, columnvalue);
						userDetails userObj = new userDetails();
						if(rs.next())
						{
							rs.beforeFirst();
							while(rs.next())
							{
								
								userObj.setUsername(rs.getString(1));
								userObj.setFirstname(rs.getString(2));
								userObj.setLastname(rs.getString(3));
								userObj.setEmail(rs.getString(4));
								userObj.setPhone(rs.getString(5));
								userObj.setSsn(rs.getString(6));
								userObj.setAddress(rs.getString(7));
								userObj.setAccountnumber(rs.getString(8));
								userlist.add(userObj);
								
							}
							rs.close();
						}
						else
						{
							rs.close();
							userObj.setUsername("");
							userObj.setFirstname("");
							userObj.setLastname("");
							userObj.setEmail("");
							userObj.setPhone("");
							userObj.setSsn("");
							userObj.setAddress("");
							userObj.setAccountnumber("");
							userlist.add(userObj);
						}									
					}
				}
				else
				{
					resultset.close();
					modelandview.addObject("norequest","No pii requests found!");
					modelandview.setViewName("piiRequests");
				}
							
			}
			catch (Exception e) 
			{
				LOG.error("Error with showing pii request: "+e.getMessage());
				ModelAndView m = new ModelAndView();
				m.setViewName("index");
				LoginHandler handler = new LoginHandler();
				String userSessionName = (String) session.getAttribute("USERNAME");
				handler.updateLoggedInFlag(userSessionName,0);
				return m;
			}			
			if(piilist.size()>0)
			{
				for(int x =0; x<piilist.size();x++)
				{
					try 
					{
						show = new PIIShow();
						show.setPid(piilist.get(x).getPid());
						show.setUsername(userlist.get(x).getUsername());
						show.setFirstname(userlist.get(x).getFirstname());
						show.setLastname(userlist.get(x).getLastname());
						show.setEmail(userlist.get(x).getEmail());
						show.setPhonenumber(userlist.get(x).getPhone());
						show.setAddress(userlist.get(x).getAddress());
						show.setAccountnumber(userlist.get(x).getAccountnumber());
						showList.add(show);
					} 
					catch (Exception e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
				}	
			}			
			modelandview.addObject("showList",showList);
			modelandview.setViewName("piiRequests");
			LOG.error("Generating the list of PII requests for admin " + showList );
			return modelandview;	
		}
		else
		{
			ModelAndView model = new ModelAndView();
			LoginHandler handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			model.setViewName("index");
			return model;
		}			
	}
	
	@RequestMapping(value="/delInternalUser", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView delUsers(HttpServletRequest request,HttpSession session) 
	{		
		String role = "";
		role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			LoginHandler handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			return model;
		}
		else if(role.equals("ADMIN"))
		{
			ModelAndView modelandview = new ModelAndView("deleteInternalUser");
			ArrayList<String> list = new ArrayList<String>();
			try
			{
				String[] delList = request.getParameterValues("cblist");				
				if(delList!=null)
				{
					for (int i = 0; i < delList.length; i++) 
					{
					    if (delList[i] != null)
					    {
					    	list.add(delList[i]);
					    }	    
					} 
					JdbcConnection_tbl_user_authentication objConn = new JdbcConnection_tbl_user_authentication();
					objConn.delete_Internal_Users(list);
					objConn.delete_Internal_Auth(list);
					modelandview = ShowInternalUsers(session);
					modelandview.addObject("delinternal", "Internal User Deleted");
				}
				else
				{
					modelandview.addObject("notchecked", "Please check users");
					modelandview = ShowInternalUsers(session);
				}
				
			}
			catch(Exception e)
			{
				LOG.error("Error with deleting internal user: "+e.getMessage());
				ModelAndView m = new ModelAndView();
				m.setViewName("index");
				LoginHandler handler = new LoginHandler();
				String userSessionName = (String) session.getAttribute("USERNAME");
				handler.updateLoggedInFlag(userSessionName,0);
				return m;
			}
			LOG.error("Generating view to delete internalusers");
			return modelandview;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			LoginHandler handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			return model;
		}		
	}

	@RequestMapping(value="/updatepiistatus", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView updatePIIStatus(HttpServletRequest request,HttpSession session) 
	{
		String role = "";
		role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			LoginHandler handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			return model;
		}
		else if(role.equals("ADMIN"))
		{
			String gemail ="";
			ArrayList<String> pidlist = new ArrayList<String>();
			ArrayList<String> emaillist = new ArrayList<String>();
			ModelAndView modelandview = new ModelAndView("piiRequests");
			try
			{
				String[] checkList = request.getParameterValues("checkList");
				String type = request.getParameter("Type");
				
				if(checkList!=null)
				{
					for (int i = 0; i < checkList.length; i++) 
					{
					    if (checkList[i] != null)
					    {
					    	pidlist.add(checkList[i]);
					    }	    
					} 
					JdbcConnection_tbl_user_authentication objConn = new JdbcConnection_tbl_user_authentication();
					objConn.updatePIIStatus(pidlist);
					emaillist = objConn.getEmailList(pidlist);					
					
					
					for(int j =0;j<pidlist.size();j++)
					{
						String id = pidlist.get(j);
						gemail = emaillist.get(j);
						
						for(int k=0; k<showList.size();k++)
						{
							if(id.equalsIgnoreCase(showList.get(k).getPid()))
							{
								SendEmail send = new SendEmail();
								if(type.equalsIgnoreCase("Approve"))
								{
									send.sendEmail(gemail,showList.get(k).getUsername(),showList.get(k).getAccountnumber(),
														 showList.get(k).getFirstname(),showList.get(k).getLastname(),
														 showList.get(k).getEmail(),showList.get(k).getPhonenumber(),showList.get(k).getAddress());
									break;
								}
								else
								{
									send.sendRejectEmail(gemail);
									break;
								}								
							}
						}
					}
					modelandview.addObject("sent", "Email sent to users!");
					modelandview = showRequest(session);						
				}
				else
				{
					modelandview = showRequest(session);
				}		
				
			}
			catch(Exception e)
			{
				LOG.error("Error with updating pii status: "+e.getMessage());
				ModelAndView m = new ModelAndView();
				m.setViewName("index");
				LoginHandler handler = new LoginHandler();
				String userSessionName = (String) session.getAttribute("USERNAME");
				handler.updateLoggedInFlag(userSessionName,0);
				return m;
			}
			LOG.error("Updating the PII status from PII_REQUESTED to COMPLETED and sending email to government agency: "+ gemail);
			return modelandview;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			LoginHandler handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			return model;
		}		
	}
	
	@RequestMapping(value="/addg")
	public ModelAndView addGovernment(HttpSession session)
	{
		String role = "";
		role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			LoginHandler handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			return model;
		}
		else if(role.equals("ADMIN"))
		{
			ModelAndView modelandview = new ModelAndView("addGovernmentAgency");		
			return modelandview;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			LoginHandler handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			return model;
		}		
	}

	
	@RequestMapping(value="/addgovernmentagency", method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView addGovernmentAgency(
			@RequestParam (value="agencyname") String agencyname,
			@RequestParam (value="agencyid") String agencyid,
			@RequestParam (value="agencyemail") String agencyemail,HttpSession session) throws Exception	
	{
		ResultSet rs =null;			
		String role = "";
		role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			LoginHandler handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			return model;
		}
		else if(role.equals("ADMIN"))
		{					
			String pwd = "";
			ModelAndView modelandview = new ModelAndView("addGovernmentAgency");
			try 
			{
				if(!agencyname.matches("^[A-z]+$"))
				{
					modelandview.addObject("agencyname","Invalid agency name!");
				}
				if(!agencyid.matches("^[0-9]+$"))
				{
					modelandview.addObject("agencyid","Invalid agency id!");
				}
				if(!validate(agencyemail))
				{
					modelandview.addObject("agencyemail","Invalid email format!");
				}
				if(agencyname.equalsIgnoreCase("")  || agencyid.equalsIgnoreCase("") || agencyemail.equalsIgnoreCase(""))
				{
					modelandview.addObject("required","Please submit all required fields");
				}
				
				if(agencyname.matches("^[A-z]+$") && agencyid.matches("^[0-9]+$") && validate(agencyemail) && !agencyid.equalsIgnoreCase(""))
				{						
						JdbcConnection_tbl_user_authentication objConn = new JdbcConnection_tbl_user_authentication();	
						rs = objConn.checkgovernmentagency(agencyname, agencyid, agencyemail);
						if(rs.next())
						{
							modelandview.addObject("invalid","government user already present!");
							rs.close();
						}
						else
						{
							rs.close();
							GenerateRSAKeys rsa = new GenerateRSAKeys();
							pwd = rsa.generateEncryptionString(4);
							objConn.addusername(agencyid,pwd);
							objConn.addgovernmentagency(agencyname, agencyid, agencyemail);							
							SendEmail send = new SendEmail();
							send.sendInternalEmail(agencyemail,agencyid,pwd);
							modelandview.addObject("invalid","government user added! & email sent to government user");
						}
				}							
			}	
			catch(Exception e)
			{
				LOG.error("Error with adding government agency view: "+e.getMessage());
				ModelAndView m = new ModelAndView();
				m.setViewName("index");
				LoginHandler handler = new LoginHandler();
				String userSessionName = (String) session.getAttribute("USERNAME");
				handler.updateLoggedInFlag(userSessionName,0);
				return m;
			}			
			LOG.error(" Added government agency with details: "+agencyname+","+agencyid+","+agencyemail);			
			return modelandview;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			LoginHandler handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			return model;
		}		
	}

	@RequestMapping(value="/addi")
	public ModelAndView addInternal(HttpSession session)
	{
		String role = "";
		role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			LoginHandler handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			return model;
		}
		else if(role.equals("ADMIN"))
		{
			ModelAndView modelandview = new ModelAndView("addInternalUser");		
			return modelandview;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			LoginHandler handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			return model;
		}
		
	}

	
	@RequestMapping(value="/addinternaluser", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView addInternalUser(
			@RequestParam (value="firstname") String firstname,
			@RequestParam (value="middlename") String middlename,
			@RequestParam (value="lastname") String lastname,
			@RequestParam (value="ssn") String ssn,
			@RequestParam (value="email") String email,
			@RequestParam (value="phonenumber") String phonenumber,
			@RequestParam (value="dateofbirth") String dateofbirth,
			@RequestParam (value="address") String address,
			@RequestParam (value="state") String state,
			@RequestParam (value="username") String username,
			@RequestParam (value="designation") String usertype,
			@RequestParam (value="zip") String zip, HttpSession session) throws Exception{
		
		String role = "";
		ResultSet resultset = null;
		ResultSet rs = null;
		role = (String)session.getAttribute("Role");
		ModelAndView modelandview = new ModelAndView("addInternalUser");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			LoginHandler handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			return model;
		}
		else if(role.equals("ADMIN"))
		{			
			String pwd = "";
			try
			{
				if(!firstname.matches("^[A-z]+$"))
				{
					modelandview.addObject("firstname", "incorrect firstname");
				}
				
				if(!ssn.matches("^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$"))
				{
					modelandview.addObject("ssn", "please enter ssn number in correct format!");
				}
				if(!validate(email))
				{
					modelandview.addObject("email", "incorrect email format");
				}
				if(!phonenumber.matches( "^\\+?[1]\\d{10,10}$"))
				{
					modelandview.addObject("phonenumber", "incorrect phonenumber type");
				}
				if(!username.matches("^[0-9]+$"))
				{
					modelandview.addObject("username", "incorrect username type");
				}
				if(!zip.matches("^[0-9]{5}$"))
				{
					modelandview.addObject("zip", "zipcode is not correct!");
				}
				if(username.equalsIgnoreCase("") || firstname.equalsIgnoreCase("")  || ssn.equalsIgnoreCase("") || email.equalsIgnoreCase("") || phonenumber.equalsIgnoreCase("") || zip.equalsIgnoreCase(""))
				{
					modelandview.addObject("required", "please submit the required fields!");
				}
				
				if(firstname.matches("^[A-z]+$") && 
				   ssn.matches("^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$") &&
				   validate(email) &&
				   phonenumber.matches( "^\\+?[1]\\d{10,10}$") && 
				   username.matches("^[0-9]+$") && 
				   zip.matches("^[0-9]{5}$"))
				{
					JdbcConnection_tbl_user_authentication objConn = new JdbcConnection_tbl_user_authentication();
					resultset = objConn.checkusername(username);
					if(resultset.next())
					{
						modelandview.addObject("present", "username already present");
						resultset.close();
					}
					else
					{
						resultset.close();
						GenerateRSAKeys rsa = new GenerateRSAKeys();
						pwd = rsa.generateEncryptionString(4);
						objConn.addusername(username,pwd);
						rs = objConn.checkinternaluser(ssn);
						if(rs.next())
						{
							modelandview.addObject("present", "internal user already present");
							rs.close();
						}
						else
						{
							rs.close();
							objConn.add_internalUser(username,firstname, middlename, lastname, ssn, email, usertype, phonenumber, dateofbirth, address, state, zip);
							if(usertype.equals("MANAGER"))
							{
								objConn.addManager(username,"MANAGER");
							}
							modelandview.addObject("successful", "user successfully added! & email sent to user");							
							SendEmail send = new SendEmail();
							send.sendInternalEmail(email,username,pwd);
						}		
					}	
				}/*
				else
				{
					modelandview.addObject("invalid", "invalid details entered!");					
				}*/					
			}
			catch(Exception e)
			{
				LOG.error("Error with adding internal user: "+e.getMessage());
				ModelAndView m = new ModelAndView();
				m.setViewName("index");
				LoginHandler handler = new LoginHandler();
				String userSessionName = (String) session.getAttribute("USERNAME");
				handler.updateLoggedInFlag(userSessionName,0);
				return m;
			}				
			LOG.error(" Added internal user with details: "+firstname+","+middlename+","+lastname+","+ssn+","+email+","+usertype+","+phonenumber+","+dateofbirth+","+address+","+state+","+zip);
			return modelandview;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			LoginHandler handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			return model;
		}	
		
	}
	
	@RequestMapping(value="/modifyinternal")
	public ModelAndView modifyInternal(HttpSession session)
	{
		String role = "";
		role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			LoginHandler handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			return model;
		}
		else if(role.equals("ADMIN"))
		{
			ModelAndView modelandview = new ModelAndView("modifyInternalAccount");		
			return modelandview;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			LoginHandler handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			return model;
		}
		
	}

	
	@RequestMapping(value="/modifyinternaluser", method = RequestMethod.POST)
	public ModelAndView modifyInternalUser(
			@RequestParam (value="username") String username,
			@RequestParam (value="column") String column,
			@RequestParam (value="newinfo") String newinfo, HttpSession session)
	{
		ResultSet r = null;
		String role = "";
		role = (String)session.getAttribute("Role");
		JdbcConnection_tbl_user_authentication objConn = new JdbcConnection_tbl_user_authentication();
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			LoginHandler handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			return model;
		}
		else if(role.equals("ADMIN"))
		{
			ModelAndView modelandview = new ModelAndView("modifyInternalAccount");
			try 
			{
				if(username.equalsIgnoreCase("") || newinfo.equalsIgnoreCase(""))
				{
					modelandview.addObject("required","please enter the required fields!");
				}
				r = objConn.checkusername(username);
				if(r.next())
				{
					if(username.matches("^[0-9]+$"))
					{						
						if(column.equalsIgnoreCase("address"))
						{							
							objConn.modifyinternaluser(username, column,newinfo);
						}
						else if(column.equalsIgnoreCase("email") && validate(newinfo))
						{							
							objConn.modifyinternaluser(username, column,newinfo);
						}
						else if(column.equalsIgnoreCase("phonenumber") && newinfo.matches("^\\+?[1]\\d{10,10}$"))
						{							
							objConn.modifyinternaluser(username, column,newinfo);
						}
						else
						{
							modelandview.addObject("newinfo","new information format!");
						}									
					}
					else
					{
						modelandview.addObject("incorrect","incorrect username");
					}
					r.close();
				}
				else
				{
					modelandview.addObject("notpresent","username not present!");
					modifyInternal(session);
					r.close();
				}				
			} 
			catch (Exception e) 
			{
				LOG.error("Error with modifying internal user: "+e.getMessage());
				ModelAndView m = new ModelAndView();
				m.setViewName("index");
				LoginHandler handler = new LoginHandler();
				String userSessionName = (String) session.getAttribute("USERNAME");
				handler.updateLoggedInFlag(userSessionName,0);
				return m;
			}
			LOG.error(" modifying internal user with details of username : "+username+", with column: "+column+", to: "+newinfo);
			return modelandview;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			LoginHandler handler = new LoginHandler();
			String userSessionName = (String) session.getAttribute("USERNAME");
			handler.updateLoggedInFlag(userSessionName,0);
			return model;
		}		
	}	
}
