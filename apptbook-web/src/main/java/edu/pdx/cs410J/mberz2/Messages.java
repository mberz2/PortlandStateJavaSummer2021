package edu.pdx.cs410J.mberz2;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
	/**
	 * Method for printing a message that outputs the number of appointments.
	 *
	 * @param count Number to print.
	 * @return String containing the message.
	 */
	@SuppressWarnings("DefaultLocale")
	public static String getAppointmentCount(int count ) {
		return String.format( "Server has %d appointments.", count );
	}

	/**
	 * Method for printing out a missing parameter message.
	 *
	 * @param parameterName Parameter that is missing.
	 * @return String containing the message.
	 */
	public static String missingRequiredParameter( String parameterName ) {
		return String.format("The required parameter \"%s\" is missing",
				parameterName);
	}

	/**
	 * Method to print out a message after deleting all.
	 *
	 * @return String containing the message.
	 */
	public static String deleteAllAppointments() {
		return "All appointments have been deleted.";
	}

	/**
	 * Method to print out an appointment's owner.
	 *
	 * @param o owner to print.
	 * @return String containing the message.
	 */
	public static String printOwner (String o) {
		return o;
	}

	/**
	 * Method to print out a formatted single appointment.
	 *
	 * @param a Appointment to print.
	 * @return String containing the formatted appointment output.
	 */
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

	/**
	 * Method to print that an appointment book was not found for an owner.
	 *
	 * @return String containing the message.
	 */
	public static String printMissingOwner() {
		return "** Error: No appointment book for this owner.";
	}

	/**
	 * Method to print that no matching appointments were found.
	 *
	 * @return String containing the message.
	 */
	public static String printNoAppointmentsFound() {
		return "** Error: No appointments found between those dates.";
	}

	/**
	 * Method to print all matching appointments between a given set of dates.
	 * Formats the dates and attempts to find matches. Matches are written to
	 * a string writer which is returned for printing.
	 *
	 * @param data Map containing all appointmentBooks.
	 * @param owner owner to search for.
	 * @param beginTime beginTime to search for.
	 * @param endTime endTime to search for.
	 * @return String containing the matching appointments.
	 */
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

				boolean include = !d.before(min) && !d.after(max);

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
