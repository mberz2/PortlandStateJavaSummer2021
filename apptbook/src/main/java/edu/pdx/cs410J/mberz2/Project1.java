package edu.pdx.cs410J.mberz2;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

	public static final String USAGE =
			"ava -jar /apptbook/target/apptbook-2021.0.0.jar [options] <args>";
	public static final String RESOURCE =
			"src/main/resources/edu/pdx/cs410J/mberz2/";

	public static void main(String[] args) throws IOException {

		boolean readmeFlag = false;

		//Check number of arguments.
		System.out.println(args.length);
		if (args.length == 0) {
			System.err.println("Error: Missing command line arguments");
			printUsage();
			System.exit(1);
		} else if (args.length > 7) {
			System.err.println("Error: Too many command line arguments");
			printUsage();
			System.exit(1);
		}

		//Check for readme flag.
		for (String arg : args) {
			if (arg.equalsIgnoreCase(("-README"))){

				printReadme();

			}

		}
	}

	/**
	 * Method to print the contents of the README.txt file as a loaded resource
	 * from the relative-path of the resource directory. Retrieves the file
	 * and prints it via a buffered reader.
	 *
	 * @throws IOException if unable to find the requested file.
	 * @throws NullPointerException if the requested file is null.
	 */
	public static void printReadme() throws IOException {
	  InputStream readme = Project1.class.getResourceAsStream("README.txt");
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

  public static String[] parseInput(String[] args){

    boolean err = false;
    SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");


    String[] newArgs = new String[0];
    if (args.length == 7)
      //Two options flags, take from index 1-7
      newArgs = Arrays.copyOfRange(args, 1, 7);

    else if (args.length == 6)
      //One option flag, take from index 0-6
      newArgs = Arrays.copyOfRange(args, 0, 6);

    if(!(newArgs[0].matches("^[a-zA-Z\"\\s]*$"))){
      printError("owner", newArgs[0]);
      err = true;
    }

    if(!(newArgs[2].matches("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$"))){
      printError("begin time (date)", newArgs[2]);
      err = true;
    }

    if(!(newArgs[3].matches("^([01]?\\d|2[0-3]):?([0-5]\\d)$"))){
      printError("begin time (time)", newArgs[3]);
      err = true;
    }

    if(!(newArgs[4].matches("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$"))){
      printError("end time (date)", newArgs[4]);
      err = true;
    }

    if(!(newArgs[5].matches("^([01]?\\d|2[0-3]):?([0-5]\\d)$"))){
      printError("end time (time)", newArgs[5]);
      err = true;
    }

    if (err)
      printUsage();

    return newArgs;
  }

  public static void printError(String s, String x){
    System.err.println("Error in <" + s + "> argument.");
    System.err.println("<" + x + "> contains improper characters.");
  }

  public static void printUsage(){
    System.err.println(USAGE);
    System.exit(1);
  }

}