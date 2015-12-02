package com.sundevils.web.controller;

import handlers.adminHandlers.LoginHandler;
import handlers.externaluserHandlers.requestCardHandler;
import handlers.individualuserHandlers.AddRecepientHandler;
import handlers.individualuserHandlers.UserAccounts;
import handlers.individualuserHandlers.UserRecepients;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import utilities.CaptchaUtility;
import utilities.OtpUtility;
import utilities.TimeUtility;
import authentication.Requests;

@Controller

public class AnishaController {
	final static Logger logger = Logger.getLogger(AnishaController.class);

	long startTime = 0;
	ArrayList<UserAccounts> useraccounts = new ArrayList<UserAccounts>();
	ArrayList<UserRecepients> userrecepients = new ArrayList<UserRecepients>();
	ArrayList<Requests> requests=new ArrayList<Requests>();
	AddRecepientHandler handler = new AddRecepientHandler();
	LoginHandler lhandler = new LoginHandler();
	String userEmail = "";

	@RequestMapping(value="/VerifyExternalUser",method=RequestMethod.GET)
	public ModelAndView ViewRequests(HttpSession session){
		String role = (String) session.getAttribute("Role");
		if(role == null){
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("MANAGER")){
			try{
		   String managerUserName = (String)session.getAttribute("USERNAME");
			ModelAndView model = new ModelAndView("VerifyExternalUser");
			requests = handler.getRequests(managerUserName);
			if(!requests.isEmpty())
				model.addObject("requests", requests);
			else
				model.addObject("norequests", "You currently do not have any requests");
			return model;
			}
			catch(Exception e){
				e.printStackTrace();
				logger.error("Caugth an exception:"+ e);
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

	@RequestMapping(value="/VerifyExternalUser",method=RequestMethod.POST)
	public ModelAndView Requests(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String role = (String) session.getAttribute("Role");
		if(role == null){
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("MANAGER")){
			try{
			ModelAndView model = new ModelAndView("VerifyExternalUser");
			String id = request.getParameter("approve");
			String id1 = request.getParameter("decline");
			Requests request1 = new Requests();
			String Requesttype="";
			if(id!=null){
				for(Iterator<Requests> it=requests.iterator();it.hasNext();){
					request1= it.next();
					if(request1.getRequestid().equals(id)){
						Requesttype=request1.getRequesttype();
						break;
					}
				}
				if(Requesttype.equals("modify")){
					if(!request1.getModifiedcolumn().equalsIgnoreCase("accounttype"))
					handler.updatePI(request1.getUsername(), request1.getModifiedcolumn(), request1.getOldvalue(), request1.getNewvalue());
					else
					handler.updatePIAccount(request1.getUsername(), request1.getModifiedcolumn(), request1.getOldvalue(), request1.getNewvalue(),request1.getNewaccountnumber());

					handler.updateRequest(id,"APPROVE");
					logger.error("Request for user"+ request1.getUsername()+ " has been approved");
					requests.remove(request1);
					ModelAndView newmodel = new ModelAndView("VerifyExternalUser");
					model = newmodel;
				}
				else if(Requesttype.equals("REQUEST_CARD")){
					requestCardHandler handler = new requestCardHandler();
					handler.approveCardChange(request1.getOldvalue(),"APPROVE");
					model.addObject("Successful", "Card Replacement has been appoved");	
					logger.error("Request for user"+ request1.getUsername()+ " has been approved");
                    requests.remove(request1);
					ModelAndView newmodel = new ModelAndView("VerifyExternalUser");
					model = newmodel;
				}
			}
			else if(id1!=null) {
				for(Iterator<Requests> it=requests.iterator();it.hasNext();){
					request1= it.next();
					if(request1.getRequestid().equals(id1)){
						Requesttype=request1.getRequesttype();
						break;
					}
				}
				if(Requesttype.equals("modify")){
					handler.updateRequest(id1,"REJECT");
					requests.remove(request1);
					logger.error("Request for user"+ request1.getUsername()+ " has been rejected");
					ModelAndView newmodel = new ModelAndView("VerifyExternalUser");
					model = newmodel;
				}
				else if(Requesttype.equals("REQUEST_CARD")){
					requestCardHandler handler = new requestCardHandler();
					handler.approveCardChange(request1.getOldvalue(),"REJECT");
					model.addObject("Successful", "Card Replacement has been rejected");
					logger.error("Request for user"+ request1.getUsername()+ " has been rejected");
					requests.remove(request1);
					ModelAndView newmodel = new ModelAndView("VerifyExternalUser");
					model = newmodel;
				}
			}

			if(!requests.isEmpty())
				model.addObject("requests", requests);
			else
				model.addObject("norequests", "You currently do not have any requests");
			return model;
			}
			catch(Exception e){
				e.printStackTrace();
				logger.error("Caugth an exception:"+ e);
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
	
	@RequestMapping(value = "/login/captcha" , method = RequestMethod.GET)
	public ModelAndView generateCaptcha(HttpServletRequest request,HttpServletResponse response) throws IOException{
		ModelAndView model = new ModelAndView();
		CaptchaUtility captcha = new CaptchaUtility();
		captcha.generateCaptcha(request,response);
		model.setViewName("login");
		return model;
	}


	@RequestMapping(value="/internalTransfer" ,method=RequestMethod.GET)
	public ModelAndView internalTransfer(HttpSession session){
		String role = (String) session.getAttribute("Role");
		if(role == null){
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("MERCHANT") || role.equals("USER")){
			try{
			ModelAndView model = new ModelAndView("InternalFundTransfer");
			String userName="";
			userName = (String)session.getAttribute("USERNAME");
			useraccounts = handler.getaccountdetails(userName);
			model.addObject("useraccounts", useraccounts);
			return model;
			}
			catch(Exception e){
				e.printStackTrace();
				logger.error("Caugth an exception:"+ e);
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
	
	@RequestMapping(value="/internalTransfer" ,method=RequestMethod.POST)
	public ModelAndView makeInternalTransfer(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		String role = (String) session.getAttribute("Role");
		if(role == null){
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("MERCHANT") || role.equals("USER")){
			try{
			ModelAndView model = new ModelAndView("InternalFundTransfer");
			model.addObject("useraccounts", useraccounts);
			String SourceAccount = "";
			String DestinationAccount="";
			String SourceAccountNumber="";
			String DestinationAccountNumber="";
			String Amount="";
			String userName="";
			double SourceBalance = 0.0;
			double amount = 0;
			userName = (String)session.getAttribute("USERNAME");
			SourceAccount= request.getParameter("sourceuseraccounts");
			DestinationAccount= request.getParameter("destinationuseraccount");
			Amount = request.getParameter("amount");
			if(Amount.equals("")){
				model.addObject("amountError", "Please enter a valid amount");
				logger.error("Could not process transaction for " + userName +
						"because : " + amount + " is not a valid amount" );
				return model;
			}
			else
			{
				try {
					amount = Double.parseDouble(Amount);
					} catch(NumberFormatException ex)
					{
						model.addObject("amountError", "Please enter a valid amount");
						logger.error("Could not process transaction for " + userName +
								"because : " + amount + " is not a valid amount" );
						return model;

					}
			}
			if(SourceAccount.equals(DestinationAccount)){
				model.addObject("destinationError", "Please select a different destination account for fund transfer");
				logger.error("Could not process transaction for " + userName +
						"because of invalid destination" );
			}
			else if(amount < 0){
				model.addObject("amountError", "Please enter a valid amount");
				logger.error("Could not process transaction for " + userName +
						"because : " + amount + " is not a valid amount" );
			}
			else{
				for(UserAccounts ac :useraccounts){
					if(ac.getAccounttype().equals(DestinationAccount)){
						DestinationAccountNumber = ac.getAccountnumber();
						
						if(SourceBalance < amount ){
							model.addObject("balanceInsuficientError", "Transfer cant take place due to insufficient balance");
							logger.error("Could not process transaction for " + userName +
									"because of insufficient balance" );

						}	
						else{
							int TransactionID = 0;
							boolean success = false;
							String transferType = "fundstransfer";
							SourceBalance = SourceBalance-amount;
							lhandler.updateBalance(SourceAccountNumber,SourceBalance,userName);
							String Status = "pendingapproval";
							success = handler.submitTrasferRequest(userName,TransactionID,Amount,SourceAccountNumber,DestinationAccountNumber,TimeUtility.generateSysDateMethod(),transferType,Status,success);
							if(success){
								logger.error("Succesfully submitted the transaction for " + userName );

								model.addObject("success","Successfully submited the transaction for approval");
								model.setViewName("InternalFundTransfer");
							}
							else{
								model.addObject("failure", "Could not submit transaction");
								logger.error("could not submit the transaction for " + userName );

							}
						}
					}
					else if(ac.getAccounttype().equals(SourceAccount)){
						SourceAccountNumber = ac.getAccountnumber();
						SourceBalance = Double.parseDouble(ac.getBalance());
					}
				}
			}
			return model;
			}
			catch(Exception e){
				e.printStackTrace();
				logger.error("Caugth an exception:"+ e);
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
	@RequestMapping(value = "/externalTransfer" , method = RequestMethod.GET)
	public ModelAndView externalTransfer(HttpServletRequest request,HttpSession session) throws SQLException{
		String role = (String) session.getAttribute("Role");
		if(role == null){
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("MERCHANT") || role.equals("USER")){
			ResultSet rs = null;
			try{
			ModelAndView model = new ModelAndView("ExternalFundTransfer");
			String userName="";
			userName = (String)session.getAttribute("USERNAME");
			rs = lhandler.requestLoginHandler(userName);
			if(rs.next()){
				userEmail = rs.getString("email");
			}
			useraccounts = handler.getaccountdetails(userName);
			userrecepients =  handler.getRecepients(userName);
			model.addObject("useraccounts", useraccounts);
			model.addObject("recepients", userrecepients);
			if(request.getParameter("otpButton")!=null){
				startTime = System.currentTimeMillis();
				OtpUtility otp = new OtpUtility();
				otp.sendOtp(request,userEmail);
			}
			if(request.getParameter("submit")!=null)
			{
				model=makeExternalTransfer(request,session);
			}
			return model;
			}
			catch(Exception e){
				e.printStackTrace();
				logger.error("Caugth an exception:"+ e);
				ModelAndView model = new ModelAndView();
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
	@RequestMapping(value = "/externalTransfer" , method =RequestMethod.POST)
	public ModelAndView makeExternalTransfer(HttpServletRequest request,HttpSession session){
		String role = (String) session.getAttribute("Role");
		if(role == null){
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("MERCHANT") || role.equals("USER")){
			try{
			ModelAndView model = new ModelAndView("ExternalFundTransfer");
			model.addObject("useraccounts", useraccounts);
			model.addObject("recepients", userrecepients);
			String SourceAccount = "";
			String DestinationAccount="";
			String SourceAccountNumber="";
			Double SourceBalance=0.0;
			String Amount="";
			String userName="";
			String Otpdata="";
			String otpString="";
			double amount = 0;
			userName = (String)session.getAttribute("USERNAME");
			SourceAccount= request.getParameter("sourceuseraccounts");
			DestinationAccount= request.getParameter("destinationuseraccount");
			Otpdata= request.getParameter("otpCode");
			Amount = request.getParameter("amount");
			otpString = (String)session.getAttribute("OTP");

			long diff = System.currentTimeMillis() - startTime;
			int minutes = (int) ((diff / (1000*60)) % 60);
			if(minutes > 3){
				otpString = "";
			}
			if(Amount.equals("") || Otpdata.equals("") ){
				model.addObject("mandatory", "All fields are mandatory");
			}
			else
			{
				try {
				amount = Double.parseDouble(Amount);
				} catch(NumberFormatException ex)
				{
					model.addObject("amountError", "Please enter a valid amount");
					logger.error("Could not process transaction for " + userName +
							"because : " + amount + " is not a valid amount" );
					return model;

				}
				if(amount < 0){
					model.addObject("amountError", "Please enter a valid amount");
					logger.error("Could not process transaction for " + userName +
							"because : " + amount + " is not a valid amount" );

				}
				else if(!otpString.equals(Otpdata)){
					model.addObject("wrongOtp", "Otp code does not match");
					logger.error("Could not process transaction for " + userName +
							"because otp entered by the user does not match" );

				}
				else{
					for(UserAccounts ac :useraccounts){
						if(ac.getAccounttype().equals(SourceAccount)){
							SourceAccountNumber = ac.getAccountnumber();
							SourceBalance= Double.parseDouble(ac.getBalance());
				
						}
					}
					for(UserRecepients re : userrecepients)
						if(re.getAccountnumber().equals(DestinationAccount)){
							if(SourceBalance < amount ){
								model.addObject("balanceInsuficientError", "Transfer cant take place due to insufficient balance");
								logger.error("Could not process transaction for " + userName +
										"because of insufficient balance" );

							}	
							else{
								int TransactionID = 0;
								boolean success = false;
								String transferType = "fundstransfer";
								String Status = "pendingapproval";
								SourceBalance = SourceBalance-amount;
								lhandler.updateBalance(SourceAccountNumber,SourceBalance,userName);
								success = handler.submitTrasferRequest(userName,TransactionID,Amount,SourceAccountNumber,DestinationAccount,TimeUtility.generateSysDateMethod(),transferType,Status,success);
								if(success){
									model.addObject("success","Successfully submited the transaction for approval");
									logger.error("Succesfully submitted the transaction for " + userName );

									model.setViewName("ExternalFundTransfer");
								}
								else{
									model.addObject("failure", "Could not submit transaction");
									logger.error("could not submit the transaction for " + userName );

								}
							}
						}
				}
			}
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
	@RequestMapping(value="/addRecepient",method={RequestMethod.POST, RequestMethod.GET})
	public ModelAndView registerRecepient(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws SQLException{
		String role = (String) session.getAttribute("Role");
		if(role == null){
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("MERCHANT") || role.equals("USER")){
			ModelAndView model = new ModelAndView("AddARecepient");
			String userName = "";
			String firstName = "";
			String lastName = "";
			try{
				String accountNumber = "";
				String confirmedAccNumber = "";
				String email = "";
				String otpData = "";
				String otpString = "";
				String debitcard="";
				String userDebitCard1="";
				String userDebitCard2="";
				String userAccountNumber1 = "";
				String userAccountNumber2 = "";
				//String userEmail="";
				//getting user debitcard no. for validaton
				userName = (String)session.getAttribute("USERNAME");

				useraccounts = handler.getaccountdetails(userName);
				for(UserAccounts ac :useraccounts){
					if(ac.getAccounttype().equals("Checking Account")){
						userDebitCard1 = ac.getUserdebitcard();
						userAccountNumber1 = ac.getAccountnumber();
					}
					if(ac.getAccounttype().equals("Saving Account")){
						userDebitCard2 = ac.getUserdebitcard();
						userAccountNumber2 = ac.getAccountnumber();
					}
				}

				//get user email id
				ResultSet rs1 = lhandler.requestLoginHandler(userName);
				if(rs1.next()){
					userEmail = rs1.getString("email");
				}
				if(request.getParameter("otpButton")!=null){
					startTime = System.currentTimeMillis();
					OtpUtility otp = new OtpUtility();
					otp.sendOtp(request,userEmail);
					model.setViewName("AddARecepient");
				}
				else 
					if(request.getParameter("add")!=null)
					{
						firstName = request.getParameter("firstname");
						lastName = request.getParameter("lastname");
						accountNumber =  request.getParameter("accountnumber");
						confirmedAccNumber = request.getParameter("confirmaccountnumber");
						email=request.getParameter("email");
						otpData = request.getParameter("otpCode");
						otpString = (String)session.getAttribute("OTP");
						debitcard=request.getParameter("debitcard");

						long diff = System.currentTimeMillis() - startTime;
						int minutes = (int) ((diff / (1000*60)) % 60);
						if(minutes > 3){
							otpString = "";
						}

						if(debitcard.equals("") || firstName.equals("") ||  lastName.equals("") || accountNumber.equals("") ||  confirmedAccNumber.equals("") ||email.equals("") || otpData.equals("")){
							model.addObject("emptyFields", "All fields are mandatory");
						}
						else if(!accountNumber.equals(confirmedAccNumber)){
							model.addObject("accountNumDismatch", "Account Numbers Does Not Match");
						}
						else if(!otpString.equals(otpData)){
							model.addObject("wrongOtp", "Otp code does not match");
						}
						else if(!(debitcard.equals(userDebitCard1) || debitcard.equals(userDebitCard2))){
							model.addObject("wrongDebit", "debit card number does not match");
						}
						else if(userAccountNumber1.equals(accountNumber) || userAccountNumber2.equals(accountNumber)){
							model.addObject("sameuser", "cannot add recepient as he is the same user");
						}
						else
						{
							boolean success= false;
							ResultSet rs =  handler.getExsistinguser(accountNumber);
							if(rs.next()){//so receipent is user of bank
								success = handler.addRecepient(userName,request.getParameter("firstname"),request.getParameter("lastname"),request.getParameter("accountnumber"),request.getParameter("email"),success);
								if(success)

								{
									model.addObject("Successfull", "Recepient added successfully");
									logger.error("Succesfully added the recepient: " + firstName + lastName +" for user: " + userName);
									model.setViewName("AddARecepient");}
								else
									{
									model.addObject("dulicateuser", "Reepient with above information(account number) already exsists");
									logger.error("Could not add the recepient: " + firstName + lastName +" for user: " + userName +" as recepient already exsists");
									}
							}
							else{
								logger.error("Could not add the recepient: " + firstName + lastName +" for user: " + userName +" as recepient do not exsist in the bank");
								model.addObject("invaliduser", "recepient does not exsist in the bank");

							}
						}

					}
			}
			catch( Exception e ){
				e.printStackTrace();
				logger.error("Caugth an exception:"+ e);
				//ModelAndView model = new ModelAndView();
				String username = (String) session.getAttribute("USERNAME");
				lhandler.updateLoggedInFlag(username, 0);
				session.invalidate();
				model.setViewName("index");
				return model;
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

}
