package GUI;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import org.omg.CosNaming.NamingContextExt;
import LocalMonitoringStation.*;
import RegionalMonitoringCentre.*;
/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
/**
 * The Main GUI that is used for the RMC Client.
 * 
 * Faser Parvez u1167937
 * Assignment 2 CamCobra
 */
public class GUI_RMC extends JFrame {
	// Default serial ID
	private static final long serialVersionUID = 5992272537886935199L;
	// A copy of the current JFrame
	private JFrame self;
	// RMC server object
	private RMC rmcserver;
	// Naming service reference
	private NamingContextExt nameService;
	// The main tabbed object reference
	private JTabbedPane tabs;
	private JButton refreshButton;
	private JButton removeButton;
	private JButton addButton;
	private JLabel TitleLabel;
	private JPanel TitlePanel;
	private JPanel ButtonPanel;

	/**
	 * Default Constructor
	 */
	public GUI_RMC(RMC server, NamingContextExt nameService) {
		super();
		this.rmcserver = server;
		this.nameService = nameService;
		this.self = this;
		this.setSize(500, 680);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("CamCobra - RMC Client");
		initialiseComponents();
	}

	/**
	 * initialise the components correctly to build the main GUI.
	 */
	private void initialiseComponents() {
		// Set the main frame to use a BorderLayout
		getContentPane().setLayout(new BorderLayout());
		
		// Setup Top Panel
		getContentPane().add(HeaderPane(), BorderLayout.NORTH);
		// Setup Center Panel
		getContentPane().add(CenterPane(), BorderLayout.CENTER);
	}

	/**
	 * Set up the Header Pane
	 */
	private JPanel HeaderPane() {
		JPanel panel = new JPanel();
		BoxLayout panelLayout = new BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS);
		panel.setLayout(panelLayout);
		panel.setSize(984, 50);
		panel.setPreferredSize(new java.awt.Dimension(494, 64));
		panel.setForeground(new java.awt.Color(0,0,0));
		panel.setBackground(new java.awt.Color(203,228,228));
		panel.add(getTitlePanel());
		panel.add(getButtonPanel());
		return panel;
	}

	/**
	 * Setup the Center Pane which will hold the information for
	 * the tab area and the table.
	 */
	private JPanel CenterPane() {	
		// Setup a new TabbedPane		
		tabs = new JTabbedPane();
		// add the data
		updateTabbedArea();
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(tabs, BorderLayout.CENTER);
		tabs.setPreferredSize(new java.awt.Dimension(641, 441));
		tabs.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
		for (int c = 0; c < tabs.getComponentCount(); ++c)
		{
			tabs.getComponentAt(c).getBackground();
			tabs.setBackgroundAt(c, Color.BLACK);
			tabs.getComponentAt(c).getForeground();
			tabs.setForegroundAt(c, Color.WHITE);
		}

		tabs.setOpaque(true);
		tabs.setUI(new BasicTabbedPaneUI()); 
		//tabs.setBackgroundAt(index, background)
		return panel;
	}

	/**
	 * This method will update the GUI by refreshing
	 */
	public void updateGUI(RMC server){
		this.rmcserver = server;
		updateTabbedArea();
	}

	/**
	 * This method will display all the information inside the tabs area.
	 */
	public void updateTabbedArea() {
		// Remove all existing tabs		
		tabs.removeAll();
		// Get a list of stations
		String[] stations = rmcserver.get_stations();
		// Loop over all stations
		for(String station : stations) {		
			// Obtain the LMS
			LMS lms = rmcserver.get_station(station);
			// Setup a new main Station Pane
			ZonesTabs stationPane = new ZonesTabs(lms);
			// Add the Station Panel to the Main tabbed area
			tabs.addTab(station, stationPane);
		}
	}

	/**
	 * This method will setup the add button
	 */
	private JButton getAddButton() {
		if(addButton == null) {
			addButton = new JButton();
			addButton.setText("Add");
			addButton.setBounds(117, 45, 37, 23);
			addButton.setSize(45, 23);
			addButton.setPreferredSize(new java.awt.Dimension(115, 25));
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					// Obtain the name of the Station
					String input = JOptionPane.showInputDialog("Enter LMS name to add");
					// Ensure the user has input a value
					if ((input != null) && (input.length() > 0)) {
						try {
							// try to find the object reference
							LMS lms = LMSHelper.narrow(nameService.resolve_str(input));
							// Add to the server
							rmcserver.add_station(lms);
							// Perform an update
							updateGUI(rmcserver);
						} catch (Exception err) {
							JOptionPane.showMessageDialog(self, "Invalid LMS name.");
						}
					} else {
						JOptionPane.showMessageDialog(self, "Please enter an LMS name.");
					}
				}
			});
		}
		return addButton;
	}
	
	/**
	 * This method will setup the remove button
	 */
	private JButton getRemoveButton() {
		if(removeButton == null) {
			removeButton = new JButton();
			removeButton.setText("Remove");
			removeButton.setBounds(178, 46, 54, 22);
			removeButton.setPreferredSize(new java.awt.Dimension(115, 25));
			removeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
						// Get the list of known stations
					String input = JOptionPane.showInputDialog("Enter LMS name to remove.");
					// Ensure the user has input a value
					if ((input != null) && (input.length() > 0)) {
						try {
							// try to find the object reference
							LMS lms = LMSHelper.narrow(nameService.resolve_str(input));
							// Add to the server
							rmcserver.remove_station(lms);
							// Perform an update
							updateGUI(rmcserver);
						} catch (Exception err) {
							JOptionPane.showMessageDialog(self, "Invalid LMS name.");
						}
					} else {
						JOptionPane.showMessageDialog(self, "Please enter an LMS name.");
					}
				}
			}
			);

		}
		return removeButton;
	}
	
	/**
	 * This method will setup the refresh button
	 */
	private JButton getRefreshButton() {
		if(refreshButton == null) {
			refreshButton = new JButton();
			refreshButton.setText("Refesh");
			refreshButton.setBounds(243, 44, 54, 25);
			refreshButton.setPreferredSize(new java.awt.Dimension(115, 25));
			refreshButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					new Thread() {
						public void run(){
							try {
								rmcserver = RMCHelper.narrow(nameService.resolve_str("JavaRMC"));
								// Update the GUI
								updateGUI(rmcserver);
								// Update the status bar
							} catch (Exception err) {
								System.err.print("A servious error has occured");
								err.printStackTrace();
							}
						}
					}.start();
				}
			});
		}
		return refreshButton;
	}

	/**
	 * This method will setup the title Panel
	 */
	private JPanel getTitlePanel() {
		if(TitlePanel == null) {
			TitlePanel = new JPanel();
			FlowLayout jPanel1Layout = new FlowLayout();
			TitlePanel.setLayout(jPanel1Layout);
			TitlePanel.setPreferredSize(new java.awt.Dimension(494, 31));
			TitlePanel.setBackground(new java.awt.Color(203,228,228));
			TitlePanel.add(getTitleLabel());
		}
		return TitlePanel;
	}
	
	/**
	 * This method will setup the Button Panel
	 */
	private JPanel getButtonPanel() {
		if(ButtonPanel == null) {
			ButtonPanel = new JPanel();
			FlowLayout jPanel2Layout = new FlowLayout();
			ButtonPanel.setLayout(jPanel2Layout);
			ButtonPanel.setPreferredSize(new java.awt.Dimension(494, 29));
			ButtonPanel.setBackground(new java.awt.Color(203,228,228));
			ButtonPanel.add(getAddButton());
			ButtonPanel.add(getRemoveButton());
			ButtonPanel.add(getRefreshButton());
		}
		return ButtonPanel;
	}
	
	/**
	 * This method will setup the title Label
	 */
	private JLabel getTitleLabel() {
		if(TitleLabel == null) {
			TitleLabel = new JLabel();
			TitleLabel.setText("CAMCOBRA");
			TitleLabel.setFont(new java.awt.Font("Segoe UI",1,20));
			TitleLabel.setForeground(new java.awt.Color(0,0,160));
			TitleLabel.setPreferredSize(new java.awt.Dimension(115, 23));
		}
		return TitleLabel;
	}

}