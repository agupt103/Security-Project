package utilities;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

public class TimeUtility {

//	public static String generateDateMethod()
//	{
//		String correct_time = "";
//		long returnTime;  
//		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//		String TIME_SERVER = "time-a.nist.gov";   
//		NTPUDPClient timeClient = new NTPUDPClient();
//		InetAddress inetAddress = null;
//		try {
//			inetAddress = InetAddress.getByName(TIME_SERVER);
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
//		TimeInfo timeInfo = null;
//		try {
//			timeInfo = timeClient.getTime(inetAddress);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
//		Date time = new Date(returnTime);
//		correct_time = dateFormat.format(time).toString();
//		return correct_time;
//
//	}
//	
//	public static Date generateDate()
//	{
//		long returnTime;  
//		String TIME_SERVER = "time-a.nist.gov";   
//		NTPUDPClient timeClient = new NTPUDPClient();
//		InetAddress inetAddress = null;
//		try {
//			inetAddress = InetAddress.getByName(TIME_SERVER);
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
//		TimeInfo timeInfo = null;
//		try {
//			timeInfo = timeClient.getTime(inetAddress);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
//		Date time = new Date(returnTime);
//		return time;
//	}
	
	public static String generateSysDateMethod()
	{
		String system_time = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		system_time = dateFormat.format(date).toString();
		return system_time;
	}

	public static int generateSysYearMethod()
	{
		String system_year = "";
		int int_sys_year = 0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		system_year = dateFormat.format(date).toString();
		int_sys_year = Integer.parseInt(system_year);
		return int_sys_year;
	}

	public static int generateSysMonthMethod()
	{
		String system_month = "";
		int int_sys_month = 0;
		DateFormat dateFormat_sec = new SimpleDateFormat("MM");
		Date date = new Date();
		system_month = dateFormat_sec.format(date).toString();
		int_sys_month =  Integer.parseInt(system_month);
		return int_sys_month;
	}

	public static int generateSysSecondsMethod()
	{
		String system_time = "";
		int int_sys_time = 0;
		DateFormat dateFormat_sec = new SimpleDateFormat("ss");
		Date date = new Date();
		system_time = dateFormat_sec.format(date).toString();
		int_sys_time =  Integer.parseInt(system_time);
		return int_sys_time;
	}
	
	public static int generateSysMinutesMethod()
	{
		String system_time = "";
		int int_sys_time = 0;
		DateFormat dateFormat_sec = new SimpleDateFormat("mm");
		Date date = new Date();
		system_time = dateFormat_sec.format(date).toString();
		int_sys_time =  Integer.parseInt(system_time);
		return int_sys_time;
	}
	
	public static int generateSysHoursMethod()
	{
		String system_time = "";
		int int_sys_time = 0;
		DateFormat dateFormat_sec = new SimpleDateFormat("HH");
		Date date = new Date();
		system_time = dateFormat_sec.format(date).toString();
		int_sys_time =  Integer.parseInt(system_time);
		return int_sys_time;
	}

	public static long getDifferenceinSeconds(String modelTime,
			String otpGenerateTime) {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(modelTime);
			d2 = format.parse(otpGenerateTime);

			//in milliseconds
			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			return (diffSeconds+(diffMinutes*60)+(diffHours*60*60)+(diffDays*24*60*60));

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

}
