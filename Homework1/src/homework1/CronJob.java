package homework1;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;

@SuppressWarnings("serial")
public class CronJob extends HttpServlet{
	int hello = 0;
	
	/*@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)  throws IOException {
		Properties props = new Properties();
		hello++;
		
        Session session = Session.getDefaultInstance(props, null);

        String msgBody = UserSubList.CronMessage;
        UserSubList.clearMessage();
        
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("admin@example.com", "Example.com Admin"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress("yc1000@gmail.com", "Mr. User"));
            msg.setSubject("Your Example.com account has been activated");
            msg.setText(msgBody);
            Transport.send(msg);

        } catch (AddressException e) {
			e.printStackTrace();
        } catch (MessagingException e) {
			e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
*/
	@SuppressWarnings("deprecation")
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)  throws IOException {
		Properties props = new Properties();
		
        Session session = Session.getDefaultInstance(props, null);
        
    	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    	Key guestbookKey = KeyFactory.createKey("Guestbook", "default");

    	Query query = new Query("Greeting", guestbookKey).addSort("date", Query.SortDirection.DESCENDING);
    	List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(1000));
        String update = "";
    	for(Entity e: greetings) {
    		Date edate = (Date) e.getProperty("date");
    		Date yesterday = new Date();
    		yesterday.setDate(yesterday.getDay() - 1);
    		if(edate.after(yesterday)) {
    			update += "\nOn " + e.getProperty("date") + " " + 
    				   e.getProperty("user") + " posted " + 
 					   "\nTitle:\n" + e.getProperty("title") + 
 					   "\nMessage:\n" + e.getProperty("content") + "\n";
    		}
    	}
    	
    	
        //String msgBody = UserSubList.CronMessage;
        boolean retry;
        do {
        	retry = false;
            try {
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress("dailynews@yuwei-testing.appspotmail.com", "Admin"));
                for(User u: UserSubList.userList) {
                	msg.addRecipient(Message.RecipientType.TO, new InternetAddress(u.toString(), "Valued User"));
                }
                msg.addRecipient(Message.RecipientType.TO,
                                 new InternetAddress("yc1000@gmail.com", "Mr. User"));
                msg.setSubject("Daily Update");
                msg.setText(update);
                Transport.send(msg);

            } catch (Exception e) {
            	retry = true;
            }
        } while(retry);

        UserSubList.clearMessage();
	}
}
