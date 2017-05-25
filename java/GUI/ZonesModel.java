package GUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import LocalMonitoringStation.*;
import SensorCamera.*;
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
 * The this is Zones Model which will display all the information
 * inside the zones tabs
 * 
 * Faser Parvez u1167937
 * Assignment 2 CamCobra
 */
public class ZonesModel extends JPanel {
	// Default serial ID
	private static final long serialVersionUID = -6390800410286779927L;
	private JLabel SubTitleLabel;
	// LMS Server reference
	private LMS lmsserver;
	// List of sensors
	private Sensor[] camera;
	// A list of logs that are made by the LMS server
	private DefaultListModel<String> ModelLogs;
	JPanel picturepanel = new JPanel();
	
	/**
	 * Default Constructor
	 */
	public ZonesModel(LMS server, String zone) {
		super(new BorderLayout(), true);
		this.lmsserver = server;
		this.camera = server.get_camera(zone);
		setLayout(new BorderLayout());
		setName(zone);
		initialiseComponents();
	}

	/**
	 * This will initialise the components to build the given Zone tab.
	 */
	private void initialiseComponents() {
		// Setup the main table within the tab
		add(NorthPanel(), BorderLayout.NORTH);
		// Setup the log window areas
		this.add(SouthPanel(), BorderLayout.CENTER);
	}

	/**
	 * This will setup the north panel which will display the table information
	 */
	private JPanel NorthPanel() {
		// Columns to be used by the Table
		String[] columns = {"Camera Name", "Camera Status", "Current Image", "Setted Image"};
		// Create a new Table Model
		TableModel model = new TableModel(camera, columns);
		// Create the JTable
		final JTable table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false);
		table.getColumn("Camera Status").setCellRenderer(new ButtonRenderer());
	    table.getColumn("Camera Status").setCellEditor(
	        new ButtonEditor(new JCheckBox()));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(BorderFactory.createCompoundBorder());
		table.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent evt) {
		    	// Only work upon a double click
		    	if(evt.getClickCount() != 1){
		    		return;
		    	}
		    	// Get the row and col number
		        int row = table.rowAtPoint(evt.getPoint());
		        int col = table.columnAtPoint(evt.getPoint());
	        	// Sensor name
	        	String name = table.getModel().getValueAt(row, 0).toString();
	        	// Get the sensor object
	        	Sensor sensor = findCamera(name);		        
		        // Ensure the click came from a valid row/col
		        if (row >= 0 && col == 1) {		        	
		        	// Toggle the sensor state
		        	if(sensor != null){
		        		if(sensor.camera_on()){
		        			// turn off and update
		        			sensor.switch_off();
		        			JOptionPane.showMessageDialog(null,"Camera is now off","TITLE",JOptionPane.WARNING_MESSAGE);
		        		} else {
		        			// turn on and update
		        			sensor.switch_on();
		        			JOptionPane.showMessageDialog(null,"Camera is now on","TITLE",JOptionPane.WARNING_MESSAGE);
		        		}
		        		
		        		// Update the table
		        		table.getModel().setValueAt(sensor.getstatus(), row, 1);
		        		table.updateUI();
		        	}
		        } 
		        else if (row >= 0 && col == 2) {
		        	picturepanel.repaint();
		        	picturepanel.revalidate();
		        	picturepanel.removeAll();
		        	ImageIcon image = new ImageIcon("image/" + sensor.current_image().warning.value + ".jpg");
		        	JLabel label = new JLabel("", image, JLabel.LEFT);
		        	picturepanel.add(label);
			    }
		        else if (row >= 0 && col == 3) {
		        	picturepanel.repaint();
		        	picturepanel.revalidate();
		        	picturepanel.removeAll();
		        	ImageIcon image = new ImageIcon("image/" + sensor.image_warning().value + ".jpg");
		        	JLabel label = new JLabel("", image, JLabel.LEFT);
		        	picturepanel.add(label);	
		        	
		        }

		    }
		});

		// Create a Scroll Pane
		JScrollPane sp = new JScrollPane(table);
		sp.setPreferredSize(new Dimension(100, 100));
		// Add to a panel to obtain maximum size
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(sp, BorderLayout.NORTH);
		panel.add(getJLabel1(), BorderLayout.CENTER);
		return panel;
	}

	/**
	 * This will setup the south panel which will display the image and change
	 * information
	 */
	private JPanel SouthPanel() {
		// Add both panels to the main Panel
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setPreferredSize(new Dimension(0, 250));;
		panel.add(setupImagePanel());
		ModelLogs = new DefaultListModel<String>();
		JList<String> logList = new JList<String>(ModelLogs);
		logList.setBackground(Color.LIGHT_GRAY);
		logList.setForeground(Color.RED);
		logList.setPreferredSize(new java.awt.Dimension(97, 111));
		updateCameraLogs();
		panel.add(new JScrollPane(logList), BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * This will setup the image panel to display the images
	 */
	private JPanel setupImagePanel() {
		BorderLayout jpLayout = new BorderLayout();
		picturepanel.setLayout(jpLayout);
		picturepanel.setPreferredSize(new java.awt.Dimension(100, 117));
		picturepanel.setSize(100, 117);
		picturepanel.revalidate();
		picturepanel.repaint();
		
		return picturepanel;
	}

	/**
	 * This will setup the time format to display with the logs
	 */
	private String TimeFormat(long timestamp, String message){
		// Obtain the current formatted date
		SimpleDateFormat date_format = new SimpleDateFormat("EEEE, dd MMMM yyyy : HH:mm a");
		String date = date_format.format(new Date(timestamp));
		return String.format("[%s] %s", date, message);
	}

	/**
	 * This will set the LMS
	 */
	public void setLMS(LMS station){
		lmsserver = station;
	}

	/**
	 * This will update the Camera Logs information
	 */
	public void updateCameraLogs() {
		// Clear all logs
		ModelLogs.clear();
		// get the name of the zone
		String zone = getName();
		// Holds all logs from all sensors within the log
		ArrayList<Log> logs = new ArrayList<Log>();
		// Loop over all sensors
		for(Sensor s : lmsserver.get_camera(zone)) {
			// Get a list of the logs
			Log[] log_array = lmsserver.retrieve_logs(s.cameraname(), zone);

			// Add the array to the end of all logs
			logs.addAll(Arrays.asList(log_array));
		}
		// Sort the Logs based upon time
		Collections.sort(logs, new Comparator<Log>() {
			@Override
			public int compare(Log log1, Log log2) {
				return (int) (log1.timestamp - log2.timestamp);
			}
		});
		// Reverse the order (Most recent first)
		Collections.reverse(logs);
		// Loop over all logs
		for(Log log : logs) {
			// Format the message to be displayed
			String placeholder = "%s current image is: %.0f";
			String message = String.format(placeholder, log.sensor, log.level.value);
			// format the final log
			String formatted_log = TimeFormat(log.timestamp, message);
			// Add the log to the model
			ModelLogs.addElement(formatted_log);
		}	
	}
	
	/**
	 * This will find the Camera information
	 */
	private Sensor findCamera(String name){
		// Loop over all known sensors
		for(Sensor s : camera) {
			// Ensure the correct sensor has been found
    		if(s.cameraname().equals(name)){
    			return s;
    		}
    	}
		return null;
	}
	
	private JLabel getJLabel1() {
		if(SubTitleLabel == null) {
			SubTitleLabel = new JLabel();
			SubTitleLabel.setText("Select \"Current Image\" or \"Setted Image\" to display Camera image below");
			SubTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
			SubTitleLabel.setForeground(new java.awt.Color(0,0,0));
			SubTitleLabel.setFont(new java.awt.Font("Segoe UI",1,12));
		}
		return SubTitleLabel;
	}

	/**
	 * This will render the button which will be displayed inside the cells
	 * in the table.
	 */
	class ButtonRenderer extends JButton implements TableCellRenderer {

		  public ButtonRenderer() {
		    setOpaque(true);
		  }

		  public Component getTableCellRendererComponent(JTable table, Object value,
		      boolean isSelected, boolean hasFocus, int row, int column) {
		    if (isSelected) {
		      setForeground(table.getSelectionForeground());
		      setBackground(table.getSelectionBackground());
		    } else {
		      setForeground(table.getForeground());
		      setBackground(UIManager.getColor("Button.background"));
		    }
		    setText((value == null) ? "jhgg" : value.toString());
		    return this;
		  }
		}
	
	/**
	 * This will render the button which will be displayed inside the cells
	 * in the table.
	 */
	class ButtonEditor extends DefaultCellEditor {
		  protected JButton button;

		  private String label;

		  private boolean isPushed;

		  public ButtonEditor(JCheckBox checkBox) {
		    super(checkBox);
		    button = new JButton();
		    button.setOpaque(true);
		    }
		  }
}
