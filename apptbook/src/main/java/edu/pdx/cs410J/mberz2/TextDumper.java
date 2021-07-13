package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.AppointmentBookDumper;
import java.io.*;
import java.util.Collection;
import java.nio.file.*;


/**
 * This class implements read-only class {@link AppointmentBookDumper}. This
 * class contains methods for dumping the contents of a Writer object into a
 * given path.
 */
public class TextDumper implements AppointmentBookDumper<AppointmentBook> {

	private final Writer writer;
	//private String fileName;

	public TextDumper(Writer writer) {
		this.writer = writer;
	}

	/*
	public void setFileName(String f){
		this.fileName = f;
	}
	 */

	/**
	 *
	 * @throws IOException
	 */
	@Override
	public void dump(AppointmentBook appBook) throws IOException {

		try {
			Collection<Appointment> apps = appBook.getAppointments();
			for (Appointment a: apps) {
				String[] btSplit = a.getBeginTimeString().split("\\s");
				String[] etSplit = a.getBeginTimeString().split("\\s");

				writer.write(appBook.getOwnerName() + "|"
						+ a.getDescription()
						+ "|" + btSplit[0] + "|" + btSplit[1]
						+ "|" + etSplit[0] + "|" + etSplit[1]
						+ "\n");
			}

			writer.flush();
			writer.close();

		} catch (InvalidPathException e){
			System.err.println("Error: Invalid character in path.");
			System.exit(1);
		} catch (NoSuchFileException e){
			System.err.println("Error: No such directory.");
			System.exit(1);
		}

	}
}