package edu.pdx.cs410J.mberz2;

import static edu.pdx.cs410J.mberz2.Project2.printErrorUsage;
import static edu.pdx.cs410J.mberz2.Project2.printSyntaxError;

public class StringParser {

	public static final String README =
			"java -jar /apptbook/target/apptbook-2021.0.0.jar -README";

	public StringParser(){}

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

}
