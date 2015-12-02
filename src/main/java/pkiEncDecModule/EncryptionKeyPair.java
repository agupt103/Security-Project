package pkiEncDecModule;

import java.security.PrivateKey;
import java.security.PublicKey;

public class EncryptionKeyPair {
	
	private PrivateKey priveKey;
	private PublicKey pubKey;
	private String priveKeyStr;
	private String pubKeyStr;
		
	
	public EncryptionKeyPair(String priveKeyStr, String pubKeyStr){
		this.priveKeyStr = priveKeyStr;
		this.pubKeyStr = pubKeyStr;
	}
	
	public EncryptionKeyPair(PrivateKey priveKey, PublicKey pubKey, 
			String priveKeyStr, String pubKeyStr){
		this.priveKeyStr = priveKeyStr;
		this.pubKeyStr = pubKeyStr;
		this.priveKey = priveKey;
		this.pubKey = pubKey;
	}

	public void setEncryptionKeyPair(PrivateKey priveKey, PublicKey pubKey){
		this.priveKey = priveKey;
		this.pubKey = pubKey;
	}
	
	public String getPriveKeyStr() {
		return priveKeyStr;
	}

	public void setPriveKeyStr(String priveKeyStr) {
		this.priveKeyStr = priveKeyStr;
	}

	public String getPubKeyStr() {
		return pubKeyStr;
	}

	public void setPubKeyStr(String pubKeyStr) {
		this.pubKeyStr = pubKeyStr;
	}

	public PrivateKey getPriveKey() {
		return priveKey;
	}

	public void setPriveKey(PrivateKey priveKey) {
		this.priveKey = priveKey;
	}

	public PublicKey getPubKey() {
		return pubKey;
	}

	public void setPubKey(PublicKey pubKey) {
		this.pubKey = pubKey;
	}
	
}
