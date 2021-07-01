package edu.pdx.cs410J.mberz2;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

	public static final String USAGE =
			"ava -jar /apptbook/target/apptbook-2021.0.0.jar [options] <args>";
	public static final String RESOURCE =
			"src/main/resources/edu/pdx/cs410J/mberz2/";

	public static void main(String[] args) throws ParseException, IOException {


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

				File readme = new File("classpath:/README.txt");

				try (BufferedReader br = new BufferedReader(new FileReader(readme))) {
					String line;
					while ((line = br.readLine()) != null) {
						System.out.println(line);
					}
				}

			}

		}



		//String[] newArgs = parseInput(args);
    /*
    Appointment app = new Appointment(newArgs[1], newArgs[2]+" "+newArgs[3], newArgs[4]+" "+newArgs[5]);

    AppointmentBook<AbstractAppointment> appBook = new AppointmentBook<>(newArgs[0], app);

    System.out.println(appBook.getOwnerName());

    System.out.println(app.getDescription());
    System.out.println(app.getBeginTimeString());
    System.out.println(app.getEndTimeString());

    System.exit(0);
    */
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