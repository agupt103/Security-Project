package pkiEncDecModule;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.security.crypto.codec.Base64;

public class SerialDeserializerModule {

	public String publicKeyToString(PublicKey key) throws IOException {
		String encoded = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(key);
		objectOutputStream.close();
		encoded = new String(Base64.encode(byteArrayOutputStream.toByteArray()));
		return encoded;
	}

	public PublicKey stringToPublicKey(String string) 
			throws IOException, ClassNotFoundException {
		byte[] bytes = Base64.decode(string.getBytes());
		ObjectInputStream objectInputStream = new ObjectInputStream
				(new ByteArrayInputStream(bytes) );
		PublicKey keyObject = (PublicKey) objectInputStream.readObject();
		return keyObject;
	}
	
	public String privateKeyToString(PrivateKey key) throws IOException {
		String encoded = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(key);
		objectOutputStream.close();
		encoded = new String(Base64.encode(byteArrayOutputStream.toByteArray()));
		return encoded;
	}

	public PrivateKey stringToPrivateKey(String string) 
			throws IOException, ClassNotFoundException {
		byte[] bytes = Base64.decode(string.getBytes());
		ObjectInputStream objectInputStream = new ObjectInputStream
				(new ByteArrayInputStream(bytes) );
		PrivateKey keyObject = (PrivateKey) objectInputStream.readObject();
		return keyObject;
	}

}
