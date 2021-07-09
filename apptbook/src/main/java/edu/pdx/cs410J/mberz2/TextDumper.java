package edu.pdx.cs410J.mberz2;


import edu.pdx.cs410J.AppointmentBookDumper;
import java.io.*;
import java.util.Collection;

/**
 *
 */
public class TextDumper implements AppointmentBookDumper<AppointmentBook> {

	private final String fileName;

	/**
	 *
	 * @param fileName
	 */
	public TextDumper(String fileName) {
		this.fileName = fileName;
	}

	/**
	 *
	 * @param appointment
	 * @throws IOException
	 */
	@Override
	public void dump(AppointmentBook appointment) throws IOException {
		File file = new File(fileName);
		FileWriter fileWriter = new FileWriter(file, false);

		Collection<Appointment> apps = appointment.getAppointments();
		//System.out.println(apps);

		for (Appointment a: apps) {

			String[] btSplit = a.getBeginTimeString().split("\\s");
			String[] etSplit = a.getBeginTimeString().split("\\s");

			String toFile = appointment.getOwnerName()
					+ "|" + a.getDescription()
					+ "|" + btSplit[0] + "|" + btSplit[1]
					+ "|" + etSplit[0] + "|" + etSplit[1]
					+ "\n";

			fileWriter.write(toFile);
		}
		fileWriter.close();
	}
}