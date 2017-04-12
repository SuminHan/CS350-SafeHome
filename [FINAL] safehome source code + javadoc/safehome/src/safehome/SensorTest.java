package safehome;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import safehome.web.DeviceUnit;
import safehome.web.SecurityZone;

/*
 * CLASS NAME : SensorTest 
 * DESCRIPTION
 * 		A dialog to test a set of sensors interactively 
 * 		This class maintains 5 winDoorSensors and 5 motionDetectors
 * 		The dialog shows status of sensors and provide enable/disable buttons for sensors
 * 		If the sensor state is changed asynchronously, 
 * 		the refresh button is needed to update sensor status table
 * PRECONDITION : From main dialog, "Sensor" button was chosen
 * PARAMETER : none
 * RETURN VALUE : none
 * USAGE : click a button among "refresh", "Enable All", "Disable All" buttons
 * EXAMPLE : click refresh button to update sensor status table
 * POSTCONDITION : none
 * NOTES : Need Sensor Test dialog to change sensor's read() value
 */ 
/**
 * <code>SensorTest</code> class is a simple dialog to test a set of sensors interactively. 
 * This class maintains 5 door sensors and 5 motion detectors. The dialog shows status of sensors and provides
 * enable/disable buttons for sensors. If the sensor state is changed asynchronously, 
 * the new state is not updated before the refresh button is pressed.
 * <p>
 * The dialog of <code>SensorTest</code> looks like this:<p>
 * <img src="../../doc-files/sensors.PNG">
 * <p>
 * 
 * @author cs350 TA
 *
 */
public class SensorTest extends JFrame implements ActionListener
{	
	
	JTextField[] testW;	// used to display result of DeviceWinDoorSensor.test()
	JTextField[] readW;	// used to display result of DeviceWinDoorSensor.read()
		
	JTextField[] testM;	// used to display result of DeviceMotionDetector.test()
	JTextField[] readM;	// used to display result of DeviceMotionDetector.read()
	
	boolean[] dsensor = new boolean[10];
	
	
	/*
	 * NAME : SensorTest (constructor)
	 * DESCRIPTION
	 * 		Initiate sensors instances
	 * 		and make layout of sensor status table and buttons
	 * PRECONDITION
	 * 		The main instantiates MainDemo class and MainDemo instantiates this class
	 * 		in consecutively
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION : The layout of this dialog is made
	 * NOTES : none
	 */ 
	/**
	 * Constructs the sensor status dialog. Constructor initiates sensor 
	 * instances and make layout of sensor status table and buttons. 
	 * 
	 * <p>
	 * Pre-condition: None
	 * <p>
	 * Post-condition: The sensor status frame is ready. It is initially invisible.
	 * <p>
	 */
	public SensorTest()
	{
		super("Sensor Status");
		setSize(455, 400);
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(0, (screenSize.height - frameSize.height));
		getContentPane().setLayout(null);
		setResizable(false);
		
		// instanciate sensor and text field for status
		testW = new JTextField[SensorControl.MAX_SENSOR];
		readW = new JTextField[SensorControl.MAX_SENSOR];
		
		testM = new JTextField[SensorControl.MAX_SENSOR];
		readM = new JTextField[SensorControl.MAX_SENSOR];
		
		for(int i=0; i<SensorControl.MAX_SENSOR; i++)
		{
			testW[i] = new JTextField();
			readW[i] = new JTextField();
			
			testW[i].setEditable(false);
			readW[i].setEditable(false);
			
			testM[i] = new JTextField();
			readM[i] = new JTextField();
			
			testM[i].setEditable(false);
			readM[i].setEditable(false);
		}
		
		
		// make buttons
		
		/*
		JButton buttonrefresh = new JButton("refresh");
		buttonrefresh.addActionListener(this);
		JButton buttonEnable = new JButton("Enable All");
		buttonEnable.addActionListener(this);
		JButton buttonDisable = new JButton("Disable All");
		buttonDisable.addActionListener(this);
		*/
		// attach text field to dialog
		JPanel displayPanel = new JPanel(new GridLayout(13, 3));
		displayPanel.add(new Label("Type and ID"));
		displayPanel.add(new Label("Test"));
		displayPanel.add(new Label("Read"));
		
		// attach test fields of array to dialog
		for(int i=0; i<SensorControl.MAX_SENSOR; i++)
		{
			displayPanel.add(new Label("WinDoor "+MainDemo.sensorController.getIDWinDoorSensor(i)));
			displayPanel.add(testW[i]);
			displayPanel.add(readW[i]);
		}
		for(int i=0; i<SensorControl.MAX_SENSOR; i++)
		{
			displayPanel.add(new Label("Motion "+MainDemo.sensorController.getIDMotionDetector(i)));
			displayPanel.add(testM[i]);
			displayPanel.add(readM[i]);
		}
		
		/*
		displayPanel.add(new Label(""));
		displayPanel.add(new Label(""));
		displayPanel.add(new Label(""));
		
		// attach buttons to dialog
		displayPanel.add(buttonrefresh);
		displayPanel.add(buttonEnable);
		displayPanel.add(buttonDisable);
		*/
		displayPanel.setBounds(25, 25, 400, 370);
		getContentPane().add(displayPanel);
		
		// initially one time update is necessary to fill up sensor status table
		refresh();
	}
	
	/*
	 * NAME : actionPerformed
	 * DESCRIPTION
	 * 		This function is the action listener(call back function) of three buttons
	 * 		refresh - simply update status information
	 * 		Enable All - call enable() of all sensors
	 * 		Disable All - call disable() of all sensors
	 * PRECONDITION : User click on any button
	 * PARAMETER : event - ActionEvent that carrying what button was pressed
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE
	 * 		If refresh button is clicked by user, 
	 * 		the information in sensor status table will be updated
	 * POSTCONDITION
	 * 		Maybe sensor status be changed and
	 * 		sensor status table is updated
	 * NOTES : none
	 */
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	/** 
	 * Invoked when an action occurs. When the "Refresh" button is pressed, 
	 * All the states of sensors are updated. When the "Enable All" button 
	 * or "Disable All" button is pressed, All the sensors are enabled or disabled.  
	 * After all the actions, the status is refreshed 
	 * <p>
	 * Pre-condition: A user clicks on one of three buttons "Refresh", "Enable All" and "Disable All"
	 * <p>
	 * Post-condition: The sensor status table is updated. Sensors are enabled or disabled according 
	 * to the button pressed.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	
	
	public void actionPerformed(ActionEvent event)
	{
	}
	
	
	
	/*
	 * NAME : refresh
	 * DESCRIPTION
	 * 		This function updates the sensors status information
	 * 		call and get result from all of read() and test() methods of sensors
	 * 		display enable in red, disable in black
	 * PRECONDITION : this dialog and sensors are initiated
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION : sensor status table is updated
	 * NOTES : none
	 */
	/**
	 * Updates the sensors status information. <code>refresh()</code> calls <code>read()</code>
	 * and <code>test()</code> methods from which all sensors and get results. If a sensor is enabled 
	 * the corresponding table entry displays 'enabled' with red color. If a sensor detects an opened window
	 * or motion, the corresponding table entry displays 'open' or 'detect' with red color.
	 * <p>
	 * Pre-condition: Sensor Status dialog and sensors are initiated
	 * <p>
	 * Post-condition: The sensor status table is updated 
	 */
	public void refresh()
	{
		// loop all sensors with calling read(), test() method of sensors
		for(int i=0; i<SensorControl.MAX_SENSOR; i++)
		{
			if(SecurityZone.getDeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, i+1).getUsing()){
				if(MainDemo.sensorController.testWinDoorSensor(i))
				{
					testW[i].setForeground(Color.RED);
					testW[i].setText("enable");
				}
				else
				{
					testW[i].setForeground(Color.BLACK);
					testW[i].setText("disable");
				}
					
				if(MainDemo.sensorController.readWinDoorSensor(i))
				{
					readW[i].setForeground(Color.RED);
					readW[i].setText("open");
					dsensor[i] = true;
				}
				else
				{
					readW[i].setForeground(Color.BLACK);
					readW[i].setText("close");
					dsensor[i] = false;
				}
			}
			else{
				dsensor[i] = false;
			}

			if(SecurityZone.getDeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, i+1).getUsing()){
				if(MainDemo.sensorController.testMotionDetector(i))
				{
					testM[i].setForeground(Color.RED);
					testM[i].setText("enable");
				}
				else
				{
					testM[i].setForeground(Color.BLACK);
					testM[i].setText("disable");
				}
					
				if(MainDemo.sensorController.readMotionDetector(i))
				{
					readM[i].setForeground(Color.RED);
					readM[i].setText("detect");
					dsensor[i+5] = true;
				}
				else
				{
					readM[i].setForeground(Color.BLACK);
					readM[i].setText("clear");
					dsensor[i+5] = false;
				}
			}
			else{
				dsensor[i+5] = false;
			}
		}
		
		MainDemo.alarmController.detect(dsensor);
	}
	
	
	
	/*
	 * meaningless member variable in this application
	 * prevent java compiler warning message
	 */
	static final long serialVersionUID = 465564;
}