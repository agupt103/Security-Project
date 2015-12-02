package authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
 
@Repository
public class UserDao {
 
    public User loadUserByUsername(final String username) {
        User user = new User();
        
        if(username.equalsIgnoreCase("soroosh")){
        	user.setFirstName("Soroosh");
        	user.setLastName("Gholami");
        	user.setUsername("user");
        	user.setPassword("1111");
        	Role r = new Role();
        	r.setName("ROLE_ADMIN");
        	List<Role> roles = new ArrayList<Role>();
        	roles.add(r);
        	user.setAuthorities(roles);
        }
        
        else if(username.equalsIgnoreCase("Soogi")){
        	user.setFirstName("Soogi");
        	user.setLastName("Gholami");
        	user.setUsername("user");
        	user.setPassword("123456");
        	Role r = new Role();
        	r.setName("ROLE_USER");
        	List<Role> roles = new ArrayList<Role>();
        	roles.add(r);
        	user.setAuthorities(roles);
        }
        
        return user;
    }
}