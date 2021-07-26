package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.ParserException;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
	public static String getMappingCount( int count ) {
		return String.format( "Server has %d appointments.", count );
	}

	public static String formatKeyValuePair( String key, String value ) {
		return String.format("  %s -> %s", key, value);
	}

	public static String missingRequiredParameter( String parameterName ) {
		return String.format("The required parameter \"%s\" is missing", parameterName);
	}

	public static String mappedKeyValue( String key, String value ) {
		return String.format( "Mapped %s to %s", key, value );
	}

	public static String allMappingsDeleted() {
		return "All mappings have been deleted.";
	}

	public  static String printAppointment ( Appointment aAppointment) {
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Date beginDateTime = null;
		Date endDateTime = null;

		try {
			beginDateTime = df.parse(aAppointment.getBeginTimeString());
		} catch (ParseException e) {
			System.err.println("Incorrect begin date");
		}
		try {
			endDateTime = df.parse(aAppointment.getEndTimeString());
		} catch (ParseException e) {
			System.out.println("Incorrect end date");
		}

		int duration = (int) ((endDateTime.getTime() - beginDateTime.getTime())
				/ (1000*60));

		return String.format( "Appointment with description: %s \nFrom: %s to %s \nDuration: %d\n",
				aAppointment.getDescription(), aAppointment.getBeginTimeString(),
				aAppointment.getEndTimeString(), duration);
	}

	public static String printAppointment (String description, String startTime, String endTime) {

		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		Date beginDateTime = null;
		Date endDateTime = null;

		try {
			beginDateTime = df.parse(startTime);
		} catch (ParseException e) {
			System.out.println("Incorrect begin date");
		}
		try {
			endDateTime = df.parse(endTime);
		} catch (ParseException e) {
			System.out.println("Incorrect end date");
		}

		int duration = (int) ((endDateTime.getTime() - beginDateTime.getTime())
				/ (1000*60));

		return String.format("Description: %s " +
						"\nFrom: %s to %s " +
						"\nDuration: %d minutes.\n",
				description, startTime, endTime, duration);
	}

	public static String printOwner(String owner) {
		return String.format("Owner Name: %s \n", owner);
	}
}
