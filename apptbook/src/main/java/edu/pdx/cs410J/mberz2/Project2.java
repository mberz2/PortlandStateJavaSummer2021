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

	private static final int MAX = 10;
	private static int FLAGS = 0;
	private static String FILE = "";
	private static final Map<String, Integer> options = new HashMap<>();

	// Strings for commonly used messages.
	public static final String USAGE =
			"Usage: java -jar /apptbook/target/apptbook-2021.0.0.jar " +
					"[options] <args>";
	public static final String README =
			"java -jar /apptbook/target/apptbook-2021.0.0.jar -README";


	public static void main(String[] args) throws IOException, ParserException {

		// Check arguments for valid inputs.
		checkInput(args);

		AppointmentBook newBook = parseInput(args);

		//Combine and dump.
		if(options.get("TextFile") == 1){
			AppointmentBook tempBook = loadFile(newBook);
			writeFile(tempBook);
		}

		if(options.get("Print") == 1)
			printAppt(newBook);

		System.exit(0);

	}

	public static void checkInput(String [] args) throws IOException {

		options.put("Print", 0);
		options.put("TextFile", 0);
		options.put("Parsed", 0);

		/* Base cases, ZERO or TOO MANY (over total acceptable, MAX) */
		if (args.length == 0) {
			System.err.println("Error: Missing command line arguments");
			printUsage(1);
		} else if (args.length > MAX) {
			System.err.println("Error: Too many command line arguments");
			printUsage(1);
		}

		for (String arg : args) {
			if (arg.startsWith("-")) {
				if (arg.equalsIgnoreCase(("-README"))) {
					printRes("README2.txt");
				}
				else if (arg.equalsIgnoreCase(("-PRINT"))) {
					FLAGS++;
					options.put("Print", 1);
				}
				else if (arg.equalsIgnoreCase(("-TEXTFILE"))){
					FLAGS = FLAGS + 2;
					options.put("TextFile", 1);
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
		//if(((options.get("Print") == 1) && args.length != 7) ||
				//((options.get("Print") == 1) && args.length != 9)){
		if(printEnabled() && (args.length != 7 && args.length != 9))
				printErrorUsage("Invalid number of arguments " +
						"(for -print enabled).", 1);

		/* If textFile is enabled, can only have 8, 9 or 10 args */
		else if(fileEnabled() && (args.length != 8 && args.length != 9))
			printErrorUsage("Invalid number of arguments " +
					"(for -textFile enabled).", 1);
	}

	public static AppointmentBook parseInput(String[] args) {

		// New array for holding parsed arguments.
		String[] newArgs = Arrays.copyOfRange(args, FLAGS, args.length);

		StringParser sp = new StringParser();
		if (!sp.validateString(args)) {
			System.err.println("Run with -README to see proper formatting.");
			System.err.println(README);
			System.exit(1);
		};

		// Create a new appointment from parsed input.
		Appointment app = new Appointment(newArgs[1],
				newArgs[2]+" "+newArgs[3], newArgs[4]+" "+newArgs[5]);

		return new AppointmentBook(newArgs[0], app);
	}

	public static AppointmentBook loadFile(AppointmentBook appBook)
			throws ParserException {
		TextParser textParser = new TextParser(FILE);
		AppointmentBook tempBook = textParser.parse();

		if(!tempBook.getOwnerName().equals(appBook.getOwnerName())){
			System.err.println("Incompatible owners.\nPlease check that the" +
					"new appointment owner is the same as the loaded file.");
			System.exit(1);
		}

		Collection<Appointment> apps = tempBook.getAppointments();
		for (Appointment a: apps)
			appBook.addAppointment(a);

		options.put("Parsed", 1);
		return appBook;
	}

	public static void writeFile(AppointmentBook appBook) throws IOException {
		TextDumper textDumper = new TextDumper(FILE);
		textDumper.dump(appBook);
	}

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

	public static void printAppt(AppointmentBook appBook){
		System.out.println(appBook);
		Collection<Appointment> apps = appBook.getAppointments();
		for (Appointment a : apps)
			System.out.println(a);
	}

	public static void printSyntaxError(String s, String x){
		System.err.println("Error in <" + s + "> argument.");
		System.err.println("<" + x + "> contains improper characters.");
	}

	public static void printErrorUsage(String s, int status){
		System.err.println(s);
		printUsage(status);
	}

	public static void printUsage(int status){
		System.err.println(USAGE);
		System.exit(status);
	}

	public static boolean printEnabled(){
		return options.get("Print")==1;
	}

	public static boolean fileEnabled(){
		return options.get("TextFile")==1;
	}

}
