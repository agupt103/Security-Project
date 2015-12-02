package utilities;

import java.util.Random;

public class MyStringRandomGen {

	private static final String CHAR_LIST =
			"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static final int RANDOM_STRING_LENGTH = 10;

	/**
	 * This method generates random string
	 * @return
	 */
	public String generateRandomString(){

		StringBuffer randStr = new StringBuffer();
		for(int i=0; i<RANDOM_STRING_LENGTH; i++){
			int number = getRandomNumber();
			char ch = CHAR_LIST.charAt(number);
			randStr.append(ch);
		}
		return randStr.toString();
	}

	/**
	 * This method generates random numbers
	 * @return int
	 */
	private int getRandomNumber() {
		int randomInt = 0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(CHAR_LIST.length());
		if (randomInt - 1 == -1) {
			return randomInt;
		} else {
			return randomInt - 1;
		}
	}


	//Code to generate code for Savings Number
	public long getSavingAccountNo()
	{
		Random rng = new Random(); 
		long first14 = (rng.nextLong() % 100000000000000L) + 5200000000000000L;
		return first14;

	}

	//Code to generate code for Checking Number
	public long getCheckingAccountNo()
	{
		Random rng = new Random(); 
	
		long first14 = (rng.nextLong() % 100000000000000L) + 6200000000000000L;
		
		return first14;

	}

	//Code to generate code for Credit Card Number
	public long getCreditcardAccountNo()
	{
		Random rng = new Random(); 
		
		long first14 = (rng.nextLong() % 100000000000000L) + 4200000000000000L;
		
		return first14;

	}
	//Code to generate code for Debit Card Number
	public Long getDebitcardAccountNo()
	{
		Random rng = new Random(); 
		long first14 = (rng.nextLong() % 100000000000000L) + 3200000000000000L;
		return first14;

	}
	//Code to generate code for Loan Number
	public Long getLoanAccountNo()
	{
		Random rng = new Random(); 
		long first14 = (rng.nextLong() % 100000000000000L) + 7200000000000000L;
		return first14;


	}

	//Code to generate code for Temporary user number
	public String gettmpUsername()
	{
		Random rng = new Random(); 
		String tempusername;
		long first14 = (rng.nextLong() % 10000000000L) + 220000000000L;
		tempusername = String.valueOf(first14);
		return tempusername;

	}
}
