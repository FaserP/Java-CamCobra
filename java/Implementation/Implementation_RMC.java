package Implementation;
import java.util.*;
import LocalMonitoringStation.*;
import RegionalMonitoringCentre.*;
import RegionalMonitoringCentre.Warning;
import SensorCamera.*;
/**
 * This class is an implementation of the RMC. It extends the RMCPOA which
 * is used to give the RMC implementation. 
 * 
 * Faser Parvez u1167937
 * Assignment 2 CamCobra
 */
public class Implementation_RMC extends RMCPOA {
	// Sensor List
	private List<LMS> rmcstations;
	//  users list
	private Map<String, Map<String, ArrayList<User>>> users;
	// warning list
	private ArrayList<Warning> warnings;
	
	/**
	 */
	public Implementation_RMC() {
		rmcstations = new ArrayList<LMS>();
		users = new HashMap<String, Map<String, ArrayList<User>>>();
		warnings = new ArrayList<Warning>();
	}

	/**
	 * This will add the lms to a list.
	 */	
	@Override
	public void add_station(LMS lms) {
		if(!rmcstations.contains(lms)) {
			rmcstations.add(lms);
		}else{
			int index = rmcstations.indexOf(lms);
			rmcstations.set(index, lms);
		}
	}

	/**
	 * This will remove the lms from a list
	 */
	@Override
	public void remove_station(LMS lms) {
		rmcstations.remove(lms);
	}

	/**
	 * This method will allow a new user to register a use in a zone
	 */
	@Override
	public void register_user(User user, String lms, String zone) {
		if(!users.containsKey(lms)){
			users.put(lms, new HashMap<String, ArrayList<User>>());
		}
		if(!users.get(lms).containsKey(zone)){
			users.get(lms).put(zone, new ArrayList<User>());
		}	
		if(!users.get(lms).get(zone).contains(user)){
			users.get(lms).get(zone).add(user);
		}
	}

	/**
	 * This will return all of the lms from the list
	 */
	@Override
	public LMS get_station(String station) {
		for(LMS lms : rmcstations) {
			if(lms.name().equals(station)) {
				return lms;
			}
		}
		return null;
	}

	/**
	 * Will return all of the lms in a array list.
	 */
	@Override
	public String[] get_stations() {
		String[] station_list = new String[rmcstations.size()];
		for(int i = 0; i < rmcstations.size(); i++) {
			station_list[i] = rmcstations.get(i).name();
		}
		return station_list;
	}

	/**
	 * This will return all of the zones from the list.
	 */
	@Override
	public String[] get_zones(String station) {
		for(LMS lms : rmcstations) {
			if(lms.name().equals(station)) {
				return lms.get_zones();
			}
		}
		return new String[0];
	}

	/**
	 * This will return all of the sensors from the list.
	 */
	@Override
	public Sensor[] get_sensors(String station, String zone) {
		for(LMS lms : rmcstations) {
			if(lms.name().equals(station)) {
				String[] zones = lms.get_zones();
				for(String z : zones) {
					if(z.equals(zone)) {
						return lms.get_camera(zone);
					}
				}
				break;		
			}
		}
		return new Sensor[0];
	}

	/**
	 * This method will return all the current sensor data for the station
	 */
	@Override
	public String[][] get_sensors_data(String station, String zone) {
		Sensor[] sensors = get_sensors(station, zone);
		String[][] data = new String[sensors.length][4];
		for(int i = 0; i < sensors.length; i++) {
			Sensor sensor = sensors[i];
			String name = sensor.cameraname();
			String status = sensor.getstatus();
			String warning_level = Double.toString(sensor.image_warning().value);
			String reading = Double.toString(sensor.get_image().warning.value);
			data[i] = new String[]{ name, status, warning_level, reading };			
		}
		return data;
	}

	/**
	 * This will return a list of warnings for a given LMS and zone 
	 */
	@Override
	public Warning[] get_all_warnings(String lms, String zone) {
		ArrayList<Warning> warning_list = new ArrayList<Warning>();
		for(Warning warning : warnings) {
			if(warning.lms.equals(lms) && warning.zone.equals(zone)){
				warning_list.add(warning);
			}
		}
		Collections.sort(warning_list, new Comparator<Warning>() {
			@Override
			public int compare(Warning w1, Warning w2) {
				return (int) (w1.timestamp - w2.timestamp);
			}
		});
		Collections.reverse(warning_list);
		return warning_list.toArray(new Warning[warning_list.size()]);
	}
}
