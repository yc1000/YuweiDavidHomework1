package homework1;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.http.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import java.util.Date;


@SuppressWarnings("serial")
public class Post extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String guestbookName = req.getParameter("guestbookName");
		Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		Date date = new Date();
		Entity post = new Entity("Greeting", guestbookKey);
		post.setProperty("user", user);
		post.setProperty("date", date);
		post.setProperty("title", title);
		post.setProperty("content", content);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(post);
		resp.sendRedirect("/homework1.jsp?guestbookName=" + guestbookName);

    }

}