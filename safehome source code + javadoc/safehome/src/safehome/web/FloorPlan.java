package safehome.web;

import safehome.MainDemo;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * FloorPlan which repaints every element on the floor plan.
 * @author SummerSnow
 *
 */
public class FloorPlan extends JPanel implements Runnable
{
	private static final long serialVersionUID = -52256459393885701L;
	private static Image fpimg;
	private static Image camImg;
	private static Image wdImg;
	private static Image moImg;
	private static int icw = 40;
	private static int ich = 40;
	private static int scw = 30;
	private static int sch = 30;
	private static final int csz = 43;
	public static int repaintBlink = 0;
	
	public FloorPlan()
	{
		fpimg = new ImageIcon("floorplan.png").getImage();
		camImg = new ImageIcon("camera_icon_small2.png").getImage(); // 40 x 40
		wdImg = new ImageIcon("windoor_icon.jpg").getImage(); // 30 x 30
		moImg = new ImageIcon("motion_icon.jpg").getImage(); // 30 x 30
		
		
		setLayout(null);
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				 mouseOnItem(e);
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			} 
		});
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

				Point p = e.getPoint();
				int camClick = cameraClick(p.getX(), p.getY());
				int sensorClick = sensorClick(p.getX(), p.getY());
				//JOptionPane.showMessageDialog(FloorPlan.this, p.getX() + "," + p.getY());
				
				if(WebMainView.isZoneMode()){
					if(camClick > 0){
						DeviceUnit cam = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_CAMERA, camClick);
						if(cam.getZone() != WebMainView.getCurrentZone()){
							int dialogButton = JOptionPane.showConfirmDialog(FloorPlan.this, "Add camera #" + camClick + " to current zone?");
							if(dialogButton == JOptionPane.YES_OPTION){
								cam.setZone(WebMainView.getCurrentZone());
							}
						}
						else{

							if(WebMainView.getCurrentZone() == 0){
								JOptionPane.showMessageDialog(FloorPlan.this, "This is default Zone (#0)");
							}
							else{
								int dialogButton = JOptionPane.showConfirmDialog(FloorPlan.this, "Remove camera #" + camClick + " to current zone?");
								if(dialogButton == JOptionPane.YES_OPTION){
									cam.setZone(0);
								}
							}
						}
					} else if(sensorClick > 0){
						if(sensorClick >= 6){
							int moNum = sensorClick - 5;
							DeviceUnit mo = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, moNum);
							if(mo.getZone() != WebMainView.getCurrentZone()){
								int dialogButton = JOptionPane.showConfirmDialog(FloorPlan.this, "Add motion #" + moNum + " to current zone?");
								if(dialogButton == JOptionPane.YES_OPTION){
									mo.setZone(WebMainView.getCurrentZone());
								}
							}
							else{
								if(WebMainView.getCurrentZone() == 0){
									JOptionPane.showMessageDialog(FloorPlan.this, "This is default Zone (#0)");
								}
								else{
									int dialogButton = JOptionPane.showConfirmDialog(FloorPlan.this, "Remove motion #" + moNum + " to current zone?");
									if(dialogButton == JOptionPane.YES_OPTION){
										mo.setZone(0);
									}
								}
							}
						}
						else if(sensorClick >= 1){
							int wdNum = sensorClick;
							DeviceUnit wd = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, wdNum);
							if(wd.getZone() != WebMainView.getCurrentZone()){
								int dialogButton = JOptionPane.showConfirmDialog(FloorPlan.this, "Add window #" + wdNum + " to current zone?");
								if(dialogButton == JOptionPane.YES_OPTION){
									wd.setZone(WebMainView.getCurrentZone());
								}
							}
							else{
								if(WebMainView.getCurrentZone() == 0){
									JOptionPane.showMessageDialog(FloorPlan.this, "This is default Zone (#0)");
								}
								else{
									int dialogButton = JOptionPane.showConfirmDialog(FloorPlan.this, "Remove window #" + wdNum + " to current zone?");
									if(dialogButton == JOptionPane.YES_OPTION){
										wd.setZone(0);
									}
								}
							}
						}
					}
				}
				else {
					if(camClick > 0){
						Surveillance.getCameraTest(camClick).setVisible(true);
					} else if(sensorClick > 0){
						if(sensorClick >= 6){
							int moNum = sensorClick - 6;
							if(MainDemo.controlSoftware.isAway()){
								if(MainDemo.sensorController.testMotionDetector(moNum)){
									MainDemo.sensorController.disableMotionDetector(moNum);
								}
								else{
									MainDemo.sensorController.enableMotionDetector(moNum);
								}
							}
							else{
								DeviceUnit mo = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, moNum+1);
								if(mo.getUsing()){
									mo.setUsing(false);
								}
								else{
									mo.setUsing(true);
								}
							}
						}
						else if(sensorClick >= 1){
							int wdNum = sensorClick - 1;
	
							if(MainDemo.controlSoftware.isAway()){
								if(MainDemo.sensorController.testWinDoorSensor(wdNum)){
									MainDemo.sensorController.disableWinDoorSensor(wdNum);
								}
								else{
									MainDemo.sensorController.enableWinDoorSensor(wdNum);
								}
							}
							else{
								DeviceUnit wd = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, wdNum+1);
								if(wd.getUsing()){
									wd.setUsing(false);
								}
								else{
									wd.setUsing(true);
								}
							}
							
						}
					}
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		Thread th = new Thread(this);
		th.start();
	}
	
	private void mouseOnItem(MouseEvent e){
		Point p = e.getPoint();
		int camClick = cameraClick(p.getX(), p.getY());
		int sensorClick = sensorClick(p.getX(), p.getY());
		//JOptionPane.showMessageDialog(FloorPlan.this, p.getX() + "," + p.getY());
		if(camClick >= 0){
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		else if(sensorClick > 0){
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		else{
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	private int cameraClick(double x, double y){
		for(int i  = 1; i <= SecurityZone.camNum; i++){
			DeviceUnit cam = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_CAMERA, i);
			if((cam.getX() <= x && x <= cam.getX() + icw) && (cam.getY() <= y && y <= cam.getY() + ich)){
				return i;
			}
		}
		
		return -1;
	}
	
	private int sensorClick(double x, double y){
		for(int i  = 1; i <= SecurityZone.wdNum; i++){
			DeviceUnit sensor = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, i);
			if((sensor.getX() <= x && x <= sensor.getX() + scw) && (sensor.getY() <= y && y <= sensor.getY() + sch)){
				return i;
			}
		}
		for(int i  = 1; i <= SecurityZone.wdNum; i++){
			DeviceUnit sensor = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, i);
			if((sensor.getX() <= x && x <= sensor.getX() + scw) && (sensor.getY() <= y && y <= sensor.getY() + sch)){
				return i+5;
			}
		}
		
		return -1;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(fpimg, 0, 0, this);
		
		if(WebMainView.isZoneMode()){
			for(int i = 1; i <= SecurityZone.camNum; i++){
				DeviceUnit cam = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_CAMERA, i);
				if(cam.getZone() == WebMainView.getCurrentZone()){
					g.setColor(Color.ORANGE);
					g.fillOval(cam.getX()-6, cam.getY()-6, csz+9, csz+9);
				}
				
				g.drawImage(camImg, cam.getX(), cam.getY(), this);
	            g.setColor(Color.BLACK);
	            g.setFont(new Font("Arial", Font.PLAIN, 10));
	            g.drawString("#"+i, cam.getX(), cam.getY()+9);
			}
			for(int i = 1; i <= SecurityZone.wdNum; i++){
				DeviceUnit wd = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, i);
				if(wd.getZone() == WebMainView.getCurrentZone()){
					g.setColor(Color.ORANGE);
					g.fillOval(wd.getX()-6, wd.getY()-6, csz, csz);
				}
				
				g.drawImage(wdImg, wd.getX(), wd.getY(), this);
	            g.setColor(Color.WHITE);
	            g.setFont(new Font("Arial", Font.PLAIN, 10));
	            g.drawString("#"+i, wd.getX(), wd.getY()+9);
			}
			for(int i = 1; i <= SecurityZone.mNum; i++){
				DeviceUnit mo = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, i);
				if(mo.getZone() == WebMainView.getCurrentZone()){
					g.setColor(Color.ORANGE);
					g.fillOval(mo.getX()-6, mo.getY()-6, csz, csz);
				}
				
				g.drawImage(moImg, mo.getX(), mo.getY(), this);
	            g.setColor(Color.WHITE);
	            g.setFont(new Font("Arial", Font.PLAIN, 10));
	            g.drawString("#"+i, mo.getX(), mo.getY()+9);
			}
			
			// ZONE Display
	        g.setColor(Color.BLACK);
	        g.setFont(new Font("Consolas", Font.PLAIN, 40));
	        g.drawString("ZONE #"+WebMainView.getCurrentZone(), 220, 430);
		}
		else {
			for(int i = 1; i <= SecurityZone.camNum; i++){
				DeviceUnit cam = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_CAMERA, i);
				g.drawImage(camImg, cam.getX(), cam.getY(), this);
	            g.setColor(Color.BLACK);
	            g.setFont(new Font("Arial", Font.PLAIN, 10));
	            g.drawString("#"+i, cam.getX(), cam.getY()+9);
			}
			
			for(int i = 1; i <= SecurityZone.wdNum; i++){
				DeviceUnit wd = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, i);
				if(MainDemo.controlSoftware.isAway() && wd.getUsing()){
					if(MainDemo.sensorController.testWinDoorSensor(i-1)){
						g.setColor(Color.GREEN);
						g.fillOval(wd.getX()-6, wd.getY()-6, csz, csz);
					}
					if(MainDemo.alarmController.dSensor(DeviceUnit.TYPE_WINDOW_SENSOR, i) && repaintBlink < 5){
			            g.setColor(Color.RED);
						g.fillOval(wd.getX()-6, wd.getY()-6, csz, csz);
					}
				}
				
				g.drawImage(wdImg, wd.getX(), wd.getY(), this);
	            g.setColor(Color.WHITE);
	            g.setFont(new Font("Arial", Font.PLAIN, 10));
	            g.drawString("#"+i, wd.getX(), wd.getY()+9);
	            
	            if(!wd.getUsing()){
		            g.setColor(Color.BLACK);
		            g.setFont(new Font("Arial", Font.PLAIN, 33));
		            g.drawString("X", wd.getX()+6, wd.getY()+26);
	            }
			}
			
			for(int i = 1; i <= SecurityZone.mNum; i++){
				DeviceUnit mo = SecurityZone.getDeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, i);

				if(MainDemo.controlSoftware.isAway() && mo.getUsing()){
					if(MainDemo.sensorController.testMotionDetector(i-1)){
						g.setColor(Color.GREEN);
						g.fillOval(mo.getX()-6, mo.getY()-6, csz, csz);
					}
					if(MainDemo.alarmController.dSensor(DeviceUnit.TYPE_MOTION_SENSOR, i) && repaintBlink < 5){
			            g.setColor(Color.RED);
						g.fillOval(mo.getX()-6, mo.getY()-6, csz, csz);
					}
				}
				
				g.drawImage(moImg, mo.getX(), mo.getY(), this);
	            g.setColor(Color.WHITE);
	            g.setFont(new Font("Arial", Font.PLAIN, 10));
	            g.drawString("#"+i, mo.getX(), mo.getY()+9);
	            
	            if(!mo.getUsing()){
		            g.setColor(Color.BLACK);
		            g.setFont(new Font("Arial", Font.PLAIN, 33));
		            g.drawString("X", mo.getX()+6, mo.getY()+26);
	            }
			}
		}
			
		// Power Display
        if(MainDemo.controlSoftware.isPowered()){
			g.setColor(Color.BLUE);
			g.fillRect(20, 410, 40, 30);
        }
        else{
        	g.setColor(Color.GRAY);
			g.fillRect(20, 410, 40, 30);
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 13));
        g.drawString("Power", 65, 430);

        // Armed Display
        if(MainDemo.controlSoftware.isAway()){
			g.setColor(Color.RED);
			g.fillRect(110, 410, 40, 30);
        }
        else{
        	g.setColor(Color.GRAY);
			g.fillRect(110, 410, 40, 30);
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 13));
        g.drawString("Armed", 155, 430);
		
	}
	
	public void run()
	{
		while(true)
		{
			try
			{
				Thread.sleep(100);
				repaintBlink = (repaintBlink + 1)%10;
			}
			catch(InterruptedException e)
			{
			}
			repaint();
		}
	}
}
