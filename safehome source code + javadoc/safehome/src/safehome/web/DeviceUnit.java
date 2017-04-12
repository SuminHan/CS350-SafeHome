package safehome.web;

/**
 * Used to save the corresponding zone nubmer and using status. (X sign at FloorPlan)
 * @author SummerSnow
 *
 */
public class DeviceUnit {
	public final static int TYPE_WINDOW_SENSOR = 1;
	public final static int TYPE_MOTION_SENSOR = 2;
	public final static int TYPE_CAMERA = 3;
	
	int type;
	int num;
	int zone;
	int x, y;
	boolean using;
	public DeviceUnit(int t, int n, int z, int x, int y, boolean using){
		this.type = t;
		this.num = n;
		this.zone = z;
		this.x = x;
		this.y = y;
		this.using = using;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getZone(){
		return zone;
	}
	public void setZone(int z){
		this.zone = z;
	}
	public boolean getUsing(){
		return using;
	}
	public void setUsing(boolean using){
		this.using = using;
	}
	
}
