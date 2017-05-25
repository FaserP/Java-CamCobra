import org.omg.CORBA.ORB;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.PortableServer.*;
import Implementation.*;
import LocalMonitoringStation.*;
import SensorCamera.*;
/**
 * LMSServer class to run the LMS Server
 * 
 * Faser Parvez u1167937
 * Assignment 2 CamCobra
 */
public class LMSServer {
	// LMS Server
	private static Implementation_LMS lmsServer;
	// Naming Server
	private static NamingContextExt nameService;
	
	/**
	 */
	public static void main(String[] args) {
		try {
			// Get the User LMS Name
			String lmsname = ConsoleValidation.GetString("Enter a LMS Name:");
			
			// create and initialise the ORB
			final ORB orb = ORB.init(args, null);

			// get reference to rootpoa & activate the POAManager
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();

			// create servant and register it with the ORB
			lmsServer = new Implementation_LMS(lmsname);

			// get object reference from the servant
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(lmsServer);
			LMS server_ref = LMSHelper.narrow(ref);

			// Get a reference to the Naming service
			org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references("NameService");
			if (nameServiceObj == null) {
				System.out.println("nameServiceObj = null");
				return;
			}

			// Use NamingContextExt which is part of the Interoperable
			nameService = NamingContextExtHelper.narrow(nameServiceObj);
			if (nameService == null) {
				System.out.println("nameService = null");
				return;
			}
			// bind the Count object in the Naming service
			NameComponent[] countName = nameService.to_name(lmsname);
			nameService.rebind(countName, server_ref);

			// Server has loaded up correctly
			System.out.format("The LMS '%s' is now activated.%n", lmsname);

			// Wait for invocations from clients upon a new thread
			new Thread() { 
				public void run() {
					orb.run();
				}
			}.start();
			
			// Start the Polling of the zones
			startMessage();
		} 
		catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}
	}
	
	/**
	 * This method will start the polling process in order to update all the 
	 * current readings.
	 */
	private static void startMessage() {
		do {
			// Get a list of all the registered zones
			String[] zones = lmsServer.get_zones();

			// Loop over all zones
			for(String zone : zones) {
				// Get all the sensors within the given zone
				Sensor[] sensors = lmsServer.get_camera(zone);
				// Loop over all the sensors in the given zone
				for(Sensor sensor : sensors) {
					try {
						// Connect to the Sensor
						Sensor sens = SensorHelper.narrow(nameService.resolve_str(sensor.cameraname()));
						// Obtain a reading from the sensor
						Image level = sens.get_image();
						// Record the reading with the LMS
						lmsServer.record_camera(sens, zone, level);
					} catch (NotFound e) {
						System.err.format("ERROR: Sensor '%s' NOT FOUND %n", sensor.cameraname());
						System.err.format(e.getMessage());
					} catch (CannotProceed e) {
						System.err.println("ERROR: Unable to Proceed");
						System.err.format(e.getMessage());
					} catch (InvalidName e) {
						System.err.format("ERROR: Invalid name '%s' given %n", sensor.cameraname());
						System.err.format(e.getMessage());
					}
				}
			}
		} while(true);
	}
}
