package safehome.ztest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import org.junit.Test;
import org.mockito.Mockito;
import org.testng.abbot.fixture.JOptionPaneFixture;

import safehome.Alarm;
import safehome.CameraTest;
import safehome.ControlSoftware;
import safehome.MainDemo;
import safehome.SensorControl;
import safehome.SensorTest;
import safehome.device.DeviceSensorTester;
import safehome.web.DeviceUnit;
import safehome.web.Login;
import safehome.web.SecurityZone;
import safehome.web.WebService;

public class MainDemoTest {
	DeviceUnit d;
	MainDemo md;
	Timer t;
	Dimension screenSize;
	static final int DT = 100;
	Robot r;
	Point p;
	int _w, _h;
	@Test
	public void testWebCP() throws AWTException {
		t = new Timer(DT*2, null);
		t.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				kenter();
			}
		});
        t.setRepeats(false);
        
		r = new Robot();
		md = new MainDemo();
		md.setVisible(true);

		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		_w = screenSize.width;
		_h = screenSize.height;
		
		assertNotNull(md.sensorController);
		assertNotNull(md.alarmController);
		assertNotNull(md.webService);
		assertNotNull(md.controlSoftware);
		assertNotNull(md.sensorTest);
		assertNotNull(md.cameraTest);
		assertNotNull(md.login);
		assertTrue(md.isVisible());
		assertTrue(md.sensorController.isAlive());
		
		r.delay(1000);
		p = md.getLocation();
		r.mouseMove(p.x + 90, p.y + 160);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		r.delay(DT);
		assertTrue(md.login.isVisible());

		r.delay(DT*4);
		click(_w-60, _h/2+90);
		r.keyPress(KeyEvent.VK_0);
		r.keyRelease(KeyEvent.VK_0);
		click(_w-60, _h/2+140);
		r.delay(DT);
		kenter();

		click(_w-60, _h/2+90);
		r.keyPress(KeyEvent.VK_BACK_SPACE);
		r.keyRelease(KeyEvent.VK_BACK_SPACE);
		click(_w-60, _h/2+140);
		r.delay(DT);
		kenter();
		kenter();
		
		assertFalse(md.login.securityZone.isVisible());
		
		click(_w-60, _h/2+140);
		r.keyPress(KeyEvent.VK_1);
		r.keyRelease(KeyEvent.VK_1);
		r.keyPress(KeyEvent.VK_3);
		r.keyRelease(KeyEvent.VK_3);
		r.keyPress(KeyEvent.VK_7);
		r.keyRelease(KeyEvent.VK_7);
		r.keyPress(KeyEvent.VK_9);
		r.keyRelease(KeyEvent.VK_9);
		kenter();
		r.delay(DT*4);

		assertTrue(md.login.securityZone.isVisible());
		
		p = md.login.securityZone.getLocation();
		click(p.x+155, p.y+45);
		click(p.x+155, p.y+65);
		assertTrue(md.webService.aboutView.isVisible());
		md.webService.aboutView.actionPerformed(e("close"));
		r.delay(DT);
		assertFalse(md.webService.aboutView.isVisible());
		

		click(p.x+95, p.y+45);
		click(p.x+95, p.y+65);
		assertTrue(md.webService.survView.isVisible());
		p = md.webService.survView.getLocation();
		click(p.x+79, p.y+188);
		//panleft x7
		click(_w-320, 70);
		click(_w-320, 70);
		click(_w-320, 70);
		click(_w-320, 70);
		click(_w-320, 70);
		click(_w-320, 70);
		click(_w-320, 70);
		//panright x12
		click(_w-125, 70);
		click(_w-125, 70);
		click(_w-125, 70);
		click(_w-125, 70);
		click(_w-125, 70);
		click(_w-125, 70);
		click(_w-125, 70);
		click(_w-125, 70);
		click(_w-125, 70);
		click(_w-125, 70);
		click(_w-125, 70);
		click(_w-125, 70);
		//zoomin x10
		click(_w-320, 100);
		click(_w-320, 100);
		click(_w-320, 100);
		click(_w-320, 100);
		click(_w-320, 100);
		click(_w-320, 100);
		click(_w-320, 100);
		click(_w-320, 100);
		click(_w-320, 100);
		click(_w-320, 100);
		click(_w-320, 100);
		//zoomout x10
		click(_w-125, 100);
		click(_w-125, 100);
		click(_w-125, 100);
		click(_w-125, 100);
		click(_w-125, 100);
		click(_w-125, 100);
		click(_w-125, 100);
		click(_w-125, 100);
		click(_w-125, 100);
		click(_w-125, 100);
		click(_w-125, 100);
		// off
		click(_w-3, 3);
		click(_w-3, 3);
		

		r.delay(DT);
		t.start();
		md.login.securityZone.actionPerformed(e("Away"));
		r.delay(DT);
		t.start();
		md.login.securityZone.actionPerformed(e("Stay"));
		r.delay(DT);
		t.start();
		md.login.securityZone.actionPerformed(e("Zone"));
		md.login.securityZone.actionPerformed(e("1"));
		md.login.securityZone.actionPerformed(e("0"));
		md.login.securityZone.actionPerformed(e("2"));

		r.delay(DT);
		t.start();
		md.login.securityZone.actionPerformed(e("Add"));
		
		p = md.login.securityZone.getLocation();
		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_CAMERA, 1);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		kenter();
		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, 2);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		kenter();
		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, 3);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		kenter();

		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_CAMERA, 1);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		kenter();
		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, 2);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		kenter();
		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, 3);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		kenter();

		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_CAMERA, 1);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		kenter();
		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, 2);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		kenter();
		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, 3);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		kenter();
		

		md.login.securityZone.actionPerformed(e("0"));
		p = md.login.securityZone.getLocation();
		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_CAMERA, 4);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		kenter();
		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, 4);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		kenter();
		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, 4);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		kenter();

		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_CAMERA, 4);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		kenter();
		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, 4);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		kenter();
		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, 4);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		kenter();

		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_CAMERA, 4);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		kenter();
		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, 4);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		kenter();
		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, 4);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		kenter();

		md.login.securityZone.actionPerformed(e("2"));
		t.start();
		md.login.securityZone.actionPerformed(e("Delete"));

		md.login.securityZone.actionPerformed(e("Finish"));

		t.start();
		md.login.securityZone.actionPerformed(e("Add"));
		t.start();
		md.login.securityZone.actionPerformed(e("Add"));
		t.start();
		md.login.securityZone.actionPerformed(e("Add"));
		t.start();
		md.login.securityZone.actionPerformed(e("Add"));
		t.start();
		md.login.securityZone.actionPerformed(e("Add"));
		t.start();
		md.login.securityZone.actionPerformed(e("Add"));
		t.start();
		md.login.securityZone.actionPerformed(e("Add"));
		t.start();
		md.login.securityZone.actionPerformed(e("Add"));
		t.start();
		md.login.securityZone.actionPerformed(e("Add"));
		t.start();
		md.login.securityZone.actionPerformed(e("Add"));

		t.start();
		md.login.securityZone.actionPerformed(e("Delete"));
		t.start();
		md.login.securityZone.actionPerformed(e("Delete"));
		t.start();
		md.login.securityZone.actionPerformed(e("Delete"));
		t.start();
		md.login.securityZone.actionPerformed(e("Delete"));
		t.start();
		md.login.securityZone.actionPerformed(e("Delete"));
		t.start();
		md.login.securityZone.actionPerformed(e("Delete"));
		t.start();
		md.login.securityZone.actionPerformed(e("Delete"));
		t.start();
		md.login.securityZone.actionPerformed(e("Delete"));
		t.start();
		md.login.securityZone.actionPerformed(e("Delete"));
		t.start();
		md.login.securityZone.actionPerformed(e("Delete"));
		t.start();
		
		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, 2);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		kenter();
		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, 3);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		kenter();
		
		r.delay(DT);
		md.controlSoftware.actionPerformed(e("1"));
		assertTrue(md.controlSoftware.isPowered());
		t.start();
		md.login.securityZone.actionPerformed(e("Away"));
		
		assertTrue(md.controlSoftware.isAway());
		
		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, 4);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		d = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, 5);
		click(p.x+d.getX()+15, p.y+d.getY()+50);
		
		t.start();
		md.login.securityZone.actionPerformed(e("Stay"));
		

		md.controlSoftware.actionPerformed(e("2"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		assertFalse(md.controlSoftware.isPowered());
		
		
		//click(_w/2+95, _h/2-100);
		//assertFalse(md.isVisible());
		//md.dispose();
		//md = null;
		//md.login.actionPerformed("login");
		
		
		/*
		t = new Timer(DT, null);
		t.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				JOptionPane.getRootFrame().dispose();
			}
		});
        t.setRepeats(false);
        
		r = new Robot();
		*/
		//md = new MainDemo();
		
		md.setVisible(true);
		
		r.delay(1000);
		p = md.getLocation();
		r.mouseMove(p.x + 90, p.y + 60);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		r.delay(DT);
		assertTrue(md.controlSoftware.isVisible());
		

		md.controlSoftware.actionPerformed(e("*"));
		md.controlSoftware.actionPerformed(e("#"));
		md.controlSoftware.actionPerformed(e("9"));
		md.controlSoftware.actionPerformed(e("8"));
		md.controlSoftware.actionPerformed(e("7"));
		md.controlSoftware.actionPerformed(e("6"));
		md.controlSoftware.actionPerformed(e("5"));
		md.controlSoftware.actionPerformed(e("4"));
		md.controlSoftware.actionPerformed(e("3"));
		md.controlSoftware.actionPerformed(e("2"));
		assertFalse(md.controlSoftware.isPowered());
		
		md.controlSoftware.actionPerformed(e("1"));
		assertTrue(md.controlSoftware.isPowered());
		
		md.controlSoftware.actionPerformed(e("*"));
		md.controlSoftware.actionPerformed(e("#"));
		md.controlSoftware.actionPerformed(e("9"));
		md.controlSoftware.actionPerformed(e("8"));
		md.controlSoftware.actionPerformed(e("7"));
		md.controlSoftware.actionPerformed(e("6"));
		md.controlSoftware.actionPerformed(e("6"));
		md.controlSoftware.actionPerformed(e("5"));
		md.controlSoftware.actionPerformed(e("4"));
		md.controlSoftware.actionPerformed(e("3"));
		assertFalse(md.controlSoftware.onPass());
		md.controlSoftware.actionPerformed(e("2"));
		assertTrue(md.controlSoftware.onPass());
		md.controlSoftware.actionPerformed(e("1"));
		assertTrue(md.controlSoftware.onPass());
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		assertFalse(md.controlSoftware.isPowered());
		
		md.controlSoftware.actionPerformed(e("1"));
		assertTrue(md.controlSoftware.isPowered());
		
		md.controlSoftware.actionPerformed(e("2"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		r.delay(3100);
		md.controlSoftware.actionPerformed(e("1"));
		assertTrue(md.controlSoftware.isPowered());

		md.controlSoftware.actionPerformed(e("2"));
		assertTrue(md.controlSoftware.onPass());
		md.controlSoftware.actionPerformed(e("2"));
		md.controlSoftware.actionPerformed(e("3"));
		md.controlSoftware.actionPerformed(e("7"));
		md.controlSoftware.actionPerformed(e("8"));
		assertTrue(md.controlSoftware.isPowered());

		md.controlSoftware.actionPerformed(e("4"));
		md.controlSoftware.actionPerformed(e("5"));
		md.controlSoftware.actionPerformed(e("6"));
		md.controlSoftware.actionPerformed(e("9"));
		md.controlSoftware.actionPerformed(e("#"));
		md.controlSoftware.actionPerformed(e("0"));
		md.controlSoftware.actionPerformed(e("*"));
		md.controlSoftware.actionPerformed(e("8"));
		assertFalse(md.controlSoftware.isAway());
		

		wdinit(-1);
		wdo();
		kenter();
		
		moinit(-1);
		mo();
		kenter();
		
		
		wdinit(-2);
		wdo();
		kenter();
		
		moinit(-2);
		mo();
		kenter();
		
		
		wdinit(1);
		wdo();
		moinit(2);
		mo();
        t.start();
		md.controlSoftware.actionPerformed(e("7"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		///////////
		assertFalse(md.controlSoftware.isAway());
		r.delay(100);
		
		r.mouseMove(_w/2, _h/2);
		r.delay(1000);
		kenter();
		wdc();
		mc();
		
		md.controlSoftware.actionPerformed(e("7"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		assertTrue(md.controlSoftware.isAway());
		

		md.controlSoftware.actionPerformed(e("*"));
		md.controlSoftware.actionPerformed(e("#"));
		md.controlSoftware.actionPerformed(e("0"));
		md.controlSoftware.actionPerformed(e("9"));
		md.controlSoftware.actionPerformed(e("7"));
		md.controlSoftware.actionPerformed(e("6"));
		md.controlSoftware.actionPerformed(e("5"));
		md.controlSoftware.actionPerformed(e("4"));
		md.controlSoftware.actionPerformed(e("3"));
		md.controlSoftware.actionPerformed(e("2"));
		assertTrue(md.controlSoftware.isAway());
		
		
		t.start();
		wdo();
		r.delay(DT);
		md.controlSoftware.actionPerformed(e("3"));
		
		md.controlSoftware.actionPerformed(e("*"));
		md.controlSoftware.actionPerformed(e("#"));
		md.controlSoftware.actionPerformed(e("0"));
		md.controlSoftware.actionPerformed(e("9"));
		md.controlSoftware.actionPerformed(e("8"));
		md.controlSoftware.actionPerformed(e("7"));
		md.controlSoftware.actionPerformed(e("6"));
		md.controlSoftware.actionPerformed(e("5"));
		md.controlSoftware.actionPerformed(e("4"));
		md.controlSoftware.actionPerformed(e("3"));
		md.controlSoftware.actionPerformed(e("2"));
		md.controlSoftware.actionPerformed(e("1"));
		assertTrue(md.controlSoftware.isAway());

		r.delay(DT);
		md.controlSoftware.actionPerformed(e("8"));
		md.controlSoftware.actionPerformed(e("7"));
		r.delay(3100);
		assertTrue(md.controlSoftware.isAway());
		
		r.delay(DT);
		t.start();
		wdc();

		r.delay(DT);
		md.controlSoftware.actionPerformed(e("8"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		assertFalse(md.controlSoftware.isAway());

		r.delay(DT);
		md.controlSoftware.actionPerformed(e("7"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		assertTrue(md.controlSoftware.onPass());
		r.delay(3100);
		md.controlSoftware.actionPerformed(e("1"));
		assertFalse(md.controlSoftware.onPass());
		assertFalse(md.controlSoftware.isAway());
		
		r.delay(DT);
		t.start();
		wdo();
		md.controlSoftware.actionPerformed(e("7"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		assertFalse(md.controlSoftware.isAway());
		assertFalse(md.controlSoftware.isReady());

		r.delay(DT);
		md.controlSoftware.actionPerformed(e("2"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		md.controlSoftware.actionPerformed(e("1"));
		assertFalse(md.controlSoftware.isPowered());
		
		md.dispose();

		
		/////////////////////////////
		
	}

	
	void kenter(){
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(DT);
	}
	void wdinit(int n){
		r.mouseMove(_w/2 - 45, _h/4*3 + 105);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_A);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyRelease(KeyEvent.VK_A);
		r.keyPress(KeyEvent.VK_DELETE);
		r.keyRelease(KeyEvent.VK_DELETE);
		switch(n){
		case -2:
			r.keyPress(KeyEvent.VK_G);
			r.keyRelease(KeyEvent.VK_G);
			break;
		case -1:
			break;
		case 0:
			r.keyPress(KeyEvent.VK_0);
			r.keyRelease(KeyEvent.VK_0);
			break;
		case 1:
			r.keyPress(KeyEvent.VK_1);
			r.keyRelease(KeyEvent.VK_1);
			break;
		case 2:
			r.keyPress(KeyEvent.VK_2);
			r.keyRelease(KeyEvent.VK_2);
			break;
		case 3:
			r.keyPress(KeyEvent.VK_3);
			r.keyRelease(KeyEvent.VK_3);
			break;
		case 4:
			r.keyPress(KeyEvent.VK_4);
			r.keyRelease(KeyEvent.VK_4);
			break;
		case 5:
			r.keyPress(KeyEvent.VK_5);
			r.keyRelease(KeyEvent.VK_5);
			break;
		}
		r.delay(DT);
	}
	void wdo(){
		r.mouseMove(_w/2 - 115, _h/4*3 + 135);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		r.delay(DT);
	}
	void wdc(){
		r.mouseMove(_w/2 - 45, _h/4*3 + 135);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		r.delay(DT);
	}
	
	void moinit(int n){
		r.mouseMove(_w/2 + 130, _h/4*3 + 105);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_A);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyRelease(KeyEvent.VK_A);
		r.keyPress(KeyEvent.VK_DELETE);
		r.keyRelease(KeyEvent.VK_DELETE);
		switch(n){
		case -2:
			r.keyPress(KeyEvent.VK_G);
			r.keyRelease(KeyEvent.VK_G);
			break;
		case -1:
			break;
		case 0:
			r.keyPress(KeyEvent.VK_0);
			r.keyRelease(KeyEvent.VK_0);
			break;
		case 1:
			r.keyPress(KeyEvent.VK_1);
			r.keyRelease(KeyEvent.VK_1);
			break;
		case 2:
			r.keyPress(KeyEvent.VK_2);
			r.keyRelease(KeyEvent.VK_2);
			break;
		case 3:
			r.keyPress(KeyEvent.VK_3);
			r.keyRelease(KeyEvent.VK_3);
			break;
		case 4:
			r.keyPress(KeyEvent.VK_4);
			r.keyRelease(KeyEvent.VK_4);
			break;
		case 5:
			r.keyPress(KeyEvent.VK_5);
			r.keyRelease(KeyEvent.VK_5);
			break;
		}
		r.delay(DT);
	}
	void mo(){
		r.mouseMove(_w/2 + 50, _h/4*3 + 135);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		r.delay(DT);
	}
	void mc(){
		r.mouseMove(_w/2 + 130, _h/4*3 + 135);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		r.delay(DT);
	}
	
	void click(int x, int y){
		r.mouseMove(x, y);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		r.delay(DT);
	}
	
	ActionEvent e(String text){
		return new ActionEvent(this, ActionEvent.ACTION_PERFORMED, text);
	}

}
