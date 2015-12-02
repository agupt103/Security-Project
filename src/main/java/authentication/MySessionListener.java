
package authentication;

import handlers.adminHandlers.ModifyUsersHandler;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
 
public class MySessionListener implements HttpSessionListener {
 
	private static final Logger LOG = Logger.getLogger(MySessionListener.class);
	public void sessionCreated(HttpSessionEvent event) {
		LOG.error("Session created");
	}
 
	public void sessionDestroyed(HttpSessionEvent event) {
        String s = (String)event.getSession().getAttribute("USERNAME");
        if (s.isEmpty()||s!=null)
        {
        	ModifyUsersHandler handler = new ModifyUsersHandler();
        	handler.setloggedin(s);
        }
	}
 
}