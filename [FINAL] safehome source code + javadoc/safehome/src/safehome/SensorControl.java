package safehome;

import safehome.device.DeviceMotionDetector;
import safehome.device.DeviceWinDoorSensor;
import safehome.web.SecurityZone;

/**
 * Refresh by thred and detects unfavorable sensor and notify to alarm.
 * @author SummerSnow
 *
 */
public class SensorControl  extends Thread {
	public static final int MAX_SENSOR = 5;	// define max number of sensors

	private DeviceWinDoorSensor[] winDoorSensors;
	private DeviceMotionDetector[] motionDetectors;
	
	public SensorControl(){
		winDoorSensors = new DeviceWinDoorSensor[MAX_SENSOR];
		motionDetectors = new DeviceMotionDetector[MAX_SENSOR];
		// Loop is needed to instantiate array
		for(int i=0; i<MAX_SENSOR; i++)
		{
			winDoorSensors[i] = new DeviceWinDoorSensor();
			motionDetectors[i] = new DeviceMotionDetector();
		}
	}
	
	public int getIDWinDoorSensor(int i){
		return winDoorSensors[i].getID();
	}
	
	public int getIDMotionDetector(int i){
		return motionDetectors[i].getID();
	}
	
	public boolean testWinDoorSensor(int i){
		return winDoorSensors[i].test();
	}
	
	public boolean readWinDoorSensor(int i){
		return winDoorSensors[i].read();
	}
	
	public boolean testMotionDetector(int i){
		return motionDetectors[i].test();
	}
	
	public boolean readMotionDetector(int i){
		return motionDetectors[i].read();
	}
	
	public void enableAllSensors(){
		for(int i=0; i<SensorControl.MAX_SENSOR; i++)
		{
			if(SecurityZone.getDeviceUnit(1, i+1).getUsing()){
				winDoorSensors[i].enable();
			}
			if(SecurityZone.getDeviceUnit(2, i+1).getUsing()){
				motionDetectors[i].enable();
			}
		}
	}
	
	public void disableAllSensors(){
		for(int i=0; i<SensorControl.MAX_SENSOR; i++)
		{
			if(SecurityZone.getDeviceUnit(1, i+1).getUsing()){
				winDoorSensors[i].disable();
			}
			if(SecurityZone.getDeviceUnit(2, i+1).getUsing()){
				motionDetectors[i].disable();
			}
		}
	}
	
	public void enableWinDoorSensor(int i){
		winDoorSensors[i].enable();
	}
	
	public void disableWinDoorSensor(int i){
		winDoorSensors[i].disable();
	}
	
	public void enableMotionDetector(int i){
		motionDetectors[i].enable();
	}
	
	public void disableMotionDetector(int i){
		motionDetectors[i].disable();
	}
	
	private synchronized void refreshSensorControl(){
		MainDemo.sensorTest.refresh();
	}
	
	public void run()
	{
		while(true)
		{
			refreshSensorControl();
			try
			{
				Thread.sleep(100);
			}
			catch(InterruptedException e)
			{
			}
		}
	}
}
