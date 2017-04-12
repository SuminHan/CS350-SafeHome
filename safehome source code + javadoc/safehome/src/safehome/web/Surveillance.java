package safehome.web;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import safehome.CameraTest;
import safehome.CameraView;

/**
 * Surveillance class contains camera Thumbnails.
 * @author SummerSnow
 *
 */
public class Surveillance extends JFrame implements ActionListener{
	private static final long serialVersionUID = 2412692078519689605L;
	private static CameraTest[] cameraTest;
	
	public Surveillance()
	{
		super("Surveillance Camera Thumbnails");
		setSize(700, 200);
		getContentPane().setLayout(null);
		setResizable(false);
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width), 0);
		

		cameraTest = new CameraTest[SecurityZone.camNum];
		for(int id = 0; id < SecurityZone.camNum; id++){
			cameraTest[id] = new CameraTest(id+1);
		}
		
		// attach camera view to dialog
		JPanel displayPanel = new JPanel(new GridLayout(1, 5));
		
		for(int i = 1; i <= 5; i++){
			displayPanel.add(new CameraView(i));
		}
		
		displayPanel.setBounds(0, 0, 700, 150);
		getContentPane().add(displayPanel);

		JPanel displayPanel2 = new JPanel(new GridLayout(1, 5));
		for(int i = 1; i <= 5; i++){
			JButton jb = new JButton("Detail " + i);
			jb.addActionListener(this);
			displayPanel2.add(jb);
		}
		displayPanel2.setBounds(0, 150, 700, 25);
		getContentPane().add(displayPanel2);

	}
	
	public static CameraTest getCameraTest(int camNum){
		return cameraTest[camNum-1];
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for(int i = 1; i <= 5; i++){
			if(e.getActionCommand().equals("Detail " + i)){
				cameraTest[i-1].setVisible(true);
			}
		}
		
	}

}
