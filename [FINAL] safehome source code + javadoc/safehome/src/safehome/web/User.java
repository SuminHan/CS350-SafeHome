package safehome.web;

/**
 * User class used for login.
 * @author SummerSnow
 *
 */
public class User {
	String userID;
	String password;
	String password2;
	User(String id, String pw, String pw2){
		userID = id;
		password = pw;
		password2 = pw2;
	}
	public boolean verifyLogin1(String id, String pw){
		if(userID.equals(id) && password.equals(pw))
			return true;
		return false;
	}
	public boolean verifyLogin2(String pw2){
		if(password2.equals(pw2))
			return true;
		return false;
	}
}
