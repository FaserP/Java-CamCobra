import org.omg.CORBA.ORB;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import Implementation.ConsoleValidation;
import SensorCamera.*;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * CameraCleint class to run the Camera Client on a command line.
 * 
 * Faser Parvez u1167937
 * Assignment 2 CamCobra
 */
public class CameraClient {
	// Sensor object reference
	private static Sensor Camera;
	/**
	 * 
	 */
	public static void main(String[] args) {
		// Get the Camera name to run onto the Client
		String cameraName = "";
		try {
			// Connect to the Camera you set up on the Camera Server
			String CameraMessage = "Enter the Camera Name you set up in the Camera Server:";
			cameraName = ConsoleValidation.GetString(CameraMessage);   	    

			// Initialise the ORB
			ORB orb = ORB.init(args, null);

			// Get a reference to the Naming service
			org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references("NameService");
			if (nameServiceObj == null) {
				System.out.println("nameServiceObj = null");
				return;
			}

			// Initialise the Interoperable naming Service via NamingContextExt
			NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
			if (nameService == null) {
				System.out.println("nameService = null");
				return;
			}
			Camera = SensorHelper.narrow(nameService.resolve_str(cameraName));
			// Intro for the user in the command line
			ShowIntro();
			// Shows User Command Line
			ShowCommandLine();

		} catch (NotFound e) {
			System.out.format("ERROR: Camera '%s', not found.%n", cameraName);  
		} catch (Exception e) {
			System.out.println("ERROR : " + e) ;
			e.printStackTrace(System.out);
		} 
	}

	/**
	 * This method provides the main user interface. A user is able to make 
	 * multiple readings, as well as remotely kill the server.
	 */
	private static void ShowCommandLine() {
		do {
			try {
				// Obtain the user input
				String input = ConsoleValidation.GetString("Enter a value shown on the user guide:");
				// Close Client
				if(input.contains("quit")){
					break;
				}
				//quit command line
				switch(input){
					case "quit":
						break;
				// switch camera on		
					case "on":
						Camera.switch_on();
						System.out.println("Camera Status: ON");
						break;	
				// switch camera off
					case "off":
						Camera.switch_off();
						System.out.println("Camera Status: OFF");
						break;
				// reset camera	
					case "reset":
						Camera.reset();
						ConsoleValidation.WriteToLog("Camera Status: RESET");
						break;
						
					default:
						double value = Double.parseDouble(input);
						// Record camera information
						Camera.record_image(value);
						// Shows the user the last listing
						ShowLastListing();
				}
			} catch(NullPointerException e) {
				// User hasn't entered anything
				System.out.println("rror: Enter a input");
			}catch(NumberFormatException e) {
				// User has not entered a well formatted number
				System.out.println("Error: Enter valid number.");
			}
		} while(true);
	}
	
	/**
	 * Give a introduction message on the command line with some instruction
	 * on how to use the system.
	 */
	private static void ShowIntro(){
		System.out.println();
		System.out.println("Welcome to the Camera Client");
		System.out.println("\t");
		System.out.println("\tTo use the Camera, please use the following:");
		System.out.println("\tEither 'ON', 'OFF, 'RESET', 'QUIT', or");
		System.out.println("\tA valid number between 1 to 10");
		System.out.println();
	}

	/**
	 * This will get all the information the user inputted previously from the
	 * command line and display the information onto the client with a date
	 * format.
	 */
	private static void ShowLastListing(){
		// convert the time
		Date timestamp = new Date(Camera.current_image().time);
		// Format the timestamp
		String date = new SimpleDateFormat("EEEE, dd MMMM yyyy : HH:mm a").format(timestamp);
		// Get the last reading
		double level = Camera.current_image().warning.value;
		// display onto the command line
		System.out.format("Last Camera information was on: %s.%n" +
				"and the image level was set to: %.0f%n", date, level);
	}
}
