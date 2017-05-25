package Implementation;
import java.util.*;
import LocalMonitoringStation.*;
import SensorCamera.*;
/**
 * This class is an implementation of the LMS. It extends the LMSPOA which
 * is used to give the LMS implementation. 
 * 
 * Faser Parvez u1167937
 * Assignment 2 CamCobra
 */
public class Implementation_LMS extends LMSPOA {
	private String LMSname;
	private Map<String, ArrayList<Implmentation_Sensor>> lmszones;
	/**
	 */
	public Implementation_LMS(String lms_name) {
		LMSname = lms_name;
		lmszones = new HashMap<String, ArrayList<Implmentation_Sensor>>();
	}

	/**
	 * This will return the LMS name.
	 */
	@Override
	public String name() {
		return LMSname;
	}

	/**
	 * This method will add a camera and zone to the given lms.
	 */
	@Override
	public void add_camera(Sensor sensor, String zone) {
		if(!lmszones.containsKey(zone)){		
			lmszones.put(zone, new ArrayList<Implmentation_Sensor>());
		}
		Implmentation_Sensor sensorEvt = new Implmentation_Sensor(sensor);
		lmszones.get(zone).add(sensorEvt);
		System.out.println();
		ConsoleValidation.WriteToLog("New Camera has been added '" + sensor.cameraname() + 
				"' to zone '" + zone + "'.");
		System.out.println();
	}

	/**
	 * This method will raise an alarm based upon a given reading from a given 
	 * sensor in the zone. If the zone doesn't already exist, the sensor does
	 * not exist in the zone, or the reading is not deemed as a threat, an 
	 * alarm will not be recorded.
	 */
	@Override
	public void raise_alerts(Sensor sensor, String zone, Image reading) {
		if(!lmszones.containsKey(zone)){		
			return;
		}
		if(reading.warning.value < sensor.image_warning().value) {
			return;
		}
		for(Implmentation_Sensor evt : lmszones.get(zone)) {			
			if(sensor.cameraname().equals(evt.lmsname()) && evt.sensor().camera_on()) {
				Alert alarm = new Alert(sensor.cameraname(), reading.time, reading.warning);
				evt.record_alarm(alarm);
				break;
			} 
		}	
	}

	/**
	 * This method will record the camera onto the zone
	 */
	@Override
	public void record_camera(Sensor sensor, String zone, Image reading) {
		if(!lmszones.containsKey(zone)){		
			return;
		}
		for(Implmentation_Sensor evt : lmszones.get(zone)) {
			if(sensor.cameraname().equals(evt.lmsname()) && sensor.camera_on()) {
				Log[] logs = evt.get_logs();
				if(logs.length != 0) {
					if(logs[logs.length-1].level.value == reading.warning.value){
						return;
					}
				}
				Log log = new Log(sensor.cameraname(), reading.time, reading.warning);
				evt.record_log(log);
				if(reading.warning.value >= sensor.image_warning().value) {
					raise_alerts(sensor, zone, reading);
				}
				evt.sensor().switch_on();
				break;
			}else if(sensor.cameraname().equals(evt.lmsname()) && sensor.camera_off()){
				break;
			}
		}		
	}

	/**
	 * This will return all the zones back
	 */
	@Override
	public String[] get_zones() {
		return lmszones.keySet().toArray(new String[lmszones.size()]);
	}

	/**
	 * This method will return an array of all the sensors that are registered 
	 * within the given zone.
	 */
	@Override
	public Sensor[] get_camera(String zone) {
		if(!lmszones.containsKey(zone)) {
			return new Sensor[0];
		}
		ArrayList<Sensor> sensors = new ArrayList<Sensor>();
		for(Implmentation_Sensor evt : lmszones.get(zone)) {
			sensors.add(evt.sensor());
		}
		return sensors.toArray(new Sensor[sensors.size()]);
	}
	
	/**
	 * This method will return all logs for the given sensor in the given zone.
	 */
	@Override
	public Log[] retrieve_logs(String sensor, String zone) {
		ArrayList<Implmentation_Sensor> events = lmszones.get(zone);
		for(Implmentation_Sensor evt : events) {
			if(evt.lmsname().equals(sensor)){
				return evt.get_logs();
			}
		}
		return new Log[0];
	}
	
	/**
	 * This method will return all alarms for the given sensor in the given 
	 * zone. 
	 */
	@Override
	public Alert[] retrieve_alerts(String sensor, String zone) {
		ArrayList<Implmentation_Sensor> events = lmszones.get(zone);
		for(Implmentation_Sensor evt : events) {
			if(evt.lmsname().equals(sensor)){
				return evt.get_alerts();
			}
		}
		return new Alert[0];
	}
}
