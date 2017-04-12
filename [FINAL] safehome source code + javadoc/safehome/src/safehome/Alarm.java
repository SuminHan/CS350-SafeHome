package safehome;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

import safehome.web.DeviceUnit;
import safehome.web.SecurityZone;

/**
 * Alarm control.
 * 
 * @author SummerSnow
 *
 */
public class Alarm{
	boolean powerON;
	boolean alarmDetected;
	boolean nowPlaying;
	boolean detectedSensor[];
	String currentMessage;
	int alarmZone;
	Clip clip;
	
	public Alarm(){
		alarmZone = 0;
		powerON = false;
		alarmDetected = false;
		nowPlaying = false;
		detectedSensor = new boolean[10];
		currentMessage = "";
		File soundFile = new File( "nuke.wav" );
		try{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream( soundFile );
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	public void powerOn(){
		powerON = true;
	}
	public void powerOff(){
		powerON = false;
	}
	
	public boolean isDetected(){
		return alarmDetected;
	}
	public boolean isPlaying(){
		return nowPlaying;
	}
	public int getAlarmZone(){
		return alarmZone;
	}
	/*
	public boolean[] dSensor(){
		return detectedSensor;
	}
	*/
	public boolean dSensor(int type, int num){
		return detectedSensor[type*5-5+num-1];
	}
	public void play(){
		clip.setFramePosition(0);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		nowPlaying = true;
	}
	public void stop(){
		clip.stop();
		nowPlaying = false;
	}
	public void detect(boolean[] ds) {
		int newDetected = -1, newClosed = -1;
		alarmDetected = false;
		for(int i = 0; i < 10; i++){
			if(ds[i]){
				alarmDetected = true;
				if(!detectedSensor[i]){
					newDetected = i;
				}
			}
			else{
				if(detectedSensor[i])
					newClosed = i;
			}
			
			detectedSensor[i] = ds[i];
		}
		
		//newly detected
		if(newDetected >= 5){
			DeviceUnit du = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, newDetected%5 + 1);
			alarmZone = du.getZone();
			
			if(powerON){
				if(MainDemo.controlSoftware.isAway() && !isPlaying())
					play();
			}
			currentMessage = "Motion Detector #" + (newDetected%5+1) + " detected";
			MainDemo.controlSoftware.setDisplayShortMessage2(currentMessage);
			MainDemo.controlSoftware.setSecurityZoneNumber(alarmZone);
			JOptionPane.showMessageDialog(null, currentMessage, "Alarm", 2);
		}
		else if(newDetected >= 0){
			DeviceUnit du = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, newDetected%5 + 1);
			alarmZone = du.getZone();
			if(powerON){
				if(MainDemo.controlSoftware.isAway() && !isPlaying())
					play();
			}
			currentMessage = "WinDoor Sensor #" + (newDetected+1) + " open";
			MainDemo.controlSoftware.setDisplayShortMessage2(currentMessage);
			MainDemo.controlSoftware.setSecurityZoneNumber(alarmZone);
			JOptionPane.showMessageDialog(null, currentMessage, "Alarm", 2);
		}
		else{
			if(isPlaying() && !alarmDetected) stop();
			
			if(MainDemo.controlSoftware.isAway()){
				if(newClosed >= 5){
					DeviceUnit du = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, newClosed%5 + 1);
					alarmZone = du.getZone();
					currentMessage = "Motion Detector #" + (newClosed+1-5) + " clear";
					MainDemo.controlSoftware.setDisplayShortMessage2(currentMessage);
					MainDemo.controlSoftware.setSecurityZoneNumber(alarmZone);
					JOptionPane.showMessageDialog(null, currentMessage, "Alarm", 1);
				}
				else if(newClosed >= 0){
					DeviceUnit du = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, newClosed%5 + 1);
					alarmZone = du.getZone();
					currentMessage = "WinDoor Sensor #" + (newClosed+1) + " closed";
					MainDemo.controlSoftware.setDisplayShortMessage2(currentMessage);
					MainDemo.controlSoftware.setSecurityZoneNumber(alarmZone);
					JOptionPane.showMessageDialog(null, currentMessage, "Alarm", 1);
				}
			}
		}
		
		
		// TODO Auto-generated method stub
		
	}
	public String getCurrentMessage() {
		// TODO Auto-generated method stub
		return currentMessage;
	}
}