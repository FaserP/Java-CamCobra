import org.omg.CORBA.ORB;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.PortableServer.*;
import Implementation.*;
import LocalMonitoringStation.*;
import RegionalMonitoringCentre.*;
/**
 * RMCServer class to run the RMC Server
 * 
 * Faser Parvez u1167937
 * Assignment 2 CamCobra
 */
public class RMCServer {
	private static Implementation_RMC RMCserver;
	private static NamingContextExt nameService;
	/**
	 */
	public static void main(String[] args) {
		try {
			// The Regional Monitoring Station name
			String name = "JavaRMC";

			// create and initialise the ORB
			final ORB orb = ORB.init(args, null);

			// get reference to rootpoa & activate the POAManager
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();

			// create servant and register it with the ORB
			RMCserver = new Implementation_RMC();

			// get object reference from the servant
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(RMCserver);
			RMC server_ref = RMCHelper.narrow(ref);

			// Get a reference to the Naming service
			org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references("NameService");
			if (nameServiceObj == null) {
				System.out.println("nameServiceObj = null");
				return;
			}

			// Naming Service (INS) specification.
			nameService = NamingContextExtHelper.narrow(nameServiceObj);
			if (nameService == null) {
				System.out.println("nameService = null");
				return;
			}

			// bind the Count object in the Naming service
			NameComponent[] countName = nameService.to_name(name);
			nameService.rebind(countName, server_ref);

			// Server has loaded up correctly
			System.out.println("RMC Server is now activated.");

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
	 * LMS object references.
	 */
	private static void startMessage() {
		do{
			// Get a list of all the registered stations
			String[] stations = RMCserver.get_stations();
			
			// Loop over all stations
			for(String station : stations) {
				try {
					ConsoleValidation.WriteToLog("Attempting to connect to '" + station + "'.");
					
					// try to get the latest reference
					LMS lms = LMSHelper.narrow(nameService.resolve_str(station));
					
					// Add (or update) the LMS to the list of known Stations
					RMCserver.add_station(lms);
					
					ConsoleValidation.WriteToLog("'" + station + "' update completed.");
				} catch (NotFound e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CannotProceed e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidName e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} while(true);
	}
}
