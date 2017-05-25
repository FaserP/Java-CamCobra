package GUI;
import javax.swing.table.AbstractTableModel;
import SensorCamera.Sensor;
/**
 * The Tabel Model which will be used to setup the camera
 * information inside a table
 * 
 * Faser Parvez u1167937
 * Assignment 2 CamCobra
 */
public class TableModel extends AbstractTableModel {
	// Default serial ID
	private static final long serialVersionUID = -9073604976646704798L;
	// The list of columns
	private String[] tablecolumn;
	// The raw data array
	private Object[][] tabledata;	
	/**
	 */
	public TableModel(Object[][] CameraData, String[] Column) {
		super();
		this.tablecolumn = Column;
		this.tabledata = CameraData;
	}
	
	/**
	 */
	public TableModel(Sensor[] Camera, String[] Column) {
		super();
		this.tablecolumn = Column;
		this.tabledata = setupTableData(Camera);
	}
	
	/**
	 * This will return the Columns from the table
	 */
	public int getColumnCount() {
		return tablecolumn.length;
	}

	/**
	 * This will return the Rows from the table
	 */
	public int getRowCount() {
		return tabledata.length;
	}

	/**
	 * This will return the Column names back from the table
	 */
	@Override
	public String getColumnName(int col) {
		return tablecolumn[col];
	}

	/**
	 * This will return the objects back which are stored in the
	 * columns and rows
	 */
	public Object getValueAt(int rows, int col) {
		return tabledata[rows][col];
	}
	
	/**
	 * This will return a certain value from the table
	 */
	@Override
	public void setValueAt(Object value, int row, int col) {
		tabledata[row][col] = value;
	}

	/**
	 * This method will return the type of object that is stored in a 
	 * a certain column
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int col) {
		return getValueAt(0, col).getClass();
	}

	/**
	 * This will get all the camera information and put them into a list
	 * which will be used to setup the table.
	 */
	private Object[][] setupTableData(Sensor[] sensors) {
		// 2D array of data
		Object[][] data = new Object[sensors.length][tablecolumn.length]; 

		// Covert the Sensor array into table data
		for(int i = 0; i < sensors.length; i++) {
			// Obtain the sensor
			Sensor sensor = sensors[i];

			// Set each value into each column
			data[i][0] = sensor.cameraname();
			data[i][1] = sensor.getstatus();
			data[i][2] = sensor.current_image().warning.value;
			data[i][3] = sensor.image_warning().value;
		}
		return data;
	}


}
