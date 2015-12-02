package utilities;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.sql.ResultSet;
import java.sql.SQLException;

import sun.misc.BASE64Encoder;
 
public class GenerateRSAKeys{
 
    public void sendKey(String username) throws Exception
    {   
    	try
    	{
    		GenerateRSAKeys generateRSAKeys = new GenerateRSAKeys(); 
    		OtpUtility otpUtility = new OtpUtility();
            generateRSAKeys.generate(username);        
            ResultSet data =getPrivKeyandOtp(username);
            if(data.next())
            {
            	String priv = new String(data.getBytes(1), "UTF-8");
            	String otp = new String(data.getBytes(2), "UTF-8");
                String phone = new String(data.getBytes(3), "UTF-8");
                String email = new String(data.getBytes(4), "UTF-8");
                
                if(priv!=null && otp!=null && phone!=null && email!=null)
                {
                	String sendTo = otp+priv;
                	otpUtility.sendEmail(sendTo, email);
                    otpUtility.sendMessage(otp, "+1"+phone);
                }
            }            
    	}  
    	
        catch (Exception e) 
		{
			throw e;
		}        
    }
 
    public void generate(String username)
    {     	
        try 
        {   
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            KeyPairGenerator generator = KeyPairGenerator.getInstance("DSA", "SUN");
            BASE64Encoder b64 = new BASE64Encoder(); 
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            generator.initialize(1024, random); 
            KeyPair pair = generator.generateKeyPair();
            Key pubKey = pair.getPublic();
            Key privKey = pair.getPrivate();           
            
            String publickey = b64.encode(pubKey.getEncoded());
            String privatekey = b64.encode(privKey.getEncoded());            
            String otp = generateEncryptionString(4);
            
            pkiConn objConn = new pkiConn();
            byte[] priv = privatekey.getBytes();
            byte[] pub = publickey.getBytes();
            byte[] o = otp.getBytes();
            objConn.insertData(username, priv, pub,o);
            
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }      
        
    }
    
    public static String generateEncryptionString(int captchaLength) 	 
    {
		String saltChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuffer captchaStrBuffer = new StringBuffer();
		java.util.Random rnd = new java.util.Random();
		while (captchaStrBuffer.length() < captchaLength)
		{
			int index = (int) (rnd.nextFloat() * saltChars.length());
			captchaStrBuffer.append(saltChars.substring(index, index+1));
		}
		return captchaStrBuffer.toString();

	}
    
    public static ResultSet getPrivKeyandOtp(String username) throws SQLException
    {    	
    	try
    	{
    		pkiConn objConn = new pkiConn();
            return objConn.getData(username);
    	}
    	catch (Exception e) 
		{
			throw e;
		}    	
    }
}
 




