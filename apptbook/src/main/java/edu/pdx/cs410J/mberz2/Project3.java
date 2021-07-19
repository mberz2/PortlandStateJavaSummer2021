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
public class Project3 {

	/* MAX sets teh maximum number of input arguments. */
	private static final int MAX = 14;

	/* Total number of enabled options flags, used to find first appt. arg */
	private static int FLAGS;

	/* String array containing valid arguments when print is enabled */
	private static final int [] VALIDPRINT = {MAX-5, MAX-3, MAX-1, MAX};

	/* String array containing valid arguments when textFile is enabled */
	private static final int [] VALIDDUMP = {MAX-4, MAX-3, MAX-2, MAX-1, MAX};

	/* Contains the file name, if textFile is enabled. */
	private static String FILE;

	/* Contains the file name, if textFile is enabled. */
	private static String PRETTYFILE;

	/* Contains the file name, if pretty printer is enabled. */
	private static Writer WRITER;

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

		// Check arguments for valid inputs.
		checkInput(args);

		// Create a new appointmentBook by parsing the input args.
		AppointmentBook newBook = parseInput(args);

		// If the print option is enabled, call to the print function for the
		// newly created appointment (from the command line).
		if(OPTIONS.get("Print") == 1)
			printAppt(newBook);

		// If a textFile option is enabled, load the appointments from the file
		// Add the appointment from the command line, and write it all back.
		if(OPTIONS.get("TextFile") == 1){
			AppointmentBook tempBook = loadFile(newBook);
			writeFile(tempBook);

			if(OPTIONS.get("Pretty") == 1){
				setWriteStream();
				PrettyPrinter printer = new PrettyPrinter(WRITER);
				printer.dump(tempBook);
			}

			// Exit successfully.
			System.exit(0);
		}

		if(OPTIONS.get("Pretty") == 1){
			setWriteStream();
			PrettyPrinter printer = new PrettyPrinter(WRITER);
			printer.dump(newBook);
		}

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

		FLAGS = 0;
		OPTIONS.put("Print", 0);
		OPTIONS.put("TextFile", 0);
		OPTIONS.put("Pretty", 0);

		/* Base cases, ZERO or TOO MANY (over total acceptable, MAX) */
		if (args.length == 0) {
			System.err.println("Error: No command line arguments.");
			printUsage(1);
		} else if (args.length > MAX) {
			System.err.println("Error: Too many command line arguments.\n" +
					"Total number cannot exceed: "+MAX);
			printUsage(1);
		}

		for (int i = 0; i < args.length; i++) {

			if (args[i].charAt(0) == '-') {

				if (args[i].equalsIgnoreCase("-README")) {
					printRes("README3.txt");

				} else if (args[i].equalsIgnoreCase("-PRINT")) {
					OPTIONS.put("Print", 1);
					++FLAGS;

				} else if (args[i].equalsIgnoreCase("-TEXTFILE")) {
					FLAGS = FLAGS + 2;
					OPTIONS.put("TextFile", 1);
					try {
						FILE = args[i + 1];
					} catch (ArrayIndexOutOfBoundsException e) {
						printErrorUsage("Error: Too few command " +
								"line arguments.", 1);
					}

				} else if (args[i].equalsIgnoreCase("-PRETTY")) {

					FLAGS = FLAGS + 2;
					OPTIONS.put("Pretty", 1);
					try {
						PRETTYFILE = args[i+1];
					} catch (ArrayIndexOutOfBoundsException e) {
						printErrorUsage("Error: Too few command " +
								"line arguments.", 1);
					}

				} else if (args[i].equals("-")) {
					// Ignore cases of a single hyphen.
					break;
				} else {

					// Error on all other hyphen combinations.
					printErrorUsage("Error: " + args[i] + " is an " +
							"invalid option.", 1);
				}
			}
		}

		// Error check for options
		if(printEnabled())
			if (doesNotContain(VALIDPRINT, args.length)){
				printErrorUsage("Error: Invalid amount " +
						"of arguments", 1);
			}

		if(printerEnabled() || fileEnabled()) {
			if (doesNotContain(VALIDDUMP, args.length))
				printErrorUsage("Error: Invalid amount " +
						"of arguments", 1);
			else if ((printerEnabled() && fileEnabled())
					&& FILE.equals(PRETTYFILE))
				printErrorUsage("Error: Cannot have both printer and " +
						"textfile paths as the same location.", 1);
		}
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
	public static AppointmentBook parseInput(String[] args)
			throws ParserException {

		// New array for holding parsed arguments.
		String[] newArgs = Arrays.copyOfRange(args, FLAGS, args.length);

		if (newArgs.length < 8) {
			printErrorUsage("Error: Too FEW command line arguments.", 1);
		}

		// Create a new appointment from parsed input.
		Appointment app = new Appointment(newArgs[1],
				newArgs[2]+" "+newArgs[3]+" "+newArgs[4],
				newArgs[5]+" "+newArgs[6]+" "+newArgs[7]);

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

		try {
			TextParser textParser = new TextParser(new FileReader(FILE));

			//Check if anything is in the file.
			AppointmentBook tempBook = textParser.parse();

			// If the owners of the new book and the parsed book don't match, exit
			if(!tempBook.getOwnerName().equals(appBook.getOwnerName())){
				System.err.println("Error: Incompatible owners.\nPlease check " +
						"that the new appointment owner is the same as the " +
						"loaded file.");
				System.exit(1);
			} else {
				// Combining the appointments.
				ArrayList<Appointment> app = appBook.getAppointments();

				for (Appointment a: app)
					tempBook.addAppointment(a);

				return tempBook;

			}

		} catch (FileNotFoundException e){

			//Nothing to merge, return original book.
			return appBook;
		}

		return appBook;
	}

	/**
	 * Method writes an appointmentBook object to a file via a TextDumper obj.
	 *
	 * @param appBook AppointmentBook to be written to a file.
	 * @throws IOException Exception handling for writing the output to file.
	 */
	public static void writeFile(AppointmentBook appBook) throws IOException {

		try{
			TextDumper textDumper = new TextDumper(new FileWriter(FILE));
			textDumper.dump(appBook);
		} catch (FileNotFoundException e){
			System.err.println("Error: No such file/directory.");
			System.exit(1);
		}
	}

	/**
	 * Method sets the text writer depending on what is in the argument
	 * following the -pretty option. If the argument is "-" the method creates
	 * a new PrintWriter to System.out, otherwise, it sets a new FileWriter to
	 * whatever is contained in the PRETTYFILE datamember.
	 *
	 * @throws IOException Exception handling for problems in reading/writing.
	 */
	public static void setWriteStream () throws IOException {
		if (PRETTYFILE.equals("-")){
			WRITER = new PrintWriter(System.out);
		} else {
			try{
				WRITER = new FileWriter(PRETTYFILE);
			} catch (FileNotFoundException e){
				System.err.println("Error: No such file/directory.");
				System.exit(1);
			}
		}
	}

	/**
	 * Helper method to check if an array does not contain a given key. Used
	 * for checking whether or not the arguments on the command line are valid.
	 *
	 * @param arr Array to check.
	 * @param key Key to find.
	 * @return Returns whether or not there was a match.
	 */
	public static boolean doesNotContain(final int[] arr, final int key) {
		return Arrays.stream(arr).noneMatch(i -> i == key);
	}

	/**
	 * Method prints a file in the resource folder via a class loader.
	 *
	 * @param s String containing the file to load.
	 * @throws IOException Exception handling for writing the input of file.
	 */
	public static void printRes(String s) throws IOException {
		// Create an input stream from the file indicated at resource class.
		InputStream file = Project3.class.getResourceAsStream(s);

		// If it is not null, start printing until there are no more lines.
		if (file != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(file));
			String line;
			while ((line = br.readLine()) != null)
				System.out.println(line);
			System.exit(0);
		}

		// Otherwise, throw an exception.
		throw new NullPointerException ("Error: File "+s+" not found.");
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

	/**
	 * Method for checking if the {@code -pretty} option flag is enabled.
	 *
	 * @return Boolean for whether or not the option is enabled.
	 */
	public static boolean printerEnabled(){
		return OPTIONS.get("Pretty")==1;
	}

}
