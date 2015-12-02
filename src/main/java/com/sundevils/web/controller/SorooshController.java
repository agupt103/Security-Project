package com.sundevils.web.controller;

import handlers.adminHandlers.LoginHandler;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pkiEncDecModule.EncryptDecryptModule;
import pkiEncDecModule.EncryptionKeyPair;
import pkiEncDecModule.SerialDeserializerModule;
import pkiEncDecModule.pkiDatabaseHandler;
import soroosh.PaymentFormDataLoader;
import soroosh.PaymentInfo;
import soroosh.PaymentInfoValidator;
import soroosh.SorooshDatabaseConnection;
import utilities.OtpUtility;
import utilities.TimeUtility;

import com.sun.jersey.core.util.Base64;

@Controller
public class SorooshController {

	private String userRole = "USER";
	private String merchantRole = "MERCHANT";
	String modelTime="2015/10/24 00:00:00";
	String otpGenerateTime;

	private static final Logger LOGGER = Logger.getLogger(SorooshController.class.getName());

	private ModelAndView redirectToAccessDeniedPage(HttpSession session) {
		ModelAndView model = new ModelAndView();
		LoginHandler handler = new LoginHandler();
		String userName = (String)session.getAttribute("USERNAME");
		if(userName != null)
			handler.updateLoggedInFlag(userName, 0);
		session.invalidate();
		model.setViewName("index");
		return model;
	}

	//returns false if the user has the required permission
	private boolean checkAccessCondition(HttpSession session, String role){
		if(session == null){
			return true;
		}
		if(((String)session.getAttribute("Role")) == null){
			return true;
		}
		if(!((String)session.getAttribute("Role")).equalsIgnoreCase(role))
			return true;
		return false;
	}

	@RequestMapping(value = {"/customerhome"}, method = RequestMethod.GET)
	public ModelAndView customerHomePage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(checkAccessCondition(request.getSession(), userRole)){
			return redirectToAccessDeniedPage(request.getSession());
		}
		ModelAndView model = new ModelAndView();
		model.setViewName("customerhome");
		return model;
	}

	@RequestMapping(value = {"/merchanthome"}, method = RequestMethod.GET)
	public ModelAndView merchantHomePage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(checkAccessCondition(request.getSession(), merchantRole)){
			return redirectToAccessDeniedPage(request.getSession());
		}
		ModelAndView model = new ModelAndView();
		model.setViewName("merchanthome");
		return model;

	}

	@RequestMapping(value = {"/otpforpayment"}, method = RequestMethod.GET)
	public ModelAndView merchantHomePage(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) throws IOException {

		String username = (String)session.getAttribute("USERNAME");

		PaymentFormDataLoader handler = new PaymentFormDataLoader();
		try {
			String email = handler.getEmailAddressForUsername(username);
			OtpUtility otp = new OtpUtility();
			otp.sendOtp(request, email);
			otpGenerateTime = TimeUtility.generateSysDateMethod()+" "+TimeUtility.generateSysHoursMethod()+":"+TimeUtility.generateSysMinutesMethod()+":"+TimeUtility.generateSysSecondsMethod();
			request.getSession().setAttribute("otpGenerateTime", otpGenerateTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = {"/merchantstartpayment"}, method = RequestMethod.GET)
	public ModelAndView merchantRequestPaymentPage(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) throws IOException {

		if(checkAccessCondition(session, merchantRole)){
			return redirectToAccessDeniedPage(session);
		}

		ModelAndView model = new ModelAndView();
		PaymentFormDataLoader handler = new PaymentFormDataLoader();

		try {
			ArrayList<String> list = (ArrayList<String>)handler.getAllMerchantsForUserPayment();
			list.remove(session.getAttribute("USERNAME"));
			model.addObject("merchants", list);
			model.addObject("users", handler.getAllUsersForMerchantPayment());
		} catch (Exception e) {
			ArrayList<String> errors = new ArrayList<String>();
			LOGGER.error("Internal error - Database Unavailable");
			errors.add("Internal error - please try again later");
			model.addObject("errors", errors);
			model.setViewName("Merchant.Organization/Payment.Error");
			e.printStackTrace();
			return model;
		}

		try {
			model.addObject("bankaccounts", handler.getAllAccountsForUserPayment((String)session.getAttribute("USERNAME")));
		} catch (Exception e) {
			ArrayList<String> errors = new ArrayList<String>();
			LOGGER.error("Internal error - Database Unavailable");
			errors.add("Internal error - please try again later");
			model.addObject("errors", errors);
			model.setViewName("Individual.Customers/Payment.Error");
			e.printStackTrace();
			return model;
		}

		model.setViewName("Merchant.Organization/Payment");
		return model;
	}

	@RequestMapping(value = {"/usermakepayment"}, method = RequestMethod.GET)
	public ModelAndView userPaymentPage(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) throws IOException {
		if(checkAccessCondition(session, userRole)){
			return redirectToAccessDeniedPage(session);
		}

		ModelAndView model = new ModelAndView();
		PaymentFormDataLoader handler = new PaymentFormDataLoader();
		try {
			model.addObject("merchants", handler.getAllMerchantsForUserPayment());
		} catch (Exception e) {
			ArrayList<String> errors = new ArrayList<String>();
			LOGGER.error("Internal error - Database Unavailable");
			errors.add("Internal error - please try again later");
			model.addObject("errors", errors);
			model.setViewName("Individual.Customers/Payment.Error");
			e.printStackTrace();
			return model;
		}

		try {
			model.addObject("bankaccounts", handler.getAllAccountsForUserPayment((String)session.getAttribute("USERNAME")));
		} catch (Exception e) {
			ArrayList<String> errors = new ArrayList<String>();
			LOGGER.error("Internal error - Database Unavailable");
			errors.add("Internal error - please try again later");
			model.addObject("errors", errors);
			model.setViewName("Individual.Customers/Payment.Error");
			e.printStackTrace();
			return model;
		}
		model.setViewName("Individual.Customers/Payment");
		return model;

	}

	@RequestMapping(value = {"/readmerchantpaymentform"}, method = {RequestMethod.POST}) 
	public ModelAndView merchantPaymentRequestFormReader(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) { 
		ModelAndView model = new ModelAndView(); 
		if(checkAccessCondition(session, merchantRole)){
			return redirectToAccessDeniedPage(session);
		}
		String username = (String)session.getAttribute("USERNAME");
		ArrayList<String> errors = null;

		String payer = null;  
		String amount = null; 
		String accountNumber = null; 

		String payerE = request.getParameter("payerE");  
		String amountE = request.getParameter("amountE"); 
		String accountNumberE = request.getParameter("accountNumberE"); 
		String signature = request.getParameter("signatureE");

		byte[] encodedpayer = Base64.decode(payerE);
		byte[] encodedamount = Base64.decode(amountE);
		byte[] encodedaccountNumber = Base64.decode(accountNumberE);

		EncryptDecryptModule encDecModule = new EncryptDecryptModule();
		SerialDeserializerModule module = new SerialDeserializerModule();
		pkiDatabaseHandler databaseHandler = new pkiDatabaseHandler();
		EncryptionKeyPair pair = null;
		try {
			pair = databaseHandler.getKeysFromDatabase(username);
		} catch (Exception e) {
			e.printStackTrace();
			errors = new ArrayList<String>();
			LOGGER.error("Internal error occured - data decryption error");
			errors.add("Internal error occured - try again later");
			model.setViewName("Merchant.Organization/Payment.Error");
			model.addObject("errors", errors);
			return model;		
		}

		PrivateKey priv;

		try {
			priv = module.stringToPrivateKey(pair.getPriveKeyStr());
			payer = new String(encDecModule.decrypt(encodedpayer, priv));
			amount = new String(encDecModule.decrypt(encodedamount, priv));
			accountNumber = new String(encDecModule.decrypt(encodedaccountNumber, priv));
		} catch (Exception e) {
			errors = new ArrayList<String>();
			LOGGER.error("Internal error occured - data decryption error");
			errors.add("Internal error occured - try again later");
			model.setViewName("Merchant.Organization/Payment.Error");
			model.addObject("errors", errors);
			return model;
		}


		if(payer.equalsIgnoreCase(username)){
			errors = new ArrayList<String>();
			errors.add("Cannot request payment from self");
			LOGGER.error("Cannot request payment from self");
			model.addObject("errors", errors);
			model.setViewName("Merchant.Organization/Payment.Error");
			model.addObject("errors", errors);
			return model;
		}

		PaymentInfoValidator formValidator = new PaymentInfoValidator();
		try {
			errors = formValidator.merchantPaymentRequestValidateData(username, payer, amount, accountNumber, signature);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			errors = new ArrayList<String>();
			LOGGER.error("Internal error occured");
			errors.add("Internal error occured - try again later");
		}
		if(errors.size() != 0){
			model.addObject("errors", errors);
			model.setViewName("Merchant.Organization/Payment.Error");
			return model;
		}

		SorooshDatabaseConnection databaseConnection = new SorooshDatabaseConnection();
		try {
			databaseConnection.putMerchantPaymentRequestInDatabase(payer, amount, accountNumber, 
					(String)session.getAttribute("USERNAME"));
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			errors = new ArrayList<String>();
			errors.add("Internal error - please try again later");
			model.addObject("errors", errors);
			model.setViewName("Merchant.Organization/Payment.Error");
			return model;
		}
		model.addObject("message", "Payment successfully submitted");
		model.setViewName("Merchant.Organization/Confirm.Payment.Action");
		LOGGER.error(username + "'s payment request successfully submitted");

		return model;
	}

	@RequestMapping(value = {"/readuserpaymentform"}, method = {RequestMethod.POST}) 
	public ModelAndView userPaymentFormReader(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) { 

		if(checkAccessCondition(session, userRole)){
			return redirectToAccessDeniedPage(session);
		}

		ArrayList<String> errors = null;
		ModelAndView model = new ModelAndView(); 

		String username = (String)session.getAttribute("USERNAME");

		String merchantName = null;  
		String accountNumber = null; 
		String amount = null; 
		String OTPMethod = null; 
		String OTPText = null; 
		String signature = null;

		String merchantNameE = (String)request.getParameter("merchantNameE");  
		String accountNumberE = request.getParameter("accountNumberE"); 
		String amountE = request.getParameter("amountE"); 
		String OTPTextE = request.getParameter("otpTextE"); 
		signature = request.getParameter("signatureE");

		byte[] encodedmerchantName = Base64.decode(merchantNameE);
		byte[] encodedaccountNumber = Base64.decode(accountNumberE);
		byte[] encodedamount = Base64.decode(amountE);
		byte[] encodedOTPText = Base64.decode(OTPTextE);

		EncryptDecryptModule encDecModule = new EncryptDecryptModule();
		SerialDeserializerModule module = new SerialDeserializerModule();
		pkiDatabaseHandler databaseHandler = new pkiDatabaseHandler();
		EncryptionKeyPair pair = null;
		try {
			pair = databaseHandler.getKeysFromDatabase(username);
		} catch (Exception e) { 
			e.printStackTrace();
			errors = new ArrayList<String>();
			LOGGER.error("Internal error occured");
			errors.add("Internal error occured - data decryption error");
			model.setViewName("Individual.Customers/Payment.Error");
			model.addObject("errors", errors);
			return model;
		}

		PrivateKey priv;

		try {
			priv = module.stringToPrivateKey(pair.getPriveKeyStr());
			merchantName = new String(encDecModule.decrypt(encodedmerchantName, priv));
			accountNumber = new String(encDecModule.decrypt(encodedaccountNumber, priv));
			amount = new String(encDecModule.decrypt(encodedamount, priv));
			OTPText = new String(encDecModule.decrypt(encodedOTPText, priv));
		} catch (Exception e) {
			e.printStackTrace();
			errors = new ArrayList<String>();
			LOGGER.error("Internal error occured");
			errors.add("Internal error occured - try again later");
			model.setViewName("Individual.Customers/Payment.Error");
			model.addObject("errors", errors);
			return model;
		}

		PaymentInfoValidator formValidator = new PaymentInfoValidator();
		try {
			errors = formValidator.userPaymentValidateData(merchantName, accountNumber, amount,  OTPMethod, 
					OTPText, signature, session, (String)session.getAttribute("USERNAME"), 
					(String)session.getAttribute("otpGenerateTime"));
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			errors = new ArrayList<String>();
			LOGGER.error("Internal error - input validation");
			errors.add("Internal error occured - try again later");
		}
		if(errors.size() == 0){
			model.setViewName("Individual.Customers/Review.Payment");

			double doubleAmount = Double.parseDouble(amount);
			PaymentInfo payment = new PaymentInfo((String)session.getAttribute("USERNAME"), accountNumber, 
					merchantName, doubleAmount);
			session.setAttribute("payment", payment);

			model.addObject("merchantname", merchantName);
			model.addObject("bankaccount", accountNumber);
			model.addObject("amount", amount);
		}
		else{
			model.addObject("errors", errors);
			model.setViewName("Individual.Customers/Payment.Error");
		}
		return model; 
	}

	@RequestMapping(value = {"/submituserpayment"}, method = {RequestMethod.POST}) 
	public ModelAndView userPaymentSubmit(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) { 

		if(checkAccessCondition(session, userRole)){
			return redirectToAccessDeniedPage(session);
		}

		String username = (String)session.getAttribute("USERNAME");

		ModelAndView model = new ModelAndView(); 
		ArrayList<String> errors = new ArrayList<String>();

		if((request.getParameter("submit") != null) && (session.getAttribute("payment") != null)){
			SorooshDatabaseConnection databaseConnection = new SorooshDatabaseConnection();
			try {
				errors = databaseConnection.putCustomerPaymentInDatabase((PaymentInfo)session.getAttribute("payment"));
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				LOGGER.error("Internal error - Database Unavailable");
				errors.add("Internal error - please try again later");
				session.removeAttribute("payment");
				model.addObject("errors", errors);
				model.setViewName("Individual.Customers/Payment.Error");
				return model;
			}

			if(errors.size() == 0){
				session.removeAttribute("payment");
				model.addObject("message", "Payment successfully submitted");
				model.setViewName("Individual.Customers/Confirm.Payment.Action");
				LOGGER.error(username + "'s payment successfully submitted");
			}
			else{
				model.addObject("errors", errors);
				model.setViewName("Individual.Customers/Payment.Error");
				return model;
			}
		}
		else if(request.getParameter("cancel") != null){
			session.removeAttribute("payment");
			model.addObject("message", "Payment successfully cancelled");
			model.setViewName("Individual.Customers/Confirm.Payment.Action");
			LOGGER.error(username + "'s payment request successfully cancelled");

		}
		else{
			errors.add("Unknown input - payment cancelled");
			session.removeAttribute("payment");
			model.setViewName("Individual.Customers/Confirm.Payment.Action");
			LOGGER.error(username + "'s payment request successfully cancelled");
		}

		return model; 
	}

	@RequestMapping(value = {"/merchantshowpayments"}, method = {RequestMethod.GET}) 
	public ModelAndView merchantShowAllPayments(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) { 

		if(checkAccessCondition(session, merchantRole)){
			return redirectToAccessDeniedPage(session);
		}

		ModelAndView model = new ModelAndView(); 
		Object allPaymentsTo = null;
		Object allPaymentsFrom = null;

		PaymentFormDataLoader handler = new PaymentFormDataLoader();
		try {
			allPaymentsTo = handler.getAllPyamentsToMerchant((String)session.getAttribute("USERNAME"));
		} catch (Exception e) {
			e.printStackTrace();
			ArrayList<String> errors = new ArrayList<String>();
			LOGGER.error("Internal error - Database Unavailable");
			errors.add("Internal error - please try again later");
			model.addObject("errors", errors);
			model.setViewName("Merchant.Organization/Payment.Error");
			return model;
		}

		try {
			allPaymentsFrom = handler.getAllPyamentsFromMerchant((String)session.getAttribute("USERNAME"));
		} catch (Exception e) {
			e.printStackTrace();
			ArrayList<String> errors = new ArrayList<String>();
			LOGGER.error("Internal error - Database Unavailable");
			errors.add("Internal error - please try again later");
			model.addObject("errors", errors);
			model.setViewName("Merchant.Organization/Payment.Error");
			return model;
		}

		session.setAttribute("submittedPayments", allPaymentsTo);
		session.setAttribute("requestedPayments", allPaymentsFrom);

		model.addObject("payments", allPaymentsTo);
		model.addObject("paymentsRequests", allPaymentsFrom);
		model.setViewName("Merchant.Organization/Merchant.Authorize.Transactions");
		return model;
	}

	@RequestMapping(value = {"/usershowpayments"}, method = {RequestMethod.GET}) 
	public ModelAndView userShowAllPayments(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) { 

		if(checkAccessCondition(session, userRole)){
			return redirectToAccessDeniedPage(session);
		}

		ModelAndView model = new ModelAndView(); 
		Object allPayments = null;

		PaymentFormDataLoader handler = new PaymentFormDataLoader();
		try {
			allPayments = handler.getAllPyamentsToCustomer((String)session.getAttribute("USERNAME"));
		} catch (Exception e) {
			e.printStackTrace();
			ArrayList<String> errors = new ArrayList<String>();
			LOGGER.error("Internal error - Database Unavailable");
			errors.add("Internal error - please try again later");
			model.addObject("errors", errors);
			model.setViewName("Individual.Customer/Payment.Error");
			return model;
		}

		session.setAttribute("submittedPayments", allPayments);
		model.addObject("payments", allPayments);
		model.setViewName("Individual.Customers/Customer.Authorize.Payments");
		return model;
	}

	@RequestMapping(value = {"/merchantpaymentdecision"}, method = {RequestMethod.POST}) 
	public ModelAndView merchantAuthorizePayments(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) { 

		ModelAndView model = new ModelAndView(); 
		int iteration = -1;

		if(request.getParameter("iteration") == null){
			return goToMerchantErrorPage("Internal error - please try again later");
		}

		try{
			iteration = Integer.parseInt(request.getParameter("iteration"));
		} catch(NumberFormatException e){
			e.printStackTrace();
			LOGGER.error("Internal error in Merchant payment");
			return goToMerchantErrorPage("Internal error - please try again later");
		}
		ArrayList<PaymentInfo> payments = (ArrayList<PaymentInfo>)session.getAttribute("submittedPayments");

		if(payments == null || payments.size() == 0 || iteration > payments.size() || iteration < 0){
			return goToMerchantErrorPage("Internal error - please try again later");
		}

		int id = payments.get(iteration).getPaymentId();
		SorooshDatabaseConnection databaseConnection = new SorooshDatabaseConnection();

		try{
			if(request.getParameter("accept") != null) {
				PaymentInfo thisPayment = payments.get(iteration);
				session.setAttribute("acceptedPayment", thisPayment);

				PaymentFormDataLoader handler = new PaymentFormDataLoader();
				try {
					Object accounts =  handler.getAllAccountsForUserPayment((String)session.
							getAttribute("USERNAME"));
					model.addObject("bankaccounts", accounts);
				} catch (Exception e) {
					ArrayList<String> errors = new ArrayList<String>();
					LOGGER.error("Internal error - database unavailable");
					errors.add("Internal error - please try again later");
					model.addObject("errors", errors);
					model.setViewName("Merchant.Organization/Payment.Error");
					e.printStackTrace();
					return model;
				}

				model.addObject("payment_payer", thisPayment.getUsername());
				model.addObject("payment_amount", thisPayment.getAmount());

				model.setViewName("Merchant.Organization/Receive.Payment");
				return model;			}
			else if(request.getParameter("reject") != null) {
				databaseConnection.updatePaymentByMerchant(id, false);
			}
		} catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
			LOGGER.error("Internal error in payment authorization");
			return goToMerchantErrorPage("Internal error in payment authorization - try again later");
		}

		return merchantShowAllPayments(request, response, session);
	}

	@RequestMapping(value = {"/merchantrequestpaymentdecision"}, method = {RequestMethod.POST}) 
	public ModelAndView merchantRequestPaymentsDecisions(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) { 

		if(checkAccessCondition(session, merchantRole)){
			return redirectToAccessDeniedPage(session);
		}

		ModelAndView model = new ModelAndView(); 
		int iteration = -1;

		if(request.getParameter("iteration") == null)
			return goToMerchantErrorPage("Internal error - please try again later");

		try{
			iteration = Integer.parseInt(request.getParameter("iteration"));
		} catch(NumberFormatException e){
			e.printStackTrace();
			LOGGER.error("Internal error - bad input");
			return goToMerchantErrorPage("Internal error - please try again later");
		}
		ArrayList<PaymentInfo> payments = (ArrayList<PaymentInfo>)session.getAttribute("requestedPayments");

		if(payments == null || payments.size() == 0 || iteration > payments.size() || iteration < 0){
			return goToMerchantErrorPage("Internal error - please try again later");
		}

		int id = payments.get(iteration).getPaymentId();
		SorooshDatabaseConnection databaseConnection = new SorooshDatabaseConnection();

		try{
			if(request.getParameter("accept") != null) {
				PaymentInfo thisPayment = payments.get(iteration);
				session.setAttribute("acceptedPayment", thisPayment);

				PaymentFormDataLoader handler = new PaymentFormDataLoader();
				try {
					Object accounts =  handler.getAllAccountsForUserPayment((String)session.
							getAttribute("USERNAME"));
					model.addObject("bankaccounts", accounts);
				} catch (Exception e) {
					ArrayList<String> errors = new ArrayList<String>();
					LOGGER.error("Internal error - database unavailable");
					errors.add("Internal error - please try again later");
					model.addObject("errors", errors);
					model.setViewName("Merchant.Organization/Payment.Error");
					e.printStackTrace();
					return model;
				}

				model.addObject("requester_merchant", thisPayment.getDestinationAccountNumber());
				model.addObject("request_payment_amount", thisPayment.getAmount());

				model.setViewName("Merchant.Organization/Pay.Payment.To.Merchant");
				return model;
			}
			else if(request.getParameter("reject") != null) {
				databaseConnection.updatePaymentByMerchant(id, false);
			}
		} catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
			LOGGER.error("Internal error in payment authorization");
			return goToMerchantErrorPage("Internal error in payment authorization - try again later");
		}

		session.removeAttribute("requestedPayments");
		return merchantShowAllPayments(request, response, session);
	}

	@RequestMapping(value = {"/payerpaymentdecision"}, method = {RequestMethod.POST}) 
	public ModelAndView userPayerAuthorizePayments(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) { 

		if(checkAccessCondition(session, userRole)){
			return redirectToAccessDeniedPage(session);
		}

		ModelAndView model = new ModelAndView(); 
		int iteration = -1;

		if(request.getParameter("iteration") == null){
			return goToMerchantErrorPage("Internal error - please try again later");
		}

		try{
			iteration = Integer.parseInt(request.getParameter("iteration"));
		} catch(NumberFormatException e){
			e.printStackTrace();
			LOGGER.error("Internal error - bad input");
			return goToMerchantErrorPage("Internal error - please try again later");
		}
		ArrayList<PaymentInfo> payments = (ArrayList<PaymentInfo>)session.getAttribute("submittedPayments");

		if(payments == null || payments.size() == 0 || iteration > payments.size() || iteration < 0){
			return goToMerchantErrorPage("Internal error - please try again later");
		}

		int id = payments.get(iteration).getPaymentId();
		SorooshDatabaseConnection databaseConnection = new SorooshDatabaseConnection();

		try{
			if(request.getParameter("accept") != null) {
				session.setAttribute("acceptedPayment", payments.get(iteration));
				session.removeAttribute("submittedPayments");

				PaymentFormDataLoader handler = new PaymentFormDataLoader();
				try {
					Object accounts =  handler.getAllAccountsForUserPayment((String)session.getAttribute("USERNAME"));
					model.addObject("bankaccounts", accounts);
				} catch (Exception e) {
					ArrayList<String> errors = new ArrayList<String>();
					LOGGER.error("Internal error - database unavailable");
					errors.add("Internal error - please try again later");
					model.addObject("errors", errors);
					model.setViewName("Individual.Customers/Payment.Error");
					e.printStackTrace();
					return model;
				}

				model.addObject("requester_merchant", payments.get(iteration).getDestinationAccountNumber());
				model.addObject("request_payment_amount", payments.get(iteration).getAmount());

				model.setViewName("Individual.Customers/Pay.Payment.To.Merchant");
				return model;
			}
			else if(request.getParameter("reject") != null) {
				databaseConnection.updatePaymentByPayer(id, false);
			}
		} catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
			LOGGER.error("Internal error in payment authorization");
			return goToMerchantErrorPage("Internal error in payment authorization - try again later");
		}

		return userShowAllPayments(request, response, session);
	}

	@RequestMapping(value = {"/merchantreceivepayment"}, method = {RequestMethod.POST}) 
	public ModelAndView merchantReceivePayments(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) { 

		ArrayList<String> errors = new ArrayList<String>();
		ModelAndView model = new ModelAndView(); 
		PaymentInfo payment = (PaymentInfo)session.getAttribute("acceptedPayment");
		String accountNumber = request.getParameter("accountNumber"); 
		String username = (String)session.getAttribute("USERNAME");

		PaymentInfoValidator formValidator = new PaymentInfoValidator();
		try {
			errors = formValidator.payerPaymentAcceptValidation(accountNumber, username, payment);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			LOGGER.error("Internal error - input validation error");
			errors.add("Internal error occured - try again later");
		}

		if(errors.size() != 0){
			model.addObject("errors", errors);
			model.setViewName("Merchant.Organization/Payment.Error");
		}

		else{
			SorooshDatabaseConnection databaseConnection = new SorooshDatabaseConnection();
			try {
				databaseConnection.receivePaymentDatabaseUpdate(payment.getPaymentId(), accountNumber);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				LOGGER.error("Internal error - database unavailable");
				errors.add("Internal error occured - try again later");
			}

			if(errors.size() == 0){
				model.setViewName("Merchant.Organization/Confirm.Payment.Action");
				model.addObject("message", "You will receive " + payment.getAmount() + " into your account " + 
						accountNumber + "(upon bank approval)");
				LOGGER.error(username + "'s payment decision submitted to bank for approval");
			}
			else{
				model.addObject("errors", errors);
				model.setViewName("Merchant.Organization/Payment.Error");
			}
		}

		session.removeAttribute("acceptedPayment");
		return model; 
	}

	@RequestMapping(value = {"/merchantpayersubmitacceptpayment"}, method = {RequestMethod.POST}) 
	public ModelAndView merchantPayerSubmitPaymentsToBank(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) { 

		if(checkAccessCondition(session, merchantRole)){
			return redirectToAccessDeniedPage(session);
		}

		ArrayList<String> errors = new ArrayList<String>();
		ModelAndView model = new ModelAndView(); 
		PaymentInfo payment = (PaymentInfo)session.getAttribute("acceptedPayment");
		String accountNumber = request.getParameter("accountNumber"); 
		String username = (String)session.getAttribute("USERNAME");

		PaymentInfoValidator formValidator = new PaymentInfoValidator();
		try {
			errors = formValidator.payerPaymentAcceptValidation(accountNumber, username, payment);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			LOGGER.error("Internal error - input validation");
			errors.add("Internal error occured - try again later");
		}

		if(errors.size() != 0){
			model.addObject("errors", errors);
			model.setViewName("Merchant.Organization/Payment.Error");
		}

		else{
			SorooshDatabaseConnection databaseConnection = new SorooshDatabaseConnection();
			try {
				databaseConnection.acceptPaymentDatabaseUpdate(payment, accountNumber, username);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				LOGGER.error("Internal error - database unavailable");
				errors.add("Internal error occured - try again later");
			}

			if(errors.size() == 0){
				model.setViewName("Merchant.Organization/Confirm.Payment.Action");
				model.addObject("message", "You accepted to pay " + payment.getAmount() + " to " + 
						payment.getUsername());
				LOGGER.error(username + "'s payment decision was submitted to bank for approval");
			}
			else{
				model.addObject("errors", errors);
				model.setViewName("Merchant.Organization/Payment.Error");
			}
		}

		session.removeAttribute("acceptedPayment");
		return model; 
	}

	@RequestMapping(value = {"/userpayersubmitacceptpayment"}, method = {RequestMethod.POST}) 
	public ModelAndView userPayerSubmitPaymentsToBank(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) { 

		if(checkAccessCondition(session, userRole)){
			return redirectToAccessDeniedPage(session);
		}		

		ArrayList<String> errors = new ArrayList<String>();
		ModelAndView model = new ModelAndView(); 
		PaymentInfo payment = (PaymentInfo)session.getAttribute("acceptedPayment");
		String accountNumber = request.getParameter("accountNumber"); 
		String username = (String)session.getAttribute("USERNAME");

		PaymentInfoValidator formValidator = new PaymentInfoValidator();
		try {
			errors = formValidator.payerPaymentAcceptValidation(accountNumber, username, payment);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			LOGGER.error("Internal error - validation error");
			errors.add("Internal error occured - try again later");
		}

		if(errors.size() != 0){
			model.addObject("errors", errors);
			model.setViewName("Individual.Customers/Payment.Error");
		}

		else{
			SorooshDatabaseConnection databaseConnection = new SorooshDatabaseConnection();
			try {
				databaseConnection.acceptPaymentDatabaseUpdate(payment, accountNumber, username);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				LOGGER.error("Internal error - database unavailable");
				errors.add("Internal error occured - try again later");
			}

			if(errors.size() == 0){
				model.setViewName("Individual.Customers/Confirm.Payment.Action");
				model.addObject("message", "You accepted to pay " + payment.getAmount() + " to " + 
						payment.getDestinationAccountNumber());
				LOGGER.error(username + "'s payment decision is submitted to bank for approval");
			}
			else{
				model.addObject("errors", errors);
				model.setViewName("Individual.Customers/Payment.Error");
			}
		}

		session.removeAttribute("acceptedPayment");
		return model; 
	}

	private ModelAndView goToMerchantErrorPage(String message) {
		ModelAndView model = new ModelAndView(); 
		ArrayList<String> errors = new ArrayList<String>();
		errors.add(message);
		model.addObject("errors", errors);
		model.setViewName("Merchant.Organization/Payment.Error");
		return model;
	}

	@RequestMapping(value = {"/getallpaymentfields"}, method = RequestMethod.GET)
	public void getallpaymentfields(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException{
		byte[] encrypted = null;
		String value = request.getParameter("value");
		String username = (String)session.getAttribute("USERNAME");

		if(value != null && value.length() != 0){
			EncryptDecryptModule encDecModule = new EncryptDecryptModule();
			SerialDeserializerModule module = new SerialDeserializerModule();
			pkiDatabaseHandler databaseHandler = new pkiDatabaseHandler();

			EncryptionKeyPair pair;
			try {
				pair = databaseHandler.getKeysFromDatabase(username);
			} catch (Exception e1) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

			PublicKey pubkey = null;
			try {
				pubkey = module.stringToPublicKey(pair.getPubKeyStr());
			} catch (ClassNotFoundException e) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

			try {
				encrypted = encDecModule.encrypt(value, pubkey);
			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
		}
		else{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		byte[] encoded = Base64.encode(encrypted);
		response.setContentType("text/plain");  
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(new String(encoded));
	}
}
