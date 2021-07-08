package edu.pdx.cs410J.mberz2;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;
import java.io.*;
import java.util.Collection;

/**
 *
 * @param <T>
 */
public class TextDumper <T extends AbstractAppointmentBook<Appointment>>
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
		Collection<Appointment> appointments = appointment.getAppointments();

		// Reads through each appointment in the collection and writes them
		// to a string which is then written to the file.
		for (Appointment app : appointments) {
			String toFile = appointment.getOwnerName()
							+ ";" + app.getDescription()
							+ ";" + app.getBeginTime()
							+ ";" + app.getEndTime()
							+ "\n";
			fileWriter.write(toFile);
		}
		fileWriter.close();
	}
}