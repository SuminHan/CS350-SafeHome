package safehome.web;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import safehome.MainDemo;

/**
 * Login Screen
 * @author SummerSnow
 *
 */
public class Login extends JFrame implements ActionListener{
	private static final long serialVersionUID = 2431767052226909107L;
	JTextField userText;
	JPasswordField passwordText;
	
	public WebMainView securityZone;
	public Login()
	{
		super("Web Login");
		securityZone = new WebMainView();
		
		setSize(300, 170);
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width), screenSize.height/2);
		getContentPane().setLayout(null);
		setResizable(false);

		
		JPanel displayPanel = new JPanel();
		displayPanel.setLayout(null);

		JLabel userLabel = new JLabel("User");
		userLabel.setBounds(10, 10, 80, 25);
		displayPanel.add(userLabel);

		userText = new JTextField("sky", 20);
		userText.setBounds(100, 10, 160, 25);
		displayPanel.add(userText);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 40, 80, 25);
		displayPanel.add(passwordLabel);

		passwordText = new JPasswordField("helloworld", 20);
		passwordText.setBounds(100, 40, 160, 25);
		displayPanel.add(passwordText);
		
		JButton loginButton = new JButton("login");
		loginButton.setBounds(180, 80, 80, 25);
		displayPanel.add(loginButton);
		loginButton.addActionListener(this);
		
		getContentPane().add(displayPanel);
		displayPanel.setVisible(true);

		displayPanel.setBounds(15, 15, 260, 150);
		getContentPane().add(displayPanel);
	}
	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		if((evt.getActionCommand().equals("login")))
		{
			if(MainDemo.webService.LoginProcess(userText.getText(), passwordText.getText())){
				securityZone.setVisible(true);
				this.setVisible(false);
			}
			else{
				JOptionPane.showMessageDialog(this, "Check ID or Password");
			}
		}
	}

}
