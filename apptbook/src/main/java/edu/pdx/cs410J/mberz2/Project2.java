package edu.pdx.cs410J.mberz2;
import edu.pdx.cs410J.AbstractAppointment;
import java.io.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * This is the main class method for the CS410J Appointment Book Project.
 * <p>The program simulates the receiving appointment data and then creates
 * an {@code Appointment} and {@code AppointmentBook} object. </p>
 * <p>If the print flag is enabled, it will print back the
 * details of the appointment.</p>
 * <p>If the readme flag is enabled, it will print the README.txt file that
 * is located in the resources.</p>
 * <p>If the textFile flag is enabled, it will read/write to a file.</p>
 *
 * @author Matthew Berzinskas
 * @since 2020-6-23
 * @see Appointment
 * @see AbstractAppointment
 */
public class Project2 {

	// Strings for commonly used messages.
	public static final String USAGE =
			"Usage: java -jar /apptbook/target/apptbook-2021.0.0.jar " +
					"[options] <args>";
	public static final String README =
			"java -jar /apptbook/target/apptbook-2021.0.0.jar -README";

	/**
	 * This is the main method/entrypoint for the program.
	 * @param args Command line arguments passed in as strings.
	 * @throws IOException Throws an IOException in the readme printing method.
	 */
	public static void main(String[] args) throws IOException {

		// Check arguments for correct number.
		checkInput(args);

		// Check for print and readme
		int print = checkOptions(args);

		// Check arguments for valid inputs.
		String [] newArgs = parseInput(args);

		// Create a new appointment from parsed input.
		Appointment app = new Appointment(newArgs[1],
				newArgs[2]+" "+newArgs[3], newArgs[4]+" "+newArgs[5]);

		// Create a new appointment book from the owner argument and appt.
		AppointmentBook<Appointment> appBook =
				new AppointmentBook<>(newArgs[0], app);

		// If a print option was detected earlier, it is printed.
		if(print == 1)
			print(appBook);
	}

	/**
	 * Method to check the correct number of arguments. The program handles the
	 * following situations.
	 * <ul>
	 *     <li>0 arguments: No command line arguments.</li>
	 *     <li>Greater than 10: The maximum number of arguments would be
	 *     six inputs three flags, and a file path.</li>
	 * </ul>
	 * If either situation is encountered, the program will call to the method
	 * that prints the proper program usage {@code printUsage} which also exits.
	 * @param args Array of command line arguments.
	 */
	public static void checkInput(String [] args){
		//Check for correct number of arguments.
		if (args.length == 0) {
			System.err.println("Error: Missing command line arguments");
			printUsage();
		} else if (args.length > 10) {
			System.err.println("Error: Too many command line arguments");
			printUsage();
		}
	}

	/**
	 * Method checks for the presence of a {@code -PRINT} or {@code -README}
	 * option flag on the inputted arguments. It also checks for other entries
	 * that are not recognized. If the print flag is enabled, it sends back
	 * a code for later printing. If readme flag is enabled, it sends to the
	 * printResource method for printing the readme.
	 *
	 * @param args Command line arguments passed in as string.
	 * @return Numeric indicator for printing, 1 if yes, 0 if no.
	 * @throws IOException if call to {@code printReadme} throws exception
	 */
	public static int checkOptions(String [] args) throws IOException {
		int print = 0;
		int file = 0;

		for (String arg : args) {
			if (arg.startsWith("-")) {
				if (arg.equalsIgnoreCase(("-README")))
					printRes("README.txt");
				if (arg.equalsIgnoreCase(("-PRINT")))
					print = 1;
				if (arg.equalsIgnoreCase(("-textFile")))
					file = 1;
				else {
					System.err.println(arg + " is not a correct option");
					printUsage();
				}
			}
		}
		return print;
	}

	/**
	 * Method parse the command line arguments using regular expressions.
	 * <p>First checks for options flags and ensures they are correct.</p>
	 * <p> Next the method checks the inputs in order against the required
	 * regular expressions.</p>
	 * <p><b>Note:</b> If any of the expressions are incorrect, an error message
	 * is printed displaying the mistake.</p>
	 * <p>At the end of the parsing, if an error was detected in any arg, the
	 * program exits with the usage message and error code.</p>
	 *
	 * @param args Array of command line arguments.
	 * @return Command line arguments stripped of any usage flags.
	 */
	public static String[] parseInput(String[] args) {

		// New array for holding parsed arguments.
		String[] newArgs;

		// If the array list contains 7 items, one is a flag, trim it off.
		// Otherwise all six are the correct arguments.
		if (args.length == 9) {
			newArgs = Arrays.copyOfRange(args, 1, 7);
			for (int i = 0; i < args.length; ++i){
				System.out.println(Arrays.toString(args));
			}
		}
		else
			newArgs = Arrays.copyOfRange(args, 0, 6);

		boolean err = false;

		String datePat = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
		String timePat = "^([01]?\\d|2[0-3]):?([0-5]\\d)$";

		if (!(newArgs[2].matches(datePat))) {
			printError("begin time (date)", newArgs[2]);
			err = true;
		}

		if (!(newArgs[3].matches(timePat))) {
			printError("begin time (time)", newArgs[3]);
			err = true;
		}

		if (!(newArgs[4].matches(datePat))) {
			printError("end time (date)", newArgs[4]);
			err = true;
		}

		if (!(newArgs[5].matches(timePat))) {
			printError("end time (time)", newArgs[5]);
			err = true;
		}

		if (err) {
			System.err.println("Run with -README to see proper formatting.");
			System.err.println(README);
			System.exit(1);
		}

		return newArgs;
	}

	/**
	 * Method to print the contents of a file, loaded as a resource, from
	 * the relative-path of the resource directory. Retrieves the file
	 * and prints it via a buffered reader. Exits normally after the print.
	 *
	 * @param s String of resource to be read.
	 * @throws IOException if unable to find the requested file.
	 * @throws NullPointerException if the requested file is null.
	 */
	public static void printRes(String s) throws IOException {

		// Create an input stream from the file indicated at resource class.
		InputStream file = Project1.class.getResourceAsStream(s);

		// If it is not null, start printing until there are no more lines.
		if (file != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(file));
			String line;
			while ((line = br.readLine()) != null)
				System.out.println(line);
			System.exit(0);
		}

		// Otherwise, throw an exception.
		throw new NullPointerException ("File "+s+" not found.");
	}

	/**
	 * Method prints the contents of an appointment book.
	 * <p>Creates a {@code Collection} object and uses a loop to iterate through
	 * all the appointments in the collection, printing them.</p>
	 *
	 * @param appBook Appointment book object to be printed.
	 */
	public static void print(AppointmentBook<Appointment> appBook){
		System.out.println("Appointment Book for "+appBook.getOwnerName());
		Collection<Appointment> apps = appBook.getAppointments();
		for (Appointment a : apps){
			System.out.println("Description:\t"+a.getDescription());
			System.out.println("Begin Time:\t\t"+a.getBeginTimeString());
			System.out.println("End Time:\t\t"+a.getEndTimeString());
		}

		System.exit(0);
	}

	/**
	 * Method prints an error with the related argument.
	 *
	 * @param s String containing the incorrect field.
	 * @param x String containing the incorrect argument.
	 */
	public static void printError(String s, String x){
		System.err.println("Error in <" + s + "> argument.");
		System.err.println("<" + x + "> contains improper characters.");
	}

	/**
	 * Method prints the usage of the program an then exits.
	 */
	public static void printUsage(){
		System.err.println(USAGE);
		System.exit(1);
	}
}