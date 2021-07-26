package edu.pdx.cs410J.mberz2;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.*;

/**
 * The main class that parses the command line and communicates with the
 * Appointment Book server using REST.
 */
public class Project4 {

	/* MAX sets teh maximum number of input arguments. */
	private static final int MAX = 14;

	/* Total number of enabled options flags, used to find first appt. arg */
	private static int FLAGS;

	/* String array containing valid arguments when print is enabled */
	private static final int [] VALIDPRINT = {MAX-5, MAX-3, MAX-1, MAX};

	/* String array containing valid arguments when textFile is enabled */
	private static final int [] VALIDDUMP = {MAX-4, MAX-3, MAX-2, MAX-1, MAX};

	private static String [] SEARCH = {"", "", ""};

	private static String OWNER;
	private static String HOST;
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

	public static final String MISSING_ARGS = "Missing command line arguments";

	public static void main(String... args) throws ParserException, IOException {

		// Check arguments for valid inputs.
		checkInput(args);

		AppointmentBookRestClient client = new AppointmentBookRestClient(HOST, PORT);
		HttpRequestHelper.Response response = null;

		if(ownerEnabled()){
			try {
				response = client.getAllAppointments(OWNER);
				System.out.println(response.getContent());
				checkResponseCode( HttpURLConnection.HTTP_OK, response);
			} catch (IOException ex) {
				System.out.println("Issue with connection.");
			}

			System.exit(1);
		} else if (searchEnabled()) {
			try {
				response = client.searchTime(SEARCH[0], SEARCH[1], SEARCH[2]);
				System.out.println(response.getContent());
				checkResponseCode(HttpURLConnection.HTTP_OK, response);
			} catch (IOException ex) {
				System.out.println("Issue with connection.");
			}
		} else {

			String [] newArgs = parseInput(args);
			String owner = newArgs[0];
			String desc = newArgs[1];
			String bt = newArgs[2]+" "+newArgs[3]+" "+newArgs[4];
			String et = newArgs[5]+" "+newArgs[6]+" "+newArgs[7];

			try {
				response = client.addAppointment(owner, desc, bt, et);
				System.out.println(response.getContent());
				checkResponseCode( HttpURLConnection.HTTP_OK, response);
			} catch (IOException ex) {
				System.out.println("Issue with connection.");
			}

			if(OPTIONS.get("PRINT") == 1){
				Appointment app = new Appointment(desc, bt, et);
				AppointmentBook appBook = new AppointmentBook(owner, app);
				printAppt(appBook);
			}
		}

		System.exit(0);
	}


	/**
	 * Makes sure that the give response has the expected HTTP status code
	 * @param code The expected status code
	 * @param response The response from the server
	 */
	private static void checkResponseCode( int code, HttpRequestHelper.Response response )
	{
		if (response.getCode() != code) {
			printError(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
					response.getCode(), response.getContent()), 1);
		}
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
					printRes("README.txt");

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

		/*
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
		 */
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
	public static String [] parseInput(String[] args)
			throws ParserException {

		// New array for holding parsed arguments.
		String[] newArgs = Arrays.copyOfRange(args, FLAGS, args.length);

		if (newArgs.length < 8) {
			printErrorUsage("Error: Too FEW command line arguments.", 1);
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
	 * Method prints a file in the resource folder via a class loader.
	 *
	 * @param s String containing the file to load.
	 * @throws IOException Exception handling for writing the input of file.
	 */
	public static void printRes(String s) throws IOException {
		// Create an input stream from the file indicated at resource class.
		InputStream file = Project4.class.getResourceAsStream(s);

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
		System.err.println("** Error: "+s);
		printUsage(status);
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
		return OPTIONS.get("PRINT")==1;
	}


	public static boolean hostEnabled(){
		return OPTIONS.get("HOST")==1;
	}


	public static boolean portEnabled(){
		return OPTIONS.get("PORT")==1;
	}

	public static boolean ownerEnabled(){
		return OPTIONS.get("OWNER")==1;
	}

	public static boolean searchEnabled(){
		return OPTIONS.get("SEARCH")==1;
	}

}