package Implementation;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import LocalMonitoringStation.*;
import SensorCamera.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
/**
 * The console class that is used for the other classes that use the
 * command line for the program. This class has methods to make sure
 * that the user is inputting the correct information into the console.
 * 
 * Faser Parvez u1167937
 * Assignment 2 CamCobra
 */
public final class ConsoleValidation {

	/**
	 * Default Constructor
	 */
	private ConsoleValidation(){
	}

	/**
	 * This method will get the boolean input that the user has input. 
	 * It will check to see if the user has input the correct boolean string.
	 */
	public static boolean GetBoolean(String message) {	
		// List of possible accepted yes values
		List<String> yes = Arrays.asList(new String[]{"yes", "true"});
		// List of possible accepted no values
		List<String> no = Arrays.asList(new String[]{"no", "false"});
		do {
			System.out.println(message);
			System.out.print("Enter Input: ");
			try {
				// Open new Input Stream Reader
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

				// gets input
				String response = bufferRead.readLine();

				// Check to see if the input goes with the yes array list
				if(yes.contains(response.toLowerCase())) {
					return true;
				}

				// Check to see if the input goes with the no array list
				if(no.contains(response.toLowerCase())) {
					return false;
				}

				// Throw an error
				throw new InputMismatchException();

			} catch(InputMismatchException e) {
				// Tells the user they need to put a valid input
				System.out.println("Error: Please respond with Yes or No.");
			} catch (IOException e) {
				// Tells the user to respond
				System.out.println("Error: Please respond with Yes or No.");
			}
		} while(true);
	}
			 
	/**
	 * This method is used to get all the information about the camera sensor
	 * that the user has input. It will also check to see if the input is valid 
	 * such as seeing if the camera name exists.
	 */
	public static Sensor Get_Sensor(String message, NamingContextExt nameService){
		do {
			System.out.println(message);
			System.out.print("Enter Input: ");

			try {
				// Open a new Input Stream Reader
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

				// Gets input
				String name = bufferRead.readLine();

				// Makes sure that the camera name has an input
				if(name.trim().equals("")) {
					throw new IOException();
				}
				// This will then try to find the camera
				return SensorHelper.narrow(nameService.resolve_str(name));
			//List of exception if the user has input the incorrect detail or an error has happen
			} catch (IOException e) {
				System.out.println("Error: Please try again.");
			} catch (NotFound e) {
				System.out.println("ERROR: Camera was not found");
			} catch (Exception e) {
				System.out.println("ERROR: A serious error has occured.");
				System.out.println(e.getMessage());
			}
		} while(true);
	}
	
	/**
	 * This method is used to get all the information about the LMS information
	 * that the user has input. It will also check to see if the input is valid 
	 * such as seeing if the LMS name exists.
	 */
	public static LMS Get_LMS(String message, NamingContextExt nameService) {
		do {
			System.out.println(message);
			System.out.print("Enter Input: ");
			try {
				// Open a new Input Stream Reader
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

				// Gets input
				String name = bufferRead.readLine();

				// Checks to see if name has an input
				if(name.trim().equals("")) {
					throw new IOException();
				}
				// Will try and find the LMS name then
				return LMSHelper.narrow(nameService.resolve_str(name));
		    //List of exception if the user has input the incorrect detail or an error has happen	
			} catch(NullPointerException e) {
				// User hasn't entered anything
				System.out.println("Error: Please provide an input.");
			} catch (IOException e) {
				// Something serious went wrong
				System.out.println("Error: Please try again.");
			} catch (NotFound e) {
				System.out.println("ERROR: Given LMS was not found");
			} catch (Exception e) {
				System.out.println("ERROR: A serious error has occured.");
				System.out.println(e.getMessage());
			}
		} while(true);
	}	
	
	/**
	 * This method will be used to make sure that all the input is printed out
	 * into a log that will also have a timestamp for each message to show to 
	 * the users when the message was printed.
	 */
	public static void WriteToLog(String message) {
		// Obtain the current formatted date
		SimpleDateFormat date_format = new SimpleDateFormat("EEEE, dd MMMM yyyy : HH:mm a");
		String date = date_format.format(new Date(System.currentTimeMillis()));
		// Output the message and formatted date to the console
		System.out.format("[%s] %s%n", date, message);
	}
	
	/**
	 * This method will make sure that whatever the user input, it will
	 * get that string input.
	 */
	public static String GetString(String message){
		do {
			// 
			System.out.println(message);
			System.out.print("Enter Input: ");

			try {
				// New Input Stream Reader
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

				// Gets input
				String input = bufferRead.readLine();

				// Checks to see if input not empty.
				if(!input.trim().isEmpty()){
					return input;
				}
			} catch(NullPointerException e) {
				// Empty input error
				System.out.println("Please make sure you write an input");
			} catch (IOException e) {
				// A general Input/Output Exception
				System.out.println("Please try again.");
			}
		} while(true);
	}

	/**
	 * This method will make sure that whatever the user input, it will
	 * get that integer input.
	 */
	public static int GetInteger(String message) {
		do {
			// this will print the user input into the command.
			System.out.println(message);
			System.out.print("Enter Input: ");

			try {
				// new Input Stream Reader
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

				// gets input
				return Integer.parseInt(bufferRead.readLine());

			} catch (NumberFormatException e) {
				// Error message, enter valid input
				System.out.println("Please enter valid integer");
			} catch (IOException e) {
				// Error message, try again
				System.out.println("Please try again.");
			}
		} while(true);
	}

	/**
	 * This method will make sure that whatever the user input, it will
	 * get that double input.
	 */
	public static double GetDouble(String message) {
		do {
			double number;
			System.out.println(message);
			System.out.print("Enter Input: ");
			try {
				// new Input Stream Reader
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

				// gets input
				String input = bufferRead.readLine();
				number = Double.parseDouble(input);
	            if (number < 1 || number > 10) {
	            	System.out.println("Invalid. Try again.");
	            }
	            else
				// parse the String
				return Double.parseDouble(input);
				
			} catch(NullPointerException e) {
				// Empty input, error message
				System.out.println("Error: Provide an input.");
			} catch(NumberFormatException e) {
				// Invalid number error
				System.out.println("Error: Provide a valid number.");
			} catch (IOException e) {
				// Error try again
				System.out.println("Error. Please try again.");
			}
		} while(true);
	}
}
