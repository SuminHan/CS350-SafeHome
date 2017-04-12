package safehome.web;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import safehome.MainDemo;

/**
 * WebMainView after successfully login to the WebInterface.
 * @author SummerSnow
 *
 */
public class WebMainView extends JFrame implements ActionListener{
	private static final long serialVersionUID = -6942150754112374943L;
	static int currentZoneView = 0;
	static boolean zoneMode = false;
	static int currentZone = -1;
	static JPanel fp, displayPanel, zmenuPanel, zonePanel;
	static JButton awayButton, stayButton, zoneButton, zoneAddButton, zoneDelButton, zoneFinishButton, zb[];
	public WebMainView()
	{
		super("Security Zones");
		
		setSize(600, 700);
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width), (screenSize.height - frameSize.height)/2);
		getContentPane().setLayout(null);
		setResizable(false);

		JMenuBar mbar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		JMenuItem turnoff = new JMenuItem("Turn off");
		turnoff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		menu.add(turnoff);

		JMenu surv = new JMenu("Surveillance");
		JMenuItem viewThumb = new JMenuItem("View Thumbnails");
		viewThumb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				WebService.survView.setVisible(true);
			}
		});
        surv.add(viewThumb);
		
		
		JMenu help = new JMenu("Help");
		JMenuItem viewAbout = new JMenuItem("About");
		viewAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				WebService.aboutView.setVisible(true);
			}
		});
		help.add(viewAbout);
		
        

		mbar.setBounds(0, 0, 600, 25);
		mbar.add(menu);
		mbar.add(surv);
		mbar.add(help);
		
		fp = new FloorPlan();
		fp.setBounds(0, 25, 600, 445);
		
		displayPanel = new JPanel();
		
		awayButton = new JButton("Away");
		awayButton.addActionListener(this);
		stayButton = new JButton("Stay");
		stayButton.addActionListener(this);
		
		displayPanel.add(awayButton);
		displayPanel.add(stayButton);
		
		displayPanel.setBounds(0, 445+25, 600, 40);
		
		zmenuPanel = new JPanel();
		
		zoneButton = new JButton("Zone");
		zoneButton.addActionListener(this);
		
		zoneAddButton = new JButton("Add");
		zoneAddButton.addActionListener(this);
		zoneAddButton.setEnabled(false);

		zoneDelButton = new JButton("Delete");
		zoneDelButton.addActionListener(this);
		zoneDelButton.setEnabled(false);
		
		zoneFinishButton = new JButton("Finish");
		zoneFinishButton.addActionListener(this);
		zoneFinishButton.setEnabled(false);
		
		zmenuPanel.add(zoneButton);
		zmenuPanel.add(zoneAddButton);
		zmenuPanel.add(zoneDelButton);
		zmenuPanel.add(zoneFinishButton);
		zmenuPanel.setBounds(0, 510, 600, 40);
		zmenuPanel.setVisible(true);
		
		zonePanel = new JPanel();

		zb = new JButton[10];
		for(int i = 0; i < 10; i++){
			zb[i] = new JButton(""+i);
			zb[i].addActionListener(this);
			zonePanel.add(zb[i]);
		}
		resetCurrentZone();
		/*
		for(int i = 0; i <= SecurityZone.getZoneNum(); i++){
			zonePanel.add(zb[i]);
		}
		*/
		

		zonePanel.setBounds(0, 570, 600, 700);
		zonePanel.setVisible(true);
		
		getContentPane().add(mbar);
		getContentPane().add(fp);
		getContentPane().add(displayPanel);
		getContentPane().add(zmenuPanel);
		getContentPane().add(zonePanel);
		
		disableZoneMode();
	}
		
	private void enableZoneMode(){
		awayButton.setEnabled(false);
		stayButton.setEnabled(false);
		zoneButton.setEnabled(false);
		zoneAddButton.setEnabled(true);
		zoneDelButton.setEnabled(true);
		zoneFinishButton.setEnabled(true);
		setCurrentZone(0);
		zoneMode = true;
	}
	
	private void disableZoneMode(){
		awayButton.setEnabled(true);
		stayButton.setEnabled(true);
		zoneButton.setEnabled(true);
		zoneAddButton.setEnabled(false);
		zoneDelButton.setEnabled(false);
		zoneFinishButton.setEnabled(false);
		setCurrentZone(-1);
		zoneMode = false;
	}
	
	public static int getCurrentZone(){
		return currentZone;
	}
	public static boolean isZoneMode(){
		return zoneMode;
	}
	
	private void setCurrentZone(int target){
		for(int i = 0; i <= SecurityZone.getZoneNum(); i++){
			if(target == i || target == -1)
				zb[i].setEnabled(false);
			else
				zb[i].setEnabled(true);
		}
		currentZone = target;
	}
	
	private void resetCurrentZone(){
		zonePanel.removeAll();
		for(int i = 0; i <= SecurityZone.getZoneNum(); i++){
			zonePanel.add(zb[i]);
		}
		zonePanel.doLayout();
		zonePanel.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if((e.getActionCommand().equals("Away")))
		{
			if(MainDemo.controlSoftware.isPowered()){
				if(MainDemo.controlSoftware.isAway()){
	                JOptionPane.showMessageDialog(WebMainView.this, "Status is already away");
				}
				else{
					int dialogButton = JOptionPane.showConfirmDialog(WebMainView.this, "Confirm?");
					if(dialogButton == JOptionPane.YES_OPTION){
						MainDemo.controlSoftware.away();
					}
				}
			}
			else{
                JOptionPane.showMessageDialog(WebMainView.this, "System must be powered");
			}
		}
		else if((e.getActionCommand().equals("Stay")))
		{
			if(MainDemo.controlSoftware.isPowered()){
				if(!MainDemo.controlSoftware.isAway()){
	                JOptionPane.showMessageDialog(WebMainView.this, "Status is already stay");
				}
				else{
					int dialogButton = JOptionPane.showConfirmDialog(WebMainView.this, "Confirm?");
					if(dialogButton == JOptionPane.YES_OPTION){
						MainDemo.controlSoftware.stay();
					}
				}
			}
			else{
                JOptionPane.showMessageDialog(WebMainView.this, "System must be powered");
			}
		}
		else if((e.getActionCommand().equals("Zone")))
		{
			enableZoneMode();
		}
		else if((e.getActionCommand().equals("Finish")))
		{
			disableZoneMode();
		}
		else if((e.getActionCommand().equals("Add")))
		{
			int dialogButton = JOptionPane.showConfirmDialog(this, "Add new zone?");
			if(dialogButton == JOptionPane.YES_OPTION){
				SecurityZone.newZone();
				resetCurrentZone();
				setCurrentZone(SecurityZone.getZoneNum());
			}
			
		}
		else if((e.getActionCommand().equals("Delete")))
		{
			if(SecurityZone.getZoneNum() > 0){
				int dialogButton = JOptionPane.showConfirmDialog(this, "Delete current zone?");
				if(dialogButton == JOptionPane.YES_OPTION){
					SecurityZone.deleteZone(getCurrentZone());
					resetCurrentZone();
					setCurrentZone(0);
				}
			}
			else{
				JOptionPane.showMessageDialog(this, "At least 1 zone must be exist");
			}
		}
		else{
			for(int i = 0; i <= SecurityZone.getZoneNum(); i++){
				if((e.getActionCommand().equals(""+i))){
					setCurrentZone(i);
				}
			}
		}
		
		
	}
	

}
