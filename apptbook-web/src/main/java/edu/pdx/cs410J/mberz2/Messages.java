package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.ParserException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for formatting messages on the server side.  Parses/prints a variety
 * of messages from the server, including counts, missing parameters, deletions,
 * printing of appointments, owners, etc.
 *
 * @author Matthew Berzinskas
 * @since 2020-6-23
 * @see Appointment
 * @see AppointmentBook
 */
public class Messages
{
	@SuppressWarnings("DefaultLocale")
	public static String getAppointmentCount(int count ) {
		return String.format( "Server has %d appointments.", count );
	}

	public static String missingRequiredParameter( String parameterName ) {
		return String.format("The required parameter \"%s\" is missing",
				parameterName);
	}

	public static String deleteAllAppointments() {
		return "All appointments have been deleted.";
	}

	public static String printOwner (String o) {
		return o;
	}

	public static String printAppointment (Appointment a) {

		SimpleDateFormat ft =
				new SimpleDateFormat("hh:mm a, E, dd MMM yyyy",
						Locale.ENGLISH);
		String bt = ft.format(a.getBeginTime());
		String et = ft.format(a.getEndTime());

		return "\n-----------------------------------------" +
				"--------------------------------------" +
				"\n| " + a.getDescription() + "\n| From " + bt + " until " + et
				+ "\n| Duration: "
				+ TimeUnit.MILLISECONDS.toMinutes(a.getEndTime().getTime()
				- a.getBeginTime().getTime()) + " mins";
	}

	public static String printMissingOwner() {
		return "** Error: No appointment book for this owner.";
	}

	public static String printNoAppointmentsFound() {
		return "** Error: No appointments found between those dates.";
	}

	public static String findAppointments(Map<String, AppointmentBook> data,
	                                      String owner, String beginTime,
	                                      String endTime) {
		StringWriter output = new StringWriter();
		try {
			SimpleDateFormat format =
					new SimpleDateFormat("MM/dd/yyyy hh:mm a",
							Locale.ENGLISH);
			Date min = format.parse(beginTime);
			Date max = format.parse(endTime);
			boolean found = false;

			AppointmentBook temp = new AppointmentBook();
			ArrayList<Appointment> appList
					= data.get(owner).getAppointments();
			for (Appointment app : appList) {

				Date d = app.getBeginTime();

				boolean include = d.after(min) && d.before(max);
				System.out.println(include);

				if (include) {
					temp.setOwnerName(owner);
					temp.addAppointment(app);
					found = true;
				}
			}

			if (found) {
				PrettyPrinter printer = new PrettyPrinter(output);
				printer.dump(temp);
				return String.valueOf(output);

			} else {
				return printNoAppointmentsFound();
			}
		} catch (ParseException | IOException e) {
			return "** Error: Parse exception while searching";
		}
	}
}
