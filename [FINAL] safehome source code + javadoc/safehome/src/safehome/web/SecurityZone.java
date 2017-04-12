package safehome.web;

/**
 * SecurityZone class contains DeviceUnit elements and number of 
 * camera, windoor sensors, motion sensors as static final int values.
 * Also displays maximum zone number currently exist.
 * @author SummerSnow
 *
 */
public class SecurityZone {
	private static DeviceUnit device[];
	private static int zoneNum = 2;
	
	public static final int camNum = 5;
	public static final int wdNum = 5;
	public static final int mNum = 5;
	
	public SecurityZone(){
		device = new DeviceUnit[]{
				new DeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, 1, 0, 5, 162, true),
				new DeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, 2, 1, 555, 34, true),
				new DeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, 3, 0, 116, 341, true),
				new DeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, 4, 0, 296, 341, true),
				new DeviceUnit(DeviceUnit.TYPE_WINDOW_SENSOR, 5, 2, 382, 408, true),
				
				new DeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, 1, 0, 170, 162, true),
				new DeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, 2, 0, 284, 155, true),
				new DeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, 3, 2, 384, 332, true),
				new DeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, 4, 1, 353, 126, true),
				new DeviceUnit(DeviceUnit.TYPE_MOTION_SENSOR, 5, 1, 474, 15, true),
				
				new DeviceUnit(DeviceUnit.TYPE_CAMERA, 1, 0, 12, 329, true),
				new DeviceUnit(DeviceUnit.TYPE_CAMERA, 2, 0, 204, 329, true),
				new DeviceUnit(DeviceUnit.TYPE_CAMERA, 3, 2, 544, 329, true),
				new DeviceUnit(DeviceUnit.TYPE_CAMERA, 4, 1, 310, 11, true),
				new DeviceUnit(DeviceUnit.TYPE_CAMERA, 5, 2, 382, 364, true)
		};
	}
	public static DeviceUnit getDeviceUnit(int type, int num){
		return device[type*5-5+num-1];
	}
	
	public static void newZone(){
		if(zoneNum < 9) zoneNum++;
	}
	public static int getZoneNum(){
		return zoneNum;
	}
	public static void deleteZone(int n){
		if(zoneNum >= 0){
			for(DeviceUnit u : device){
				if(u.getZone() == n)
					u.setZone(0);
				else if(u.getZone() > n)
					u.setZone(u.getZone()-1);
			}
			zoneNum--;
		}
	}
}
