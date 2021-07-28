package edu.pdx.cs410J.mberz2;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.*;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>AppointmentBook</code>. The API handles GET, POST, and DELETE requests
 * based on parameters in requests, and sends back information in responses.
 * Based on the different parameters, the servlet will create new appointment
 * books, post appointments, search appointments, and clear all appointments.
 *
 * @author Matthew Berzinskas
 * @since 2020-6-23
 * @see Appointment
 * @see AppointmentBook
 */
public class Project4 {

	/* MAX sets teh maximum number of input arguments. */
	private static final int MAX = 14;

	/* Total number of enabled options flags, used to find first appt. arg */
	private static int FLAGS;

	/* Int array containing valid argument counts when print is enabled */
	private static final int [] VALIDPRINT = {9, 13};

	/* Int array containing valid argument counts when search is enabled */
	private static final int [] VALIDSEARCH = {12};

	/* Int array containing valid argument counts when owner dump is enabled */
	private static final int [] VALIDOWNER = {5};

	/* String containing the owner to search for. */
	private static String OWNER;

	/* String array containing the search parameters */
	private static final String [] SEARCH = {"", "", ""};

	/* String containing the HOST to connect to */
	private static String HOST;

	/* Int containing the port to connect to */
	private static int PORT;

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
	 * Main entry point for the program. Validates command line arguments, and
	 * then based on a variety of different option conditions, performs a few
	 * different operations. Can add an appointment, search, display all, or
	 * output the readme.
	 *
	 * @param args String containing command line arguments.
	 * @throws ParserException Exception handling for parsing an appointment.
	 * @throws IOException Exception handling for input/out operations.
	 */
	public static void main(String... args) throws ParserException, IOException {

		// Check arguments for valid inputs.
		checkInput(args);

		// Setup a REST client object and HTTP Response object
		AppointmentBookRestClient client =
				new AppointmentBookRestClient(HOST, PORT);
		HttpRequestHelper.Response response;

		/* If, after checking the input, a single owner argument is detected,
		we immediately print out all associated appointments for that user. */
		if(ownerEnabled()){
			try {
				response = client.getURL(Map.of("owner,", OWNER));
				TextParser parser = new TextParser(new StringReader(response.getContent()));
				AppointmentBook appBook = parser.parse();
				if (appBook == null){
					printError("No appointment book for this owner.", 1);
				} else {
					PrettyPrinter printer = new PrettyPrinter(new PrintWriter(System.out));
					printer.dump(appBook);
					checkResponseCode(HttpURLConnection.HTTP_OK, response);
					System.exit(0);
				}
			} catch (IOException ex) {
				printError("Issue with connecting to print.", 1);
			}
		} else if (searchEnabled()) {
			try {
				response = client.getURL(Map.of("owner", SEARCH[0],
						"start", SEARCH[1],
						"end", SEARCH[2]));
				System.out.println(response.getContent());
				checkResponseCode(HttpURLConnection.HTTP_OK, response);
				System.exit(0);
			} catch (IOException ex) {
				printError("Issue with connecting to search.", 1);
			}
		} else {

			// Parse a new array for appointment arguments.
			String [] newArgs = parseInput(args);
			String owner = newArgs[0];
			String desc = newArgs[1];
			String bt = newArgs[2]+" "+newArgs[3]+" "+newArgs[4];
			String et = newArgs[5]+" "+newArgs[6]+" "+newArgs[7];

			try {
				// Try to add the new appointment.
				response = client.postURL(Map.of("owner", owner,
						"description",desc,
						"begin",bt,
						"end",et));
				System.out.println(response.getContent());
				checkResponseCode( HttpURLConnection.HTTP_OK, response);
			} catch (IOException ex) {
				printError("Issue with connecting to add.", 1);
			}

			// If PRINT is enabled, print out the new appointment to the Cl.
			if(printEnabled()){
				Appointment app = new Appointment(desc, bt, et);
				AppointmentBook appBook = new AppointmentBook(owner, app);
				printAppt(appBook);
			}
		}

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
		OPTIONS.put("HOST", 0);
		OPTIONS.put("PORT", 0);
		OPTIONS.put("SEARCH", 0);
		OPTIONS.put("PRINT", 0);
		OPTIONS.put("OWNER", 0);

		/* Base cases, ZERO or TOO MANY (over total acceptable, MAX) */
		if (args.length == 0)
			printErrorUsage("No command line arguments.", 1);
		else if (args.length > MAX)
			printErrorUsage("Error: Too many command line arguments.\n" +
					"Total number cannot exceed: "+MAX, 1);

		for (int i = 0; i < args.length; i++) {

			if (args[i].charAt(0) == '-') {

				if (args[i].equalsIgnoreCase("-README")) {
					printReadme();

				} else if (args[i].equalsIgnoreCase("-PRINT")) {
					OPTIONS.put("PRINT", 1);
					++FLAGS;

				} else if (args[i].equalsIgnoreCase("-HOST")) {
					FLAGS = FLAGS + 2;
					OPTIONS.put("HOST", 1);
					try {
						HOST = args[i + 1];
					} catch (ArrayIndexOutOfBoundsException e) {
						printErrorUsage( "Too few command line arguments.",
								1);
					}

				} else if (args[i].equalsIgnoreCase("-PORT")) {

					FLAGS = FLAGS + 2;
					OPTIONS.put("PORT", 1);
					try {
						PORT = Integer.parseInt(args[i+1]);
					} catch (ArrayIndexOutOfBoundsException e) {
						printErrorUsage("Too few command line arguments.",
								1);
					} catch (NumberFormatException ex) {
					printErrorUsage("Port \"" + PORT + "\" must be an integer", 1);
				}

				} else if (args[i].equalsIgnoreCase("-SEARCH")) {
					FLAGS++;
					OPTIONS.put("SEARCH", 1);

					try {
						SEARCH[0] = args[i + 1];
						SEARCH[1] = args[i + 2]+" "+args[i + 3]+" "+args[i + 4];
						SEARCH[2] = args[i + 5]+" "+args[i + 6]+" "+args[i + 7];

					} catch (ArrayIndexOutOfBoundsException e) {
						printErrorUsage("Too few command line arguments.",
								1);
					}

				} else if (args[i].equals("-")) {
					// Ignore cases of a single hyphen.
					break;
				} else {

					// Error on all other hyphen combinations.
					printErrorUsage( args[i] + " is an " + "invalid option.",
							1);
				}
			}
		}

		if (args.length == 5){
			OPTIONS.put("OWNER", 1);
			OWNER = args[4];
		}

		// Error check for options
		if(printEnabled())
			if (doesNotContain(VALIDPRINT, args.length)){
				printErrorUsage("Invalid amount " +
						"of arguments for printing", 1);
			}

		if(searchEnabled()) {
			if (doesNotContain(VALIDSEARCH, args.length))
				printErrorUsage("Invalid amount " +
						"of arguments for searching", 1);
		}

		if(ownerEnabled()) {
			if (doesNotContain(VALIDOWNER, args.length))
				printErrorUsage("Invalid amount " +
						"of arguments for display-all", 1);
		}
	}

	/**
	 * Checks a response code against a passed in value. Ensures that the
	 * given response has the expected HTTP status code (argument)
	 *
	 * @param code Integer containing the expected status code
	 * @param response Response object received from the servlet.
	 */
	@SuppressWarnings("DefaultLocale")
	private static void checkResponseCode(
			int code, HttpRequestHelper.Response response ) {
		if (response.getCode() != code) {
			printError(String.format("Expected HTTP code %d, got code %d.\n\n%s",
					code, response.getCode(), response.getContent()), 1);
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
	public static String [] parseInput(String[] args) {

		// New array for holding parsed arguments.
		String[] newArgs = Arrays.copyOfRange(args, FLAGS, args.length);

		if (newArgs.length < 8) {
			printErrorUsage("Too FEW command line arguments.", 1);
		}

		return newArgs;
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
	 * Method prints a text README to the standard out..
	 *
	 */
	public static void printReadme() {
		System.out.println("\n\n");
		System.out.println("Project 4 README");
		System.out.println("Author: Matthew Berzinskas (mberz2)");
		System.out.println("Course: CS410P, Advanced Programming with Java");
		System.out.println("Portland State University, Summer 2021");
		System.out.println("\nThis program implements an appointment book web " +
						"\nserver. The user is able to add new appointments, " +
						"\nsearch, display all, via a REST servlet. Similar " +
						"\nand parsing exists as in past programs.");
		System.out.println("\n\nUsage: java -jar /apptbook/target/apptbook-2021.0.0.jar [options] <args>");
		System.out.println("args are (in this order):");
		System.out.println("owner             - The person whose owns the appt book");
		System.out.println("description       - A description of the appointment");
		System.out.println("beginTime         - When the appt begins (12-hour time)");
		System.out.println("endTime           - When the appt ends (12-hour time)");
		System.out.println("Date and time should be in the format: mm/dd/yyyy hh:mm am/pm");
		System.out.println("\n\nOptions are (options may appear in any order");
		System.out.println("-host \t\thostname Host computer on which the server runs");
		System.out.println("-port \t\tport Port on which the server is listening");
		System.out.println("-search \t\tAppointments should be searched fort");
		System.out.println("-print \t\tPrints a description of the new appointment");
		System.out.println("-README \t\tPrints a README for this project and exits\n");

		System.exit(0);
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
		System.err.println("** Error: "+s);
		System.err.println(USAGE);
		System.exit(status);
	}

	/**
	 * Method for printing errors, while also outputting the program usage.
	 * The program will also pass a specified exit code.
	 *
	 * @param s String containing the defined error to print to std. error
	 * @param status Integer containing the exit status code.
	 */
	public static void printError(String s, int status){
		System.err.println("** Error: "+s);
		System.exit(status);
	}

	/**
	 * Method for checking if the {@code -print} option flag is enabled.
	 *
	 * @return Boolean for whether or not the option is enabled.
	 */
	public static boolean printEnabled(){
		return OPTIONS.get("PRINT")==1;
	}

	/**
	 * Method for checking if the {@code -search} option flag is enabled.
	 *
	 * @return Boolean for whether or not the option is enabled.
	 */
	public static boolean searchEnabled(){
		return OPTIONS.get("SEARCH")==1;
	}

	/**
	 * Method for checking if a single owner argument was passed. Indicating
	 * that the program will print the entire appointment book for that owner.
	 *
	 * @return Boolean for whether or not the option is enabled.
	 */
	public static boolean ownerEnabled(){
		return OPTIONS.get("OWNER")==1;
	}

}