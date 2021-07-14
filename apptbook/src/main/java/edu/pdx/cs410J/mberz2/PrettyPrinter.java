package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class PrettyPrinter implements AppointmentBookDumper<AppointmentBook> {

	/* Writer containing the output stream. */
	private final Writer writer;

	PrettyPrinter(Writer writer){
		this.writer = writer;
	}

	@Override
	public void dump(AppointmentBook appBook)
			throws IOException {

		writer.write("\n===============================================================================");
		writer.write("\n| Appointment Book for "+appBook.getOwnerName());

		try {
			ArrayList<Appointment> apps = appBook.getAppointments();


			for (Appointment a: apps) {
				writer.write("\n-------------------------------------------------------------------------------");

				String[] btSplit = a.getBeginTimeString().split("\\s");
				String[] etSplit = a.getEndTimeString().split("\\s");

				writer.write("\n|--------- Begin Time ----------------- End Time ------------- Duration ------|");
				writer.write("\n|\t\t"+btSplit[0] + " " + btSplit[1] + " " + btSplit[2]+"\t\t\t"
						+ etSplit[0] + " " + etSplit[1] + " " + etSplit[2]+"\t\t\t"
						+ TimeUnit.MILLISECONDS.toMinutes(a.getEndTime().getTime() - a.getBeginTime().getTime())
						+ " mins");
				writer.write("\n| "+a.getDescription());

				writer.write("\n-------------------------------------------------------------------------------");
			}

			writer.write("\n===============================================================================");
			writer.flush();
			writer.close();

		} catch (IOException e){
			System.err.println("Error: IOException in writing.");
			System.exit(1);
		}

	}

}
