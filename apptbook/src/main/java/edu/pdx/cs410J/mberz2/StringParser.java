package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.AbstractAppointment;

/**
 * Class implements methods for validating syntax of command line arguments
 * using regex. If the syntax is invalid, it prints an error message with the
 * the problematic argument. Meant to ease parsing/validation of command line
 * arguments and file-based arguments for appointment book creation.
 *
 * @author Matthew Berzinskas
 * @since 2020-6-23
 * @see Appointment
 * @see AbstractAppointment
 */
public class StringParser {

	/* Default constructor */
	public StringParser(){}

	/**
	 * Method for validating an array of strings against simple regex.
	 *
	 * @param args String arguments to validate.
	 * @return Boolean value based on whether or not an error was encountered.
	 */
	public boolean validateString(String [] args){

		boolean err = false;

		String datePat = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
		String timePat = "^([01]?\\d|2[0-3]):?([0-5]\\d)$";

		if(args.length != 6){
			return false;
		}

		if (!(args[2].matches(datePat))) {
			printSyntaxError("begin time (date) [from file]", args[2]);
			err = true;
		}

		if (!(args[3].matches(timePat))) {
			printSyntaxError("begin time (time) [from file]", args[3]);
			err = true;
		}

		if (!(args[4].matches(datePat))) {
			printSyntaxError("end time (date) [from file]", args[4]);
			err = true;
		}

		if (!(args[5].matches(timePat))) {
			printSyntaxError("end time (time) [from file]", args[5]);
			err = true;
		}

		return !err;
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

}
