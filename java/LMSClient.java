import org.omg.CORBA.ORB;
import org.omg.CosNaming.*;
import Implementation.ConsoleValidation;
import LocalMonitoringStation.*;
import SensorCamera.*;
/**
 * LMSClient class to run the Camera Client on the command line
 * 
 * Faser Parvez u1167937
 * Assignment 2 CamCobra
 */
public class LMSClient {
	// Local Monitoring Station server object
	private static LMS LMSserver;
	// Naming Service Object
	private static NamingContextExt nameService;


	/**
	 * The main entry point to the server.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Outputs the client usage guide
			LMSIntro();
			// Initialise the ORB
			ORB orb = ORB.init(args, null);
			// Get a reference to the Naming service
			org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references("NameService");
			if (nameServiceObj == null) {
				System.out.println("nameServiceObj = null");
				return;
			}
			// Initialise the Interoperable naming Service via NamingContextExt
			nameService = NamingContextExtHelper.narrow(nameServiceObj);
			if (nameService == null) {
				System.out.println("nameService = null");
				return;
			}
			// Obtain the Server reference in the Naming service
			String message = "Enter the LMS you want to connect to:";
			LMSserver = ConsoleValidation.Get_LMS(message, nameService);

			// Allows the user to input various commands to the server
			getLMSCoverage();

		} catch (Exception e) {
			System.out.println("ERROR : " + e) ;
			e.printStackTrace(System.out);
		} 
	}

	/**
	 * Give an intro for the LMS Client with instructions on how to use the client.
	 */
	private static void LMSIntro(){
		System.out.println("Welcome to the LMS Client");
		System.out.println("\t");
		System.out.println("\tTo use this client, you must enter the following;");
		System.out.println("\tLMS created from the LMS server");
		System.out.println("\tCamera created from the Camera Server");
		System.out.println("\tA Zone you must create by typing any name for the zone");
		System.out.println();
	}

	/**
	 * Give the LMS Client the information needed to input into the Command line
	 * which will then get saved into a log.
	 */
	private static void getLMSCoverage() {
			// Obtain the Sensor Name
			String message = "Enter the name of the Camera to connect to this LMS:";
			Sensor sensor = ConsoleValidation.Get_Sensor(message, nameService);

			// Obtain the Zone name
			message = "Enter a name for a zone you want the camera in:";
			String zone = ConsoleValidation.GetString(message);

			// Register the sensor to the given zone
			LMSserver.add_camera(sensor, zone);
	}
}
