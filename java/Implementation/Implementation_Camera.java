package Implementation;
import SensorCamera.*;
import SensorCamera.SensorPackage.Status;
/**
 * This class is an implementation of the camera. It extends the CameraPOA which
 * is used to give the camera implementation. 
 * 
 * Faser Parvez u1167937
 * Assignment 2 CamCobra
 */
public class Implementation_Camera extends SensorPOA {
	// Camera name
	private String cameraname;
	// Camera Status
	private Status camerastatus;
	// Camera image current
	private Image current_image;
	// Camera image warning
	private Warning image_warning;

	/**
	 * Default Constructor
	 */
	public Implementation_Camera(String sensor_name, double level) {
		cameraname = sensor_name;
		camerastatus = Status.from_int(1);
		image_warning = new Warning(level);
		record_image(0);
	}

	/**
	 * Will return the Camera Name
	 * 
	 * @return String
	 */
	@Override
	public String cameraname() {
		return cameraname;
	}

	/**
	 * Will set the Camera Name
	 */
	@Override
	public void cameraname(String newName) {
		cameraname = newName;		
	}

	/**
	 * Will return the Camera Status
	 */
	@Override
	public Status status() {
		return camerastatus;
	}

	/**
	 * Will return the last camera image
	 */
	@Override
	public Image current_image() {
		return current_image;
	}

	/**
	 * Will return the last camera image
	 */
	@Override
	public Image get_image() {
		return current_image;
	}

	/**
	 * Will return the first warning image set
	 * 
	 * @return WarningLevel
	 */
	@Override
	public Warning image_warning() {
		return image_warning;
	}

	/**
	 * Will set a new warning level image
	 */
	@Override
	public void image_warning(Warning level) {
		image_warning = level;
	}

	/**
	 * Will set the Camera is On
	 */
	@Override
	public void switch_on() {
		camerastatus = Status.from_int(1);
		ConsoleValidation.WriteToLog("Camera:ON");
	}

	/**
	 * Will set the Camera to Off
	 */
	@Override
	public void switch_off() {
		camerastatus = Status.from_int(0);	
		ConsoleValidation.WriteToLog("Camera:OFF");
	}

	/**
	 * Will reset the Camera
	 */
	@Override
	public void reset() {
		camerastatus = Status.from_int(1);
		record_image(0);
		image_warning = new Warning(0);
		ConsoleValidation.WriteToLog("Camera:RESETTED");
	}
	
	/**
	 * This method will record the last image that was made onto the camera.
	 */
	@Override
	public void record_image(double value) {
		// Obtain the current timestamp
		long timestamp = System.currentTimeMillis();
		// Create a new Warning Level
		Warning level = new Warning(value);
		// Update the current reading
		current_image = new Image(timestamp, level);
	}

	/**
	 * Will return the status string of either "On" or "Off"
	 */
	@Override
	public String getstatus() {
		return (camerastatus.value() == 1) ? "on" : "off";
	}

	/**
	 * Will return if the camera is On
	 */
	@Override
	public boolean camera_on() {
		return (camerastatus.value() == 1);
	}

	/**
	 * Will return if the camera is Off
	 */
	@Override
	public boolean camera_off() {
		return (camerastatus.value() == 0);
	}
}