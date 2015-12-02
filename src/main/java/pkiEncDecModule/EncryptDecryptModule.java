package pkiEncDecModule;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;

public class EncryptDecryptModule {
	final static String databaseEncryptionAlgo = "RSA";

	public EncryptionKeyPair generatekeysmodule() throws Exception {
		EncryptionKeyPair pairOfKeys = null;
		
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(1024, new SecureRandom());
		KeyPair keyPair = kpg.generateKeyPair();
		PrivateKey privKey = keyPair.getPrivate();
		PublicKey pubKey = keyPair.getPublic();
		
		SerialDeserializerModule module = new SerialDeserializerModule();
		String publicKeyStr = module.publicKeyToString(pubKey);
		String privateKeyStr = module.privateKeyToString(privKey);
				
		pairOfKeys = new EncryptionKeyPair(privKey, pubKey, privateKeyStr, publicKeyStr);
		return pairOfKeys;
	}
	
	public byte[] encrypt(String data, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance(databaseEncryptionAlgo);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] bytes = data.getBytes("UTF-8");
		byte[] encrypted = cipher.doFinal(bytes);
		return encrypted;
	}

	public byte[] decrypt(byte[] encryptedData, PrivateKey privateKey) 
			throws Exception {
		Cipher cipher = Cipher.getInstance(databaseEncryptionAlgo);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] decrypted = cipher.doFinal(encryptedData);
		return decrypted;
	}
	
}
