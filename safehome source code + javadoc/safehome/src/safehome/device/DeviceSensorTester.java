package safehome.device;



public abstract class DeviceSensorTester 
{
	static SafeHomeSensorTest safeHomeSensorTest;	// for a sensor test dialog
	static DeviceSensorTester head_WinDoorSensor;		// a head for linked list of whole sensors
	static DeviceSensorTester head_MotionDetector;		// a head for linked list of whole sensors
	DeviceSensorTester next;							// a link to next node of linked list
	
	abstract public void intrude();
	abstract public void release();
	
	int sensorID;				// sensor's ID : given sequentially when new operation.
	
	static int newIdSequence_WinDoorSensor = 0;
	static int newIdSequence_MotionDetector = 0;
}


