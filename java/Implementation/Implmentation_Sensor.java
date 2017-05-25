package Implementation;
import java.text.SimpleDateFormat;
import java.util.*;
import LocalMonitoringStation.*;
import SensorCamera.*;
/**
 * This class is an implementation of the Sensor. It extends the SensorEventsPOA which
 * is used to manage the sensor and the logs/alerts that goes with the sensor. 
 * 
 * Faser Parvez u1167937
 * Assignment 2 CamCobra
 */
public class Implmentation_Sensor extends SensorEventsPOA {
	// sensor name 
	private Sensor sensor;
	// sensor log
	private ArrayList<Log> Logs;
	// sensor alerts
	private ArrayList<Alert> Alerts;
	/**
	 */
	public Implmentation_Sensor(Sensor sensor) {
		this.sensor = sensor;
		this.Logs = new ArrayList<Log>();
		this.Alerts = new ArrayList<Alert>();
	}
	
	/**
	 * Return the sensor name
	 */
	@Override
	public String lmsname() {
		return sensor.cameraname();
	}
	
	/**
	 * Return the sensor
	 */
	@Override
	public Sensor sensor() {
		return sensor;
	}
	
	/**
	 * Record a log for the sensor
	 */
	@Override
	public void record_log(Log log) {
		Logs.add(log);
	}

	/**
	 * Record a alarm for the sensor
	 */
	@Override
	public void record_alarm(Alert alarm) {
		Alerts.add(alarm);
	}

	/**
	 * Return all the logs for the sensor
	 */
	@Override
	public Log[] get_logs() {
		return Logs.toArray(new Log[Logs.size()]);
	}
	
	/**
	 * Return all the alarms for the sensor
	 */
	@Override
	public Alert[] get_alerts() {
		return Alerts.toArray(new Alert[Alerts.size()]);
	}

	/**
	 * This will return all the logs for the sensor which will be
	 * given in a time format to view.
	 */
	@Override
	public Log[] get_timelogs(long timestamp) {
		String day = calculateDate(timestamp);
		ArrayList<Log> dayLogs = new ArrayList<Log>();
		for(Log log : Logs) {
			String logDate = calculateDate(log.timestamp);
			if(logDate.equals(day)) {
				dayLogs.add(log);
			}
		}
		return dayLogs.toArray(new Log[dayLogs.size()]);
	}

	/**
	 * Will return all the alerts for the sensor which will be
	 * given in a time format to view.
	 */
	@Override
	public Alert[] get_timealerts(long timestamp) {
		String day = calculateDate(timestamp);
		ArrayList<Alert> alarmLogs = new ArrayList<Alert>();
		for(Alert alarm : Alerts) {
			String logDate = calculateDate(alarm.timestamp);
			if(logDate.equals(day)) {
				alarmLogs.add(alarm);
			}
		}
		return alarmLogs.toArray(new Alert[alarmLogs.size()]);
	}
	
	/**
	 * Get a time format which will be used for the get_timelogs and
	 * get_timealerts methods.
	 */
	private String calculateDate(long millisecs) {
	    SimpleDateFormat date_format = new SimpleDateFormat("EEEE, dd MMMM yyyy : HH:mm a");
	    return date_format.format(new Date(millisecs));
	}
}
