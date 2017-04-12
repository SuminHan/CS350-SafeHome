package safehome.web;

import javax.swing.JOptionPane;


/**
 * Higher class for web interface.
 * Contains userlist, views, and security zone information.
 * @author SummerSnow
 *
 */
public class WebService {
	public static User[] userList;
	public static int numUsers = 0;
	public static Surveillance survView;
	public static AboutView aboutView;
	public static SecurityZone sz;
	
	public WebService(){
		userList = new User[10];
		addUser("sky", "helloworld", "1379");
		addUser("kaist", "cs%350!!!", "1234");
		addUser("hello", "mynewsafehome", "4321");

		
		survView = new Surveillance();
		aboutView = new AboutView();
		sz = new SecurityZone();
	}
	
	public void addUser(String id, String pw, String pw2){
		userList[numUsers++] = new User(id, pw, pw2);
	}

	public boolean LoginProcess(String id, String pw){
		for(int i = 0; i < numUsers; i++){
			if(userList[i].verifyLogin1(id, pw)){
				String value = JOptionPane.showInputDialog("Password2(init: 1379) : ");
				if(userList[i].verifyLogin2(value))
					return true;
				return false;
			}
		}
		return false;
	}
}
