package GUI;
import javax.swing.JTabbedPane;
import LocalMonitoringStation.*;
/**
 * This is Zones tabs which will display all the information
 * inside the zones tabs
 * 
 * Faser Parvez u1167937
 * Assignment 2 CamCobra
 */
public class ZonesTabs extends JTabbedPane {
	// Default serial ID
	private static final long serialVersionUID = 5995057701253646722L;
	// The server reference
	private LMS lmsserver;
	
	/**
	 * Default Constructor  
	 */
	public ZonesTabs(LMS server) {
		super();
		this.lmsserver = server;
		setName(server.name());
		initialise();
	}
	
	/**
	 * This method will initialise the components
	 */
	private void initialise() {
		String[] zones = lmsserver.get_zones();
		for(String zone : zones) {
			ZonesModel zonesModel = new ZonesModel(lmsserver, zone);
			addTab(zone, zonesModel);
		}
	}
	
	/**
	 * This will update the LMS
	 */
	public void setLMS(LMS server) {
		this.lmsserver = server;
	}
	
	/**
	 * This will update the Zones information inside the panel
	 */
	public void updateZones() {
		removeAll();
		initialise();		
		
	}	
}
