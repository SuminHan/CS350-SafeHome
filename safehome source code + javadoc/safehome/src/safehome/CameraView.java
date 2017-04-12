package safehome;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;


import java.awt.image.BufferedImage;

import safehome.device.DeviceCamera;

/*
 * CLASS NAME : CameraView 
 * DESCRIPTION
 * 		A simple java component that hold the BufferedImage from DeviceCamera
 *		and it has own drawing function 
 * PRECONDITION
 * 		This component was attached to any JFrame
 * 		There exist camera1.jpg file
 * PARAMETER : none
 * RETURN VALUE : none
 * USAGE : call repaint() method to refresh the camera view
 * EXAMPLE : none
 * POSTCONDITION : An image from a camera is displaying by this component
 * NOTES
 * 		In constructor, changing integer parameter of camera.setID(int)
 * 		can change loading image file - camera<int>.jpg  
 */ 
/**
 * <code>CameraView</code> class is a simple java component 
 * which has <code>{@link BufferedImage}</code> from <code>DeviceCamera</code>. 
 * This java component can be attached to any <code>{@linke JFrame}</code>. 
 * This class creates a <code>DeviceCamera</code> instance. 
 * The camera instance reads camera image from the file 
 * camera<int>.jpg and provides the image as an<code>BufferedImage</code> instance. 
 * <p>
 * <img src="../../doc-files/camera.PNG">
 * 
 * 
 * 
 * @author cs350TA
 *
 */
public class CameraView extends Component implements Runnable
{
	// Real instance of the Camera
	DeviceCamera camera = new DeviceCamera();
	
	/*
	 * NAME : CameraView (constructor)
	 * DESCRIPTION
	 * 		Load image file to DeviceCamera
	 * 		and draw image to attached dialog
	 * 		and initiate Thread to periodic update of camera view
	 * PRECONDITION
	 * 		The main instantiates MainDemo class and MainDemo instantiates CameraTest class
	 * 		and CameraTest instantiates this class in consecutively
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION
	 * 		JPG image file is loaded to memory and ready to draw
	 * 		and thread is running to update camera view
	 * NOTES : none
	 */ 
	/**
	 * Constructs camera view component. Constructor set the id of the camera instance and 
	 * start a thread to repaint the updated camera image periodically. <code>DeviceCamera</code> 
	 * reads an image from the file camera<int>.jpg in the current working director. 
	 * If the file cannot be opened, <code>DeviceCamera</code> shows an error message.  
	 * <p>
	 * Pre-condition: The main instantiates MainDemo class and MainDemo instantiates CameraTest class
	 * and CameraTest instantiates this class in consecutively
	 * <p>
	 * Post-condition: jpg image file is loaded to memory and the camera instance is ready to draw. 
	 * Thread is running to update camera view periodically. 
	 *   
	 */
	public CameraView(int id)
	{
		camera.setID(id);
		
		Thread th = new Thread(this);
		th.start();
	}
	
	/*
	 * NAME : paint
	 * DESCRIPTION
	 * 		This function get new img from camera device and re-draw image to attached dialog
	 * PRECONDITION
	 * 		JPG image file is loaded to camera device's memory and ready to draw
	 * PARAMETER : g - Graphics to draw image to display
	 * RETURN VALUE : none
	 * USAGE : To draw image, should know about usage of Graphic class 
	 * EXAMPLE : none
	 * POSTCONDITION : User can see the image from the camera
	 * NOTES : none
	 */ 
    /* (non-Javadoc)
     * @see java.awt.Component#paint(java.awt.Graphics)
     */
	
    /** 
     * Paints this component. This method gets new image from camera device and re-draw image to the component.
     * <p>
     * Pre-condition: JPG image file is loaded to camera device's memory and ready to draw
     * <p>
     * Post-condition: A user can see the image from the camera component.
     * @param g the graphics context to use for painting
     * @see java.awt.Component#paint(java.awt.Graphics)
     */
    public void paint(Graphics g) 
    {
        // Draw the image stretched to exactly cover the size of the drawing area.
        Dimension size = getSize();
        BufferedImage img = camera.getView();
        
        g.drawImage(img, 
                    0, 0, size.width, size.height,
                    0, 0, img.getWidth(null), img.getHeight(null),
                    null);                    
    }
     
    /*
	 * NAME : run
	 * DESCRIPTION
	 * 		This function executed by thread concurrently
	 *		and updates view of the camera at periodic interval
	 * PRECONDITION
	 * 		JPG image file is loaded to camera device's memory and ready to draw
	 * PARAMETER : g - Graphics to draw image to display
	 * RETURN VALUE : none
	 * USAGE : none 
	 * EXAMPLE : none
	 * POSTCONDITION : User can see the image from the camera
	 * NOTES
	 * 		Sleep interval should be less than 1000
	 * 		This function should have infinte loop
	 */ 
	/** 
	 * When an object implementing interface <code>Runnable</code> is used to create a thread, 
	 * starting the thread causes the object's run method to be called in that separately executing thread. 
	 * 
	 * This thread updates view of the camera in periodic interval 100ms.
	 * <p> 
	 * Pre-condition: JPG image file is loaded to camera device's memory and ready to draw.
	 * <p>
	 * Post-condition: A user can see the updated image from the camera 
	 * <p> 
	 * @see java.lang.Runnable#run()
	 */
    
	public void run()
	{
		while(true)
		{
			try
			{
				Thread.sleep(100);
			}
			catch(InterruptedException e)
			{
			}
			repaint();
		}
	}
    
    /*
	 * meaningless member variable in this application
	 * prevent java compiler warning message
	 */
	static final long serialVersionUID = 45634;
}


