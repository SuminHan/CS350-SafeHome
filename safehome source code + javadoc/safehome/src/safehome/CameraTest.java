package safehome;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * CLASS NAME : CameraTest 
 * DESCRIPTION
 * 		A dialog to test camera's functions
 * 		It has four buttons to control camera's view (pan left/right, zoom in/out)
 * 		and one big screen of camera view
 * PRECONDITION : From main dialog, "Camera" button was chosen
 * PARAMETER : none
 * RETURN VALUE : none
 * USAGE : click the buttons to pan or zoom the camera
 * EXAMPLE : click zoom in button to zoom in the camera's view
 * POSTCONDITION : none
 * NOTES : If possible, click all buttons to test all functionality
 */ 

/**
 *  * This shows a dialog to test a camera's functions. The dialog has four buttons to control 
 * camera's view(pan left/right, zoom in/out) and one big screen of camera view. It looks like this:
 * <p>
 * @author SummerSnow
 *
 */
public class CameraTest extends JFrame implements ActionListener
{
	CameraView cameraView;
	
	/*
	 * NAME : CameraTest (constructor)
	 * DESCRIPTION
	 * 		Initiate sensors instances
	 * 		and place buttons(pan left/right, zoom in/out) and camera screen
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
	 * Constructs a camera frame. The camera frame consists of a camera screen and four buttons. 
	 * 
	 * <p>
	 * Pre-condition: <code>MainDemo</code> instance is initiated.
	 * <p>
	 * Post-condition: The layout of Camera test frame is made.
	 */
	public CameraTest(int id)
	{
		super("Camera Example");
		cameraView = new CameraView(id);
		setSize(455, 570);
		getContentPane().setLayout(null);
		setResizable(false);
		
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width), 0);
		
		
		// make buttons
		JButton buttonPanLeft = new JButton("PanLeft");
		buttonPanLeft.addActionListener(this);
		JButton buttonPanRight = new JButton("PanRight");
		buttonPanRight.addActionListener(this);
		JButton buttonZoomIn = new JButton("ZoomIn");
		buttonZoomIn.addActionListener(this);
		JButton buttonZoomOut = new JButton("ZoomOut");
		buttonZoomOut.addActionListener(this);
		
		// attach buttons to dialog
		JPanel displayPanel = new JPanel(new GridLayout(2, 2));
		displayPanel.add(buttonPanLeft);
		displayPanel.add(buttonPanRight);
		displayPanel.add(buttonZoomIn);
		displayPanel.add(buttonZoomOut);
		displayPanel.setBounds(25, 25, 400, 60);
		getContentPane().add(displayPanel);
		
		// attach camera view to dialog
		displayPanel = new JPanel(new GridLayout(1, 1));
		displayPanel.add(cameraView);
		displayPanel.setBounds(25, 110, 400, 400);
		getContentPane().add(displayPanel);
	}
	
	/*
	 * NAME : actionPerformed
	 * DESCRIPTION
	 * 		This function is the action listener(call back function) of four buttons
	 * 		PanLeft, PanRight, ZoomIn, ZoomOut to control camera view
	 * PRECONDITION : User click on any button
	 * PARAMETER : event - ActionEvent that carrying what button was pressed
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE
	 * 		If PanLeft button is clicked by user, 
	 * 		the view of camera is panned left
	 * POSTCONDITION : View of camera is changed and display is repainted.
	 * NOTES : none
	 */
	/* (non-Javadoc)
	 * @param event ActionEvent that carrying what button was pressed
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	/** 
	 * Invoked when an action occurs. When the "PanLeft" or "PanRight" button is pressed,
	 * the camera view pans left or right accordingly. When the "ZoonIn" or "ZoomOut" button is pressed
	 * the camera view zooms in  or out accordingly. The change of camera view is updated immediately
	 *  
	 * <p>
	 * Pre-condition: A user clicks on one of four buttons "ZoomIn", "ZoomOut", "PanLeft" and "PanRight"
	 * <p>
	 * Post-condition: View of camera is changed and display is repainted
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event)
	{
		if(event.getActionCommand().equals("PanLeft"))
		{
			cameraView.camera.panLeft();
		}
		else if(event.getActionCommand().equals("PanRight"))
		{
			cameraView.camera.panRight();
		}
		else if(event.getActionCommand().equals("ZoomIn"))
		{
			cameraView.camera.zoomIn();	
		}
		else if(event.getActionCommand().equals("ZoomOut"))
		{
			cameraView.camera.zoomOut();	
		}
		
		cameraView.repaint();
	}
	
	/*
	 * meaningless member variable in this application
	 * prevent java compiler warning message
	 */
	static final long serialVersionUID = 99767;
}