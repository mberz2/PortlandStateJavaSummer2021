package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.ParserException;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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

	public static String printOwner (String o) {
		return "Owner Name: "+o;
	}

	public static String printAppointment (Appointment a) {

		SimpleDateFormat ft = new SimpleDateFormat("hh:mm a, E, dd MMM yyyy");
		String bt = ft.format(a.getBeginTime());
		String et = ft.format(a.getEndTime());

		return "\n-----------------------------------------" +
				"--------------------------------------" +
				"\n| " + a.getDescription() + "\n| From " + bt + " until " + et
				+ "\n| Duration: "
				+ TimeUnit.MILLISECONDS.toMinutes(a.getEndTime().getTime()
				- a.getBeginTime().getTime()) + " mins";
	}
}
