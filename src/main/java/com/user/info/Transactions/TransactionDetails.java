package com.user.info.Transactions;

public class TransactionDetails {
	
	private String userName;
	private String transactionId;
	private String transactionAmount;
	private String newAmount;
	private String sourceAccount;
	private String destAccount;
	private String dateandTime;
	private String transferType;
	private String status;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getNewAmount() {
		return newAmount;
	}
	public void setNewAmount(String newAmount) {
		this.newAmount = newAmount;
	}
	public String getSourceAccount() {
		return sourceAccount;
	}
	public void setSourceAccount(String sourceAccount) {
		this.sourceAccount = sourceAccount;
	}
	public String getDestAccount() {
		return destAccount;
	}
	public void setDestAccount(String destAccount) {
		this.destAccount = destAccount;
	}
	public String getDateandTime() {
		return dateandTime;
	}
	public void setDateandTime(String dateandTime) {
		this.dateandTime = dateandTime;
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

}