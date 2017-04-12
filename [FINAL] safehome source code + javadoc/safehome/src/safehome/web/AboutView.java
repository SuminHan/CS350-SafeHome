package safehome.web;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import safehome.CameraTest;

/**
 * Help > About
 * @author SummerSnow
 *
 */
public class AboutView extends JFrame implements ActionListener{
	private static final long serialVersionUID = -4475666821527590593L;
	public static final int camNum = 5;
	public static CameraTest[] cameraTest;
	
	public AboutView()
	{
		super("About");
		setSize(450, 200);
		getContentPane().setLayout(null);
		setResizable(false);
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);
		
		// attach camera view to dialog
		JPanel displayPanel = new JPanel(new GridLayout(1, 2));
		JLabel imgLabel = new JLabel();
		ImageIcon img = new ImageIcon("software-engineering.jpg");
		imgLabel.setIcon(img);
		imgLabel.setIconTextGap(30);
		displayPanel.add(imgLabel);
		JTextArea description = new JTextArea(
				  "KAIST 2015 Spring\n"
				+ "[CS350] Intro. Software Engineering\n"
				+ "_______TEAM 5_______\n"
				+ "   - Sumin Han\n"
				+ "   - Joonhoo Oh\n"
				+ "\n"
				+ "SafeHome -- Version 1.0\n");
		description.setLineWrap(true);
		description.setEditable(false);
		description.setMargin(new Insets(7,  7,  7,  7));
		displayPanel.add(description);


		displayPanel.setBounds(0, 0, 450, 150);
		getContentPane().add(displayPanel);
		
		JButton jb = new JButton("close");
		jb.addActionListener(this);
		jb.setBounds(450-90, 150, 80, 20);
		getContentPane().add(jb);
		

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("close")){
			this.setVisible(false);
		}
	}

}
