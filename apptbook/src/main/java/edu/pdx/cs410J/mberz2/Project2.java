package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.ParserException;
import java.io.*;
import java.util.*;

/**
 * This is the main class method for the CS410J Appointment Book Project.
 * <p>The program simulates the receiving an appointment via command line
 * arguments and proceeds through operations to validate the input, process
 * a limited number of options, and then creates an {@code Appointment} and
 * {@code AppointmentBook} object. </p>
 * <p>If the print flag is enabled, it will print back the
 * details of the appointment.</p>
 * <p>If the readme flag is enabled, it will print the README.txt file that
 * is located in the resources.</p>
 * <p>If the textfile flag is enabled, it will read in any appointments stored
 * in a given text file, and write the new appointment to it.</p>
 *
 * @author Matthew Berzinskas
 * @since 2020-6-23
 * @see Appointment
 * @see AbstractAppointment
 */
public class Project2 {

	/* MAX sets teh maximum number of input arguments. */
	private static final int MAX = 10;

	/* Total number of enabled options flags, used to find first appt. arg */
	private static int FLAGS;

	/* Contains the path to the file, if textFile is enabled. */
	private static String FILE = "";

	/* Map containing the different option fields, set all to 0 */
	private static final Map<String, Integer> OPTIONS = new HashMap<>();

	/* String containing syntax for program usage. */
	public static final String USAGE =
			"Usage: java -jar /apptbook/target/apptbook-2021.0.0.jar " +
					"[options] <args>";
	/* String containing syntax for how to display README */
	public static final String README =
			"java -jar /apptbook/target/apptbook-2021.0.0.jar -README";

	/**
	 * Main class. Entry point into the program. Creates an appointment object
	 * as specified by the user and adds it to an appointmentBook object.
	 * <p>Based on optional flags, the program will also read in appointments
	 * from a text file {@code -textFile}, display the program readme
	 * {@code -readme}, and print the newly created appointment back to the
	 * user {@code -print}.</p>
	 * <p>The program will exit gracefully on a number of different conditions,
	 * including wrong argument count, improper fomat, etc.</p>
	 *
	 * @param args String array containing the command line arguments.
	 * @throws IOException Exception handling for incorrect file.
	 * @throws ParserException Exception handling for improper parsing.
	 */
	public static void main(String[] args) throws IOException, ParserException {

		FLAGS = 0;
		OPTIONS.put("Print", 0);
		OPTIONS.put("TextFile", 0);
		OPTIONS.put("Parsed", 0);

		// Check arguments for valid inputs.
		checkInput(args);

		// Create a new appointmentBook by parsing the input args.
		AppointmentBook newBook = parseInput(args);

		// If a textFile option is enabled, load the appointments from the file
		// Add the appointment from the command line, and write it all back.
		if(OPTIONS.get("TextFile") == 1){
			AppointmentBook tempBook = loadFile(newBook);
			writeFile(tempBook);
		}

		// If the print option is enabled, call to the print function for the
		// newly created appointment (from the command line).
		if(OPTIONS.get("Print") == 1)
			printAppt(newBook);

		// Exit successfully.
		System.exit(0);

	}

	/**
	 * Method parses/validates the input String array. The method checks for
	 * minimal/maximum number of arguments, optional flags enabled, and then
	 * based on the number of flags enabled, determines the acceptable number
	 * of arguments. If the {@code -textFile} option is enabled, the method
	 * parses out the fileName (the immediately following argument).
	 *
	 * @param args String array containing the command line arguments to parse.
	 * @throws IOException Exception handling for loading a README file.
	 */
	public static void checkInput(String [] args) throws IOException {

		/* Base cases, ZERO or TOO MANY (over total acceptable, MAX) */
		if (args.length == 0) {
			System.err.println("Error: Missing command line arguments");
			printUsage(1);
		} else if (args.length > MAX) {
			System.err.println("Error: Too many command line arguments");
			printUsage(1);
		}

		/* If there are an acceptable range of arguments, check for options
		flags. If readme is detected, program exits immediately. If a textFile
		is indicated, the next argument is extracted as the file. */
		for (String arg : args) {
			if (arg.startsWith("-")) {
				if (arg.equalsIgnoreCase(("-README"))) {
					printRes("README2.txt");
				}
				else if (arg.equalsIgnoreCase(("-PRINT"))) {
					FLAGS++;
					OPTIONS.put("Print", 1);
				}
				else if (arg.equalsIgnoreCase(("-TEXTFILE"))){
					FLAGS = FLAGS + 2;
					OPTIONS.put("TextFile", 1);
					int i = Arrays.asList(args).indexOf(arg);
					FILE = args[i+1];
				}
				else {
					System.err.println(arg + " is not a correct option");
					printUsage(1);
				}
			}
		}

		/* Check for FLAGS, will determine next set of allowable numbers */
		if((args.length - FLAGS) >6)
			printErrorUsage("Error: Too MANY command line arguments", 1);
		else if (args.length <6)
			printErrorUsage("Error: Too FEW command line arguments", 1);

		/* If print is enabled, can only have 7, 8, or 10 args. */
		if(printEnabled() && (args.length != 7 && args.length != 9))
				printErrorUsage("Error: Invalid number of arguments " +
						"(for -print enabled).", 1);

		/* If textFile is enabled, can only have 8, 9 or 10 args */
		else if(fileEnabled() && (args.length != 8 && args.length != 9))
			printErrorUsage("Error: Invalid number of arguments " +
					"(for -textFile enabled).", 1);
	}

	/**
	 * Method parses the input into an acceptable format. Based on the number
	 * of enabled option flags, the method extracts the last six arguments and
	 * passes it to a StringParser object for validation. If the StringParser
	 * returns successfully, an appointmentBook object is created from the
	 * acceptable arguments. If not, the program exits gracefully.
	 *
	 * @param args String array containing the command line arguments.
	 * @return appointmentBook object containing the new appointment.
	 */
	public static AppointmentBook parseInput(String[] args) {

		// New array for holding parsed arguments.
		String[] newArgs = Arrays.copyOfRange(args, FLAGS, args.length);


		StringParser sp = new StringParser();
		if (!sp.validateString(newArgs)) {
			System.err.println("Run with -README to see proper formatting.");
			System.err.println(README);
			System.exit(1);
		}

		// Create a new appointment from parsed input.
		Appointment app = new Appointment(newArgs[1],
				newArgs[2]+" "+newArgs[3], newArgs[4]+" "+newArgs[5]);

		return new AppointmentBook(newArgs[0], app);
	}

	/**
	 * Method loads all the appointments in a given text file and attempts to
	 * merge them with the newly created appointment. If the owners do not
	 * match the program will exit gracefully. Otherwise, the program will
	 * combine all the appointments and return the newly created book.
	 *
	 * @param appBook AppointmentBook containing the newly created appointment.
	 * @return AppointmentBook object containing the combined appt. book.
	 * @throws ParserException Exception handling for improper file parsing.
	 */
	public static AppointmentBook loadFile(AppointmentBook appBook)
			throws ParserException {
		// Create new textParser object and attempt to retrieve the appts.
		TextParser textParser = new TextParser(FILE);
		AppointmentBook tempBook = textParser.parse();

		// If the owners of the new book and the parsed book don't match, exit
		if(!tempBook.getOwnerName().equals(appBook.getOwnerName())){
			System.err.println("Incompatible owners.\nPlease check that the" +
					"new appointment owner is the same as the loaded file.");
			System.exit(1);
		}

		// Combining the appointments.
		Collection<Appointment> apps = tempBook.getAppointments();
		for (Appointment a: apps)
			appBook.addAppointment(a);

		OPTIONS.put("Parsed", 1);
		return appBook;
	}

	/**
	 * Method writes an appointmentBook object to a file via a TextDumper obj.
	 *
	 * @param appBook AppointmentBook to be written to a file.
	 * @throws IOException Exception handling for writing the output to file.
	 */
	public static void writeFile(AppointmentBook appBook) throws IOException {
		TextDumper textDumper = new TextDumper(FILE);
		textDumper.dump(appBook);
	}

	/**
	 * Method prints a file in the resource folder via a class loader.
	 *
	 * @param s String containing the file to load.
	 * @throws IOException Exception handling for writing the input of file.
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
	 * Method for printing an appointmentBook object. Leverages the apptBook's
	 * toString method to print the appointment contents to the standard out.
	 *
	 * @param appBook appointmentBook object to print.
	 */
	public static void printAppt(AppointmentBook appBook){
		System.out.println(appBook);

		Collection<Appointment> apps = appBook.getAppointments();
		for (Appointment a : apps)
			System.out.println(a);
	}

	/**
	 * Method printing errors in syntax/command line arguments. Displays the
	 * field that was incorrect, and the incorrect input.
	 *
	 * @param s String containing the argument that was incorrect.
	 * @param x String containing the incorrectly formatted orgument.
	 */
	public static void printSyntaxError(String s, String x){
		System.err.println("Error in <" + s + "> argument.");
		System.err.println("<" + x + "> contains improper characters.");
	}

	/**
	 * Method for printing errors, while also outputting the program usage.
	 * The program will also pass a specified exit code.
	 *
	 * @param s String containing the defined error to print to std. error
	 * @param status Integer containing the exit status code.
	 */
	public static void printErrorUsage(String s, int status){
		System.err.println(s);
		printUsage(status);
	}

	/**
	 * Method for printing the program usage and setting the exit code.
	 *
	 * @param status Integer containing the exit status code.
	 */
	public static void printUsage(int status){
		System.err.println(USAGE);
		System.exit(status);
	}

	/**
	 * Method for checking if the {@code -print} option flag is enabled.
	 *
	 * @return Boolean for whether or not the option is enabled.
	 */
	public static boolean printEnabled(){
		return OPTIONS.get("Print")==1;
	}

	/**
	 * Method for checking if the {@code -textFile} option flag is enabled.
	 *
	 * @return Boolean for whether or not the option is enabled.
	 */
	public static boolean fileEnabled(){
		return OPTIONS.get("TextFile")==1;
	}

}
