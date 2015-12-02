package databaseEncryptionModules;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class EncDecModule {
	
	final static String databaseEncryptionAlgo = "RSA";
	PrivateKey privateKey = null;
	PublicKey publicKey = null;
	
	public EncDecModule() throws ClassNotFoundException, IOException{
		deserilizePublicKey();
		deserilizePrivateKey();
	}
	
	public byte[] encrypt(String data) throws Exception {
		if(this.publicKey == null)
			deserilizePublicKey();
		Cipher cipher = Cipher.getInstance(databaseEncryptionAlgo);
		cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
		byte[] bytes = data.getBytes("UTF-8");
		byte[] encrypted = cipher.doFinal(bytes);
		return encrypted;
	}

	public byte[] decrypt(byte[] encryptedData) throws Exception 
	{
		if(this.privateKey == null)
		{
			deserilizePrivateKey();
		}
			
		Cipher cipher = Cipher.getInstance(databaseEncryptionAlgo);
		cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
		byte[] decrypted = cipher.doFinal(encryptedData);
		return decrypted;
	}

	private void deserilizePublicKey() throws IOException, ClassNotFoundException{
		InputStream inPublic = new FileInputStream("\\C:\\Keys\\public.ser");
		ObjectInputStream pubObjInput = new ObjectInputStream(inPublic);
		this.publicKey = (PublicKey)pubObjInput.readObject();
	}
	
	private void deserilizePrivateKey() throws IOException, ClassNotFoundException
	{
		InputStream inPrivate = new FileInputStream("\\C:\\Keys\\private.ser");
		ObjectInputStream privObjInput = new ObjectInputStream(inPrivate);
		this.privateKey = (PrivateKey)privObjInput.readObject();
	}
}
