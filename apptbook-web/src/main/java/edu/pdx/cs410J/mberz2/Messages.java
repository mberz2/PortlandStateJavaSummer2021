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

	public static String missingRequiredParameter( String parameterName ) {
		return String.format("The required parameter \"%s\" is missing", parameterName);
	}

	/*
	public static String formatKeyValuePair( String key, String value ) {
		return String.format("  %s -> %s", key, value);
	}

	public static String mappedKeyValue( String key, String value ) {
		return String.format( "Mapped %s to %s", key, value );
	}

	 */

	public static String allMappingsDeleted() {
		return "All mappings have been deleted.";
	}

	public static String printOwner (String o) {
		return o;
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

	public static Map<String, String> parseMap(String content) {
		Map<String, String> map = new HashMap<>();

		String[] lines = content.split("\n");
		for (int i = 1; i < lines.length; i++) {
			String line = lines[i];
			Map.Entry<String, String> entry = parseMapEntry(line);
			assert entry != null;
			map.put(entry.getKey(), entry.getValue());
		}

		return map;
	}

	public static Map.Entry<String, String> parseMapEntry(String content) {
		Pattern pattern = Pattern.compile("\\s*(.*) : (.*)");
		Matcher matcher = pattern.matcher(content);

		if (!matcher.find()) {
			return null;
		}

		return new Map.Entry<>() {
			@Override
			public String getKey() {
				return matcher.group(1);
			}

			@Override
			public String getValue() {
				String value = matcher.group(2);
				if ("null".equals(value)) {
					value = null;
				}
				return value;
			}

			@Override
			public String setValue(String value) {
				throw new UnsupportedOperationException("This method is not implemented yet");
			}
		};
	}
}
