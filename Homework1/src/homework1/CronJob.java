package homework1;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)  throws IOException {
		Properties props = new Properties();
		hello+=10;
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world " + hello + " times");
		
        Session session = Session.getDefaultInstance(props, null);

        String msgBody = UserSubList.CronMessage;
        UserSubList.clearMessage();
        
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("dailynews@yuwei-testing.appspotmail.com", "Admin"));
            for(User u: UserSubList.userList) {
            	msg.addRecipient(Message.RecipientType.TO, new InternetAddress(u.toString(), "Valued User"));
            }
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress("yc1000@gmail.com", "Mr. User"));
            msg.setSubject("Daily Update");
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
}
