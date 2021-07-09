package edu.pdx.cs410J.mberz2;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;
import java.io.*;
import java.util.Collection;

/**
 *
 * @param <T>
 */
public class TextDumper <T extends AbstractAppointmentBook<AbstractAppointment>>
		implements AppointmentBookDumper<T> {

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
	public void dump(T appointment) throws IOException {
		File file = new File(fileName);
		FileWriter fileWriter = new FileWriter(file, false);
		Collection<AbstractAppointment> appointments = appointment.getAppointments();

		Collection<AbstractAppointment> apps = appointment.getAppointments();
		//System.out.println(apps);

		for (AbstractAppointment a: apps) {

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