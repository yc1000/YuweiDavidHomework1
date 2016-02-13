//http://1-dot-ee461P1.appspot.com/ofyguestbook.jsp
package guestbook;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OfySignGuestbookServlet extends HttpServlet {
    static {
    	ObjectifyService.register(Greeting.class);
    }
    
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
    	//String guestbookName = req.getParameter("guestbookName");
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        
        String content = req.getParameter("content");
        Date date = new Date();
        Greeting greeting = new Greeting(user, content);
        
        ofy().save().entity(greeting).now();
        resp.sendRedirect("/ofyguestbook.jsp");
    }
}