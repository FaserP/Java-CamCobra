import javax.swing.*;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.*;
import GUI.*;
import RegionalMonitoringCentre.*;
/**
 * RMCCLient class to run the RMC Client in a GUI.
 * 
 * Faser Parvez u1167937
 * Assignment 2 CamCobra
 */
public class RMCClient extends JFrame {
	// Default serial ID
	private static final long serialVersionUID = 2775262737058189908L;
	// Name of the RMC
	private static String name = "JavaRMC";
	// Naming service reference
	private static NamingContextExt nameService;
	// Remote Monitoring Centre reference
	private static RMC RMCserver;
	// Client GUI reference
	private static GUI_RMC rmcGUI;
	/**
	 */
	public static void main(String[] args) {
		try {
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
			// Obtain the Sensor reference in the Naming service
			RMCserver = RMCHelper.narrow(nameService.resolve_str(name));

			// Start the GUI
			rmcGUI = new GUI_RMC(RMCserver, nameService);
			rmcGUI.setTitle("Remote Monitoring Centre Client");
			rmcGUI.setVisible(true);
		}
		catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}
	}
}