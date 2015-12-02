package passwordSaltModules;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class SaltModule {

	public String getHashedPassword(String password){
		String salt = BCrypt.gensalt(10);
		String passwordHash = BCrypt.hashpw(password, salt);
		return passwordHash;
	}
	
	public boolean isPasswordValid(String enteredPass, String hashedPass){
		return BCrypt.checkpw(enteredPass, hashedPass);
	}
	
}
