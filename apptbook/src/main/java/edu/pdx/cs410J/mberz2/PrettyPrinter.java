package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

public class PrettyPrinter implements AppointmentBookDumper<AppointmentBook> {

	/* Writer containing the output stream. */
	private final Writer writer;

	PrettyPrinter(Writer writer){
		this.writer = writer;
	}

	@Override
	public void dump(AppointmentBook appBook)
			throws IOException {

		System.out.println(appBook);

		try {
			ArrayList<Appointment> apps = appBook.getAppointments();

			for (Appointment a: apps) {
				System.out.println(a);
			}

			for (Appointment a: apps) {

				System.out.println(a.getBeginTimeString());

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
