package homework1;

import java.util.ArrayList;
import java.util.Date;

import com.google.appengine.api.users.User;

public class UserSubList{
	public static ArrayList<User> userList = new ArrayList<User>();
	public static String CronMessage;
	User user;
	public UserSubList() {}
	
	public static void addContent(String title, String body, User user, Date date) {
		CronMessage += "\nOn " + date + " " + user + " posted " + 
					   "\nTitle:\n" + title + 
					   "\nMessage:\n" + body + "\n";
	}
	
	public static void clearMessage() {
		CronMessage = "";
	}
	
	public void setUser(User user) {
		try {
			if(user != null) {
				this.user = user;
			}
		} catch(Exception e) {
			System.out.println("oops");
		}
		System.out.println(userList);
	}
	
	public boolean editList() {
		System.out.println(this.user);
		User newOrOld = this.user;
		boolean isNewUser = true;
		for(User u: userList) {
			if(u.equals(newOrOld)) {
				isNewUser = false;
			}
		}
		if(isNewUser) {
			userList.add(newOrOld);
			return true;
		} else {
			userList.remove(newOrOld);
			return false;
		}
	}
}
