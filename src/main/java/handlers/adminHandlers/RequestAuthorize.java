package handlers.adminHandlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import databseHandler.MySQLAccess;

public class RequestAuthorize {

	static MySQLAccess sql;

	static { 
		sql = new MySQLAccess();
	}

	public ResultSet getRequestHandler(String User) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return (ResultSet)sql.authRequest(User);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ResultSet getTransactionHandler(String status,int critical,String payment) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return (ResultSet)sql.authTransaction(status,critical,payment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ResultSet getModDelHandler(String modifyStatus,String payment,int critical) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return (ResultSet)sql.modifyDeleteTransaction(modifyStatus,payment,critical);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> checkStatusOfId(String[] rID) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ArrayList<String> status = new ArrayList<String>();
		try {
			status = sql.checkStatus(rID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public void deleteTransaction(String[] rID) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.deleteTransactions(rID);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void modifyTransaction(String[] rID,String status) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.modifyTransactions(rID,status);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	


	public void updateRequestStatus(String rStatus, String[] rID) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.updateStatus(rStatus, rID);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void approveTransaction(String rStatus,double balance, String[] rID) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.approveTransactions(rStatus,balance, rID);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void approveModifyTransaction(Double newAmount,String rStatus,double balance, String[] rID) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.approveModifyTransactions(newAmount,rStatus,balance, rID);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void approveModifySourceTransaction(Double newAmount,String rStatus,double balance, String[] rID) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.approveModifySourceTransactions(newAmount,rStatus,balance, rID);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void rejectTransaction(String rStatus,double balance, String[] rID) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.rejectTransactions(rStatus,balance, rID);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Double getBalance(String[] rID) {
		double result = 0.0;
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			result = sql.getBalanceAmount(rID);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String getDestinationAccount(String rID) {
		String result = "";
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			result = sql.getDestinationAccountNumber(rID);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Boolean checkAccountNumber(String accountNumber) {
		Boolean result = true;
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			result = sql.checkValidAccount(accountNumber);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String getSourceAccount(String rID) {
		String result = "";
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			result = sql.getSourceAccountNumber(rID);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String getTransactionType(String rID) {
		String result = "";
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			result = sql.getTransactionType(rID);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public Double getDestinationBalance(String accountNumber) {
		double result = 0.0;
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			result = sql.getBalanceDestinationAccount(accountNumber);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Double getSourceBalance(String accountNumber) {
		double result = 0.0;
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			result = sql.getBalanceSourceAccount(accountNumber);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Boolean checkSameDestination(String[] rID) {
		boolean result = false;
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			result = sql.checkSameAccountNumber(rID);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Boolean checkSameSource(String[] rID) {
		boolean result = false;
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			result = sql.checkSameSourceAccountNumber(rID);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void updateTransactions(String rStatus, String[] rID) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.updateTransactionData(rStatus, rID);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet checkRequestStatus(String rID) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return (ResultSet)sql.checkStatus(rID);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
