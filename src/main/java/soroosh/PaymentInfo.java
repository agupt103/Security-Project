package soroosh;

import java.sql.Date;

public class PaymentInfo {
	private String username;
	private String sourceAccountNumber;
	private String destinationAccountNumber;
	private double amount;
	private Date date;
	private String transferType;
	private String status;
	private int paymentId;
		
	public PaymentInfo(String username, String sourceAcountNum, String destinationAccountNum, double amount){
		this.username = username;
		this.sourceAccountNumber = sourceAcountNum;
		this.destinationAccountNumber = destinationAccountNum;
		this.amount = amount;
	}
	
	public PaymentInfo(int paymentId, String username, String sourceAcountNum, String destinationAccountNum, double amount, 
			Date date){
		this.setPaymentId(paymentId);
		this.username = username;
		this.sourceAccountNumber = sourceAcountNum;
		this.destinationAccountNumber = destinationAccountNum;
		this.amount = amount;
		this.date = date;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSourceAccountNumber() {
		return sourceAccountNumber;
	}
	public void setSourceAccountNumber(String sourceAccountNumber) {
		this.sourceAccountNumber = sourceAccountNumber;
	}
	public String getDestinationAccountNumber() {
		return destinationAccountNumber;
	}
	public void setDestinationAccountNumber(String destinationAccountNumber) {
		this.destinationAccountNumber = destinationAccountNumber;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	
	
}
