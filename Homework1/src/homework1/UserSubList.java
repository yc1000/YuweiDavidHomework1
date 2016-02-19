package homework1;

import java.util.ArrayList;

import com.google.appengine.api.users.User;

public class UserSubList{
	public static ArrayList<User> userList = new ArrayList<User>();
	User user;
	public UserSubList() {}
	
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
