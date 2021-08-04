package edu.pdx.cs410j.mberz2;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * This class extends the read-only {@link AppointmentBookDumper} class.
 * The class implements methods for dumping the contents of an AppointmentBook
 * object to a specified Writer object, set during instantiation.

 * @author Matthew Berzinskas
 * @since 2020-6-23
 * @see AppointmentBook
 */
public class PrettyPrinter implements AppointmentBookDumper<AppointmentBook> {

	/* Writer containing the output stream. */
	private final Writer writer;

	/**
	 * Default constructor, sets the instances writer to the passed argument.
	 * @param writer Argument specifying the writer stream.
	 */
	PrettyPrinter(Writer writer){
		this.writer = writer;
	}

	/**
	 * Implements a pretty writing of the passed in appointment book object to
	 * the specified writer. This can be a file, or standard out, etc. The
	 * method implements a more elegant date format than the default appointment
	 * object, prints the owner, total appointments, and then each appointment
	 * in the book. The appointment book is already sorted.
	 *
	 * @param appBook AppointmentBook object to print.
	 * @throws IOException Exception handling for problems with in/out.
	 */
	@Override
	public void dump(AppointmentBook appBook)
			throws IOException {

		SimpleDateFormat ft =
				new SimpleDateFormat("hh:mm a, E, dd MMM yyyy",
						Locale.ENGLISH);

		writer.write("==================================================" +
				"=============================");
		writer.write("\n|\tAppointment Book for: "+appBook.getOwnerName()
				+ "\t\tTotal Appointments: "+appBook.getAppointments().size());

		try {
			ArrayList<Appointment> apps = appBook.getAppointments();


			for (Appointment a: apps) {

				String bt = ft.format(a.getBeginTime());
				String et = ft.format(a.getEndTime());


				writer.write("\n-----------------------------------------" +
						"--------------------------------------");
				writer.write("\n| "+a.getDescription());
				writer.write("\n| From " + bt + " until "+ et);
				writer.write("\n| Duration: " +
						TimeUnit.MILLISECONDS.toMinutes(a.getEndTime().getTime()
								- a.getBeginTime().getTime()) + " mins");
			}

			writer.write("\n=============================================" +
					"==================================");
			writer.flush();
			writer.close();

		} catch (IOException e){
			System.err.println("Error: IOException in writing.");
			System.exit(1);
		}

	}

}
