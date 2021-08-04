package edu.pdx.cs410j.mberz2;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * This class implements read-only class {@link AppointmentBookDumper}. This
 * class contains methods for dumping the contents of an appointmentBook object
 * into a given writer.
 *
 *  @author Matthew Berzinskas
 *  @since 2020-6-23
 *  @see AppointmentBook
 */
public class TextDumper implements AppointmentBookDumper<AppointmentBook> {

	/* Writer containing the output stream. */
	private final Writer writer;

	/**
	 * Default constructor.
	 *
	 * @param writer Writer object containing the output stream.
	 */
	public TextDumper(Writer writer) {
		this.writer = writer;
	}

	/**
	 * Method for dumping the contents of an appointmentBook object to a given
	 * output stream/writer. The method loops through each appointment in a
	 * book, formats the output with delimiters, and then writes them to the
	 * writer data member of the object.
	 */
	@Override
	public void dump(AppointmentBook appBook) {

		try {
			ArrayList<Appointment> apps = appBook.getAppointments();

			for (Appointment a: apps) {

				String[] btSplit = a.getBeginTimeString().split("\\s");
				String[] etSplit = a.getEndTimeString().split("\\s");

				writer.write(appBook.getOwnerName() + "|"
						+ a.getDescription()
						+ "|" + btSplit[0] + "|" + btSplit[1] + "|" + btSplit[2]
						+ "|" + etSplit[0] + "|" + etSplit[1] + "|" + etSplit[2]
						+ "\n");
			}

			writer.flush();
			writer.close();

		} catch (IOException e){
			System.err.println("Error: IOException in writing.");
			System.exit(1);
		}

	}
}