import Implementation.*;
import SensorCamera.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;
/**
 * CameraServer class to run the Camera Server
 * 
 * Faser Parvez u1167937
 * Assignment 2 CamCobra
 */
public class CameraServer {
	/**
	 *
	 */
	public static void main(String[] args) {
		try {
			// get the camera name
			String CameraMessage = "First Enter a name for the Camera:";
			String name = ConsoleValidation.GetString(CameraMessage);

			// get the camera image warning number set to 
			CameraMessage = "Enter a Warning level number range 1-10 to set for the Camera:";
			double warning = ConsoleValidation.GetDouble(CameraMessage);

			// create ORB
			ORB orb = ORB.init(args, null);

			// gets the rootpoa and activate the POAManager
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();

			// implement the servant and register it with the ORB
			Implementation_Camera sensor = new Implementation_Camera(name, warning);

			// get object reference from the servant
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(sensor);
			Sensor sref = SensorHelper.narrow(ref);

			// Get a reference to the Naming service
			org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references("NameService");
			if (nameServiceObj == null) {
				System.out.println("nameServiceObj = null");
				return;
			}
			// Use NamingContextExt which is part of the Interoperable
			NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
			if (nameService == null) {
				System.out.println("nameService = null");
				return;
			}
			// bind the Count object in the Naming service
			NameComponent[] countName = nameService.to_name(name);
			nameService.rebind(countName, sref);

			// loads the Camera Server
			System.out.println("The Camera Server is now running.");
			orb.run();
		} 
		catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}
	}	
}
