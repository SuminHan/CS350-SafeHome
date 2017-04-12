package safehome;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import safehome.web.Login;
import safehome.web.WebService;



/**
 * This class shows a main dialog of a whole demo program.
 * When a user runs <code>MainDemo</code> class, <code>MainDemo</code> shows two buttons, 
 * Control panel and web interface. User can use application by click on buttons.
 * @author SummerSnow
 *
 */
public class MainDemo extends JFrame implements ActionListener 
{
	// instance of three test cases
	public static ControlSoftware controlSoftware;
	public static Login login;
	public static SensorControl sensorController;
	public static SensorTest sensorTest;
	public static CameraTest cameraTest;
	public static Alarm alarmController;
	public static WebService webService;
	
	
	
	
	/**
	 * Initialize <code>sensorController, alarmController, controlSoftware, 
	 * webService, sensorTest, cameraTest, login, sensorController, sensorTest</code>
	 * which are all used for both control panel and web interface.
	 * The screen will be shown up at the center of the screen by calculating size.
	 * If user close this main frame, the entire system will be turned off.
	 * 
	 */
	public MainDemo() 
	{
		super("Hardware Device Demo");
		sensorController = new SensorControl();
		alarmController = new Alarm();
		webService = new WebService();
		controlSoftware = new ControlSoftware();
		sensorTest = new SensorTest();
		cameraTest = new CameraTest(4);
		login = new Login();
		sensorController.start(); //start auto-refresh
		sensorTest.setVisible(true);
		
		setSize(200, 215);
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);
		getContentPane().setLayout(null);
		setResizable(false); 
		
		// Terminate the program when the MainDemo dialog is closed
		addWindowListener
		(
				new WindowAdapter()
				{
					public void windowClosing(WindowEvent e)
					{
						System.exit(0);
					}
				}
		);
		
		// make buttons
		JButton ControlPanel = new JButton("Control Panel");
		ControlPanel.addActionListener(this);
		
		JButton WebService = new JButton("Web Service");
		WebService.addActionListener(this);
		
		// attach buttons to dialog
		JPanel displayPanel = new JPanel(new GridLayout(2, 1));
		displayPanel.add(ControlPanel);
		displayPanel.add(WebService);
		displayPanel.setBounds(15, 15, 160, 150);
		getContentPane().add(displayPanel);
		
	}
	
	/**
	 * Invoked when an action occurs. When the "Control Panel" button is pressed, 
	 * the demo program shows control panel dialog.
	 * When the "Web Service" button is pressed, 
	 * the demo program shows login screen to enter web interface.
	 * <p>
	 * Pre-condition: A user clicks on one of three buttons "Control Panel", and "Web Service"
	 * <p>
	 * Post-condition: The corresponding dialog of the test cases is pop up.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event)
	{
		if((event.getActionCommand().equals("Control Panel")))
		{
			controlSoftware.setVisible(true);
		}
		else if((event.getActionCommand().equals("Web Service")))
		{
			login.setVisible(true);
		}
	}
	
	/*
	 * meaningless member variable in this application
	 * prevent java compiler warning message
	 */

	static final long serialVersionUID = 1876534;
}














