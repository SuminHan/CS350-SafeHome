package safehome;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import safehome.device.DeviceControlPanelAbstract;

/*
 * CLASS NAME : ControlPanel 
 * DESCRIPTION
 * 		A simple implementation of the abstract class
 * 		All implemented abstract methods has small functionality to show it is working well
 * 		All APIs that provided from super class is tested by buttons
 * PRECONDITION : From main dialog, "Control Panel" button was chosen
 * PARAMETER : none
 * RETURN VALUE : none
 * USAGE : click the numeric buttons
 * EXAMPLE : click the button 1 to test its functionality
 * POSTCONDITION : none
 * NOTES : If possible, click all buttons to test all functionality
 */ 
/**
 * This class is a simple implementation of the abstract 
 * class <code>{@link DeviceControlPanelAbstract}</code>. All abstract methods are 
 * implemented simply to show that each button is working well.
 * <p>
 * The control panel consists of three parts. It looks like this
 * <p>
 * <img src = "../../doc-files/initial.PNG"></img>
 * <p>
 * LCD on the left-up shows the current status, which button is pressed, 
 * the system is in stay or away mode, and the system is ready or not. 
 * Two LEDs on the left-down indicate the system is armed or not and 
 * the system is power-on or off. The keypad on the right-side has 12 buttons,
 * 0 ~ 9, *, #. The corresponding action occurs when a user presses each button.
 * 
 *        
 * @author cs350 TA
 * @see safehome.device.DeviceControlPanelAbstract
 */
public class ControlSoftware extends DeviceControlPanelAbstract
{
	boolean onPassEnter;
	boolean isReady;
	int systemStatus; //2 - off, 7 - away, 8 - stay
	int nextProcess;
	String receivedPass = "";
	String masterPassword = "1111";
	
	Timer timer3s;
	Timer timer1s;
	

	/**
	 * Initialize <code>onPassEnter, isReady, systemStatus</code>.
	 * The timers for 1s and 3s also initialized.
	 * (timers will call <code>passwordTimeout()</code> when they time out).
	 * Control panel display also initialized to display nothing.
	 */
	public ControlSoftware()
	{
		super();
		onPassEnter = false;
		isReady = true;
		systemStatus = 2;
		
		timer3s = new Timer(3000, null);
		timer3s.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				passwordTimeout();
			}
		});
		
		timer1s = new Timer(1000, null);
		timer1s.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				passwordTimeout();
			}
		});

		setDisplayAway(false);
		setDisplayStay(false);
		setDisplayNotReady(false);
		setArmedLED(false);
		setPoweredLED(false);
		setSecurityZoneNumber(0);
		setDisplayShortMessage1("");
		setDisplayShortMessage2("");
	}
	
	public boolean onPass(){
		return onPassEnter;
	}
	/**
	 * @return status whether system is powered.
	 */
	public boolean isPowered(){
		return systemStatus != 2;
	}
	
	/**
	 * @return status whether system is armed.
	 */
	public boolean isAway(){
		return systemStatus == 7;
	}
	
	/**
	 * @return status whether system is ready.
	 */
	public boolean isReady(){
		return isReady;
	}
	
	/**
	 * Stop timers and <code>onPassEnter</code> becomes <code>false</code>.
	 * Initializes currently received Password.
	 */
	private void cancelTask()
	{
		this.timer3s.stop();
		this.timer1s.stop();
		onPassEnter = false;
		receivedPass = "";
	}

	/**
	 * Behaviors when password timeout.
	 * Also used for many situations where there needs to be default display for control panel.
	 */
	public void passwordTimeout() {
		cancelTask();
		if (MainDemo.alarmController.isDetected()){
			setDisplayShortMessage2(MainDemo.alarmController.getCurrentMessage());
			isReady = false;
		}
		else{
			isReady = true;
			setDisplayShortMessage2("");
			if (this.systemStatus == 7)
			{
				setDisplayAway(true);
				setDisplayStay(false);
				setDisplayNotReady(false);
				setArmedLED(true);
				setDisplayShortMessage1("Away");
				setDisplayShortMessage2("");
			}
			else if (this.systemStatus == 8)
			{
				setDisplayAway(false);
				setArmedLED(false);
				if(isReady){
					setDisplayStay(true);
					setDisplayNotReady(false);
				}
				else{
					setDisplayStay(false);
					setDisplayNotReady(true);
				}
				
				setDisplayShortMessage1("Stay");
				setDisplayShortMessage2("");
			}
			else if (this.systemStatus == 2){
				setDisplayShortMessage1("");
				setDisplayShortMessage2("");
			}
			else
			{
				setDisplayShortMessage1("?????");
				setDisplayShortMessage2("?????");
			}
		}
		
	}
	
	/**
	 * If system was on require of password, this method will save currently <code>enteredNumber</code>
	 * to received password. Otherwise, returns false.
	 * @param enteredNumber
	 * @return whether currently entered number regarded as password or not
	 */
	private boolean enterPassword(int enteredNumber) {
		timer3s.stop();
		timer1s.stop();
		if(systemStatus == 2 && enteredNumber != 1){
			return true;
		}
		
		if(!onPass()) return false;
		
		receivedPass += enteredNumber;
		
		String tmp = "";
		for(int i = 0; i < receivedPass.length(); i++)
			tmp += "*"; 
		setDisplayShortMessage2("Waiting for password: " + tmp);
		
		if(receivedPass.length() == 4){
			executeNextProcess();
			onPassEnter = false;
			return true;
		}
		
		timer3s.start();
		return true;
			
	}
	
	/**
	 * Waiting for password.
	 */
	private void requirePassword(){
		timer3s.start();
		setDisplayShortMessage2("Waiting for password: ");
		onPassEnter = true;
		receivedPass = "";
	}
	
	/**
	 * Method to register requested process.
	 * @param registerNext
	 */
	private void registerNextProcess(int registerNext){
		timer3s.stop();
		timer1s.stop();
		if(systemStatus != registerNext){
			switch(registerNext){
				//case 1: setDisplayShortMessage1("On request"); break;
				case 2: setDisplayShortMessage1("Off request"); break;
				case 3: setDisplayShortMessage1("Reset"); break;
				case 7: setDisplayShortMessage1("Away mode request"); break;
				case 8: setDisplayShortMessage1("Stay mode request"); break;
			}
		}
		
		if(systemStatus == 2){ //system turned off
			if(registerNext == 1){
				bootup();
			}
		}
		else if(systemStatus != 2){ //system turned on
			if(registerNext == 1){
				//simply ignore
			}
			else if(registerNext == 3){ //turn off alarm sound
				MainDemo.alarmController.stop();
				timer1s.start();
			}
			else if(systemStatus == 7){ //system armed, 7
				if(MainDemo.alarmController.isDetected()){
					setDisplayShortMessage2("Invalid...(Alarm is on)");
					timer1s.start();
				}
				else{
					if(registerNext == 7){
						//ignore
					}
					else if(registerNext == 2){
						displayInvalid();
					}
					else if(registerNext == 8){
						nextProcess = registerNext;
						requirePassword();
					}
					else{
						displayInvalid();
					}
				}
			}
			else{ //system disarmed, 8
				if(registerNext == 8){
					//ignore
					timer1s.start();
				}
				else{
					nextProcess = registerNext;
					requirePassword();
				}
			}
		}
		timer3s.start();
	}
	
	/**
	 * Displaying current action is invalid.
	 */
	private void displayInvalid(){
		setDisplayShortMessage2("Invalid...");
		timer1s.start();
	}
	
	/**
	 * Compare with currently <code>receivedPass with <code>masterPassword</code>.
	 * @return validation of password.
	 */
	private boolean validatePassword(){
		setDisplayShortMessage1("Password validation");
		return receivedPass.equals(masterPassword);
	}
	
	/**
	 * Actual execution of next process is controlled in this method.
	 */
	private void executeNextProcess(){
		timer3s.stop();
		timer1s.stop();
		if(validatePassword()){
			switch(nextProcess){
			case 2:
				shutdown();
				break;
			case 7:
				away();
				break;
			case 8:
				stay();
				break;
			}
		}
		else{
			setDisplayShortMessage2("Failed");
		}
		timer3s.start();
		nextProcess = -1;
	}

	
	/**
	 * Bootup the system.
	 */
	private void bootup(){
		systemStatus = 8;
		setPoweredLED(true);
		setDisplayAway(false);
		setDisplayStay(true);
		setDisplayNotReady(false);
		setDisplayShortMessage1("System Booted Up");
		setDisplayShortMessage2("");
		timer1s.start();
	}
	
	/**
	 * Shutdown the system.
	 */
	private void shutdown(){
		systemStatus = 2;
		setDisplayAway(false);
		setDisplayStay(false);
		setDisplayNotReady(false);
		setArmedLED(false);
		setPoweredLED(false);
		setDisplayShortMessage1("");
		setDisplayShortMessage2("");
	}
	
	
	/**
	 * From stay mode, get into away mode.
	 * If there is any sensor detected, it gets into not ready state.
	 * Otherwise, the system sucessfully gets into armed mode.
	 */
	public void away(){
		if(isPowered()){
			MainDemo.sensorController.enableAllSensors();
			MainDemo.sensorTest.refresh();
			if(MainDemo.alarmController.isDetected()){
				isReady = false;
				MainDemo.sensorController.disableAllSensors();
				
				setDisplayAway(false);
				setArmedLED(false);
				setDisplayStay(false);
				setDisplayNotReady(true);
				setDisplayShortMessage1("Not Ready");
				setDisplayShortMessage2(MainDemo.alarmController.getCurrentMessage());
			}
			else{
				systemStatus = 7;
				isReady = true;
				MainDemo.alarmController.powerOn();
				
				setDisplayAway(true);
				setDisplayStay(false);
				setDisplayNotReady(false);
				setArmedLED(true);
				setDisplayShortMessage1("Away");
				setDisplayShortMessage2("");
			}
		}
	}
	
	/**
	 * Gets into stay mode. It also decides whether to display not ready or not.
	 */
	public void stay(){
		if(isPowered()){
				systemStatus = 8;
				MainDemo.sensorController.disableAllSensors();
				MainDemo.alarmController.powerOff();
				
				setDisplayAway(false);
				setArmedLED(false);
				if(isReady){
					setDisplayStay(true);
					setDisplayNotReady(false);
				}
				else{
					setDisplayStay(false);
					setDisplayNotReady(true);
				}
				
				setDisplayShortMessage1("Stay");
				setDisplayShortMessage2("");
		}
	}
	
	
	//////////////////////////////////
	
	public void button1()
	{
		if(!enterPassword(1)){
			registerNextProcess(1); //boot up
		}
	}
	

	public void button2()
	{
		if(!enterPassword(2)){
			registerNextProcess(2); //shutdown
		}
	}
	
	public void button3()
	{
		if(!enterPassword(3)){
			registerNextProcess(3); //reset
		}
	}
	
	public void button4()
	{
		if(!enterPassword(4)){
		}
		
	}

	public void button5()
	{
		if(!enterPassword(5)){
		}
	}

	public void button6()
	{
		if(!enterPassword(6)){
		}
	}
	
	public void button7()
	{
		if(!enterPassword(7)){
			registerNextProcess(7); //away
		}
	}
	
	public void button8()
	{
		if(!enterPassword(8)){
			registerNextProcess(8); //stay
		}
	}

	public void button9()
	{
		if(!enterPassword(9)){
		}
	}
	
	public void buttonStar()
	{
	}

	public void button0()
	{
		if(!enterPassword(0)){
		}
	}

	public void buttonSharp()
	{
	}
	
	static final long serialVersionUID = 1943342;
}