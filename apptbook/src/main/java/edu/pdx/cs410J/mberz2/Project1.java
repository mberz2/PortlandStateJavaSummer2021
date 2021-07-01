package edu.pdx.cs410J.mberz2;
import java.io.*;
import java.util.Arrays;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

	public static final String USAGE =
			"Usage: java -jar /apptbook/target/apptbook-2021.0.0.jar " +
					"[options] <args>";
	public static final String README =
			"java -jar /apptbook/target/apptbook-2021.0.0jar -README";

	public static void main(String[] args) throws IOException {

		checkInput(args);
		String [] newArgs = parseInput(args);
	}

	/**
	 * Method to print the contents of the README.txt file as a loaded resource
	 * from the relative-path of the resource directory. Retrieves the file
	 * and prints it via a buffered reader. Exits normally after the print.
	 *
	 * @throws IOException if unable to find the requested file.
	 * @throws NullPointerException if the requested file is null.
	 */
	public static void printReadme() throws IOException {
	  InputStream readme =
			  Project1.class.getResourceAsStream("README.txt");
	  if (readme != null) {
		  BufferedReader br = new BufferedReader(new InputStreamReader(readme));
		  String line;
		  while ((line = br.readLine()) != null)
			  System.out.println(line);
		  System.exit(0);
	  }

	  throw new NullPointerException
			  ("No README.txt file found.");
	}

	/**
	 * Method to check the correct number of arguments. The program handles the
	 * following situations.
	 * <ul>
	 *     <li>0 arguments: No command line arguments, print usage.</li>
	 *     <li>Greater than 8: The maximum number of arguments would be
	 *     six inputs and two flags, more than 8, prints usage and exits.</li>
	 * </ul>
	 * @param args Array of command line arguments.
	 */
	public static void checkInput(String [] args){

		//Check for correct number of arguments.
		System.out.println(args.length);
		if (args.length == 0) {
			System.err.println("Error: Missing command line arguments");
			printUsage();
			System.exit(1);
		} else if (args.length > 8) {
			System.err.println("Error: Too many command line arguments");
			printUsage();
			System.exit(1);
		}
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
	 * @throws IOException if call to {@code printReadme} throws exception
	 */
	public static String[] parseInput(String[] args) throws IOException {

		boolean print = false;

	    //Check for readme flag.
	    for (String arg : args) {
	        System.out.println("1");
		    if (arg.startsWith("-")) {
			    if (arg.equalsIgnoreCase(("-README")))
				    printReadme();
			    if (arg.equalsIgnoreCase(("-PRINT")))
				    print = true;
			    else
				    System.err.println(
						    "Error: " + arg + " is not a correct option");
		    }
	    }

	    if (args.length == 7 && print) {

		    boolean err = false;
	    	String [] newArgs = Arrays.copyOfRange(args, 1, 7);
	    	String alphaPat = "^[a-zA-Z\"\\s]*$";
	    	String datePat = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
	    	String timePat = "^([01]?\\d|2[0-3]):?([0-5]\\d)$";

		    if(!(newArgs[0].matches(alphaPat))){
			    printError("owner", newArgs[0]);
			    err = true;
		    }

		    if(!(newArgs[2].matches(datePat))){
			    printError("begin time (date)", newArgs[2]);
			    err = true;
		    }

		    if(!(newArgs[3].matches(timePat))){
			    printError("begin time (time)", newArgs[3]);
			    err = true;
		    }

		    if(!(newArgs[4].matches(datePat))){
			    printError("end time (date)", newArgs[4]);
			    err = true;
		    }

		    if(!(newArgs[5].matches(timePat))){
			    printError("end time (time)", newArgs[5]);
			    err = true;
		    }

		    if (err){
		        System.err.println(
		                "Run with -README to see proper formatting.");
		        System.err.println(README);
		        System.exit(1);
		    }

	    return newArgs;
    }

    printUsage();
    return null;
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