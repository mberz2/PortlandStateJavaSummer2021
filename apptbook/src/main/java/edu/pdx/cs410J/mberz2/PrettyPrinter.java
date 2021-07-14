package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
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

		SimpleDateFormat ft = new SimpleDateFormat("hh:mm a, E, dd MMM yyyy");

		writer.write("\n===============================================================================");
		writer.write("\n|    Appointment Book for: "+appBook.getOwnerName());

		try {
			ArrayList<Appointment> apps = appBook.getAppointments();


			for (Appointment a: apps) {

				String bt = ft.format(a.getBeginTime());
				String et = ft.format(a.getEndTime());


				writer.write("\n-------------------------------------------------------------------------------");
				writer.write("\n| "+a.getDescription());
				writer.write("\n| Beginning " + bt + " until "+ et);
				writer.write("\n| Duration: "+ TimeUnit.MILLISECONDS.toMinutes(a.getEndTime().getTime() - a.getBeginTime().getTime())
						+ " mins");


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
